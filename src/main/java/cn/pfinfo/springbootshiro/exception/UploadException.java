package cn.pfinfo.springbootshiro.exception;

public class UploadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1964060997064463521L;
	
	public UploadException(){
		super();
	}
	public UploadException(String msg){
		super(msg);
	}

}
