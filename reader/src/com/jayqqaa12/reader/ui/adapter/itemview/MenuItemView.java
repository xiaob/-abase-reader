package com.jayqqaa12.reader.ui.adapter.itemview;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayqqaa12.abase.core.ItemView;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.MenuItem;

@EViewGroup(R.layout.gv_item_menu)
public class MenuItemView extends ItemView<MenuItem>
{
	@ViewById
	TextView m_tv;
	@ViewById
	ImageView m_iv;

	public MenuItemView(Context context)
	{
		super(context);
	}

	@Override
	public void bind(MenuItem item)
	{
		m_tv.setText(item.text);
		m_iv.setImageResource(item.icon);
		
	}

}
