package fr.thomas.proto0.log;

public enum ELogLevel {
	
	INFO("INFO"),
	WARNING("WARN"),
	CRITICAL("CRITICAL"),
	DEBUG("DEBUG");
	
	private String header;
	
	private ELogLevel(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return header;
	}

}
