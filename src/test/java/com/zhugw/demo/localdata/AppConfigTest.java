package com.zhugw.demo.localdata;

import com.zhugw.demo.localdata.dao.ApplyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by zhuguowei on 4/25/17.
 */
@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppConfigTest {
    //    private ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    @Autowired
    private ApplicationContext context;

    @Resource(name = "ruleDaoProperties")
    private Map<Integer, String> rule2DaoMap;

    @Test
    public void test_component_scan(){

        ApplyDAO applyDAO = (ApplyDAO) context.getBean("applyDAO");
        assertNotNull(applyDAO);
    }
    @Test
    public void test_get_bean(){
        Properties ruleDaoProperties = (Properties) context.getBean("ruleDaoProperties");
        System.out.println(ruleDaoProperties);
    }
    @Test
    public void test_properties_2_map(){
        assertEquals(3, rule2DaoMap.size());
    }

}
