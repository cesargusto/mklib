package br.com.maikosoft.layout.swing;

@SuppressWarnings("serial")
public class MkException extends Exception {

	public MkException(Exception e) {
		super(e);
	}

	public MkException(String msg) {
		super(msg);
	}
	
	public MkException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
