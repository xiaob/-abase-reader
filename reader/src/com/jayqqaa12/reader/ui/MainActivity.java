package com.jayqqaa12.reader.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.GridView;

import com.igexin.sdk.PushManager;
import com.jayqqaa12.abase.core.ADao;
import com.jayqqaa12.abase.core.activity.AAdapter;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.db.Book;
import com.jayqqaa12.reader.ui.adapter.itemview.BookItemView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity
{
	@ViewById
	GridView gv;
	AAdapter<Book> adapter;

	@Bean
	ADao db;

	@AfterViews
	public void init()
	{
		// start push service
		PushManager.getInstance().initialize(this);

		adapter = new AAdapter<Book>(BookItemView.class, this);
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
