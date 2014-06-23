package com.jayqqaa12.reader;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.zlibrary.core.sqliteconfig.ZLSQLiteConfig;
import org.geometerplus.zlibrary.ui.android.image.ZLAndroidImageManager;
import org.geometerplus.zlibrary.ui.android.library.ZLAndroidLibrary;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.jayqqaa12.abase.core.AApp;
import com.jayqqaa12.abase.kit.IntentKit;
import com.jayqqaa12.abase.kit.sys.ReceiverKit;
import com.jayqqaa12.reader.model.db.Book;

public class App extends AApp
{
	/**
	 * 当前页面的 Paragraph index
	 */
	public static int index;

	@Override
	public void onCreate()
	{
		super.onCreate();

		new ZLSQLiteConfig(this);
		new ZLAndroidImageManager();
		new ZLAndroidLibrary(this);

	}
	
	public static void openBook(Context context,Book book){
		setObject("book", book);
		IntentKit.startSubIntent(context, FBReader.class, new String[] { FBReader.BOOK_KEY, "goto_position" },
				new Object[] { book.path, true });
	}
	
	public static Book getNowReadBook(){
		return getObject("book");
	}



}
