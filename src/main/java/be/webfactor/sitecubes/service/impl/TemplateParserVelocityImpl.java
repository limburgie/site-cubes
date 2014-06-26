package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.service.TemplateParser;
import be.webfactor.sitecubes.service.exception.TemplateParsingException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import javax.inject.Named;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Named
public class TemplateParserVelocityImpl implements TemplateParser {

	private VelocityEngine velocityEngine;

	public TemplateParserVelocityImpl() {
		velocityEngine = new VelocityEngine();
		velocityEngine.init();
	}

	public void validate(String template) {
		parse(template, new HashMap<String, Object>());
	}

	public String parse(String template, Map<String, Object> params) {
		VelocityContext context = new VelocityContext(params);
		StringWriter writer = new StringWriter();
		try {
			velocityEngine.evaluate(context, writer, TemplateParserVelocityImpl.class.getName(), template);
		} catch(ParseErrorException e) {
			throw new TemplateParsingException(e);
		} catch(MethodInvocationException e) {
			throw new TemplateParsingException(e);
		} catch(ResourceNotFoundException e) {
			throw new TemplateParsingException(e);
		}
		return writer.toString();
	}

}
