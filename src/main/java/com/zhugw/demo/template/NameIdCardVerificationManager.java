package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;

import java.util.Map;

/**
 * 姓名身份证号码实名认证
 * Created by zhuguowei on 4/21/17.
 */
public class NameIdCardVerificationManager extends AbstractFeeDataManagerTemplate {


    protected void buildParams(Map params) {
        // 组装此接口特有且恒定入参 如
        params.put("code", "name_idcard_verfication");

    }

    protected FeeData resolveResponse(String response) {
        return null;
    }
}
