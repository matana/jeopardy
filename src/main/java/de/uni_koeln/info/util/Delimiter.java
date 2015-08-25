package de.uni_koeln.info.util;

public enum Delimiter {
	
	ASCII_DELIMITER("\\W"), UNICODE_AWARE_DELIMITER("[^\\p{L}]");
	
	private String pattern;
	
	Delimiter(final String pattern) {
		this.pattern = pattern;
	}

	public String getValue() {
		return pattern;
	}

}
