package com.jeke.helpers;

import java.util.List;

import com.jeke.data.Event;

public class EventDataServicePagedResult {
	private int page;
	private int total;
	private List<Event> events;
	
	public EventDataServicePagedResult(int page, int total, List<Event> events) {
		this.page = page;
		this.total = total;
		this.events = events;
	}

	public int getPage() {
		return page;
	}

	public int getTotal() {
		return total;
	}

	public List<Event> getEvents() {
		return events;
	}
}
