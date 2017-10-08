package de.marcusjanke.casestudies.webcrawler.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * URL util
 * 
 * @author marcus
 *
 */
public class UrlUtil {

	private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

	private UrlUtil() {
	}

	/**
	 * transform to local path
	 * 
	 * @param targetFolder
	 * @param originalUrl
	 * @return localPath
	 * @throws MalformedURLException
	 */
	public static Path localPath(String targetFolder, String originalUrl) throws MalformedURLException {
		URL url;
		if (originalUrl.endsWith("/")) {
			url = new URL(originalUrl + "index.html");
		} else {
			url = new URL(originalUrl);
		}
		return Paths.get(targetFolder, url.getPath());
	}

	/**
	 * complete URL from relative URLs etc.
	 * 
	 * @param url
	 * @param originalUrl
	 * @return
	 * @throws MalformedURLException
	 */
	public static String completeUrl(String url, String originalUrl) throws MalformedURLException {
		String newUrl = url;
		if (url.startsWith("//")) {
			newUrl = "http:" + url;
		} else if (url.startsWith("/")) {
			URL originalURL = new URL(originalUrl);
			newUrl = originalURL.toExternalForm().replace(originalURL.getPath().replaceFirst("/", ""), "")
					+ url.replaceFirst("/", "");
		} else if (url.startsWith("./")) {
			URL originalURL = new URL(originalUrl);
			newUrl = originalURL.toExternalForm().substring(0, originalURL.toExternalForm().lastIndexOf("/") + 1)
					+ url.replaceFirst("./", "");
		}
		logger.debug(String.format("Completing URL from '%s to '%s", url, newUrl));
		return newUrl;
	}

	/**
	 * checks if URL refers to external host
	 * 
	 * @param externalUrl
	 * @param originalUrl
	 * @return true if URL refers to external host
	 */
	public static boolean isExternalContent(String externalUrl, String originalUrl) {
		return !isNotExternalContent(externalUrl, originalUrl);
	}

	/**
	 * checks if URL does not refer to external host
	 * 
	 * @param externalUrl
	 * @param originalUrl
	 * @return true if URL does not refer to external host
	 */
	public static boolean isNotExternalContent(String externalUrl, String originalUrl) {
		try {
			URL downloadURL = new URL(externalUrl);
			URL originalURL = new URL(originalUrl);
			String[] downloadHostParts = downloadURL.getHost().split("\\.");
			String[] originalHostParts = originalURL.getHost().split("\\.");
			if (downloadHostParts.length < 2 || originalHostParts.length < 2) {
				logger.debug(String.format("External content for downloadURL host '%s' vs. originalUrl host '%s",
						downloadURL.getHost(), originalURL.getHost()));
				return false;
			}
			if (Objects.equals(downloadHostParts[downloadHostParts.length - 1],
					originalHostParts[originalHostParts.length - 1])
					&& Objects.equals(downloadHostParts[downloadHostParts.length - 2],
							originalHostParts[originalHostParts.length - 2])) {
				return true;
			} else {
				logger.debug(String.format("External content for downloadURL host '%s' vs. originalUrl host '%s",
						downloadURL.getHost(), originalURL.getHost()));
				return false;
			}
		} catch (MalformedURLException e) {
			logger.error(String.format("Could not identify host in external Url '%s or original Url '%s'", externalUrl,
					originalUrl));
			return false;
		}
	}

	/**
	 * transform remote URL to local
	 * 
	 * @param downloadPath
	 * @return
	 * @throws MalformedURLException
	 */
	public static String localUrl(String downloadPath) throws MalformedURLException {
		String localUrl = new URL(downloadPath).getPath();
		if (localUrl.endsWith("/")) {
			localUrl = localUrl + "index.html";
		}
		if (localUrl.startsWith("/")) {
			localUrl = localUrl.replaceFirst("/", "");
		}
		return localUrl;
	}
}
