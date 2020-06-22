package uvt.example.statistic;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uvt.example.dto.Results;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",allowedHeaders = "*",maxAge = 3600)
@RestController
public class StatisticConstroller {

	@GetMapping("/get-data")
	public Results getData(@RequestParam String doiX, @RequestParam String doiY, @RequestParam boolean isInfo) throws ParseException {

		HTTPUtils httpRequest = new HTTPUtils(doiX, doiY, HTTPUtils.InfoType.DOI);
		String rcvHTTPJSON = httpRequest.getInformation();
		JSONParser parser = new JSONParser();

		JSONObject jsonObj = (JSONObject) parser.parse(rcvHTTPJSON);
		String year = Utils.getYear(jsonObj);
		String type = Utils.getType(jsonObj);
		String classCNATDCU = " ";
		String classINFO = " ";
		switch (type) {
			case "article-journal":
				List<String> issn = Utils.getIssns(jsonObj);
				String title = Utils.getContainerTitle(jsonObj);
				classINFO = "D";
				// process journal
				Journal latestJournal = Utils.Journals.stream().filter(x -> x.getIssn().equals(issn)).collect(Collectors.toList()).stream().max(Comparator.comparing(Journal::getYear)).orElse(null);
				List<Journal> categoryJournals = new ArrayList<Journal>();
				if (latestJournal != null) {
					String categoryName = latestJournal.getCategoryName();
					categoryJournals = Utils.Journals.stream().filter(x -> x.getCategoryName().equals(categoryName)).collect(Collectors.toList());
					categoryJournals.sort(Comparator.comparing(Journal::getJournalImpactFactor));
				}
				int size = categoryJournals.size();
				int rank = categoryJournals.indexOf(latestJournal) + 1;
				if (rank <= Math.ceil(0.25 * size)) {
					classCNATDCU = "ISI ROSU";
					if (title.contains("NATURE")) {
						classCNATDCU = "NATURE";
					}
				} else if (rank <= Math.ceil(0.5 * size)) {
					if (!classCNATDCU.equals("ISI ROSU")) {
						classCNATDCU = "ISI GALBEN";
					}
				} else {
					if (!classCNATDCU.equals("ISI ROSU") && !classCNATDCU.equals("ISI GALBEN")) {
						classCNATDCU = "ISI ALB";
					}
				}

				if (isInfo) {
					if (rank <= Math.ceil(0.2 * size)) {
						classINFO = "A*";
					} else if (rank <= Math.ceil(0.25 * size) + Math.ceil(0.2 * size)) {
						if (!classINFO.equals("A*")) {
							classINFO = "A";
						}
					} else if (rank <= Math.ceil(0.5 * size) + Math.ceil(0.2 * size)) {
						if (!classINFO.equals("A*") && !classINFO.equals("A")) {
							classINFO = "B";
						} else if (!classINFO.equals("A*") && !classINFO.equals("A") && !classINFO.equals("B")) {
							classINFO = "C";
						}
					}
				}

				if (latestJournal != null && latestJournal.getArticleInfluenceScore() >= 0) {
					categoryJournals.sort(Comparator.comparing(Journal::getArticleInfluenceScore));
					size = categoryJournals.size();
					rank = categoryJournals.indexOf(latestJournal) + 1;
					if (rank <= Math.ceil(0.25 * size)) {
						classCNATDCU = "ISI ROSU";
						if (title.contains("NATURE")) {
							classCNATDCU = "NATURE";
						}
					} else if (rank <= Math.ceil(0.5 * size)) {
						if (!classCNATDCU.equals("ISI ROSU")) {
							classCNATDCU = "ISI GALBEN";
						}
					} else {
						if (!classCNATDCU.equals("ISI ROSU") && !classCNATDCU.equals("ISI GALBEN")) {
							classCNATDCU = "ISI ALB";
						}
					}
					if (isInfo) {
						if (rank <= Math.ceil(0.2 * size)) {
							classINFO = "A*";
						} else if (rank <= Math.ceil(0.25 * size) + Math.ceil(0.2 * size)) {
							if (!classINFO.equals("A*")) {
								classINFO = "A";
							}
						} else if (rank <= Math.ceil(0.5 * size) + Math.ceil(0.2 * size)) {
							if (!classINFO.equals("A*") && !classINFO.equals("A")) {
								classINFO = "B";
							} else if (!classINFO.equals("A*") && !classINFO.equals("A") && !classINFO.equals("B")) {
								classINFO = "C";
							}
						}
					}
				}

				// for the moment we leave this part commented because for WOS part we need an eduroam connection
				//if (classCNATDCU.equals(" ") && isInWOS) {
				//  classCNATDCU = "ISI ESCI";
				//}
				if (isInfo && classINFO.equals("D")) {
					String PLUPage = new HTTPUtils(doiX, doiY, HTTPUtils.InfoType.PLU).getInformation();
					if (!PLUPage.equals("")) {
						classINFO = "C";
					}
				}

				System.out.println("Type: Article-Journal");
				System.out.println("ClassificCNATDCU: " + classCNATDCU);
				System.out.println("Info: " + classINFO);
				return new Results( "Article-Journal",classCNATDCU,classINFO,jsonObj.toString());
			case "paper-conference":
				String event = Utils.getEvent(jsonObj);
				String[] acrEvTitle = Utils.extrectAcrEvtTitle(event);
				String acronym = acrEvTitle[0];
				String eventTitle = acrEvTitle[1];
				// process paper
				Paper latestPaper = Utils.Papers.stream().filter(x -> x.getAcronym().equals(acronym)).collect(Collectors.toList()).stream().max(Comparator.comparing(Paper::getYear)).orElse(null);
				boolean found = false;
				if (latestPaper != null) {
					found = true;
				}
//				if (isInWOS) {
//					classCNATDCU = "ISI PROC";
//				}
				if (doiX.equals("10.1109")) {
					classCNATDCU = "IEE PROC";
				}
				if (isInfo) {
					classINFO = "D";
					if (found) {
						if (Utils.getSimilarity(eventTitle, latestPaper.getPaperName()) >= 0.750) {
							classINFO = latestPaper.getGrade();
						}
					}
				}

				System.out.println("Type: Paper-Conference");
				System.out.println("ClassificCNATDCU: " + classCNATDCU);
//				if (isInWOS) {
//
//				}
				System.out.println("Info: " + classINFO);
				return new Results( "Paper-Conference",classCNATDCU,classINFO,jsonObj.toString());
			case "chapter":
			case "book":
				String publisher = Utils.getPublisher(jsonObj);
				String publisherLocation = Utils.getPublisherLocation(jsonObj);
				// process book
				Book latestBook = Utils.Books.stream().filter(x -> x.getPublisher().equals(publisher) && x.getPublisherLocation().equals(publisherLocation)).collect(Collectors.toList()).stream().max(Comparator.comparing(Book::getYear)).orElse(null);
				if (latestBook != null) {
					classINFO = latestBook.getClassInfo();
				}
				System.out.println("Type: Book/Chapter");
				System.out.println("ClassificCNATDCU: " + classCNATDCU);
//				if (isInWOS) {
//				}
				System.out.println("Info: " + classINFO);
				return new Results( "Book/Chapter",classCNATDCU,classINFO,jsonObj.toString());
			default:
				return new Results();
		}
	}

	@GetMapping("/test")
	public Results test() { //@RequestParam Input input
		return new Results();
	}

}
