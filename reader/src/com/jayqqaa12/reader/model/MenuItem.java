package com.jayqqaa12.reader.model;


public class MenuItem
{
	public static final int ATION_SETTING =1;
	public static final int ATION_PROGRESS =2;
	public static final int ATION_NIGHT =3;
	public static final int ATION_FONT_ADD =4;
	public static final int ATION_FONT_DIM =5;
	public static final int ATION_TOC =6;
	
	
	
	public int icon;
	public String text;
	public int ation;
	
	
	public MenuItem(int icon,String text ,int ation){
		
		this.icon=icon;
		this.text=text;
		this.ation=ation;
	}

}
