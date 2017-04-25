package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;

import java.util.Map;

/**
 * 手机号码实名认证
 * Created by zhuguowei on 4/21/17.
 */
public class MobileVerificationManager extends AbstractFeeDataManagerTemplate {
    protected void buildParams(Map params) {
        params.put("code", "mobile_verification");
    }

    protected FeeData resolveResponse(String response) {
        return null;
    }
}
