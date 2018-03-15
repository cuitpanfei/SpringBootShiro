package cn.pfinfo.springbootshiro.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "SITE")
@NamedQuery(name="SiteDo.findAll", query="SELECT a FROM SiteDo a")
public class SiteDo {

	/** 序号 */
	@Id
	@SequenceGenerator(name="SITE_ID_GENERATOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SITE_ID_GENERATOR")
	private Long id;

	/** 网站名称（必须是中文） */
	@Column(name="sitename",columnDefinition="varchar2(255) NOT NULL")
	private String siteName;
	/** 网站域名*/
	@Column(name="domainname",columnDefinition="varchar2(255) NOT NULL")
	private String domainName;
	/** 
	 * 网站的公网IP地址<br><br>
	 * IPv4常用的点分十进制形式进行表示最长长度为15，例如“255.255.255.255”<br>
	 * 使用16进制形式进行表示如“ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff”,<br>
	 * 这里IPv6地址字符串形式最大长度为128/4+7+1=40字节<br>
	 * 但是IPv4映射的IPv6地址可能出现这种格式“ffff:ffff:ffff:ffff:ffff:ffff:255:255:255:255”，<br>
	 * 也就是45字节，加上结束符就是46字节了
	 */
	@Column(name="siteIP",columnDefinition="varchar2(46) NOT NULL")
	private String siteIP;
	/** 网站语言类别（中文简体/English/...） 这里是默认中文简体*/
	@Column(name="siteLanguage",columnDefinition="varchar2(255) NOT NULL")
	private String siteLanguage = "中文简体";
	/** 网站负责人姓名 */
	@Column(name="sitePersonResponsible",columnDefinition="varchar2(255) NOT NULL")
	private String sitePersonResponsible;
	/** 网站联系办公电话 */
	@Column(name="siteTel",columnDefinition="varchar2(255)")
	private String siteTel;
	/** 
	 * 网站联系手机号码 
	 * <br>
	 * <br>
	 * eg:+8618483619601
	 */
	@Column(name="sitePhone",columnDefinition="varchar2(14) NOT NULL ")
	private String sitePhone;
	/** 网站联系电子邮箱 */
	private String siteEmail;
	/** 网站服务内容 */
	@Lob
	private String serviceContent;
	/** 是否是模板 */
	@Column(name="isModel",columnDefinition="NUMBER(1,0) NOT NULL")
	private boolean isModel = false;
	/** 静态化模板url */
	private String modelUrl;
	
}
