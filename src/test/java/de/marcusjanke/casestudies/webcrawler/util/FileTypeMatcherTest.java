package de.marcusjanke.casestudies.webcrawler.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

/**
 * FileTypeMatcher tests
 * 
 * @author marcus
 *
 */
public class FileTypeMatcherTest {

	/**
	 * test known binary extensions
	 */
	@Test
	public void testBinaryExtensions() {
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.jpg")).isTrue();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.jpeg")).isTrue();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.png")).isTrue();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/index.html")).isFalse();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/index.htm")).isFalse();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/index.xhtml")).isFalse();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/css.css")).isFalse();
		assertThat(FileTypeMatcher.isBinary("https://img.abendblatt.de/tes.js")).isFalse();
		assertThat(FileTypeMatcher.isBinary(null)).isFalse();
	}

	/**
	 * test known HTML extensions
	 */
	@Test
	public void testHtmlExtensions() {
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.jpg")).isFalse();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.jpeg")).isFalse();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.png")).isFalse();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/index.html")).isTrue();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/index.htm")).isTrue();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/index.xhtml")).isTrue();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/css.css")).isFalse();
		assertThat(FileTypeMatcher.isHtml("https://img.abendblatt.de/tes.js")).isFalse();
		assertThat(FileTypeMatcher.isHtml(null)).isFalse();
	}

	/**
	 * test other known text based extensions
	 */
	@Test
	public void testTextExtensions() {
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.jpg")).isFalse();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.jpeg")).isFalse();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/img/incoming/crop212153339/8832225543-w940-cv23_11-q85/Hauptbahnhof-Schlangen2.png")).isFalse();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/index.html")).isFalse();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/index.htm")).isFalse();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/index.xhtml")).isFalse();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/css.css")).isTrue();
		assertThat(FileTypeMatcher.isText("https://img.abendblatt.de/tes.js")).isTrue();
		assertThat(FileTypeMatcher.isText(null)).isFalse();
	}
}
