package com.jeke.errors;

public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = -6583169505141745669L;
	
	private String var;
	
	public InvalidInputException(String var, String message) {
		super(message);
		
		this.var = var;
	}
	
	public InvalidInputException(String var, String message, Throwable cause) {
		super(message, cause);
		
		this.var = var;
	}
	
	public String getVar() {
		return this.var;
	}
	
	@Override
	public String getMessage() {
		return String.format("Invalid input for %s, %s", this.var, super.getMessage());
	}
}
