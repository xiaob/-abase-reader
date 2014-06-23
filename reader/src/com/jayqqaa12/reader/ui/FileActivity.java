package com.jayqqaa12.reader.ui;

import java.io.File;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayqqaa12.abase.core.ADao;
import com.jayqqaa12.abase.core.activity.AExpandableListAdapter;
import com.jayqqaa12.abase.kit.IntentKit;
import com.jayqqaa12.abase.model.Group;
import  com.jayqqaa12.abase.kit.common.*;
import com.jayqqaa12.reader.App;
import com.jayqqaa12.reader.BaseActivity;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.engine.FileEngine;
import com.jayqqaa12.reader.model.BookFile;
import com.jayqqaa12.reader.model.db.Book;
import com.jayqqaa12.reader.ui.adapter.itemview.FileGroupItemView;
import com.jayqqaa12.reader.ui.adapter.itemview.FileItemView;
import com.jayqqaa12.reader.ui.view.dialog.DeleteBookDialog;
import com.lidroid.xutils.db.sqlite.Selector;

@EActivity(R.layout.activity_file)
public class FileActivity extends BaseActivity implements OnChildClickListener, OnItemLongClickListener
{
	@ViewById
	ExpandableListView elv;
	@ViewById
	TextView tv;
	@ViewById
	ImageView iv_empty;
	@Bean
	FileEngine engine;

	@Bean
	ADao db;
	private String parentFile;

	AExpandableListAdapter<BookFile> adapter;

	@AfterViews
	public void init()
	{
		adapter = new AExpandableListAdapter<BookFile>(FileGroupItemView.class, FileItemView.class, this);

		elv.setAdapter(adapter);
		elv.setGroupIndicator(null);
		setFileView(Environment.getExternalStorageDirectory().getPath());
		initHeadView(R.string.input_book);
		iv_head_right.setVisibility(View.GONE);
		elv.setOnChildClickListener(this);
		elv.setOnItemLongClickListener(this);
	}

	public void setFileView(String filePath)
	{
		parentFile = new File(filePath).getParent();
		tv.setText(filePath);
		List<Group<BookFile>> list = engine.initFileList(filePath);
		adapter.setData(list);
		for (int i = 0; i < list.size(); i++)
		{
			elv.expandGroup(i);
		}
		if (list.size() == 0) iv_empty.setVisibility(View.VISIBLE);
		else iv_empty.setVisibility(View.GONE);

	}

	@Click(value = { R.id.rl_back })
	public void onClick(View view)
	{

		switch (view.getId())
		{
		case R.id.rl_back:
			if (parentFile == null || parentFile.equals("/")) finish();
			else setFileView(parentFile);
			break;
		}

	}

	@Override
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.keep_x, R.anim.activity_right_out);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		setFileView(tv.getText().toString());
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
	{
		BookFile file = adapter.getChild(groupPosition, childPosition);
		final File f = new File(file.path);
		if (f.canRead())
		{
			if (f.isDirectory()) setFileView(file.path);
			else
			{
				Book book = new Book(f);
				Book oldBook = db.findFirst(Selector.from(Book.class).where("path", "=", book.path));
				if (oldBook == null) book = db.saveAndFind(book, Selector.from(Book.class).where("path", "=", book.path));

				App.openBook(this, book);
			}
		}
		else T.ShortToast("对不起，您的访问权限不足!");

		return true;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
		{
			long packedPos = elv.getExpandableListPosition(position);
			int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
			int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
			BookFile file = adapter.getChild(groupPosition, childPosition);

			if (new File(file.path).isDirectory()) ;// TODO
			else IntentKit.startSubIntent(this, DeleteBookDialog.class, new String[] { "file", "MSG" }, new Object[] { file,
					DeleteBookDialog.DELETE_FILE });
			return true;
		}

		return false;
	}
}
