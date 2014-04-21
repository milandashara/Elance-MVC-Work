package com.coe.mxcommunity.ajax;

public class AjaxResult {
	public boolean status;
	public String error;
	public Object data;
	
	public AjaxResult(){
		this.error = "";
		this.data = null;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
