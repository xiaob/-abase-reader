package com.jayqqaa12.reader.ui.view.dialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jayqqaa12.abase.core.ADao;
import com.jayqqaa12.abase.kit.io.FileKit;
import com.jayqqaa12.reader.App;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.BookFile;
import com.jayqqaa12.reader.model.db.Book;
import com.jayqqaa12.reader.model.db.Toc;
import com.lidroid.xutils.db.sqlite.WhereBuilder;

@EActivity(R.layout.activity_dialog_delete_book)
public class DeleteBookDialog extends Activity
{
	public static final int DELETE_BOOK_AND_FILE = 1;
	public static final int DELETE_BOOK = 2;
	public static final int DELETE_FILE = 3;

	@Bean
	ADao db;

	@ViewById
	CheckBox cb;
	@ViewById
	TextView tv;
	Book book;

	@Extra
	BookFile file;

	@Extra
	int MSG;

	@AfterViews
	public void init()
	{
		book = App.getObject("delete_book");
		switch (MSG)
		{
		case DELETE_BOOK:
			cb.setVisibility(View.GONE);
			tv.setText(R.string.delete_book);
			break;

		case DELETE_FILE:
			cb.setVisibility(View.GONE);
			tv.setText(R.string.delete_file);
			break;
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		App.removeObject("delete_book");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		finish();
		return true;
	}

	@Click(value = { R.id.tv_delete, R.id.tv_cancel })
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_delete:

			switch (MSG)
			{
			case DELETE_BOOK_AND_FILE:
				if (cb.isChecked()) FileKit.deleteFile(book.path);
			case DELETE_BOOK:
				db.delete(book);
				db.delete(Toc.class, WhereBuilder.b("book_id", "=", book.id));
				break;
			case DELETE_FILE:
				FileKit.deleteFile(file.path);
				break;
			}
			break;

		case R.id.tv_cancel:
			break;
		}
		finish();
	}

}
