package be.webfactor.sitecubes.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageRootTest {

	@Test
	public void rootPageNameIsRoot() {
		assertEquals("Root", Page.ROOT_NAME);
	}

	@Test
	public void rootPageUrlIsSlash() {
		assertEquals("/", Page.ROOT_FRIENDLY_URL);
	}

}
