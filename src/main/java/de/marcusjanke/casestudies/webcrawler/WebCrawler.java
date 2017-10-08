package de.marcusjanke.casestudies.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.marcusjanke.casestudies.webcrawler.util.DownloadUtil;
import de.marcusjanke.casestudies.webcrawler.util.FileTypeMatcher;
import de.marcusjanke.casestudies.webcrawler.util.FileUtil;
import de.marcusjanke.casestudies.webcrawler.util.UrlUtil;

/**
 * WebCrawler
 * 
 * @author marcus
 *
 */
@Component
public class WebCrawler {

	private final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

	/**
	 * new WebCrawler
	 */
	public WebCrawler() {
		logger.info("WebCrawler created");
	}
	
	/**
	 * crawl web page
	 * 
	 * @param targetFolder
	 * @param url
	 * @param maxDepth
	 */
	public void crawlWebpage(String targetFolder, String url, int maxDepth) {
		logger.info(String.format("WebCrawler is crawling %s with depth %s", url, maxDepth));
		crawlWebpage(targetFolder, url, 0, maxDepth);
	}

	private void crawlWebpage(String targetFolder, String url, final int depth, int maxDepth) {
		List<String> downloadPaths = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(url).timeout(Constants.CONNECTION_TIMEOUT_MS).get();
			if (depth <= maxDepth) {
				doc.getAllElements()
						.forEach(elem -> StreamSupport.stream(elem.attributes().spliterator(), true).sequential()
								.filter(this::isDownloadable)
								.forEach(attribute -> processAttribute(attribute, downloadPaths, url)));
				Path targetPath = UrlUtil.localPath(targetFolder, url);
				FileUtil.saveFile(targetPath, doc);
				logger.info(String.format("Downloaded '%s' to '%s", url, targetPath.toAbsolutePath()));
			}
		} catch (FileAlreadyExistsException e) {
			logger.info(String.format("Download of '%s' ommitted (duplicate)", url));
		} catch (IOException e) {
			logger.error(String.format("Failed to download '%s'", url), e);
		}
		downloadPaths.stream().parallel().filter(FileTypeMatcher::isBinary)
				.forEach(nextUrl -> DownloadUtil.downloadBinaryFile(nextUrl, targetFolder));
		downloadPaths.stream().parallel().filter(FileTypeMatcher::isText)
				.forEach(nextUrl -> DownloadUtil.downloadTextFile(nextUrl, targetFolder));
		downloadPaths.stream().parallel().filter(FileTypeMatcher::isHtml)
				.forEach(nextUrl -> crawlWebpage(targetFolder, nextUrl, depth + 1, maxDepth));
	}

	private void processAttribute(Attribute attribute, List<String> downloadPaths, String originalUrl) {
		try {
			String wellformedDownloadPath = UrlUtil.completeUrl(attribute.getValue(), originalUrl);
			if (UrlUtil.isNotExternalContent(wellformedDownloadPath, originalUrl)) {
				downloadPaths.add(wellformedDownloadPath);
				String localPath = UrlUtil.localUrl(wellformedDownloadPath);
				logger.debug(
						String.format("Changing remote path '%s to local path '%s", wellformedDownloadPath, localPath));
				attribute.setValue(localPath);
			}
		} catch (MalformedURLException e) {
			logger.error(String.format("Could not process download path '%s found on '%s'", attribute.getValue(),
					originalUrl));
		}
	}

	private boolean isDownloadable(Attribute attribute) {
		return isDownloadable(attribute.getValue());
	}

	private boolean isDownloadable(String url) {
		return FileTypeMatcher.isHtml(url) || FileTypeMatcher.isText(url) || FileTypeMatcher.isBinary(url)
				|| url.endsWith("/");
	}
}
