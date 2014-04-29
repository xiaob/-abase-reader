/*
 * Copyright (C) 2007-2013 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.fbreader;

public interface ActionCode {
	  public String  SHOW_LIBRARY = "library";
	public String  SHOW_PREFERENCES = "preferences";
	public String  SHOW_BOOK_INFO = "bookInfo";
	public String  SHOW_TOC = "toc";
	public String  SHOW_BOOKMARKS = "bookmarks";
	public String  SHOW_NETWORK_LIBRARY = "networkLibrary";

	public String  SWITCH_TO_NIGHT_PROFILE = "night";
	public String  SWITCH_TO_DAY_PROFILE = "day";

	public String  SHARE_BOOK = "shareBook";

	public String  SEARCH = "search";
	public String  FIND_PREVIOUS = "findPrevious";
	public String  FIND_NEXT = "findNext";
	public String  CLEAR_FIND_RESULTS = "clearFindResults";

	public String  SET_TEXT_VIEW_MODE_VISIT_HYPERLINKS = "hyperlinksOnlyMode";
	public String  SET_TEXT_VIEW_MODE_VISIT_ALL_WORDS = "dictionaryMode";

	public String  TURN_PAGE_BACK = "previousPage";
	public String  TURN_PAGE_FORWARD = "nextPage";

	public String  MOVE_CURSOR_UP = "moveCursorUp";
	public String  MOVE_CURSOR_DOWN = "moveCursorDown";
	public String  MOVE_CURSOR_LEFT = "moveCursorLeft";
	public String  MOVE_CURSOR_RIGHT = "moveCursorRight";

	public String  VOLUME_KEY_SCROLL_FORWARD = "volumeKeyScrollForward";
	public String  VOLUME_KEY_SCROLL_BACK = "volumeKeyScrollBackward";
	public String  SHOW_MENU = "menu";
	public String  SHOW_NAVIGATION = "navigate";

	public String  GO_BACK = "goBack";
	public String  EXIT = "exit";
	public String  SHOW_CANCEL_MENU = "cancelMenu";

	public String  SET_SCREEN_ORIENTATION_SYSTEM = "screenOrientationSystem";
	public String  SET_SCREEN_ORIENTATION_SENSOR = "screenOrientationSensor";
	public String  SET_SCREEN_ORIENTATION_PORTRAIT = "screenOrientationPortrait";
	public String  SET_SCREEN_ORIENTATION_LANDSCAPE = "screenOrientationLandscape";
	public String  SET_SCREEN_ORIENTATION_REVERSE_PORTRAIT = "screenOrientationReversePortrait";
	public String  SET_SCREEN_ORIENTATION_REVERSE_LANDSCAPE = "screenOrientationReverseLandscape";

	public String  INCREASE_FONT = "increaseFont";
	public String  DECREASE_FONT = "decreaseFont";

	public String  PROCESS_HYPERLINK = "processHyperlink";

	public String  SELECTION_SHOW_PANEL = "selectionShowPanel";
	public String  SELECTION_HIDE_PANEL = "selectionHidePanel";
	public String  SELECTION_CLEAR = "selectionClear";
	public String  SELECTION_COPY_TO_CLIPBOARD = "selectionCopyToClipboard";
	public String  SELECTION_SHARE = "selectionShare";
	public String  SELECTION_TRANSLATE = "selectionTranslate";
	public String  SELECTION_BOOKMARK = "selectionBookmark";

	public String  INSTALL_PLUGINS = "plugins";
}
