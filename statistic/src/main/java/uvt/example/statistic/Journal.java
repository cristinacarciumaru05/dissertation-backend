package uvt.example.statistic;

import lombok.Data;

@Data
public class Journal {
	private double journalImpactFactor;
	private double articleInfluenceScore;
	private String categoryName;
	private String issn;
	private int year;

	public Journal(double journalImpactFactor, double articleInfluenceScore, String categoryName, String issn, int year) {
		this.journalImpactFactor = journalImpactFactor;
		this.articleInfluenceScore = articleInfluenceScore;
		this.categoryName = categoryName;
		this.issn = issn;
		this.year = year;
	}
}
