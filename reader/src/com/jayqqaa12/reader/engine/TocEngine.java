package com.jayqqaa12.reader.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.geometerplus.zlibrary.text.model.ZLTextModel;

import com.jayqqaa12.abase.core.ADao;
import com.jayqqaa12.abase.core.listener.OnLoadStatusListener;
import com.jayqqaa12.abase.kit.MsgKit;
import com.jayqqaa12.abase.kit.Txt;
import com.jayqqaa12.abase.kit.common.L;
import com.jayqqaa12.reader.App;
import com.jayqqaa12.reader.model.db.Toc;

@EBean
public class TocEngine
{
	@Bean
	ADao dao;
	
	
	private static Pattern PN = Pattern.compile("第{1}[0-9一二三四五六七八九十百千]+[章回卷节集]{1}");
	private static Set<String> keys = new HashSet<String>();
	private static final char[] pattern = "第".toCharArray();
	private static Matcher m;

	public static List<Toc> tocList = new ArrayList<Toc>();
	

	public static boolean find(int index, char[] text, int offset, int length)
	{
		int count = 0;
		int pos = 0;
		for (pos = find(text, offset, length, 0); pos != -1; pos = find(text, offset, length, pos + 1))
		{
			count++;
			if (count > 0) break;
		}
		if (count > 0)
		{
			String title = new String(Arrays.copyOfRange(text, offset + pos, offset + length));
			m = PN.matcher(title);
			if (m.find())
			{
				if (title.contains("　")) title = Txt.split(title, "　")[0];
				else if (title.contains("】")) title = Txt.split(title, "】")[0];
				return addToc(title, index, offset + pos);
			}
		}

		return false;
	}

	private static boolean addToc(String title, int index, int pos)
	{
		String keyAll = m.group(0);
		String key = keyAll;
		if (m.find()) keyAll += m.group(0);

		StringBuffer sb = new StringBuffer();

		if (title.length() > 30)
		{
			int start = title.indexOf(key);
			title = title.substring(start, title.length());
			if (title.length() > 30) return false;
			else pos += start;
		}

		if (keyAll.startsWith("第0"))
		{
			sb.append("第").append(keyAll.substring(2, keyAll.length()));
			keyAll = sb.toString();
		}

		if (!keys.contains(keyAll))
		{
			keys.add(keyAll);
			// if (title.contains("\\s") )title =title.replaceAll("\\s", "");
			// L.i("key = " + keyAll + " find toc =" + title );
			tocList.add(new Toc(title, index, pos, App.getNowReadBook()));

			return true;
		}
		return false;
	}

	public static int find(char[] text, int offset, int length, int pos)
	{
		if (pos < 0) pos = 0;

		final int patternLength = pattern.length;
		final int last = offset + length - patternLength;
		final char firstChar = pattern[0];
		for (int i = offset + pos; i <= last; i++)
		{
			if (text[i] == firstChar)
			{
				int j = 1;
				for (int k = i + 1; j < patternLength; ++j, ++k)
				{
					if (pattern[j] != text[k])
					{
						break;
					}
				}
				if (j >= patternLength) { return i - offset; }
			}
		}
		return -1;
	}

	@Background
	public void readToc(ZLTextModel zlTextModel, OnLoadStatusListener callback)
	{
		L.i("start read toc ");
		long start = System.currentTimeMillis();
		int count = zlTextModel.serachTop();
		long end = System.currentTimeMillis();
		L.i(" end time =" + (end - start) + "s" + " find search =" + count);
		callback.onLoadStatus(null,MsgKit.MSG_LOAD);
		if (tocList.size() > 0) dao.saveAll(tocList);

	}

	public static void clearCache()
	{
		tocList.clear();
		keys.clear();
	}

}
