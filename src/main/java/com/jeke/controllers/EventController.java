package com.jeke.controllers;

import java.util.Date;
import java.util.List;

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

import com.jeke.data.Event;
import com.jeke.data.HttpError;
import com.jeke.errors.InvalidInputException;
import com.jeke.helpers.EventDataService;

@RestController
public class EventController {
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private EventDataService dataService;
	
	@RequestMapping(value = "/events", method=RequestMethod.GET)
	public List<Event> events(
		@RequestParam(value="page", defaultValue="0") String pageStr,
		@RequestParam(value="limit", defaultValue="20") String limitStr,
		@RequestParam(value="startdate", defaultValue="0") String startDateStr,
		@RequestParam(value="enddate", defaultValue="") String endDateStr) {
		
		long page = parsePositiveInteger(pageStr, "page");
		long limit = parsePositiveInteger(limitStr, "limit");
		Date startDate = new Date(parsePositiveInteger(startDateStr, "startdate"));
		Date endDate = endDateStr.isEmpty() ? new Date(Long.MAX_VALUE) : new Date(parsePositiveInteger(endDateStr, "enddate"));

		if (limit <= 0) {
			throw new InvalidInputException("limit", "must be greater than zero");
		}
		
		return dataService.summaries(page, limit, startDate, endDate);
	}

	@RequestMapping(value = "/events/{id}", method=RequestMethod.GET)
	public Event eventDetails(@PathVariable("id") String id) {
		return dataService.detail(id);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<HttpError> handleInvalidInputException(InvalidInputException e) {
		return new ResponseEntity<HttpError>(new HttpError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	private long parsePositiveInteger(String variable, String variableName) {
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
