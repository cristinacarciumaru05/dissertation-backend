package uvt.example.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatisticApplication {

	public static void main(String[] args) {

		// Before starting the application load the Journals into memory so we can access them easily

		SpringApplication.run(StatisticApplication.class, args);
	}

}
