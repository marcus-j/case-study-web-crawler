package de.marcusjanke.casestudies.webcrawler.util;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import de.marcusjanke.casestudies.webcrawler.Constants;

/**
 * tests for FileUtil
 * 
 * @author marcus
 *
 */
public class FileUtilTest {

	private static final String TEST_TARGET = "test/unittests/" + LocalDateTime.now().format(Constants.DIRECTORY_DATE_FORMAT);

	/**
	 * test writing from JSoup doc
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveJsoupDoc() throws IOException {
		String html = "<html><head><title>First parse</title></head><body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		Path path = Paths.get(TEST_TARGET, "test.html");
		FileUtil.saveFile(path, doc);
		assertThat(Files.exists(path)).isTrue();
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.stream().forEach(line -> assertThat(html).contains(line.trim()));
	}

	/**
	 * test writing from String
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveText() throws IOException {
		Path path = Paths.get(TEST_TARGET, "test.txt");
		FileUtil.saveFile(path, "A text");
		assertThat(Files.exists(path)).isTrue();
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.stream().forEach(line -> line.contains("A text"));
	}

	/**
	 * test writing bytes
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveBinaryData() throws IOException {
		Path path = Paths.get(TEST_TARGET, "test.bin");
		FileUtil.saveFile(path, "A text".getBytes());
		assertThat(Files.exists(path)).isTrue();
		assertThat(Files.readAllBytes(path)).isEqualTo("A text".getBytes());
	}
}
