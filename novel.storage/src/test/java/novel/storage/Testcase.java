package novel.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import novel.storage.Impl.NovelStorageImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class Testcase {
	@Test
	public void testMybatisConnection() throws FileNotFoundException {
		SqlSession session = new SqlSessionFactoryBuilder().build(new FileInputStream("conf/SqlMapConfig.xml"))
				.openSession();
		System.out.println(session);
	}
	
	@Test
	public void testProcessor() throws Exception{
		Processor processor = new NovelStorageImpl();
		processor.process();
	}
	
	@Test
	public void testProcessorBXWX() throws Exception{
		Processor processor = new NovelStorageImpl();
		processor.process();
	}
}
