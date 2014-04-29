package com.jayqqaa12.reader.model;

import java.util.ArrayList;
import java.util.List;

public class Group<T>
{
	public int id;
	public String tag;
	public int count;
	public List<T> chilren =new ArrayList<T>();
	
	
}
