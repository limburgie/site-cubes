package be.webfactor.sitecubes.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SiteDefaultSiteTest {

	@Test
	public void defaultSiteHasDefaultSiteEnabled() {
		assertTrue(Site.DEFAULT_SITE.isDefaultSite());
	}

	@Test
	public void defaultSiteHasWebsiteAsName() {
		assertEquals("Website", Site.DEFAULT_SITE.getName());
	}

	@Test
	public void defaultSiteHasSiteAsFriendlyUrl() {
		assertEquals("site", Site.DEFAULT_SITE.getFriendlyUrl());
	}

}
