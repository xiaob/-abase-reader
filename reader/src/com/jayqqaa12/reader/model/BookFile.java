package com.jayqqaa12.reader.model;

import java.io.Serializable;

public class BookFile implements Serializable
{

	public String name;
	public String ext;
	public String path;

	public BookFile(String name, String path)
	{
		this.name = name;
		this.path = path;

	}

	public BookFile(String name, String path, String ext)
	{
		this(name, path);
		this.ext = ext;

	}

}
