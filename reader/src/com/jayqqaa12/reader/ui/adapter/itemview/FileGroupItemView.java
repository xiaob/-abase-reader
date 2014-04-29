package com.jayqqaa12.reader.ui.adapter.itemview;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.TextView;

import com.jayqqaa12.abase.core.adapter.ItemView;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.BookFile;
import com.jayqqaa12.reader.model.Group;

@EViewGroup(R.layout.elv_file)
public class FileGroupItemView extends ItemView<Group<BookFile>>
{
	
	@ViewById
	TextView elv_tv;
	

	public FileGroupItemView(Context context)
	{
		super(context);
	}


	public void bind(Group<BookFile> group)
	{
		elv_tv.setText(group.tag);
		
		
	}
	
	

}
