
package org.geometerplus.fbreader.bookmodel;

import java.util.List;

import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.formats.FormatPlugin;
import org.geometerplus.zlibrary.text.model.ZLTextModel;

public abstract class BookModel {
	public static BookModel createModel(Book book) throws BookReadingException {
		final FormatPlugin plugin = book.getPlugin();
		System.err.println("using plugin: " + plugin.supportedFileType() + "/" + plugin.type());

		
		final BookModel model;
		switch (plugin.type()) {
			case NATIVE:
				model = new NativeBookModel(book);
				break;
			case JAVA:
				model = new JavaBookModel(book);
				break;
			default:
				throw new BookReadingException("unknownPluginType", plugin.type().toString(), null);
		}
		plugin.readModel(model);
		return model;
	}

	public final Book Book;
	public final TOCTree TOCTree = new TOCTree();

	public static final class Label {
		public final String ModelId;
		public final int ParagraphIndex;

		public Label(String modelId, int paragraphIndex) {
			ModelId = modelId;
			ParagraphIndex = paragraphIndex;
		}
	}

	protected BookModel(Book book) {
		Book = book;
	}

	public abstract ZLTextModel getTextModel();
	public abstract ZLTextModel getFootnoteModel(String id);
	protected abstract Label getLabelInternal(String id);

	public interface LabelResolver {
		List<String> getCandidates(String id);
	}

	private LabelResolver myResolver;

	public void setLabelResolver(LabelResolver resolver) {
		myResolver = resolver;
	}

	public Label getLabel(String id) {
		Label label = getLabelInternal(id);
		if (label == null && myResolver != null) {
			for (String candidate : myResolver.getCandidates(id)) {
				label = getLabelInternal(candidate);
				if (label != null) {
					break;
				}
			}
		}
		return label;
	}
}
