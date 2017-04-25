package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;

import java.util.HashMap;
import java.util.Map;

/**
 * 收费数据门面模式
 * Created by zhuguowei on 4/21/17.
 */
public class FeeDataManagerFacade {
    private static Map<String, AbstractFeeDataManagerTemplate> code2FeeDataManagerMap = new HashMap();
    static{
        code2FeeDataManagerMap.put("NAME_IDCARD_VERIFICATION", new NameIdCardVerificationManager());
        //...
    }
    public FeeData getFeeData(String code, Map<String, Object> params){
        return code2FeeDataManagerMap.get(code).getFeeData(params);
    }
}
