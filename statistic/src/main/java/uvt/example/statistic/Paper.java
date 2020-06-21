package uvt.example.statistic;

public class Paper {
	private String paperName;
	private String acronym;
	private String year;
	private String grade;

	public Paper(String paperName, String acronym,String year,String grade) {
		this.paperName = paperName;
		this.acronym = acronym;
		this.year = year;
		this.grade = grade;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
