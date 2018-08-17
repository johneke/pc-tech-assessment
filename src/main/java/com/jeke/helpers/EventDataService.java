package com.jeke.helpers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeke.data.Event;

/**
 * Cheap data layer abstraction
 * 
 * @return
 */
@Service
public class EventDataService {
	private static final Logger logger = LoggerFactory.getLogger(EventDataService.class);

	private static String SOURCE_URL_PROP = "source";

	private Map<String, Event> eventsById;
	private List<Event> eventsOrdered;

	/**
	 * Loads and parses the event data JSON
	 * 
	 * @param source location to JSON data to use
	 */
	public EventDataService() {
		this.eventsById = new HashMap<>();
		this.eventsOrdered = new ArrayList<>();

		String source = System.getProperty(SOURCE_URL_PROP);

		if (source == null) {
			logger.error("No data source specified. Exitting...");
			System.exit(-1);
		}

		if (!loadEvents(source)) {
			logger.error("Failed to load data :( Exitting...");
			System.exit(-1);
		}
	}

	public List<Event> summaries(long page, long limit, Date start, Date end) {
		List<Event> targets = this.eventsOrdered.stream()
				.filter(event -> event.getDate().compareTo(start) > 0 && event.getDate().compareTo(end) < 0)
				.collect(Collectors.toList());
		
		int begin = (int)(page * limit);
		int stop = (begin + limit) >= targets.size() ? targets.size() - 1 : (int)(begin + limit);
		List<Event> results = new ArrayList<>();
		
		if (begin < targets.size()) {
			for (int i = begin; i < stop; i++) {
				Event event = new Event(targets.get(i));
				event.setDetails(null); // disable details field for summary
				results.add(event);
			}
		}
		
		return results;
	}

	public Event detail(String id) {
		return this.eventsById.get(id);
	}

	private boolean loadEvents(String source) {
		logger.info("Loading data source: " + source);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			Event[] events = mapper.readValue(new URL(source), Event[].class);
			for (Event event: events) {
				this.eventsById.put(event.getId(), event);
				this.eventsOrdered.add(event);
			}
			Collections.sort(this.eventsOrdered);
		} catch (IOException e) {
			logger.error("Error occured while downloading data source", e);
			return false;
		}
		
		return true;
	}
}
