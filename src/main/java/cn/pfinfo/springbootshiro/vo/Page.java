package cn.pfinfo.springbootshiro.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7556963508459099138L;
	
	private String limitAllAppoint;
	private String currentPageAllAppoint;
	private String dataLength;
	private T data;

}
