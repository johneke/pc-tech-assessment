package com.jeke.errors;

public class UnknownEventException extends RuntimeException {
	private static final long serialVersionUID = 6639213080002016213L;

	public UnknownEventException(String id) {
		super("Unknown event id: " + id);
	}
	
	public UnknownEventException(String id, Throwable cause) {
		super(id, cause);
	}
}
