package com.zhugw.demo.localdata;

import com.zhugw.demo.localdata.service.LocalDataRuleService;
import com.zhugw.demo.template.model.ApplyInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by zhuguowei on 4/25/17.
 */
@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class LocalDataRuleServiceTest {
    @Autowired
    private LocalDataRuleService localDataRuleService;
    @Test
    public void batchQueryLocalData() throws Exception {
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setIdcard("123456199001011233");
        Map map = localDataRuleService.batchQueryLocalData(applyInfo, newArrayList(1,2,3));
        assertEquals(3, map.size());
        System.out.println(map);
    }

}