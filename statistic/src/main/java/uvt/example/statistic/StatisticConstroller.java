package uvt.example.statistic;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StatisticConstroller {

	@GetMapping("/get-data")
	public String getData(@RequestParam String doiX, @RequestParam String doiY, @RequestParam boolean isInfo) throws ParseException {

		HTTPUtils httpRequest = new HTTPUtils(doiX, doiY, HTTPUtils.InfoType.DOI);
		String rcvHTTPJSON = httpRequest.getInformation();
		JSONParser parser = new JSONParser();

		JSONObject jsonObj = (JSONObject) parser.parse(rcvHTTPJSON);
		String year = Utils.getYear(jsonObj);
		String type = Utils.getType(jsonObj);
		switch (type) {
			case "article-journal":
				List<String> issn = Utils.getIssns(jsonObj);
				String title = Utils.getContainerTitle(jsonObj);
				String classCNATDCU = " ";
				String classINFO = "D";
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

			case "paper-conference":
				String event = Utils.getEvent(jsonObj);
				String[] acrEvTitle = Utils.extrectAcrEvtTitle(event);
				String acronym = acrEvTitle[0];
				String eventTitle = acrEvTitle[1];
				// process paper
			case "chapter":
			case "book":
				String publisher = Utils.getPublisher(jsonObj);
				String publisherLocation = Utils.getPublisherLocation(jsonObj);
				// process book
			default:
				return "Invalid type";
		}
	}

}
