
package org.geometerplus.fbreader.formats.fb2;

import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.core.encodings.AutoEncodingCollection;
import org.geometerplus.zlibrary.core.image.ZLImage;

import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.bookmodel.BookReadingException;
import org.geometerplus.fbreader.formats.JavaFormatPlugin;

public class FB2Plugin extends JavaFormatPlugin {
	public FB2Plugin() {
		super("fb2");
	}

	@Override
	public ZLFile realBookFile(ZLFile file) throws BookReadingException {
		final ZLFile realFile = FB2Util.getRealFB2File(file);
		if (realFile == null) {
			throw new BookReadingException("incorrectFb2ZipFile", file);
		}
		return realFile;
	}

	@Override
	public void readMetaInfo(Book book) throws BookReadingException {
		new FB2MetaInfoReader(book).readMetaInfo();
	}

	@Override
	public void readUids(Book book) throws BookReadingException {
		// this method does nothing, we expect it will be never called
	}

	@Override
	public void readModel(BookModel model) throws BookReadingException {
		new FB2Reader(model).readBook();
	}

	@Override
	public ZLImage readCover(ZLFile file) {
		return new FB2CoverReader().readCover(file);
	}

	@Override
	public String readAnnotation(ZLFile file) {
		return new FB2AnnotationReader().readAnnotation(file);
	}

	@Override
	public AutoEncodingCollection supportedEncodings() {
		return new AutoEncodingCollection();
	}

	@Override
	public void detectLanguageAndEncoding(Book book) {
		book.setEncoding("auto");
	}
}
