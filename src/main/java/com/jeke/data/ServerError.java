package com.jeke.data;

import java.util.Map;

public class ServerError {
	private int status;
	private Map<String, String> details;
	
	public ServerError(int status, Map<String, String> details) {
		this.status = status;
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}
}
