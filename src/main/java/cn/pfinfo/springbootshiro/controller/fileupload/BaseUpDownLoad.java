package cn.pfinfo.springbootshiro.controller.fileupload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;

import cn.pfinfo.springbootshiro.entity.Attachment;
import cn.pfinfo.springbootshiro.service.common.IAttachmentService;

public abstract class BaseUpDownLoad {
	
    protected Environment env;
    protected IAttachmentService attachmentService;

    //上传文件的保存路径
    public final static String dirTemp = "temp";
    /**
     * 文件的访问前缀，默认为本服务器url，子类通过{@link #env}获取配置，使子类满足不同的文件前缀需求
     */
    protected String preDomain;
    /** 文件保存真实目录 */
    protected String localPath;
    /** 文件目录生成规则 */
    protected String fomat;


    public BaseUpDownLoad(Environment env,IAttachmentService attachmentService){
	    this.env =env;
	    this.attachmentService = attachmentService;
	    init();
    }
    private void init(){
        /**
         * 文件的访问前缀，默认为本服务器url
         */
        preDomain = env.getProperty("SERVER_PRE_URL");
        /** 文件保存真实目录 */
        localPath = env.getProperty("REAL_SAVE_PATH");
        /** 文件目录生成规则 */
        fomat = env.getProperty("DIR_SIMPLE_DATE_FORMAT");

    }


	/**
	 * 根据上下文环境去查询附件信息
	 * @param request
	 * @return
	 */
	protected abstract Attachment findAttachment(HttpServletRequest request);

}
