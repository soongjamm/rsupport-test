package com.rsupport.soongjamm.notice.application.impl;

public class UnauthorizedTaskException extends RuntimeException {
	public UnauthorizedTaskException() {
		super();
	}

	public UnauthorizedTaskException(String msg) {
		super(msg);
	}
}
