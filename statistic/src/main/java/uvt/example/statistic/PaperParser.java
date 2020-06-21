package uvt.example.statistic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaperParser  implements CustomFileReader{
	public PaperParser(String fileName) {
		readFile(fileName);
	}
	public List<Paper> papers = new ArrayList<Paper>();
	public List<Paper> getPapers() {
		return papers;
	}

	@Override
	public void readFile(String fileName) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(fileName));
				while ((line = br.readLine()) != null) {
					String[] paperData = line.split( ",");
					if(paperData.length >3 ) {
						// paperName,acronym,year,grade
						papers.add(new Paper( paperData[1], paperData[2],paperData[3], paperData[4]));
					}
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
