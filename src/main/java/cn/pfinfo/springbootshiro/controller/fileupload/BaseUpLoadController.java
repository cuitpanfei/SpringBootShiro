package cn.pfinfo.springbootshiro.controller.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import cn.pfinfo.springbootshiro.common.config.shiro.ShiroUtil;
import cn.pfinfo.springbootshiro.common.exception.BDException;
import cn.pfinfo.springbootshiro.common.util.HttpContextUtils;
import cn.pfinfo.springbootshiro.common.util.Result;
import cn.pfinfo.springbootshiro.common.util.file.FileType;
import cn.pfinfo.springbootshiro.common.util.file.FileTypeJudgeUtil;
import cn.pfinfo.springbootshiro.common.util.file.FileUtil;
import cn.pfinfo.springbootshiro.entity.Attachment;
import cn.pfinfo.springbootshiro.service.common.IAttachmentService;

/**
 * 公共上传控制器
 * @author panfei
 * @createDate 2018-03-10
 *
 */
public abstract class BaseUpLoadController extends BaseUpDownLoad {

	public BaseUpLoadController(Environment env, IAttachmentService attachmentService){
		super(env,attachmentService);
	}
	/**
	 *  单文件上传
	 * @param file 上传文件
	 * @return 上传结果
	 * @throws Exception
	 */
	protected Result Upload(MultipartFile file) throws Exception{
	    if (file.isEmpty()) {
	        return Result.error("文件为空");
	    }
	    Attachment info = new Attachment();
	    info.setUserId(ShiroUtil.getCurrentUserId());
	    // 上传文件原名
	    info.setOldName(file.getOriginalFilename());
	    // 上传文件大小
	    info.setFileSize(file.getSize());
	    // 上传文件类型
	    info.setType(file.getContentType());
	    Attachment attachment = findAttachment(HttpContextUtils.getHttpServletRequest());
	    Map<String,Object> map = new HashMap<>(16);
	    if(attachment==null){
	    	doCustomChick(file);
	    	String fileType = getFileType(file.getInputStream());
	    	String savePath = createSavePath(fileType);
	    	// 生成实际存储的真实文件名
            String realName = FileUtil.createUUIDName();
            FileUtil.uploadFile(file.getBytes(), savePath, realName);
            info.setNewName(realName);
            info.setSuffix(realName.substring(realName.lastIndexOf(".")));
            info.setPath(savePath+realName);
	    	attachment = attachmentService.save(info);
	    }
    	map.put("msg", "上传成功");
    	map.put("url", preDomain+attachment.getPath());
    	return Result.ok(map);
	}

	private String createSavePath(String filetype) throws IOException {
		filetype = filetype==null?"":filetype;
		String savePath = localPath;
    	// 文件目录
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        String ymd = sdf.format(new Date());
        String imageDate="/"+filetype+"/" + ymd + "/";
        savePath += imageDate;
        return savePath;
	}

	/**
	 * 自定义验证规则，针对不同的文件，可能验证规则会不一样，这里提供抽象方法用供子类实现
	 * @param file 上传的文件
	 * @throws BDException 不符合规则时抛出自定义异常
	 */
	protected abstract void doCustomChick(MultipartFile file) throws BDException;


	/**
	 * 根据文件流获取文件类型
	 * @param is 文件流
	 * @return 文件类型
	 * @throws IOException
	 */
	protected String getFileType(InputStream is) throws IOException {
		FileType type = FileTypeJudgeUtil.getType(is);
		return FileTypeJudgeUtil.isFileType(type);
	}

}
