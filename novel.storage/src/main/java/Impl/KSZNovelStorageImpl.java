package Impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import novel.crawler.entity.Book;
import novel.crawler.enums.Web;
import novel.crawler.factory.SpiderFactory;
import novel.storage.Processor;

public class KSZNovelStorageImpl implements Processor {
	private static final Map<String, String> Tasks = new TreeMap<>();
	private SqlSessionFactory sqlSessionFactory;

	public KSZNovelStorageImpl() throws Exception {
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("conf/SqlMapConfig.xml"));
	}

	static {
		Tasks.put("A", "http://www.kanshuzhong.com/map/A/1/");
		Tasks.put("B", "http://www.kanshuzhong.com/map/B/1/");
		Tasks.put("C", "http://www.kanshuzhong.com/map/C/1/");
		Tasks.put("D", "http://www.kanshuzhong.com/map/D/1/");
		Tasks.put("E", "http://www.kanshuzhong.com/map/E/1/");
		Tasks.put("F", "http://www.kanshuzhong.com/map/F/1/");
		Tasks.put("G", "http://www.kanshuzhong.com/map/G/1/");
		Tasks.put("H", "http://www.kanshuzhong.com/map/H/1/");
		Tasks.put("I", "http://www.kanshuzhong.com/map/I/1/");
		Tasks.put("J", "http://www.kanshuzhong.com/map/J/1/");
		Tasks.put("K", "http://www.kanshuzhong.com/map/K/1/");
		Tasks.put("L", "http://www.kanshuzhong.com/map/L/1/");
		Tasks.put("M", "http://www.kanshuzhong.com/map/M/1/");
		Tasks.put("N", "http://www.kanshuzhong.com/map/N/1/");
		Tasks.put("O", "http://www.kanshuzhong.com/map/O/1/");
		Tasks.put("P", "http://www.kanshuzhong.com/map/P/1/");
		Tasks.put("Q", "http://www.kanshuzhong.com/map/Q/1/");
		Tasks.put("R", "http://www.kanshuzhong.com/map/R/1/");
		Tasks.put("S", "http://www.kanshuzhong.com/map/S/1/");
		Tasks.put("T", "http://www.kanshuzhong.com/map/T/1/");
		Tasks.put("U", "http://www.kanshuzhong.com/map/U/1/");
		Tasks.put("V", "http://www.kanshuzhong.com/map/V/1/");
		Tasks.put("W", "http://www.kanshuzhong.com/map/W/1/");
		Tasks.put("X", "http://www.kanshuzhong.com/map/X/1/");
		Tasks.put("Y", "http://www.kanshuzhong.com/map/Y/1/");
		Tasks.put("Z", "http://www.kanshuzhong.com/map/Z/1/");

	}

	@Override
	public void process() {
		// FIXME Auto-generated method stub
		ExecutorService service = Executors.newFixedThreadPool(Tasks.size());
		List<Future<String>> futrues = new ArrayList<>(Tasks.size());

		for (Entry<String, String> entry : Tasks.entrySet()) {
			String key = entry.getKey();
			final String vale = entry.getValue();
			futrues.add(service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					// FIXME Auto-generated method stub
					Iterator<List<Book>> iterator = SpiderFactory.SpiderGenerate(Web.KSZ.toString()).iterator(vale, 10);
					while (iterator.hasNext()) {
						List<Book> books = iterator.next();
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

		for (Future<String> future : futrues) {
			try {
				System.out.println("×¥È¡[" + future.get() + "]½áÊø!");
			} catch (InterruptedException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
