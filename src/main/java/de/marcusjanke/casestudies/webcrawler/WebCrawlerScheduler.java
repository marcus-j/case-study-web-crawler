package de.marcusjanke.casestudies.webcrawler;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * WebCrawlerScheduler
 * 
 * @author marcus
 *
 */
@Component
public class WebCrawlerScheduler {

	private final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

	@Value("${crawler.max-depth}")
	private int maxDepth;
	@Value("${crawler.source-url}")
	private String url;
	@Value("${crawler.target-directory}")
	private String targetFolder;
	@Autowired
	private WebCrawler webCrawler;

	/**
	 * new WebCrawlerScheduler
	 */
	public WebCrawlerScheduler() {
		logger.info("WebCrawlerScheduler created");
	}

	/**
	 * start work
	 */
	@Scheduled(fixedRate = 30 * 60 * 1000)
	public void runScheduledCrawling() {
		logger.info("run scheduled crawling");
		String targetDir = targetFolder + LocalDateTime.now().format(Constants.DIRECTORY_DATE_FORMAT);
		webCrawler.crawlWebpage(targetDir, url, maxDepth);
	}
}
