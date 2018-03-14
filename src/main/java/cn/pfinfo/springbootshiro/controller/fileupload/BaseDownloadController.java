package cn.pfinfo.springbootshiro.controller.fileupload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.pfinfo.springbootshiro.entity.Attachment;
import cn.pfinfo.springbootshiro.service.common.IAttachmentService;
import org.springframework.core.env.Environment;

public abstract class BaseDownloadController extends BaseUpDownLoad{

	protected BaseDownloadController(Environment env, IAttachmentService attachmentService){
		super(env,attachmentService);
	}
	
	
	//文件下载相关代码
	protected String downloadFile(Attachment attachment, HttpServletResponse response) {
	    if (attachment != null&&attachment.getOldName()!=null) {
	    	String fileName = attachment.getOldName();
	        //设置文件路径
	        String realPath = attachment.getPath();
	        File file = new File(realPath , fileName);
	        if (file.exists()) {
	            response.setContentType("application/force-download");// 设置强制下载不打开
	            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
	            byte[] buffer = new byte[1024];
	            FileInputStream fis = null;
	            BufferedInputStream bis = null;
	            try {
	                fis = new FileInputStream(file);
	                bis = new BufferedInputStream(fis);
	                OutputStream os = response.getOutputStream();
	                int i = bis.read(buffer);
	                while (i != -1) {
	                    os.write(buffer, 0, i);
	                    i = bis.read(buffer);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                if (bis != null) {
	                    try {
	                        bis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (fis != null) {
	                    try {
	                        fis.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }
	    return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected abstract String doDownLoad(HttpServletRequest request,HttpServletResponse response);
	
}
