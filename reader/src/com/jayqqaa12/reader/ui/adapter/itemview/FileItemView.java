package com.jayqqaa12.reader.ui.adapter.itemview;

import java.io.File;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayqqaa12.abase.core.adapter.ItemView;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.model.BookFile;

@EViewGroup(R.layout.lv_file)
public class FileItemView extends ItemView<BookFile>
{
	@ViewById
	ImageView lv_iv;
	@ViewById
	TextView lv_tv;

	public FileItemView(Context context)
	{
		super(context);
	}

	public void bind(BookFile file)
	{
		
		
		File mFile = new File(file.path);

		String fileName = mFile.getName();
		lv_tv.setText(fileName);
		if (mFile.isDirectory()) lv_iv.setImageResource(R.drawable.file_type_folder);
		else
		{
			String fileEnds = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();// 取出文件后缀名并转成小写
			if (fileEnds.equals("txt")) lv_iv.setImageResource(R.drawable.file_type_txt);
			else if (fileEnds.equals("epub")) lv_iv.setImageResource(R.drawable.file_type_epub);
			else lv_iv.setImageResource(R.drawable.others);
		}
		
		
	}

	
	
}
