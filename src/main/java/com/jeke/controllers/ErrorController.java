package com.jeke.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.jeke.data.ServerError;


@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	private static final String PATH = "/error";

	@Autowired
	private ErrorAttributes errorAttributes;

	@RequestMapping(value = PATH, produces = "application/json")
	public ResponseEntity<ServerError> error(HttpServletRequest request) {
		ServletWebRequest servletWebRequest = new ServletWebRequest(request);
		Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(servletWebRequest, false);
		Map<String, String> errorDetails = new HashMap<>();
		
		errorAttributes.forEach((attribute, value) -> {
			errorDetails.put(attribute, value.toString());
		});
		
		int status; 
		
		try {
			status = Integer.parseInt(errorAttributes.get("status").toString());
		} catch (NumberFormatException e) {
			logger.error("Failed to parse status from error attributes. Defaulting to 500 Internal Error", e);
			status = 500;
		}
		
		return new ResponseEntity<ServerError>(new ServerError(status, errorDetails), HttpStatus.valueOf(status));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
