package com.novelspider.crawler;

import com.novelspider.entity.Book;
import com.novelspider.enums.Type;

/**
 * @author Administrator
 * 笔趣阁
 * @param <T>
 */
public class BQGspider extends Abstractspider {

	@Override
	public Object analyzeHTMLByString(Type type,String html) {
			switch(type){
				case booklist:
					
					break;
				case chapterlist:
					break;
				case content:
					break;
			}
			System.out.println();

		// TODO Auto-generated method stub
		return null;
	}

}
