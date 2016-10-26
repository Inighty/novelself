package novel.web.service;

import org.springframework.stereotype.Service;

import novel.storage.Processor;
import novel.storage.Impl.BXWXNovelStorageImpl;
import novel.storage.Impl.KSZNovelStorageImpl;

@Service
public class SpiderService {

	public void crawl() throws Exception {
		Processor processor = new KSZNovelStorageImpl();
		processor.process();
		processor = new BXWXNovelStorageImpl();
		processor.process();
	}
}
