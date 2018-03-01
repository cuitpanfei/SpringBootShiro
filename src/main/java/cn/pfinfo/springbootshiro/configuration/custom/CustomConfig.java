package cn.pfinfo.springbootshiro.configuration.custom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

	@Value("${base.img.path}")
	public static String UPLOADED_FOLDER = System.getProperty("user.dir")+"/upload/img/";
}
