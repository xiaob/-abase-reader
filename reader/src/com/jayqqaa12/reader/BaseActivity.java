package com.jayqqaa12.reader;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity
public class BaseActivity extends Activity
{
	@ViewById
	protected TextView tv_head,  tv_head_logo;

	@ViewById
	protected ImageView iv_head_left, iv_head_right;

	public void initHeadView(int title)
	{

		tv_head_logo.setVisibility(View.GONE);
		tv_head.setVisibility(View.VISIBLE);
		tv_head.setText(title);
		iv_head_left.setImageResource(R.drawable.return_button);

	}

	@Click
	public void iv_head_leftClicked(View v)
	{
		finish();
	}

}
