package novel.storage;

import java.text.SimpleDateFormat;
import java.util.Date;

import novel.storage.Impl.BXWXNovelStorageImpl;
import novel.storage.Impl.KSZNovelStorageImpl;

public class Bootstrap {
	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		Processor processor = new KSZNovelStorageImpl();
		processor.process();

		processor = new BXWXNovelStorageImpl();
		processor.process();
	}

}
