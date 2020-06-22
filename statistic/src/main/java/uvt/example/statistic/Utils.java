package uvt.example.statistic;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utils {

	public static List<Journal> Journals = new ArrayList<Journal>();
	public static List<Paper> Papers = new ArrayList<Paper>();
	public static List<Book> Books = new ArrayList<Book>();

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

	// general function to find all files from a directory
	public static List<String> getFilePaths(String directory) {
		List<String> paths2 = new ArrayList<String>();
		try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
			paths.filter(Files::isRegularFile).forEach(x -> paths2.add(x.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paths2;
	}

	public static double getSimilarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0; /* both strings are zero length */
		}
		return (longerLength - levensteinDistance(longer, shorter)) / (double) longerLength;

	}

	// Example implementation of the Levenshtein Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int levensteinDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue),
									costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}


}

