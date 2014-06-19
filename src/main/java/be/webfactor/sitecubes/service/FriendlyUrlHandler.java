package be.webfactor.sitecubes.service;

public interface FriendlyUrlHandler {

	boolean isValid(String input);

	String normalize(String input);

}
