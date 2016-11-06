package novel.storage.Impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import novel.crawler.entity.Book;
import novel.crawler.factory.SpiderFactory;
import novel.storage.Processor;

public abstract class AbstractNovelStorageImpl implements Processor {
	protected Map<String, String> Tasks = new TreeMap<>();
	protected SqlSessionFactory sqlSessionFactory;

	public AbstractNovelStorageImpl() throws Exception {

		ClassLoader classLoader = getClass().getClassLoader();
		// String result =
		// IOUtils.toString(classLoader.getResourceAsStream("rule.xml"),
		// "utf8");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(classLoader.getResourceAsStream("SqlMapConfig.xml"));
		// sqlSessionFactory = new SqlSessionFactoryBuilder().build(new
		// FileInputStream("conf/SqlMapConfig.xml"));
		// FIXME Auto-generated method stub

	}

	public void process() {

		ExecutorService service = Executors.newFixedThreadPool(Tasks.size());
		List<Future<String>> futrues = new ArrayList<>(Tasks.size());

		for (Entry<String, String> entry : Tasks.entrySet()) {
			String key = entry.getKey();
			final String value = entry.getValue();

			futrues.add(service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					// FIXME Auto-generated method stub
					Iterator<List<Book>> iterator = SpiderFactory.SpiderGenerate(value).iterator(key, value, 10);
					while (iterator.hasNext()) {
						List<Book> books = iterator.next();
						if (value.contains("23wx")) {
							books.stream().forEach(e -> e.setType(key));
						}
						if (books.size() == 0) {
							continue;
						}

						SqlSession session = sqlSessionFactory.openSession();
						try {
							session.insert("batchInsert", books);
						} catch (Exception e) {
							// FIXME: handle exception
							throw new RuntimeException(e.toString());
						}

						finally {
							// TODO: handle finally clause
							session.commit();
							session.close();
						}
						Thread.sleep(1_000);
					}
					return key;
				}
			}));
		}
		service.shutdown();
		// process();
		// //// ≈–∂œ÷¥––Ω· ¯√ª
		// try {
		// if (service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
		// Thread.sleep(1_000);
		// process();
		// }
		// } catch (InterruptedException e) {
		// // FIXME Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
