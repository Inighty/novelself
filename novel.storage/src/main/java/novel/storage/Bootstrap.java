package novel.storage;

import novel.storage.Impl.BXWXNovelStorageImpl;
import novel.storage.Impl.KSZNovelStorageImpl;

public class Bootstrap {

	public static void main(String[] args) throws Exception {
		// FIXME Auto-generated method stub
		Processor processor = new KSZNovelStorageImpl();
		processor.process();
		
		processor = new BXWXNovelStorageImpl();
		processor.process();
	}

}
