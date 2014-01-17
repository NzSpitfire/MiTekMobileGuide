package nz.co.mitek.search;


import org.apache.lucene.queryParser.ParseException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class SimplePDFSearch {
	// location where the index will be stored.
	private static final String INDEX_DIR = "src/main/resources/index";
	private static final int DEFAULT_RESULT_SIZE = 100;

	public boolean checkPDF(File pdfFile, String text) {
		int result = 0;
		try {
			IndexItem pdfIndexItem = index(pdfFile);
			// creating an instance of the indexer class and indexing the items
			Indexer indexer = new Indexer(INDEX_DIR);
			indexer.index(pdfIndexItem);
			indexer.close();

			// creating an instance of the Searcher class to the query the index
			Searcher searcher = new Searcher(INDEX_DIR);
			result = searcher.findByContent(text, DEFAULT_RESULT_SIZE);
			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return containsWord(result);
	}

	// Extract text from PDF document
	public static IndexItem index(File file) throws IOException {
		PDDocument doc = PDDocument.load(file);
		String content = new PDFTextStripper().getText(doc);
		doc.close();
		return new IndexItem((long) file.getName().hashCode(), file.getName(),
				content);
	}

	// Print the results
	private static boolean containsWord(int result) {
		if (result == 1)
			return true;
		else
			return false;

	}
}
