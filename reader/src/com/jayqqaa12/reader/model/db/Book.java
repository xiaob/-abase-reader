package com.jayqqaa12.reader.model.db;

import java.io.File;
import java.io.Serializable;

import com.jayqqaa12.abase.kit.Txt;
import com.jayqqaa12.reader.R;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.sqlite.FinderLazyLoader;

public class Book implements Serializable
{

	public int id;
	public String name ;
	public String path ;
	public String iconUrl;
	public String icon;
	public String type;

	@Finder(valueColumn = "id", targetColumn = "book_id" ) 
	public FinderLazyLoader<Toc> tocList;
	
	public Book(){
	}

	public Book(File f)
	{
		this.path =f.getPath();
		this.name=f.getName();
		if (name.contains(".")){
			String [] names=Txt.split(name, ".") ;
			type=names[1];
			name=names[0];
		}
		
	}
	

	public int getIconRes()
	{
		if( type.toLowerCase().equals("txt")) return R.drawable.cover_txt;
		else if( type.toLowerCase().equals("epub")) return R.drawable.cover_epub;
		else if( type.toLowerCase().equals("ebk")) return R.drawable.cover_ebk;
		else if( type.toLowerCase().equals("pdf")) return R.drawable.cover_pdf;
		else if( type.toLowerCase().equals("umd")) return R.drawable.cover_umd;
		else if( type.toLowerCase().equals("new")) return R.drawable.cover_net;
		else return R.drawable.cover_default_new;
		
	}

	public boolean isEpub()
	{
		return  type.toLowerCase().equals("epub");
	}

	
	

}
