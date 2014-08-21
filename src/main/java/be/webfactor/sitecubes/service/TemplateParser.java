package be.webfactor.sitecubes.service;

import java.util.Map;

public interface TemplateParser {

	void validate(String template);

	String parse(String template, Map<String, Object> params);

}
