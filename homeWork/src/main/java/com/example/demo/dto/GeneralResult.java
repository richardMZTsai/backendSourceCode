package com.example.demo.dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.demo.enumModel.GeneralResultStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;




public class GeneralResult {

	@JsonIgnore
	Logger logger = LogManager.getLogger();

	//反饋結果，0:成功、非0:失敗
	private int resultCode;

	//反饋錯誤訊息
	private String errorInfo;

	//備註
	private String note;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		if(errorInfo!=null && errorInfo.trim().length()>0 
				&& !errorInfo.equals(GeneralResultStatus.OK.getDescription())
				&& !errorInfo.equals("OK")) {
			logger.error(errorInfo);
		}
		this.errorInfo = errorInfo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setErrorInfo(Thread thread, Exception e) {
		this.errorInfo = e.toString() + ", className:" + Thread.currentThread().getStackTrace()[2].getClassName()
				+ ", methodName:" + Thread.currentThread().getStackTrace()[2].getMethodName()
				+", error methodName:" + e.getStackTrace()[2].getMethodName()
				+ ", line number:"
				+ e.getStackTrace()[2].getLineNumber();
		logger.error(this.errorInfo);
		logger.error(e.toString(), e);
	}

	public GeneralResult() {
		this.setResultCode(GeneralResultStatus.Unknown.getStatus());
	}

}
