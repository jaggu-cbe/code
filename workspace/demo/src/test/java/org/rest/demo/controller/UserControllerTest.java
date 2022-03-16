package org.rest.demo.controller;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.rest.demo.JsonFileLoader;
import org.rest.demo.exception.ResourceNotFoundException;
import org.rest.demo.model.Count;
import org.rest.demo.model.Response;
import org.rest.demo.model.UpdateContent;
import org.rest.demo.model.User;
import org.rest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static User[] users;

	private static User[] updatedUsers;
	
	@BeforeAll
	public static void intialize() throws IOException {
		JsonFileLoader mapper = new JsonFileLoader();
		users =  mapper.loadTestJson("users.json");
		updatedUsers = mapper.loadTestJson("users_updated.json");
	}
	
	@Test
	public void getUserCount_returnResult() throws Exception {
		
		Count count = new Count();	    
	    count.setTotalUniqueUserIds(10); 
	    
		Response res = new Response();
		res.setStatus(HttpStatus.OK.value());
		res.setRes(count);
		
		BDDMockito.given(userService.getUserCount()).willReturn(res);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/count"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
		  .andDo(MockMvcResultHandlers.print())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.res.totalUniqueUserIds", CoreMatchers.is(10)));
		
	}
	
	@Test
	public void getUserCount_returnError() throws Exception {		
		BDDMockito.given(userService.getUserCount()).willThrow(new ResourceNotFoundException("not found"));
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/count"));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
		  .andDo(MockMvcResultHandlers.print())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)));
	}
	
	
	@Test
	public void updatedUserList_returnResult() throws JsonProcessingException, Exception {
		UpdateContent uc = new UpdateContent();
		uc.setId(4);
		uc.setContent("1800Flowers");
		
		Response res = new Response();
		res.setStatus(HttpStatus.OK.value());
		res.setRes(updatedUsers);
		
		BDDMockito.given(userService.updatedUserList(ArgumentMatchers.any(UpdateContent.class))).willReturn(res);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/upateList")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(uc)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
		  .andDo(MockMvcResultHandlers.print())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.res[3].title", CoreMatchers.is("1800Flowers")))
		  .andExpect(MockMvcResultMatchers.jsonPath("$.res[3].body", CoreMatchers.is("1800Flowers")));
	}
	
	@Test
	public void updatedUserList_returnError() throws JsonProcessingException, Exception {
		UpdateContent uc = new UpdateContent();
		uc.setId(4);
		uc.setContent("1800Flowers");
			
		BDDMockito.given(userService.updatedUserList(ArgumentMatchers.any(UpdateContent.class))).willThrow(new ResourceNotFoundException("not found"));
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/upateList")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(uc)));

		response.andExpect(MockMvcResultMatchers.status().isNotFound())
		  .andDo(MockMvcResultHandlers.print())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)));
	}
	
	
	@Test
	public void updatedUserList_payloadError() throws JsonProcessingException, Exception {
		String payload = "{ \"dummy\": \"dummy\"}";
		
		Response res = new Response();
		res.setStatus(HttpStatus.OK.value());
		res.setRes(updatedUsers);
		
		BDDMockito.given(userService.updatedUserList(ArgumentMatchers.any(UpdateContent.class))).willReturn(res);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/upateList")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(payload)));

		response.andExpect(MockMvcResultMatchers.status().isInternalServerError())
		  .andDo(MockMvcResultHandlers.print())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(500)));
	}
	
	
	@Test
	public void updatedUserList_validationError() throws JsonProcessingException, Exception {
		UpdateContent uc = new UpdateContent();
		uc.setId(200);
		uc.setContent("1800Flowers");
		
		Response res = new Response();
		res.setStatus(HttpStatus.OK.value());
		res.setRes(updatedUsers);
		
		BDDMockito.given(userService.updatedUserList(ArgumentMatchers.any(UpdateContent.class))).willReturn(res);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/upateList")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(uc)));

		response.andExpect(MockMvcResultMatchers.status().isBadRequest())
		  .andDo(MockMvcResultHandlers.print())
		  .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)));
	}
	
	@Test
	public void invalidRequest() throws JsonProcessingException, Exception {
		UpdateContent uc = new UpdateContent();
		uc.setId(200);
		uc.setContent("1800Flowers");
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/hit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(uc)));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
		  .andDo(MockMvcResultHandlers.print());		  
	}
	
}
