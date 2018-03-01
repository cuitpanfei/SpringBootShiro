package cn.pfinfo.springbootshiro.controller.fileupload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.pfinfo.springbootshiro.exception.UploadException;

/**
 * 图片上传
 * @author panfei
 * @createDate 2018-03-01
 */
@Controller
public class UploadController {

    @Autowired
    private Environment env;
	//上传文件的保存路径
    protected String dirTemp = "temp";
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
	protected Map<String,Object> doPost(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
        	//文件保存真实目录
            String localPath = env.getProperty("REAL_SAVE_IMAGE_PATH");
            //图片服务器访问前缀
            String preImageDomain = env.getProperty("UPLOAD_IMAGE_SERVER_PRE_URL");
            String fomat = env.getProperty("DIR_SIMPLE_DATE_FORMAT");
            String compon = localPath;
            String savePath = compon;

            // 临时文件目录
            String tempPath =savePath+ dirTemp;
            SimpleDateFormat sdf = new SimpleDateFormat(fomat);
            String ymd = sdf.format(new Date());
            String imageDate="/image/" + ymd + "/";
            savePath += imageDate;
            //创建文件夹
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            tempPath += "/" + ymd + "/";
            //创建临时文件夹
            File dirTempFile = new File(tempPath);
            if (!dirTempFile.exists()) {
                dirTempFile.mkdirs();
            }

            boolean allowed = file!=null;
            if (!allowed) {
            	throw new UploadException("不支持的类型");
            }
            String SizeThreshold=env.getProperty("FILE_SIZE_MAX");
            long maxSize=Long.parseLong(SizeThreshold);

            // 图片大小限制
            if (file.getSize() > maxSize) {
            	throw new UploadException("图片大小不能超过"+maxSize/1024/1024+"M");
            }
            // 包含原始文件名的字符串
            String fi = file.getOriginalFilename();
            // 提取文件拓展名
            String fileNameExtension = fi.substring(fi.lastIndexOf("."), fi.length());
            // 生成实际存储的真实文件名
            String realName = UUID.randomUUID().toString() + fileNameExtension;
            // 图片存放的真实路径
            String realPath = savePath + "/" + realName;
            // 将文件写入指定路径下
            Files.write(Paths.get(realPath), file.getBytes());
            // 返回图片的URL地址
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url",preImageDomain+imageDate+ realName);
        }catch (Exception e){
        	resultMap.put("success", 0);
            resultMap.put("message", "上传失败！"+e.getMessage());
            e.printStackTrace();
        }
        return resultMap;

    }

}
