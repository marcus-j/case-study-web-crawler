package de.marcusjanke.casestudies.webcrawler.util;

import static org.assertj.core.api.Assertions.*;

import java.net.MalformedURLException;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * URL util tests
 * 
 * @author marcus
 *
 */
public class UrlUtilTest {

	/**
	 * test URL transformation to local
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testLocalUrl() throws MalformedURLException {
		assertThat(UrlUtil.localUrl("https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png"))
				.isEqualTo("resources/img/meta-icons/apple-touch-icon.png");
	}

	/**
	 * test URL completion
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testCompleteUrl() throws MalformedURLException {
		assertThat(UrlUtil.completeUrl("/resources/img/meta-icons/apple-touch-icon.png", "https://www.abendblatt.de/"))
				.isEqualTo("https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png");
		assertThat(UrlUtil.completeUrl("./resources/img/meta-icons/apple-touch-icon.png", "https://www.abendblatt.de/"))
				.isEqualTo("https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png");
		assertThat(
				UrlUtil.completeUrl("//www.video.oms.eu/ada/cloud/omsv_container_151.js", "https://www.abendblatt.de/"))
						.isEqualTo("http://www.video.oms.eu/ada/cloud/omsv_container_151.js");
	}

	/**
	 * test for external content
	 */
	@Test
	public void testExternalContent() {
		assertThat(UrlUtil.isExternalContent("https://img.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png",
				"https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png")).isFalse();
		assertThat(UrlUtil.isExternalContent("https://google.de/resources/img/meta-icons/apple-touch-icon.png",
				"https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png")).isTrue();
	}

	/**
	 * test for not external content
	 */
	@Test
	public void testNotExternalContent() {
		assertThat(
				UrlUtil.isNotExternalContent("https://img.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png",
						"https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png")).isTrue();
		assertThat(UrlUtil.isNotExternalContent("https://google.de/resources/img/meta-icons/apple-touch-icon.png",
				"https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png")).isFalse();
	}

	/**
	 * test URL transformation to local path
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testLocalPath() throws MalformedURLException {
		assertThat(UrlUtil.localPath("downloads/", "https://www.abendblatt.de/resources/img/meta-icons/apple-touch-icon.png"))
				.isEqualTo(Paths.get("downloads/resources/img/meta-icons/apple-touch-icon.png"));
	}

}
