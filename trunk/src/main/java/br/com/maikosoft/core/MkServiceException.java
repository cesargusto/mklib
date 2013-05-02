package br.com.maikosoft.core;


@SuppressWarnings("serial")
public class MkServiceException extends Exception {

	public MkServiceException(Exception e) {
		super(e);
	}

	public MkServiceException(String msg) {
		super(msg);
	}

	public MkServiceException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
