package org.rest.demo.model;

public class Response {

	private int status;
	private Object res;
	private String error;
	
	public Response() {		
	}

	public Response(int status, Object res, String error) {
		super();
		this.status = status;
		this.res = res;
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getRes() {
		return res;
	}

	public void setRes(Object res) {
		this.res = res;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", res=" + res + ", error=" + error + "]";
	}	

}
