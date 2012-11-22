package com.iksgmbh.moglicc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iksgmbh.moglicc.exceptions.MogliPluginException;
import com.iksgmbh.utils.FileUtil;

/**
 * Object to build a data structure with information needed to create a result file
 * 
 * @author Reik Oberrath
 */
public class BuildUpGeneratorResultData implements GeneratorResultData {
	
	public static final String NO_CONTENT = "No generated content set.";
	
	protected Properties properties = new Properties();
	
	protected String generatedContent;
	protected List<String> validationErrors = new ArrayList<String>();
	
	public void setGeneratedContent(String generatedContent) {
		this.generatedContent = generatedContent;
	}

	public void addProperty(final String key, final String value) {
		properties.put(key.toLowerCase(), value); // case insensitive!
	}

	@Override
	public String getGeneratedContent() {
		return generatedContent;
	}

	@Override
	public String getProperty(final String key) {
		return (String) properties.get(key.toLowerCase()); // case insensitive!
	}
	
	public int getPropertiesNumber() {
		return properties.size();
	}

	public Properties getProperties() {
		return properties;
	}
	
	public void validate() throws MogliPluginException {
		if (generatedContent == null) {
			validationErrors.add(NO_CONTENT);
		}
		
		if (validationErrors.size() > 0) {
			throw new MogliPluginException(buildErrorString());
		}
		
	}

	private String buildErrorString() {
		final StringBuffer sb = new StringBuffer();
		for (final String errorMessage : validationErrors) {
			sb.append(errorMessage).append(FileUtil.getSystemLineSeparator());
		}
		return sb.toString().trim();
	}
	
	public String searchTextInGeneratedContentBetween(final String s1, final String s2) {
		int pos = generatedContent.indexOf(s1);
		if (pos == -1) {
			return "";
		}
		String substring = generatedContent.substring(pos);
		pos = substring.indexOf(s2);
		if (pos == -1) {
			return "";
		}
		return substring.substring(s1.length() + 1, pos);
	}

	
}