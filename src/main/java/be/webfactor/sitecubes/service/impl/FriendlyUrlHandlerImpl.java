package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.service.FriendlyUrlHandler;

import javax.inject.Named;

@Named
public class FriendlyUrlHandlerImpl implements FriendlyUrlHandler {

	public boolean isValid(String input) {
		return input != null && !input.trim().isEmpty() && input.matches("([a-z]?[0-9]?/?-?)*");
	}

	public String normalize(String input) {
		return input.toLowerCase().replaceAll("[^a-z\\s]", "").replaceAll("\\s", "-");
	}

}
