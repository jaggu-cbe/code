package org.rest.demo.util;

import java.util.Arrays;

import org.rest.demo.logging.LogExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@LogExecutionTime
	public <T> T httpReq(String url, Class<T> t) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, t).getBody();
	}
	
}
