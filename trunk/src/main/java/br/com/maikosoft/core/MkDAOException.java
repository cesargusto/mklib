package br.com.maikosoft.core;

@SuppressWarnings("serial")
public class MkDAOException extends Exception {

	public MkDAOException(Exception e) {
		super(e);
	}

	public MkDAOException(String msg) {
		super(msg);
	}
	
	public MkDAOException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
