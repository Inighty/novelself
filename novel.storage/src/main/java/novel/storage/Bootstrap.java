package novel.storage;

import novel.storage.Impl.NovelStorageImpl;

public class Bootstrap {
	public static void main(String[] args) throws Exception {
		Processor processor = new NovelStorageImpl();

		while (true) {
			processor.process();
			Thread.sleep(1000 * 1800);
		}
		// processor = new DDNovelStorageImpl();
		// processor.process();
	}

}
