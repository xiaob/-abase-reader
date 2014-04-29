package com.jayqqaa12.reader.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.jayqqaa12.reader.model.BookFile;
import com.jayqqaa12.reader.model.Group;
import com.jayqqaa12.reader.ui.adapter.itemview.FileGroupItemView;
import com.jayqqaa12.reader.ui.adapter.itemview.FileGroupItemView_;
import com.jayqqaa12.reader.ui.adapter.itemview.FileItemView;
import com.jayqqaa12.reader.ui.adapter.itemview.FileItemView_;

@EBean
public class FileAdapter extends BaseExpandableListAdapter
{
	@RootContext
	Context context;
	
	private List<Group<BookFile>> groups=new ArrayList<Group<BookFile>>();

	public void setData(List<Group<BookFile>> groups)
	{
		this.groups = groups;
		
		this.notifyDataSetChanged();
	}
 
	@Override
	public int getGroupCount()
	{
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return getGroup(groupPosition).chilren.size();
	}


	@Override
	public Group<BookFile> getGroup(int groupPosition)
	{
		return groups.get(groupPosition);
	}


	@Override
	public BookFile getChild(int groupPosition, int childPosition)
	{
		return getGroup(groupPosition).chilren.get(childPosition);
	}


	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}


	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}


	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		FileGroupItemView view;
		if (convertView == null) view = FileGroupItemView_.build(context);
		else view = (FileGroupItemView) convertView;
		view.bind(getGroup(groupPosition));
		
		return view;
		
	}


	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		FileItemView view;
		if (convertView == null) view = FileItemView_.build(context);
		else view = (FileItemView) convertView;

		view.bind(getChild(groupPosition, childPosition));

		return view;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	
}





