package uvt.example.statistic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JournalParser implements CustomFileReader {
	private List<Journal> journals = new ArrayList<Journal>();

	public JournalParser(String fileName) {
		readFile(fileName);
	}

	private static Journal parseJournalObject(JSONObject journal) {
		double journalImpactFactor = (double) journal.get("journalImpactFactor");
		double articleInfluenceScore = (double) journal.get("articleInfluenceScore");
		String categoryName = (String) journal.get("categoryName");
		String issn = (String) journal.get("issn");
		if (issn == null) {
			issn = "";
		}
		long year = (long) journal.get("year");

		return new Journal(journalImpactFactor, articleInfluenceScore, categoryName, issn, year);
	}

	@Override
	public void readFile(String fileName) {
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(fileName)) {
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray journalsList = (JSONArray) obj;
			journalsList.forEach(emp -> journals.add(parseJournalObject((JSONObject) emp)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (net.minidev.json.parser.ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Journal> getJournals() {
		return journals;
	}

	public void setJournals(List<Journal> journals) {
		this.journals = journals;
	}

}

