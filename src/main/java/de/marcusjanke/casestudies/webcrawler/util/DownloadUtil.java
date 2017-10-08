package de.marcusjanke.casestudies.webcrawler.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcusjanke.casestudies.webcrawler.Constants;

/**
 * DownloadUtil
 * 
 * @author marcus
 *
 */
public class DownloadUtil {

	private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

	private DownloadUtil() {
	}

	/**
	 * download text files 
	 * 
	 * @param urlString
	 * @param targetFolder
	 */
	public static void downloadTextFile(String urlString, String targetFolder) {
		try  {
			Path targetPath = getTargetPath(urlString, targetFolder);
			Response response = request(urlString);
			FileUtil.saveFile(targetPath, response.body());
			logger.info(String.format("Downloaded '%s' to '%s", urlString, targetPath.toAbsolutePath()));
		} catch (FileAlreadyExistsException e) {
			logger.info(String.format("Download of '%s' ommitted (duplicate)", urlString));
		} catch (ConnectException e) {
			logger.error(String.format("Download '%s' encountered connection error '%s'",urlString, e.getMessage()));
		} catch (SocketTimeoutException e) {
			logger.error(String.format("Download '%s' encountered connection error '%s'",urlString, e.getMessage()));
		}catch (IOException e) {
			logger.error(String.format("Failed to download '%s'",urlString), e);
		}
	}

	/**
	 * download binary files
	 * 
	 * @param urlString
	 * @param targetFolder
	 */
	public static void downloadBinaryFile(String urlString, String targetFolder) {
		try {
			Path targetPath = getTargetPath(urlString, targetFolder);
			Response response = request(urlString);
			FileUtil.saveFile(targetPath, response.bodyAsBytes());
			logger.info(String.format("Downloaded '%s' to '%s", urlString, targetPath.toAbsolutePath()));
		} catch (FileAlreadyExistsException e) {
			logger.info(String.format("Download of '%s' ommitted (duplicate)", urlString));
		} catch (ConnectException e) {
			logger.error(String.format("Download '%s' encountered connection error '%s'",urlString, e.getMessage()));
		} catch (SocketTimeoutException e) {
			logger.error(String.format("Download '%s' encountered connection error '%s'",urlString, e.getMessage()));
		} catch (IOException e) {
			logger.error(String.format("Failed to download '%s'", urlString), e);
		}
	}
	
	private static Path getTargetPath(String urlString, String targetFolder) throws MalformedURLException {
		return Paths.get(targetFolder, new URL(urlString).getPath());
	}
	
	private static Response request(String urlString) throws IOException {
		URL url = new URL(urlString);
		return Jsoup.connect(url.toString()).ignoreContentType(true).timeout(Constants.CONNECTION_TIMEOUT_MS).execute();
	}
}
