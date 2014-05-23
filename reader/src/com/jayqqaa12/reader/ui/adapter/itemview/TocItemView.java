package com.jayqqaa12.reader.ui.adapter.itemview;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.TextView;

import com.jayqqaa12.abase.core.ItemView;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.db.Toc;

@EViewGroup(R.layout.lv_toc)
public class TocItemView extends ItemView<Toc>
{
	@ViewById
	TextView lv_tv;

	public TocItemView(Context context)
	{
		super(context);
	}

	@Override
	public void bind(Toc item)
	{
		lv_tv.setText(item.title);
	}

}
