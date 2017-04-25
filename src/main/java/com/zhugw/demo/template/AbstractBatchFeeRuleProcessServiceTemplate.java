package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;
import com.zhugw.demo.template.model.RuleContext;

import java.util.List;
import java.util.Map;

/**
 * 通用批量收费规则处理模板
 * Created by zhuguowei on 4/21/17.
 */
public abstract class AbstractBatchFeeRuleProcessServiceTemplate {
    private static final FeeDataManagerFacade facade = new FeeDataManagerFacade();
    public void process(List<Map> params, RuleContext ruleContext){
        try {
            String feeDataCode = ruleContext.getRule().getFeeDataCode();
            String[] codes = feeDataCode.split(",");
            for (int i = 0; i < codes.length; i++) {
                FeeData feeData = facade.getFeeData(codes[i], params.get(i));
                if(!checkFeeData(feeData)){
                    if(i < codes.length - 1){
                        // 校验未通过 继续往下执行
                        continue;
                    }
                    // 所有校验均未通过处理
                    ruleContext.setResult(ruleContext.getRule().getResult()); // 设置决策结果：拒绝
                    ruleContext.setMessage(buildMessage(feeData));

                }
                // 任一校验通过 直接返回
                return ;

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
