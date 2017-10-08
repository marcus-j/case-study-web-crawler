package de.marcusjanke.casestudies.webcrawler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.junit.Test;

/**
 * WebCrawler test
 * 
 * @author marcus
 *
 */
public class WebCrawlerTest {

	private WebCrawler webCrawler = new WebCrawler();

	/**
	 * test webpage download
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDownloadWebpage() throws IOException {
		String targetDir = "test/downloads/google/" + LocalDateTime.now().format(Constants.DIRECTORY_DATE_FORMAT);
		webCrawler.crawlWebpage(targetDir, "https://www.google.de/", 0);
		assertThat(Files.exists(Paths.get(targetDir, "index.html"))).isTrue();
	}
}
