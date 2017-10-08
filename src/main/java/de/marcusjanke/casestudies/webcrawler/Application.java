package de.marcusjanke.casestudies.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * App entry point.
 * 
 * @author marcus
 *
 */
@SpringBootApplication
@EnableScheduling
public class Application {
	
	/**
	 * start app
	 * 
	 * @param args
	 */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
