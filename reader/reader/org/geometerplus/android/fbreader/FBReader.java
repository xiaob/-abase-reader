package org.geometerplus.android.fbreader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;
import org.geometerplus.android.fbreader.api.ApiListener;
import org.geometerplus.android.fbreader.api.ApiServerImplementation;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.android.util.UIUtil;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.Bookmark;
import org.geometerplus.fbreader.book.SerializerUtil;
import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.fbreader.options.CancelMenuHelper;
import org.geometerplus.zlibrary.core.application.ZLApplicationWindow;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.core.view.ZLViewWidget;
import org.geometerplus.zlibrary.text.view.ZLTextView;
import org.geometerplus.zlibrary.ui.android.error.ErrorKeys;
import org.geometerplus.zlibrary.ui.android.library.UncaughtExceptionHandler;
import org.geometerplus.zlibrary.ui.android.library.ZLAndroidLibrary;
import org.geometerplus.zlibrary.ui.android.view.AndroidFontUtil;
import org.geometerplus.zlibrary.ui.android.view.ZLAndroidWidget;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jayqqaa12.abase.core.APopup;
import com.jayqqaa12.reader.R;
import com.jayqqaa12.reader.ui.view.pop.MenuPop;
import com.jayqqaa12.reader.ui.view.pop.itemview.ProgressItemView;

/***
 * Intent intent = new Intent(this, FBReader.class);
 * intent.putExtra(FBReader.BOOK_KEY ,path); intent.putExtra("goto_position",
 * false); startActivity(intent);
 * 
 * @author 12
 * 
 */
@NoTitle
@Fullscreen
@EActivity(R.layout.activity_fb)
public class FBReader extends Activity implements ZLApplicationWindow
{

	public static final String ACTION_OPEN_BOOK = "android.fbreader.action.VIEW";
	public static final String BOOK_KEY = "fbreader.book";
	public static final String BOOKMARK_KEY = "fbreader.bookmark";

	static final int ACTION_BAR_COLOR = Color.DKGRAY;

	public static final int REQUEST_PREFERENCES = 1;
	public static final int REQUEST_CANCEL_MENU = 2;

	public static final int RESULT_DO_NOTHING = RESULT_FIRST_USER;
	public static final int RESULT_REPAINT = RESULT_FIRST_USER + 1;

	private FBReaderApp myFBReaderApp;
	private volatile Book myBook;
	private int myFullScreenFlag;

	@ViewById
	RelativeLayout root_view;
	@ViewById
	ZLAndroidWidget main_view;

	@Bean
	MenuPop menu;

	APopup progressPop;

	@AfterViews
	protected void init()
	{
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
		main_view.setFBActivity(this);
		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		getZLibrary().setActivity(this);

		myFBReaderApp = (FBReaderApp) FBReaderApp.Instance();
		if (myFBReaderApp == null) myFBReaderApp = new FBReaderApp(new BookCollectionShadow());

		getCollection().bindToService(this, null);
		myBook = null;
		myFBReaderApp.setWindow(this);
		myFBReaderApp.initWindow();
		
		progressPop = new APopup(ProgressItemView.class);
		
		myFullScreenFlag = getZLibrary().ShowStatusBarOption.getValue() ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;

		if (myFBReaderApp.getPopupById(TextSearchPopup.ID) == null) new TextSearchPopup(myFBReaderApp);
		if (myFBReaderApp.getPopupById(SelectionPopup.ID) == null) new SelectionPopup(myFBReaderApp);

		myFBReaderApp.addAction(ActionCode.SHOW_PREFERENCES, new ShowPreferencesAction(this, myFBReaderApp));
		myFBReaderApp.addAction(ActionCode.SHOW_TOC, new ShowTOCAction(this, myFBReaderApp));
		myFBReaderApp.addAction(ActionCode.SHOW_NAVIGATION, new NavigationAction(this, myFBReaderApp));
		// myFBReaderApp.addAction(ActionCode.SELECTION_COPY_TO_CLIPBOARD, new
		// SelectionCopyAction(this, myFBReaderApp));
		myFBReaderApp.addAction(ActionCode.SELECTION_TRANSLATE, new SelectionTranslateAction(this, myFBReaderApp));
		// myFBReaderApp.addAction(ActionCode.SHOW_CANCEL_MENU, new
		// ShowCancelMenuAction(this, myFBReaderApp));
	}

	public static void openBookActivity(Context context, Book book, Bookmark bookmark)
	{
		context.startActivity(new Intent(context, FBReader_.class).setAction(ACTION_OPEN_BOOK)
				.putExtra(BOOK_KEY, SerializerUtil.serialize(book)).putExtra(BOOKMARK_KEY, SerializerUtil.serialize(bookmark))
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}

	private static ZLAndroidLibrary getZLibrary()
	{
		return (ZLAndroidLibrary) ZLAndroidLibrary.Instance();
	}

	private synchronized void openBook(Intent intent, Runnable action, boolean force)
	{
		if (!force && myBook != null) { return; }
		myBook = SerializerUtil.deserializeBook(intent.getStringExtra(BOOKMARK_KEY));
		final Bookmark bookmark = SerializerUtil.deserializeBookmark(intent.getStringExtra(BOOKMARK_KEY));

		String fromIntentBookKey = intent.getStringExtra(BOOK_KEY);
		if (!TextUtils.isEmpty(fromIntentBookKey))
		{
			myBook = createBookForFile(ZLFile.createFileByPath(fromIntentBookKey));
		}
		else
		{
			if (myBook == null)
			{
				final Uri data = intent.getData();
				if (data != null)
				{
					myBook = createBookForFile(ZLFile.createFileByPath(data.getPath()));
				}
			}
		}
		boolean gotoPosition = getIntent().getBooleanExtra("goto_position", true);
		myFBReaderApp.openBook(myBook, bookmark, action, gotoPosition);
	}

	private Book createBookForFile(ZLFile file)
	{
		if (file == null) { return null; }
		Book book = myFBReaderApp.Collection.getBookByFile(file);
		if (book != null) { return book; }
		if (file.isArchive())
		{
			for (ZLFile child : file.children())
			{
				book = myFBReaderApp.Collection.getBookByFile(child);
				if (book != null) { return book; }
			}
		}
		return null;
	}

	private Runnable getPostponedInitAction()
	{
		return new Runnable()
		{
			public void run()
			{
				runOnUiThread(new Runnable()
				{
					public void run()
					{
						DictionaryUtil.init(FBReader.this, null);
					}
				});
			}
		};
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu m)
	{
		super.onMenuOpened(featureId, m);

		if (!this.menu.isShowing()) this.menu.showAtLocation(findViewById(R.id.root_view), Gravity.BOTTOM, 0, 0);
		return menu.isShowing();
	}

	public void openBookFromRequest(Intent intent)
	{
		openBook(intent, null, true);
	}

	@Override
	protected void onNewIntent(final Intent intent)
	{
		final String action = intent.getAction();
		final Uri data = intent.getData();

		if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0)
		{
			super.onNewIntent(intent);
		}
		else if (Intent.ACTION_VIEW.equals(action) && data != null && "fbreader-action".equals(data.getScheme()))
		{
			myFBReaderApp.runAction(data.getEncodedSchemeSpecificPart(), data.getFragment());
		}
		else if (Intent.ACTION_VIEW.equals(action) || ACTION_OPEN_BOOK.equals(action))
		{
			getCollection().bindToService(this, new Runnable()
			{
				public void run()
				{
					openBook(intent, null, true);
				}
			});
		}
		// else if (Intent.ACTION_SEARCH.equals(action))
		// {
		// final String pattern = intent.getStringExtra(SearchManager.QUERY);
		// final Runnable runnable = new Runnable()
		// {
		// public void run()
		// {
		// final TextSearchPopup popup = (TextSearchPopup)
		// myFBReaderApp.getPopupById(TextSearchPopup.ID);
		// popup.initPosition();
		// myFBReaderApp.TextSearchPatternOption.setValue(pattern);
		// if (myFBReaderApp.getTextView().search(pattern, true, false, false,
		// false) != 0)
		// {
		// runOnUiThread(new Runnable()
		// {
		// public void run()
		// {
		// myFBReaderApp.showPopup(popup.getId());
		// }
		// });
		// }
		// else
		// {
		// runOnUiThread(new Runnable()
		// {
		// public void run()
		// {
		// UIUtil.showErrorMessage(FBReader.this, "textNotFound");
		// popup.StartPosition = null;
		// }
		// });
		// }
		// }
		// };
		// UIUtil.wait("search", runnable, this);
		// }
		else
		{
			super.onNewIntent(intent);
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		getCollection().bindToService(this, new Runnable()
		{
			public void run()
			{
				new Thread()
				{
					public void run()
					{
						openBook(getIntent(), getPostponedInitAction(), false);
						myFBReaderApp.getViewWidget().repaint();
					}
				}.start();

				myFBReaderApp.getViewWidget().repaint();
			}
		});
		final ZLAndroidLibrary zlibrary = getZLibrary();

		final int fullScreenFlag = zlibrary.ShowStatusBarOption.getValue() ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
		if (fullScreenFlag != myFullScreenFlag)
		{
			finish();
			startActivity(new Intent(this, getClass()));
		}

		((PopupPanel) myFBReaderApp.getPopupById(TextSearchPopup.ID)).setPanelInfo(this, root_view);
		((PopupPanel) myFBReaderApp.getPopupById(SelectionPopup.ID)).setPanelInfo(this, root_view);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		switchWakeLock(hasFocus && getZLibrary().BatteryLevelToTurnScreenOffOption.getValue() < myFBReaderApp.getBatteryLevel());
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		myStartTimer = true;
		final int brightnessLevel = getZLibrary().ScreenBrightnessLevelOption.getValue();
		if (brightnessLevel != 0)
		{
			setScreenBrightness(brightnessLevel);
		}
		else
		{
			setScreenBrightnessAuto();
		}
		if (getZLibrary().DisableButtonLightsOption.getValue())
		{
			setButtonLight(false);
		}

		registerReceiver(myBatteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		PopupPanel.restoreVisibilities(myFBReaderApp);
		ApiServerImplementation.sendEvent(this, ApiListener.EVENT_READ_MODE_OPENED);

		getCollection().bindToService(this, new Runnable()
		{
			public void run()
			{
				final BookModel model = myFBReaderApp.Model;
				if (model == null || model.Book == null) { return; }
				onPreferencesUpdate(myFBReaderApp.Collection.getBookById(model.Book.getId()));
			}
		});
	}

	@Override
	protected void onPause()
	{
		try
		{
			unregisterReceiver(myBatteryInfoReceiver);
		} catch (IllegalArgumentException e)
		{}
		myFBReaderApp.stopTimer();
		if (getZLibrary().DisableButtonLightsOption.getValue())
		{
			setButtonLight(true);
		}
		myFBReaderApp.onWindowClosing();
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		ApiServerImplementation.sendEvent(this, ApiListener.EVENT_READ_MODE_CLOSED);
		PopupPanel.removeAllWindows(myFBReaderApp, this);
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		getCollection().unbind();
		super.onDestroy();
	}

	@Override
	public void onLowMemory()
	{
		myFBReaderApp.onWindowClosing();
		super.onLowMemory();
	}

	//
	// @Override
	// public boolean onSearchRequested() {
	// final FBReaderApp.PopupPanel popup = myFBReaderApp.getActivePopup();
	// myFBReaderApp.hideActivePopup();
	// final SearchManager manager =
	// (SearchManager)getSystemService(SEARCH_SERVICE);
	// manager.setOnCancelListener(new SearchManager.OnCancelListener() {
	// public void onCancel() {
	// if (popup != null) {
	// myFBReaderApp.showPopup(popup.getId());
	// }
	// manager.setOnCancelListener(null);
	// }
	// });
	// startSearch(myFBReaderApp.TextSearchPatternOption.getValue(), true, null,
	// false);
	// return true;
	// }

	public void showSelectionPanel()
	{
		final ZLTextView view = myFBReaderApp.getTextView();
		((SelectionPopup) myFBReaderApp.getPopupById(SelectionPopup.ID)).move(view.getSelectionStartY(), view.getSelectionEndY());
		myFBReaderApp.showPopup(SelectionPopup.ID);
	}

	public void hideSelectionPanel()
	{
		final FBReaderApp.PopupPanel popup = myFBReaderApp.getActivePopup();
		if (popup != null && popup.getId() == SelectionPopup.ID)
		{
			myFBReaderApp.hideActivePopup();
		}
	}

	private void onPreferencesUpdate(Book book)
	{
		AndroidFontUtil.clearFontCache();
		myFBReaderApp.onBookUpdated(book);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case REQUEST_CANCEL_MENU:
			runCancelAction(data);
			break;
		}
	}

	public void runCancelAction(Intent intent)
	{
		final CancelMenuHelper.ActionType type;
		try
		{
			type = CancelMenuHelper.ActionType.valueOf(intent.getStringExtra(CancelActivity.TYPE_KEY));
		} catch (Exception e)
		{
			// invalid (or null) type value
			return;
		}
		Bookmark bookmark = null;
		if (type == CancelMenuHelper.ActionType.returnTo)
		{
			try
			{
				bookmark = SerializerUtil.deserializeBookmark(intent.getStringExtra(CancelActivity.BOOKMARK_KEY));
			} catch (Exception e)
			{
				return;
			}
		}
		myFBReaderApp.runCancelAction(type, bookmark);
	}

	public void navigate()
	{
		progressPop.getBindView().bind();
		progressPop.showAtLocation(findViewById(R.id.root_view), Gravity.BOTTOM, 0, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return (main_view != null && main_view.onKeyDown(keyCode, event)) || super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return (main_view != null && main_view.onKeyUp(keyCode, event)) || super.onKeyUp(keyCode, event);
	}

	private void setButtonLight(boolean enabled)
	{
		try
		{
			final WindowManager.LayoutParams attrs = getWindow().getAttributes();
			final Class<?> cls = attrs.getClass();
			final Field fld = cls.getField("buttonBrightness");
			if (fld != null && "float".equals(fld.getType().toString()))
			{
				fld.setFloat(attrs, enabled ? -1.0f : 0.0f);
				getWindow().setAttributes(attrs);
			}
		} catch (NoSuchFieldException e)
		{} catch (IllegalAccessException e)
		{}
	}

	private PowerManager.WakeLock myWakeLock;
	private boolean myWakeLockToCreate;
	private boolean myStartTimer;

	public final void createWakeLock()
	{
		if (myWakeLockToCreate)
		{
			synchronized (this)
			{
				if (myWakeLockToCreate)
				{
					myWakeLockToCreate = false;
					myWakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
							"FBReader");
					myWakeLock.acquire();
				}
			}
		}
		if (myStartTimer)
		{
			myFBReaderApp.startTimer();
			myStartTimer = false;
		}
	}

	private final void switchWakeLock(boolean on)
	{
		if (on)
		{
			if (myWakeLock == null)
			{
				myWakeLockToCreate = true;
			}
		}
		else
		{
			if (myWakeLock != null)
			{
				synchronized (this)
				{
					if (myWakeLock != null)
					{
						myWakeLock.release();
						myWakeLock = null;
					}
				}
			}
		}
	}

	private BroadcastReceiver myBatteryInfoReceiver = new BroadcastReceiver()
	{
		public void onReceive(Context context, Intent intent)
		{
			final int level = intent.getIntExtra("level", 100);
			setBatteryLevel(level);
			switchWakeLock(hasWindowFocus() && getZLibrary().BatteryLevelToTurnScreenOffOption.getValue() < level);
		}
	};

	private void setScreenBrightnessAuto()
	{
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.screenBrightness = -1.0f;
		getWindow().setAttributes(attrs);
	}

	public void setScreenBrightness(int percent)
	{
		if (percent < 1)
		{
			percent = 1;
		}
		else if (percent > 100)
		{
			percent = 100;
		}
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.screenBrightness = percent / 100.0f;
		getWindow().setAttributes(attrs);
		getZLibrary().ScreenBrightnessLevelOption.setValue(percent);
	}

	public int getScreenBrightness()
	{
		final int level = (int) (100 * getWindow().getAttributes().screenBrightness);
		return (level >= 0) ? level : 50;
	}

	private BookCollectionShadow getCollection()
	{
		return (BookCollectionShadow) myFBReaderApp.Collection;
	}

	@Override
	public void runWithMessage(String key, Runnable action, Runnable postAction)
	{
		UIUtil.runWithMessage(this, key, action, postAction, false);
	}

	private int myBatteryLevel;

	@Override
	public int getBatteryLevel()
	{
		return myBatteryLevel;
	}

	private void setBatteryLevel(int percent)
	{
		myBatteryLevel = percent;
	}

	@Override
	public void close()
	{
		((ZLAndroidLibrary) ZLAndroidLibrary.Instance()).finish();
	}

	@Override
	public ZLViewWidget getViewWidget()
	{
		return main_view;
	}

	@Override
	public void processException(Exception exception)
	{
		exception.printStackTrace();

		final Intent intent = new Intent("android.fbreader.action.ERROR", new Uri.Builder().scheme(exception.getClass().getSimpleName())
				.build());
		intent.putExtra(ErrorKeys.MESSAGE, exception.getMessage());
		final StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		intent.putExtra(ErrorKeys.STACKTRACE, stackTrace.toString());
		try
		{
			startActivity(intent);
		} catch (ActivityNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
