package uvt.example.statistic;

import lombok.Data;

@Data
public class Paper {
	private String paperName;
	private String acronym;
	private String year;
	private String grade;

	public Paper(String paperName, String acronym, String year, String grade) {
		this.paperName = paperName;
		this.acronym = acronym;
		this.year = year;
		this.grade = grade;
	}
}
