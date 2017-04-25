package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;
import com.zhugw.demo.template.model.RuleContext;

import java.util.Map;

/**
 * 通用收费规则处理模板
 * Created by zhuguowei on 4/21/17.
 */
public abstract class AbstractFeeRuleProcessServiceTemplate2 {
    private static FeeDataManagerFacade facade = new FeeDataManagerFacade();
    public void process(Map params, RuleContext ruleContext){
        try {
            FeeData feeData = facade.getFeeData(ruleContext.getRule().getFeeDataCode(), params);
            if(!checkFeeData(feeData)){
                // 校验未通过处理
                ruleContext.setResult(ruleContext.getRule().getResult()); // 设置决策结果：拒绝
                ruleContext.setMessage(buildMessage(feeData));
            }
        } catch (Exception e) {
            // 接口调用异常 默认为人工复核
            ruleContext.setResult("REVIEW"); // 设置决策结果：人工复核
            ruleContext.setMessage(String.format("接口调用失败: %s",e.getMessage()));
        }

    }

    protected abstract boolean checkFeeData(FeeData feeData);

    protected abstract String buildMessage(FeeData feeData);

}
