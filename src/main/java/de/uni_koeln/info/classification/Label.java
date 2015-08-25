package de.uni_koeln.info.classification;

public enum Label {
	
	BAD("bad"), AVERAGE("average"), EXCELLENT("excellent"), UNKNOWN("unknown");
	
	private String label;
	
	Label(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public Label computeLabel(int score) {
		switch(score) {
			case 1 : return BAD; 
			case 2 : return AVERAGE; 
			case 3 : return EXCELLENT; 
			default : break;
		}
		return UNKNOWN;
	}

}
