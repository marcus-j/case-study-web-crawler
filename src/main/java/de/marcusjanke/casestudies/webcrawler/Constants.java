package de.marcusjanke.casestudies.webcrawler;

import java.time.format.DateTimeFormatter;

/**
 * Global constants that do not need to be configurable through
 * application.properties
 * 
 * @author marcus
 *
 */
public class Constants {

	public static final DateTimeFormatter DIRECTORY_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
	public static final int CONNECTION_TIMEOUT_MS = 10_000;
	public static final int CRAWL_INTERVAL_MIN = 30;
}
