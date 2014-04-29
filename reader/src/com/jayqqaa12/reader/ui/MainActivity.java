package com.jayqqaa12.reader.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.GridView;

import com.jayqqaa12.abase.core.AbaseDao;
import com.jayqqaa12.abase.core.adapter.AbaseBaseAdapter;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.db.Book;
import com.jayqqaa12.reader.ui.adapter.itemview.BookItemView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity
{
	@ViewById
	GridView gv;
	AbaseBaseAdapter<Book> adapter;
	
	private AbaseDao db = AbaseDao.create();

	@AfterViews
	public void init()
	{
		adapter = new AbaseBaseAdapter<Book>(BookItemView.class,this);
		gv.setAdapter(adapter);
		setData();
	}
	

	@Override
	protected void onResume()
	{
		super.onStart();
		setData();
	}

	private void setData()
	{
		if (adapter != null)
		{
			adapter.data = db.findAll(Book.class);
			Book book2 = new Book();
			book2.type = "new";
			adapter.data.add(book2);
			adapter.notifyDataSetChanged();
		}
	}

}
