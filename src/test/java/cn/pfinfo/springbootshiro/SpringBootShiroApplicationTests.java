package cn.pfinfo.springbootshiro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.pfinfo.springbootshiro.service.site.IMenuService;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootShiroApplication.class)
@WebAppConfiguration
@Log4j
public class SpringBootShiroApplicationTests {
	
	@Autowired
	private IMenuService menuServiceImpl;

	@Test
	public void contextLoads() {
	}
	@Test
	public void menuTest() {
		log.info("--------------------\n menuServiceImpl:"+menuServiceImpl);
		log.info("--------------------");
		menuServiceImpl.findAll();
	}

}
