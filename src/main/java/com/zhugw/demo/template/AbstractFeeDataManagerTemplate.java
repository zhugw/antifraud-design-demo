package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;

import java.util.Map;

/**
 * Created by zhuguowei on 4/21/17.
 */
public abstract class AbstractFeeDataManagerTemplate {
    protected abstract void buildParams(Map params);

    public FeeData getFeeData(Map params){
        buildParams(params);
        String response = sendRequest(params);
        return resolveResponse(response);
    }

    protected abstract FeeData resolveResponse(String response);

    private String sendRequest(Map params) {
        return "";
    }
}
