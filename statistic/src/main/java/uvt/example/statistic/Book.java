package uvt.example.statistic;

public class Book {
	private String publisher;
	private String publisherLocation;
	private int year;
	private String grade;

	public Book(String publisher,String publisherLocation,int year,String grade) {
		this.publisher = publisher;
		this.publisherLocation = publisherLocation;
		this.year = year;
		this.grade = grade;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherLocation() {
		return publisherLocation;
	}

	public void setPublisherLocation(String publisherLocation) {
		this.publisherLocation = publisherLocation;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
