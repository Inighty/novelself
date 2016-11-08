package novel.storage;

import novel.storage.Impl.BXWXNovelStorageImpl;
import novel.storage.Impl.HHNovelStorageImpl;
import novel.storage.Impl.KSZNovelStorageImpl;
import novel.storage.Impl.LTNovelStorageImpl;

public class Bootstrap {
	public static void main(String[] args) throws Exception {
		Processor processor = new KSZNovelStorageImpl();
		processor.process();
		processor = new HHNovelStorageImpl();
		processor.process();
		processor = new BXWXNovelStorageImpl();
		processor.process();
		processor = new LTNovelStorageImpl();
		processor.process();

		// processor = new DDNovelStorageImpl();
		// processor.process();
	}

}
