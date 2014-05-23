package com.jayqqaa12.reader.ui.view.pop;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.ColorProfile;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.core.resources.ZLResource;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.jayqqaa12.abase.core.APopup;
import com.jayqqaa12.abase.core.Abase;
import com.jayqqaa12.abase.core.activity.AAdapter;
import com.jayqqaa12.abase.kit.IntentKit;
import com.jayqqaa12.abase.kit.ManageKit;
import com.jayqqaa12.reader.App;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.MenuItem;
import com.jayqqaa12.reader.ui.TocActivity;
import com.jayqqaa12.reader.ui.adapter.itemview.MenuItemView;

@EBean
public class MenuPop extends APopup implements OnItemClickListener
{
	@RootContext
	Context context;
	

	@Override
	protected View initView()
	{
		View view = ManageKit.getInflater().inflate(R.layout.pop_menu, null);
		GridView gv = (GridView) view.findViewById(R.id.gv);
		gv.setAdapter(new AAdapter<MenuItem>(MenuItemView.class, Abase.getContext(), addMenuItem()));
		this.setAnimationStyle(R.style.AnimBottom);
		gv.setOnItemClickListener(this);
		return view;
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		final FBReaderApp fbreaderApp = (FBReaderApp) FBReaderApp.Instance();

		switch (((MenuItem) parent.getAdapter().getItem(position)).ation)
		{
		case MenuItem.ATION_TOC:
			this.dismiss();
			if (fbreaderApp.Model.TOCTree.hasChildren() && App.getNowReadBook().isEpub()) fbreaderApp.runAction(ActionCode.SHOW_TOC);
			else  {
				IntentKit.startSubIntent(context, TocActivity.class);
				((Activity)context).overridePendingTransition(R.anim.activity_bottom_in, R.anim.keep_y);
			}
			break;
		case MenuItem.ATION_SETTING:
			this.dismiss();
			fbreaderApp.runAction(ActionCode.SHOW_PREFERENCES);
			break;
		case MenuItem.ATION_FONT_ADD:
			fbreaderApp.runAction(ActionCode.INCREASE_FONT);
			break;
		case MenuItem.ATION_FONT_DIM:
			fbreaderApp.runAction(ActionCode.DECREASE_FONT);
			break;
		case MenuItem.ATION_NIGHT:
			if (ColorProfile.DAY.equals(fbreaderApp.getColorProfileName())) fbreaderApp.runAction(ActionCode.SWITCH_TO_NIGHT_PROFILE);
			else fbreaderApp.runAction(ActionCode.SWITCH_TO_DAY_PROFILE);
			break;
		case MenuItem.ATION_PROGRESS:
			this.dismiss();
			fbreaderApp.runAction(ActionCode.SHOW_NAVIGATION);
			
			break;
		}
	}

	private List<MenuItem> addMenuItem()
	{

		List<MenuItem> data = new ArrayList<MenuItem>();
		data.add(new MenuItem(R.drawable.icon_item_directory, ZLResource.resource("menu").getResource(ActionCode.SHOW_TOC).getValue(),
				MenuItem.ATION_TOC));
		data.add(new MenuItem(R.drawable.icon_item_bright, ZLResource.resource("menu").getResource(ActionCode.SWITCH_TO_NIGHT_PROFILE)
				.getValue(), MenuItem.ATION_NIGHT));
		data.add(new MenuItem(R.drawable.icon_bookshelf_set_up, ZLResource.resource("menu").getResource(ActionCode.SHOW_PREFERENCES)
				.getValue(), MenuItem.ATION_SETTING));
		data.add(new MenuItem(R.drawable.icon_btn_font_big, ZLResource.resource("menu").getResource(ActionCode.INCREASE_FONT).getValue(),
				MenuItem.ATION_FONT_ADD));
		data.add(new MenuItem(R.drawable.icon_btn_font_small, ZLResource.resource("menu").getResource(ActionCode.DECREASE_FONT).getValue(),
				MenuItem.ATION_FONT_DIM));
		data.add(new MenuItem(R.drawable.icon_item_progress,
				ZLResource.resource("menu").getResource(ActionCode.SHOW_NAVIGATION).getValue(), MenuItem.ATION_PROGRESS));

		return data;
	}

	


}
