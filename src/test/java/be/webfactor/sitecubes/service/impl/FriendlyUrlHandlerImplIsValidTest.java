package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FriendlyUrlHandlerImplIsValidTest {

	private FriendlyUrlHandler handler;

	@Before
	public void setup() {
		handler = new FriendlyUrlHandlerImpl();
	}

	@Test
	public void nullFriendlyUrlIsInvalid() {
		assertFalse(handler.isValid(null));
	}

	@Test
	public void emptyFriendlyUrlIsInvalid() {
		assertFalse(handler.isValid(" "));
	}

	@Test
	public void onlyLowerCaseCharsIsValid() {
		assertTrue(handler.isValid("home"));
	}

	@Test
	public void upperCaseCharsIsValid() {
		assertFalse(handler.isValid("Home"));
	}

	@Test
	public void invalidCharsIsInvalid() {
		assertFalse(handler.isValid("*@#$"));
	}

}
