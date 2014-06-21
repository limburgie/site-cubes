package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;

@Named
public class FriendlyUrlHandlerImpl implements FriendlyUrlHandler {

	public boolean isValid(String input) {
		return StringUtils.isNotBlank(input) && input.matches("([a-z]?[0-9]?/?-?)*");
	}

	public String normalize(String input) {
		return input.toLowerCase().replaceAll("[^a-z\\s]", "").replaceAll("\\s", "-");
	}

}
