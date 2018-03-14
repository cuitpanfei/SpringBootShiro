package cn.pfinfo.springbootshiro.controller.fileupload;

import javax.servlet.http.HttpServletRequest;

import cn.pfinfo.springbootshiro.service.common.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.pfinfo.springbootshiro.common.exception.BDException;
import cn.pfinfo.springbootshiro.common.util.Result;
import cn.pfinfo.springbootshiro.entity.Attachment;

/**
 * 图片上传
 * @author panfei
 * @createDate 2018-03-01
 */
@Controller
public class UploadController extends BaseUpLoadController {

    public UploadController(Environment env, IAttachmentService attachmentService) {
        super(env, attachmentService);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
	protected Result doPost(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        Result resultMap = Result.ok();
		try{
            //图片服务器访问前缀
            preDomain = env.getProperty("SERVER_PRE_URL");
            resultMap = super.Upload(file);
        }catch (Exception e){
        	resultMap = Result.error("上传失败！\r\n"+e.getMessage());
            e.printStackTrace();
        }
        return resultMap;

    }

	@Override
	protected void doCustomChick(MultipartFile file) throws BDException {
		String SizeThreshold=env.getProperty("FILE_SIZE_MAX");
        long maxSize=Long.parseLong(SizeThreshold);
        // 图片大小限制
        if (file.getSize() > maxSize) {
        	throw new BDException("图片大小不能超过"+maxSize/1024/1024+"M");
        }
	}

	@Override
	protected Attachment findAttachment(HttpServletRequest request) {
		return null;
	}

}
