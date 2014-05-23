package com.jayqqaa12.reader.ui.adapter.itemview;

import java.io.File;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.jayqqaa12.abase.core.ItemView;
import com.jayqqaa12.abase.kit.IntentKit;
import com.jayqqaa12.reader.App;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.db.Book;
import com.jayqqaa12.reader.ui.FileActivity;
import com.jayqqaa12.reader.ui.view.dialog.DeleteBookDialog;

@EViewGroup(R.layout.gv_item)
public class BookItemView extends ItemView<Book>
{
	@ViewById
	Button gv_bt;

	public BookItemView(Context context)
	{
		super(context);
	}

	@AfterInject
	public void init()
	{}

	public void bind(final Book book)
	{
		gv_bt.setText(book.name);
		if (book.iconUrl == null) gv_bt.setBackgroundResource(book.getIconRes());
		else ; // TODO
		if ("new".equals(book.type))
		{
			gv_bt.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					IntentKit.startSubIntent(getContext(), FileActivity.class);
					((Activity) getContext()).overridePendingTransition(R.anim.activity_right_in, R.anim.keep_x);
				}
			});
		}
		else
		{
			gv_bt.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					App.setObject("delete_book", book);
					if (new File(book.path).exists()) App.openBook(getContext(), book);
					else IntentKit.startSubIntent(getContext(), DeleteBookDialog.class, new String[] {  "MSG" }, new Object[] { 
							DeleteBookDialog.DELETE_BOOK });

				}
			});
			gv_bt.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					App.setObject("delete_book", book);
					IntentKit.startSubIntent(getContext(), DeleteBookDialog.class, new String[] { "MSG" }, new Object[] { 
							DeleteBookDialog.DELETE_BOOK_AND_FILE });
					return true;
				}
			});
		}

	}
}
