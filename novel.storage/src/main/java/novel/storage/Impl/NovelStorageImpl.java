package novel.storage.Impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.*;

import novel.crawler.enums.Type;
import novel.crawler.spider.Spider;
import novel.crawler.util.Tool;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import novel.crawler.entity.Book;
import novel.storage.Processor;
import org.dom4j.Element;

public class NovelStorageImpl implements Processor {
	Map<String, String> Tasks = new TreeMap<>();
	private SqlSessionFactory sqlSessionFactory;
	Tool tool;

	public NovelStorageImpl() throws Exception {

		ClassLoader classLoader = getClass().getClassLoader();
		// String result =
		// IOUtils.toString(classLoader.getResourceAsStream("rule.xml"),
		// "utf8");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(classLoader.getResourceAsStream("SqlMapConfig.xml"));
		// sqlSessionFactory = new SqlSessionFactoryBuilder().build(new
		// FileInputStream("conf/SqlMapConfig.xml"));

		tool = new Tool();
	}

	public void process() {

		for (Entry<String, Map<String, Element>> entry : tool.ruleMap.entrySet()) {
			String site = entry.getKey();
			if (!tool.Valid.contains(site)) {
				continue;
			}
			String taskStr = entry.getValue().get("novel-book-list").getTextTrim();
			for (String classify : taskStr.split(";")) {
				String[] item = classify.split(",");
				Tasks.put(item[0], item[1]);
			}
		}

		ExecutorService service = Executors.newFixedThreadPool(Tasks.size());
		List<Future<String>> futures = new ArrayList<>(Tasks.size());

		for (Entry<String, String> entry : Tasks.entrySet()) {
			String key = entry.getKey();
			final String value = entry.getValue();

			futures.add(service.submit(() -> {
				Iterator<List<Book>> iterator = new Spider(value, tool, Type.booklist).iterator(key, value);
				while (iterator.hasNext()) {
					List<Book> books = iterator.next();
					if (value.contains("23wx")) {
						books.forEach(e -> e.setType(key));
					}
					if (books.size() == 0) {
						continue;
					}

					SqlSession session = sqlSessionFactory.openSession();
					try {
						session.insert("batchInsert", books);
					} catch (Exception e) {
						throw new RuntimeException(e.toString());
					} finally {
						session.commit();
						session.close();
					}
					Thread.sleep(1_000);
				}
				return key;
			}));
		}
		service.shutdown();
		// process();
		//// ִ�н���
		try {
			for (Future<String> item : futures) {
				item.get();
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
