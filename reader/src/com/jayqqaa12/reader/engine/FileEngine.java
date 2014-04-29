package com.jayqqaa12.reader.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.androidannotations.annotations.EBean;

import com.jayqqaa12.abase.util.common.T;
import com.jayqqaa12.reader.model.BookFile;
import com.jayqqaa12.reader.model.Group;

@EBean
public class FileEngine
{
	private static final List<String> FILE_END = Arrays.asList(new String[] { "txt", "html", "epub", "oeb", "fb2", "mobi", "rtf" });

	public String mCurrentFilePath = "";

	public List<Group<BookFile>> initFileList(String filePath)
	{
		Set<String> tags = new HashSet<String>();
		List<Group<BookFile>> groups = new ArrayList<Group<BookFile>>();

		mCurrentFilePath = filePath;
		File[] files = new File(filePath).listFiles();
		String tag = null;
		if (files == null)
		{
			T.ShortToast("sd 卡不可用");
			return groups;
		}
		for (File file : files)
		{
			if(file.isHidden()||!file.canRead())continue;
			if (file.isDirectory()) tag = "文件夹";
			else tag = "可阅读的书";
			if (!tags.contains(tag))
			{
				Group<BookFile> g = new Group<BookFile>();
				g.tag = tag;
				addData(tag, file, g);
				if (g.chilren.size() > 0)
				{
					groups.add(g);
					tags.add(tag);
				}
			}
			else
			{
				for (Group<BookFile> g : groups)
				{
					if (g.tag.equals(tag))
					{
						addData(tag, file, g);
					}
				}
			}
		}

		return groups;
	}

	private Group<BookFile> addData(String tag, File file, Group<BookFile> g)
	{

		if (tag.equals("文件夹")) g.chilren.add(new BookFile(file.getName(), file.getPath()));
		else
		{
			String fileEnd = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
			if (FILE_END.contains(fileEnd)) g.chilren.add(new BookFile(file.getName(), file.getPath(), fileEnd));
		}
		return g;
	}

}
