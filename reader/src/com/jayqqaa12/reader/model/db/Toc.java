package com.jayqqaa12.reader.model.db;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.sqlite.ForeignLazyLoader;



public class Toc implements Serializable
{
	public int id;
	public String title;
	public int postion;
	public int offset;
	
	@Foreign(column = "book_id", foreign = "id" )
    public ForeignLazyLoader<Book> book;
	
	public Toc(){
		
	}
	
	public Toc(String title ,int index ,int pos ,Book book)
	{
		this.title=title;
		this.postion=index;
		this.offset=pos;
		this.book= new ForeignLazyLoader<Book>(Toc.class, "book_id", book.id);
	}
	
	
	
	

}
