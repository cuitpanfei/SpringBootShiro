package cn.pfinfo.springbootshiro.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;



/**
 * 字典表
 * 
 * @author cuitpf
 */
@Setter
@Getter
public class DictDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//标签名
	private String name;
	//数据值
	private String value;
	//类型
	private String type;
	//描述
	private String description;
	//排序（升序）
	private BigDecimal sort;
	//父级编号
	private Long parentId;
	//创建者
	private Integer createBy;
	//创建时间
	private Date createDate;
	//更新者
	private Long updateBy;
	//更新时间
	private Date updateDate;
	//备注信息
	private String remarks;
	//删除标记
	private String delFlag;

	@Override
	public String toString() {
		return "DictDO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				", type='" + type + '\'' +
				", description='" + description + '\'' +
				", sort=" + sort +
				", parentId=" + parentId +
				", createBy=" + createBy +
				", createDate=" + createDate +
				", updateBy=" + updateBy +
				", updateDate=" + updateDate +
				", remarks='" + remarks + '\'' +
				", delFlag='" + delFlag + '\'' +
				'}';
	}
}
