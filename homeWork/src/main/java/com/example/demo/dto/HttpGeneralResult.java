package com.example.demo.dto;


public class HttpGeneralResult {

	private int responseCode;
	private String httpErrorInfo;
	private String responseValue;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getHttpErrorInfo() {
		return httpErrorInfo;
	}
	public void setHttpErrorInfo(String httpErrorInfo) {
		this.httpErrorInfo = httpErrorInfo;
	}
	public String getResponseValue() {
		return responseValue;
	}
	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}
	
	
	
	 
}
