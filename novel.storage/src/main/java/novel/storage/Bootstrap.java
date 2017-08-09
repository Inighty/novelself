package novel.storage;

import novel.storage.Impl.NovelStorageImpl;

public class Bootstrap {
	public static void main(String[] args) throws Exception {
		while (true) {
			Processor processor = new NovelStorageImpl();
			processor.process();
		}
		// processor = new DDNovelStorageImpl();
		// processor.process();
	}

}
