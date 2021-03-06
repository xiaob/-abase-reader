//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.jayqqaa12.reader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.jayqqaa12.abase.core.ADao_;
import com.jayqqaa12.reader.R.id;
import com.jayqqaa12.reader.R.layout;
import com.jayqqaa12.reader.engine.FileEngine_;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class FileActivity_
    extends FileActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_file);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        engine = FileEngine_.getInstance_(this);
        db = ADao_.getInstance_(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static FileActivity_.IntentBuilder_ intent(Context context) {
        return new FileActivity_.IntentBuilder_(context);
    }

    public static FileActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new FileActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        iv_head_left = ((ImageView) hasViews.findViewById(id.iv_head_left));
        iv_head_right = ((ImageView) hasViews.findViewById(id.iv_head_right));
        tv_head_logo = ((TextView) hasViews.findViewById(id.tv_head_logo));
        tv_head = ((TextView) hasViews.findViewById(id.tv_head));
        tv = ((TextView) hasViews.findViewById(id.tv));
        iv_empty = ((ImageView) hasViews.findViewById(id.iv_empty));
        elv = ((ExpandableListView) hasViews.findViewById(id.elv));
        {
            View view = hasViews.findViewById(id.iv_head_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        FileActivity_.this.iv_head_leftClicked(view);
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.rl_back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        FileActivity_.this.onClick(view);
                    }

                }
                );
            }
        }
        init();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, FileActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, FileActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public FileActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (context_ instanceof Activity) {
                    ((Activity) context_).startActivityForResult(intent_, requestCode);
                } else {
                    context_.startActivity(intent_);
                }
            }
        }

    }

}
