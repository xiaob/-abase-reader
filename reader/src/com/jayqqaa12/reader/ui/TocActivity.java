package com.jayqqaa12.reader.ui;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.geometerplus.fbreader.fbreader.FBReaderApp;

import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jayqqaa12.abase.core.activity.AAdapter;
import com.jayqqaa12.abase.core.listener.OnLoadStatusListener;
import com.jayqqaa12.abase.kit.MsgKit;
import com.jayqqaa12.reader.App;
import com.jayqqaa12.reader.BaseActivity;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.engine.TocEngine;
import com.jayqqaa12.reader.model.db.Toc;
import com.jayqqaa12.reader.ui.adapter.itemview.TocItemView;

@Fullscreen
@EActivity(R.layout.activity_toc)
public class TocActivity extends BaseActivity implements OnLoadStatusListener
{
	@ViewById
	ListView lv;
	@ViewById
	ProgressBar pb;
	@Bean
	TocEngine engine;

	AAdapter<Toc> adapter;
	final FBReaderApp fbreaderApp = (FBReaderApp) FBReaderApp.Instance();

	@AfterViews
	public void init()
	{
		adapter = new AAdapter<Toc>(TocItemView.class, this);
		tv_head.setText(App.getNowReadBook().name);
		lv.setAdapter(adapter);
		List<Toc> data = App.getNowReadBook().tocList.load();
		if (data.size() > 0) setData(data);
		else if (TocEngine.tocList.size() > 0) setData(TocEngine.tocList);
		else engine.readToc(fbreaderApp.Model.getTextModel(), this);

	}

	private void setData(List<Toc> data)
	{
		adapter.setData(data);
		pb.setVisibility(View.GONE);
		setSelection(data);
	}

	private void setSelection(List<Toc> data)
	{
		int i = 0;
		for (Toc t : data)
		{
			if (t.postion == App.index || Math.abs(App.index - t.postion) <= 100)
			{
				lv.setSelection(i);
				break;
			}
			i++;
		}
	}

	@UiThread
	public void onLoadStatus(Object obj, int status)
	{
		switch (status)
		{
		case MsgKit.MSG_LOAD:
			setData(TocEngine.tocList);
			break;
		}
	}
	
 

	@ItemClick
	public void lvItemClicked(Toc item)
	{
		fbreaderApp.BookTextView.gotoPosition(item.postion, 0, item.offset);
		finish();
	}

	@Override
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.keep_y, R.anim.activity_bottom_out);
	}



}
