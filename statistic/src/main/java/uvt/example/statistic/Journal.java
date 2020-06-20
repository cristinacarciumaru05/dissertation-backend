package uvt.example.statistic;

public class Journal {
	private double journalImpactFactor;
	private double articleInfluenceScore;
	private String categoryName;
	private String issn;
	private long year;

	public Journal(double journalImpactFactor, double articleInfluenceScore, String categoryName, String issn, long year) {
		this.journalImpactFactor = journalImpactFactor;
		this.articleInfluenceScore = articleInfluenceScore;
		this.categoryName = categoryName;
		this.issn = issn;
		this.year = year;
	}

	public double getJournalImpactFactor() {
		return journalImpactFactor;
	}

	public void setJournalImpactFactor(double journalImpactFactor) {
		this.journalImpactFactor = journalImpactFactor;
	}

	public double getArticleInfluenceScore() {
		return articleInfluenceScore;
	}

	public void setArticleInfluenceScore(double articleInfluenceScore) {
		this.articleInfluenceScore = articleInfluenceScore;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}
}
