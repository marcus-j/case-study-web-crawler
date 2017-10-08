package de.marcusjanke.casestudies.webcrawler.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.junit.Test;

import de.marcusjanke.casestudies.webcrawler.Constants;

/**
 * DownloadUtil tests
 * 
 * @author marcus
 *
 */
public class DownloadUtilTest {

	/**
	 * test binary download
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDownloadBinary() throws IOException {
		String targetFolder = "test/downloads/" + LocalDateTime.now().format(Constants.DIRECTORY_DATE_FORMAT);
		DownloadUtil.downloadBinaryFile(
				"https://img.abendblatt.de/img/incoming/crop212153249/0888711391-w158-cv3_2-q85/Delta-Air-Lines-fliegt-bald-von-Berlin-nach-New-York.jpg",
				targetFolder);
		Path path = Paths.get(targetFolder, "img/incoming/crop212153249/0888711391-w158-cv3_2-q85/Delta-Air-Lines-fliegt-bald-von-Berlin-nach-New-York.jpg");
		assertThat(Files.exists(path)).isTrue();
	}

	/**
	 * test text download
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDownloadText() throws IOException {
		String targetFolder = "test/downloads/" + LocalDateTime.now().format(Constants.DIRECTORY_DATE_FORMAT);
		DownloadUtil.downloadTextFile("https://www.abendblatt.de/archiv/nachrichten-vom-7-10-2017.html", targetFolder);
		Path path = Paths.get(targetFolder, "archiv/nachrichten-vom-7-10-2017.html");
		assertThat(Files.exists(path)).isTrue();
	}
}
