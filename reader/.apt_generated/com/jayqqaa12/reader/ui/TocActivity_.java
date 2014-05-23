//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.jayqqaa12.reader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jayqqaa12.reader.R.layout;
import com.jayqqaa12.reader.engine.TocEngine_;
import com.jayqqaa12.reader.model.db.Toc;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class TocActivity_
    extends TocActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_toc);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        engine = TocEngine_.getInstance_(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, android.view.ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static TocActivity_.IntentBuilder_ intent(Context context) {
        return new TocActivity_.IntentBuilder_(context);
    }

    public static TocActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new TocActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tv_head_logo = ((TextView) hasViews.findViewById(com.jayqqaa12.reader.R.id.tv_head_logo));
        tv_head = ((TextView) hasViews.findViewById(com.jayqqaa12.reader.R.id.tv_head));
        iv_head_right = ((ImageView) hasViews.findViewById(com.jayqqaa12.reader.R.id.iv_head_right));
        iv_head_left = ((ImageView) hasViews.findViewById(com.jayqqaa12.reader.R.id.iv_head_left));
        pb = ((ProgressBar) hasViews.findViewById(com.jayqqaa12.reader.R.id.pb));
        lv = ((ListView) hasViews.findViewById(com.jayqqaa12.reader.R.id.lv));
        {
            View view = hasViews.findViewById(com.jayqqaa12.reader.R.id.iv_head_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TocActivity_.this.iv_head_leftClicked(view);
                    }

                }
                );
            }
        }
        {
            AdapterView<?> view = ((AdapterView<?> ) hasViews.findViewById(com.jayqqaa12.reader.R.id.lv));
            if (view!= null) {
                view.setOnItemClickListener(new OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TocActivity_.this.lvItemClicked(((Toc) parent.getAdapter().getItem(position)));
                    }

                }
                );
            }
        }
        init();
    }

    @Override
    public void onLoadStatus(final int what, final Object obj) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                TocActivity_.super.onLoadStatus(what, obj);
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, TocActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, TocActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public TocActivity_.IntentBuilder_ flags(int flags) {
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
