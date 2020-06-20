package uvt.example.statistic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	public List<Journal> Journals = new ArrayList<Journal>();

	public static String getType(JSONObject jsonObject) {
		return (String) jsonObject.get("type");
	}

	public static String getYear(JSONObject jsonObject) {
		try {
			JSONObject deposited = (JSONObject) jsonObject.get("deposited");
			String StringDate = (String) deposited.get("date-time");
			return Integer.toString(new SimpleDateFormat("yyyy-dd-mm").parse(StringDate).getYear() + 1900);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
		}
		return "";
	}

	public static List<String> getIssns(JSONObject jsonObject) {
		List<String> issns = new ArrayList<String>();
		JSONArray x = (JSONArray) jsonObject.get("ISSN");
		x.forEach(e -> issns.add((String) e));
		return issns;
	}

	public static String getContainerTitle(JSONObject jsonObject) {
		return (String) jsonObject.get("container-title");
	}

	public static String getEvent(JSONObject jsonObject) {
		return (String) jsonObject.get("event");
	}

	public static String[] extrectAcrEvtTitle(String event) {
		String[] k = new String[2];
		String[] splitedEvent = event.split(" ");
		String acronym = "";
		String eventTitle = "";

		for (int i = 0; i < splitedEvent.length; i++) {
			if (splitedEvent[i].matches(".*\\d.*")) {
				splitedEvent[i] = "";
			}
			if (splitedEvent[i].contains("(") || splitedEvent[i].contains(")")) {
				acronym = splitedEvent[i].replace("(", "").replace(")", "");
				splitedEvent[i] = "";
			}
		}

		for (int i = 0; i < splitedEvent.length; i++) {
			if (i < splitedEvent.length - 1) {
				if (!splitedEvent[i].equals("")) {
					eventTitle = eventTitle + splitedEvent[i] + " ";
				}
			} else {
				eventTitle = eventTitle + splitedEvent[i];
			}
		}

		k[0] = acronym;
		k[1] = eventTitle;
		return k;
	}

	public static String getPublisher(JSONObject jsonObject) {
		return (String) jsonObject.get("publisher");
	}

	public static String getPublisherLocation(JSONObject jsonObject) {
		return (String) jsonObject.get("publisher-location");
	}


}

