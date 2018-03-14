package cn.pfinfo.springbootshiro.common.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "LOG")
@NamedQuery(name="LogDO.findAll", query="SELECT a FROM LogDO a")
public class LogDO {
	
	/** 序号 */
	@Id
	@SequenceGenerator(name="LOG_ID_GENERATOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_ID_GENERATOR")
	private Long id;

	/** 用户id */
	private Long userId;
	/** 用户名称*/
	private String username;
	/** 操作 */
	private String operation;
	/** 执行时常 */
	private Integer time;
	/** 方法 */
	private String method;
	/** 参数 */
	@Lob
	private String params;
	/** 操作者ip */
	private String ip;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtCreate;

	@Override
	public String toString() {
		return "LogDO{" +
				"id=" + id +
				", userId=" + userId +
				", username='" + username + '\'' +
				", operation='" + operation + '\'' +
				", time=" + time +
				", method='" + method + '\'' +
				", params='" + params + '\'' +
				", ip='" + ip + '\'' +
				", gmtCreate=" + gmtCreate +
				'}';
	}
}