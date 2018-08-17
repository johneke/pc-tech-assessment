package com.jeke.data;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event implements Comparable<Event> {
	private String id;
    @JsonProperty("event_date") private Date date;
    @JsonProperty("event_type") private String type;
    @JsonProperty("event_summary") private String summary;
    @JsonProperty("event_size") private int metric;
    @JsonProperty("event_details") private String details;
	
	public Event() {
		id = UUID.randomUUID().toString();
	}
	
	public Event(Event o) {
		this.id = o.id;
		this.date = o.date;
		this.type = o.type;
		this.summary = o.summary;
		this.metric = o.metric;
		this.details = o.details;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public int getMetric() {
		return metric;
	}
	
	public void setMetric(int metric) {
		this.metric = metric;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.date.toString() + " " + this.type + " " + this.summary + " " + this.metric + " " + this.details;
	}

	@Override
	public int compareTo(Event o) {
		return this.getDate().compareTo(o.getDate());
	}
}
