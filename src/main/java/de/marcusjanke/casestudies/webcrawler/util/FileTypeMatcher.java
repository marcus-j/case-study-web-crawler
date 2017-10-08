package de.marcusjanke.casestudies.webcrawler.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * File type matcher for known extensions
 * 
 * @author marcus
 *
 */
public class FileTypeMatcher {

	static List<String> HTML_EXTENSIONS = Arrays.asList("html", "htm", "xhtml");
	static List<String> TEXT_EXTENSIONS = Arrays.asList("css", "js");
	static List<String> BINARY_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg");

	/**
	 * test if binary
	 * 
	 * @param url
	 * @return true if binary
	 */
	public static boolean isBinary(String url) {
		return hasExtension(url, BINARY_EXTENSIONS);
	}

	/**
	 * test if other text
	 * 
	 * @param url
	 * @return true if other text
	 */
	public static boolean isText(String url) {
		return hasExtension(url, TEXT_EXTENSIONS);
	}

	/**
	 * test if html
	 * 
	 * @param url
	 * @return true if html
	 */
	public static boolean isHtml(String url) {
		return hasExtension(url, HTML_EXTENSIONS);
	}

	private static boolean hasExtension(String url, List<String> extensions) {
		if (Objects.nonNull(url) && url.lastIndexOf(".") > 0) {
			String fileExtension = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
			return extensions.contains(fileExtension);
		}
		return false;
	}
}
