package be.webfactor.sitecubes.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaseEntityIsNewTest {

	private static final long ACTUAL_ID = 1;

	@Test
	public void newlyCreatedObjectIsNew() {
		assertTrue(new Page().isNew());
	}

	@Test
	public void ifObjectHasNullIdThenItIsNew() {
		Page page = new Page();
		page.setId(null);

		assertTrue(page.isNew());
	}

	@Test
	public void objectWithIdIsNotNew() {
		Page page = new Page();
		page.setId(ACTUAL_ID);

		assertFalse(page.isNew());
	}

}
