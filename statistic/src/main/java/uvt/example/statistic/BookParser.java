package uvt.example.statistic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookParser implements CustomFileReader {
	public List<Book> books = new ArrayList<Book>();

	public BookParser(String fileName) {
		readFile(fileName);
	}

	public List<Book> getBooks() {
		return books;
	}

	@Override
	public void readFile(String fileName) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] bookData = line.split(",");
				if (bookData.length > 5) {
					int year = 0;
					if (bookData[4].length() > 4) {
						try {
							year = Integer.parseInt(bookData[4].substring(4));
						} catch (NumberFormatException fe) {
							year = 0;
						}
					} else {
						try {
							year = Integer.parseInt(bookData[4]);
						} catch (NumberFormatException fe) {
							year = 0;
						}
					}
					//publisher,publisherLocation,year,Grade
					books.add(new Book(bookData[2], bookData[3], year, bookData[5]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
