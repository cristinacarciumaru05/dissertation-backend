package uvt.example.statistic;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StatisticConstroller {

	@GetMapping("/get-data")
	public String getData(@RequestParam String doiX, @RequestParam String doiY) throws ParseException {

		HTTPUtils httpRequest = new HTTPUtils(doiX, doiY, HTTPUtils.InformationType.DOI);
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
