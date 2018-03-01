package cn.pfinfo.springbootshiro.dao.exception;

/**
 * Created by panfei on 2018/1/19.
 */
public class DaoException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6342043506867147436L;

	public DaoException(String msg){
        super(msg);
    }
}
