package thanhnvMigi.migi.result;

import java.io.Serializable;

import jakarta.ws.rs.core.Response.Status;

public class ServiceResult<T> implements Serializable {

	private int status;
	private String message;
	private T data;

	public ServiceResult() {
		super();
	}

	public ServiceResult(int status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
