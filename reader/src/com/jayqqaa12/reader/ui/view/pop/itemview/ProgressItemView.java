package com.jayqqaa12.reader.ui.view.pop.itemview;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.text.view.ZLTextView;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;

import com.jayqqaa12.abase.core.BindView;
import com.jayqqaa12.reader.R;

@EViewGroup(R.layout.pop_progress)
public class ProgressItemView extends BindView
{
	@ViewById
	SeekBar sb;
	FBReaderApp fbreaderApp = (FBReaderApp) FBReaderApp.Instance();

	public ProgressItemView(Context context)
	{
		super(context);
	}

	@Override
	public void bind()
	{
		
		final ZLTextView.PagePosition page = fbreaderApp.getTextView().pagePosition();
		

		if (sb.getMax() != page.Total - 1 || sb.getProgress() != page.Current - 1)
		{
			sb.setMax(page.Total - 1);
			sb.setProgress(page.Current - 1);
		}
		
	 
	}

	@SeekBarProgressChange(R.id.sb)
	public void onSeekBarProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		
		if (fromUser)
		{
			final int page = progress + 1;
			final ZLTextView view = fbreaderApp.getTextView();
			if (page == 1) view.gotoHome();
			view.gotoPage(page);
			fbreaderApp.getViewWidget().reset();
			fbreaderApp.getViewWidget().repaint();
		}
	}

	@Click({ R.id.iv_left, R.id.iv_right })
	public void click(View view)
	{

		switch (view.getId())
		{
		case R.id.iv_left:

			break;

		case R.id.iv_right:

			break;
		}

	}

}
