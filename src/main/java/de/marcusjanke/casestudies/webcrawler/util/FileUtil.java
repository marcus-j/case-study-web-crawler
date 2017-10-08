package de.marcusjanke.casestudies.webcrawler.util;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

import org.jsoup.nodes.Document;

/**
 * File util
 * 
 * @author marcus
 *
 */
public class FileUtil {

	private static final OpenOption[] SAVING_OPTIONS = new OpenOption[] { WRITE, CREATE_NEW };

	private FileUtil() {
	}

	/**
	 * save file from JSoup doc
	 * 
	 * @param targetPath
	 * @param doc
	 * @throws IOException
	 */
	public static void saveFile(Path targetPath, Document doc) throws IOException {
		saveFile(targetPath, doc.outerHtml());
	}

	/**
	 * save file from String content
	 * 
	 * @param targetPath
	 * @param content
	 * @throws IOException
	 */
	public static void saveFile(Path targetPath, String content) throws IOException {
		saveFile(targetPath, content.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * save file from binary data
	 * 
	 * @param targetPath
	 * @param data
	 * @throws IOException
	 */
	public static void saveFile(Path targetPath, byte[] data) throws IOException {
		if (Objects.nonNull(targetPath)) {
			if (Objects.nonNull(targetPath.getParent())) {
				Files.createDirectories(targetPath.getParent());
			}
			try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(targetPath, SAVING_OPTIONS))) {
				if (Objects.nonNull(out)) {
					out.write(data);
					out.close();
				}
			}
		}
	}
}
