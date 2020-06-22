package uvt.example.statistic;

import lombok.Data;

@Data
public class Book {
	private String publisher;
	private String publisherLocation;
	private int year;
	private String classInfo;

	public Book(String publisher, String publisherLocation, int year, String classInfo) {
		this.publisher = publisher;
		this.publisherLocation = publisherLocation;
		this.year = year;
		this.classInfo = classInfo;
	}

}
