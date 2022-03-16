package org.rest.demo.controller;

import javax.validation.Valid;

import org.rest.demo.logging.LogExecutionTime;
import org.rest.demo.model.Response;
import org.rest.demo.model.UpdateContent;
import org.rest.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/count")
	@LogExecutionTime
	public ResponseEntity<Response> getUserCount() {		
		return new ResponseEntity<Response>(userService.getUserCount(), HttpStatus.OK);		
	}
	
	
	@PutMapping("/upateList")
	@LogExecutionTime
	public ResponseEntity<Response> updatedUserList(@Valid @RequestBody UpdateContent uc) {
		return new ResponseEntity<Response>(userService.updatedUserList(uc), HttpStatus.OK);
	}
	
}
