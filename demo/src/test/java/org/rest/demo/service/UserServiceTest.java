package org.rest.demo.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rest.demo.JsonFileLoader;
import org.rest.demo.exception.ResourceNotFoundException;
import org.rest.demo.model.Count;
import org.rest.demo.model.Response;
import org.rest.demo.model.UpdateContent;
import org.rest.demo.model.User;
import org.rest.demo.util.HttpUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private HttpUtil httpUtil;
	
	@InjectMocks
	private UserService userService;
	
	private static User[] users;
	
	@BeforeAll
	public static void intialize() throws IOException {
		JsonFileLoader mapper = new JsonFileLoader();
		users =  mapper.loadTestJson("users.json");
	}
	
	@Test
	public void getUserCount_returnResult() throws Exception {	
		BDDMockito.given(userService.fetchUsers(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(users);		
		Response actualRes = userService.getUserCount();			
		Assertions.assertThat(((Count)actualRes.getRes()).getTotalUniqueUserIds()).isEqualTo(10);		
	}
		
	@Test
	public void getUserCount_returnError() throws Exception {	
		BDDMockito.given(userService.fetchUsers(ArgumentMatchers.any(), ArgumentMatchers.any())).willThrow(new ResourceNotFoundException("not found"));							
		try {
			userService.getUserCount();
			fail("expected exception was not occured.");
		} catch(ResourceNotFoundException e) {
			
		}	
	}

	@Test
	public void updatedUserList_returnResult() {
		UpdateContent uc = new UpdateContent();
		uc.setId(4);
		uc.setContent("1800Flowers");
		
		BDDMockito.given(userService.fetchUsers(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(users);		
		Response actualRes = userService.updatedUserList(uc);			
		Assertions.assertThat(((User[])actualRes.getRes())[3].getTitle()).isEqualTo("1800Flowers");
	}
	
	@Test
	public void updatedUserList_returnError() throws Exception {
		UpdateContent uc = new UpdateContent();
		uc.setId(4);
		uc.setContent("1800Flowers");
		
		BDDMockito.given(userService.fetchUsers(ArgumentMatchers.any(), ArgumentMatchers.any())).willThrow(new ResourceNotFoundException("not found"));							
		
		try {
			userService.updatedUserList(uc);
			fail("expected exception was not occured.");
		} catch(ResourceNotFoundException e) {
			
		}	
	}
	
}
