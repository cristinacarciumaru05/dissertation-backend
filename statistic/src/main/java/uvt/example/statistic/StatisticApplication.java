package uvt.example.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class StatisticApplication {

	public static void main(String[] args) {
		// Before starting the application load the Journals into memory so we can access them easily
		for (String path : Utils.getFilePaths("C:\\Users\\Teofil\\Desktop\\TestGirt\\master\\statistic\\src\\main\\resources\\jsonJournalFiles")) {
			JournalParser jParser = new JournalParser(path);
			List<Journal> journals = jParser.getJournals();
			Utils.Journals.addAll(journals);
		}
		// Before starting the application load the Papers into memory so we can access them easily
		for (String path : Utils.getFilePaths("C:\\Users\\Teofil\\Desktop\\TestGirt\\master\\statistic\\src\\main\\resources\\csvCoreFiles")) {
			PaperParser pParser = new PaperParser(path);
			List<Paper> papers = pParser.getPapers();
			Utils.Papers.addAll(papers);
		}
		for (String path : Utils.getFilePaths("C:\\Users\\Teofil\\Desktop\\TestGirt\\master\\statistic\\src\\main\\resources\\csvSenseFiles")) {
			BookParser bParser = new BookParser(path);
			List<Book> books = bParser.getBooks();
			Utils.Books.addAll(books);
		}
		SpringApplication.run(StatisticApplication.class, args);
	}

}
