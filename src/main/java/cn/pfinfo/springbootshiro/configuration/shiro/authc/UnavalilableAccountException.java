package cn.pfinfo.springbootshiro.configuration.shiro.authc;

import org.apache.shiro.authc.DisabledAccountException;

/**
 * 
 * @author panfei
 * @since 0.1
 * @version 2018
 */
public class UnavalilableAccountException extends DisabledAccountException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7138970177495383239L;
	/**
     * Creates a new UnavalilableAccountException.
     */
	public UnavalilableAccountException(){
		super();
	}
	/**
     * Constructs a new UnavalilableAccountException.
     *
     * @param message the reason for the exception
     */
	public UnavalilableAccountException(String msg){
		super(msg);
	}
	/**
     * Constructs a new UnavalilableAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
	public UnavalilableAccountException(Throwable cause) {
        super(cause);
    }
	/**
	 * Constructs a new UnavalilableAccountException.
	 *
	 * @param message the reason for the exception
	 * @param cause the underlying Throwable that caused this exception to be thrown.
	 */
	public UnavalilableAccountException(String msg,Throwable cause) {
		super(msg,cause);
	}
}
