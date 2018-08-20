package com.jeke.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeke.config.Configuration;
import com.jeke.data.Event;
import com.jeke.data.HttpError;
import com.jeke.data.PaginatedResource;
import com.jeke.errors.InvalidInputException;
import com.jeke.errors.UnknownEventException;
import com.jeke.helpers.EventDataService;
import com.jeke.helpers.EventDataServicePagedResult;

@RestController
public class EventController {
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private EventDataService dataService;
	
	@RequestMapping(value = Configuration.PREFIX + Configuration.VERSION + "/events", method=RequestMethod.GET)
	public PaginatedResource<Event> events(
		@RequestParam(value="page", defaultValue="0") String pageStr,
		@RequestParam(value="limit", defaultValue="25") String limitStr,
		@RequestParam(value="startdate", defaultValue="0") String startDateStr,
		@RequestParam(value="enddate", defaultValue="") String endDateStr) {
		
		int page = parsePositiveInteger(pageStr, "page");
		int limit = parsePositiveInteger(limitStr, "limit");
		Date startDate = new Date(parsePositiveLong(startDateStr, "startdate"));
		Date endDate = endDateStr.isEmpty() ? new Date(Long.MAX_VALUE) : new Date(parsePositiveLong(endDateStr, "enddate"));

		if (limit <= 0) {
			throw new InvalidInputException("limit", "must be greater than zero");
		}
		
		EventDataServicePagedResult result = dataService.summaries(page, limit, startDate, endDate);
		int numPages = result.getTotal()/limit;
		
		PaginatedResource<Event> paginatedEvents = new PaginatedResource<>();
		paginatedEvents.setPage(result.getPage());
		paginatedEvents.setLimit(limit);
		paginatedEvents.setItems(result.getEvents());
		paginatedEvents.setTotalPages(result.getTotal() % limit == 0 ? numPages : numPages + 1);
		
		return paginatedEvents;
	}

	@RequestMapping(value = Configuration.PREFIX + Configuration.VERSION + "/events/{id}", method=RequestMethod.GET)
	public Event eventDetails(@PathVariable("id") String id) {
		Event event = dataService.detail(id);
		if (event != null) {
			return event;
		}
		
		throw new UnknownEventException(id);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<HttpError> handleInvalidInputException(InvalidInputException e) {
		return new ResponseEntity<HttpError>(new HttpError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnknownEventException.class)
	public ResponseEntity<HttpError> handleUnknownEventException(UnknownEventException e) {
		return new ResponseEntity<HttpError>(new HttpError(404, e.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	private int parsePositiveInteger(String variable, String variableName) {
		try {
			int var = Integer.parseInt(variable);
			if (var < 0) {
				throw new InvalidInputException(variableName, "must be a positive integer");
			}
			return var;
		} catch (NumberFormatException e) {
			logger.error("Integer parse failed", e);
			throw new InvalidInputException(variableName, "not a valid integer", e);
		}
	}
	
	private long parsePositiveLong(String variable, String variableName) {
		try {
			long var = Long.parseLong(variable);
			if (var < 0) {
				throw new InvalidInputException(variableName, "must be a positive integer");
			}
			return var;
		} catch (NumberFormatException e) {
			logger.error("Integer parse failed", e);
			throw new InvalidInputException(variableName, "not a valid integer", e);
		}
	}
}
