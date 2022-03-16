package org.rest.demo.service;

import java.util.Arrays;
import org.rest.demo.exception.ResourceNotFoundException;
import org.rest.demo.logging.LogExecutionTime;
import org.rest.demo.model.Count;
import org.rest.demo.model.Response;
import org.rest.demo.model.UpdateContent;
import org.rest.demo.model.User;
import org.rest.demo.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Value("${users.feed.url}")
	private String url;
	
	@Autowired
	private HttpUtil httpUtil;
	
	@LogExecutionTime
	public Response getUserCount() {
		
		User[] users = fetchUsers(url, User[].class);
			    	    
		long totalUniqueUserIds = Arrays.stream(users)
	    								.map(s -> s.getUserId())
	    								.sorted()
	    								.distinct()
	    								.count();
	    
		logger.info("totalUniqueUserIds: {}", totalUniqueUserIds);
		
	    Count count = new Count();	    
	    count.setTotalUniqueUserIds(totalUniqueUserIds); 
	    
		Response res = new Response();
		res.setStatus(HttpStatus.OK.value());
		res.setRes(count);
	    
		logger.debug("Response: {}|", res);
		
		return res;
		
	}
	
	@LogExecutionTime
	public Response updatedUserList(UpdateContent uc) {
	
		User[] users = fetchUsers(url, User[].class);
		
		users = Arrays.stream(users)
				.peek(u -> {
					if(u.getId() == uc.getId()) {
						u.setTitle(uc.getContent());
						u.setBody(uc.getContent());
					}
				})
				.toArray(User[]::new);
		
		Response res = new Response();
		res.setStatus(HttpStatus.OK.value());
		res.setRes(users);
		
		logger.debug("Response: {}|", res);
		
		return res;
	}
	
	public <T> T fetchUsers(String url, Class<T> t) {
		try {
			return httpUtil.httpReq(url, t);
		} catch(Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
	
}
