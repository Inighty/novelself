package novel.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import novel.storage.Impl.BXWXNovelStorageImpl;
import novel.storage.Impl.KSZNovelStorageImpl;

public class Testcase {
	@Test
	public void testMybatisConnection() throws FileNotFoundException {
		SqlSession session = new SqlSessionFactoryBuilder().build(new FileInputStream("conf/SqlMapConfig.xml"))
				.openSession();
		System.out.println(session);
	}
	
	@Test
	public void testProcessor() throws Exception{
		Processor processor = new KSZNovelStorageImpl();
		processor.process();
	}
	
	@Test
	public void testProcessorBXWX() throws Exception{
		Processor processor = new BXWXNovelStorageImpl();
		processor.process();
	}
}