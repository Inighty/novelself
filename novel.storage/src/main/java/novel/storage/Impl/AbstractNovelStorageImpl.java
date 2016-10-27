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
//		String result = IOUtils.toString(classLoader.getResourceAsStream("rule.xml"), "utf8");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(classLoader.getResourceAsStream("SqlMapConfig.xml"));
//		sqlSessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("/SqlMapConfig.xml"));
	}

	public void process() {
		// FIXME Auto-generated method stub
		ExecutorService service = Executors.newFixedThreadPool(Tasks.size());
		List<Future<String>> futrues = new ArrayList<>(Tasks.size());

		for (Entry<String, String> entry : Tasks.entrySet()) {
			String key = entry.getKey();
			final String value = entry.getValue();
			futrues.add(service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					// FIXME Auto-generated method stub
					Iterator<List<Book>> iterator = SpiderFactory.SpiderGenerate(value).iterator(value,
							10);
					while (iterator.hasNext()) {
						List<Book> books = iterator.next();
						if (books.size() == 0) {
							continue;
						}
						SqlSession session = sqlSessionFactory.openSession();
						session.insert("batchInsert", books);
						session.commit();
						session.close();
						Thread.sleep(1_000);
					}
					return key;
				}

			}));
		}
		service.shutdown();

//		for (Future<String> future : futrues) {
//			try {
//				System.out.println("×¥È¡[" + future.get() + "]½áÊø!");
//			} catch (InterruptedException e) {
//				// FIXME Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// FIXME Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}
}
