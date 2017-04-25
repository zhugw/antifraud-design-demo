package com.zhugw.demo.template;

import com.zhugw.demo.template.model.FeeData;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.*;

/**
 * 收费数据门面模式
 * Created by zhuguowei on 4/21/17.
 */
public class FeeDataManagerFacade2 {
    private static Map<String, AbstractFeeDataManagerTemplate> code2FeeDataManagerMap = new HashMap();
    static{
        code2FeeDataManagerMap.put("NAME_IDCARD_VERIFICATION", new NameIdCardVerificationManager());
        //...
    }
    private static final ExpressionParser expressionParser = new SpelExpressionParser();
    public FeeData getFeeData(String code, Map<String, Object> params){
        if("BATCH_QUERY_FEEDATA".equals(code)){ // 批量查询
            List<Map<String,Object>> paramList = (List<Map<String, Object>>) params.get("paramList");
            List<FeeData> feeDataList = new ArrayList();
            FeeData previous = null;
            for (Map<String, Object> param : paramList) {
                String realCode = (String) param.get("code"); // 实际接口编码
                Objects.requireNonNull(realCode,"接口编码不可为空");
                // 若输入参数依赖前一查询结果
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    String value = entry.getValue().toString();
                    if(value.startsWith("#{")){
                        // 表示动态变量 需要解析
                        String el = value.replaceFirst("#\\{(.+)}", "$1");
                        Expression expression = expressionParser.parseExpression(el);
                        Object resolvedValue = expression.getValue(previous);
                        entry.setValue(resolvedValue); // 实际值替换动态变量
                    }
                }
                FeeData feeData = code2FeeDataManagerMap.get(realCode).getFeeData(params);
                feeDataList.add(feeData);
                previous = feeData;
            }
            FeeData result = new FeeData();
            result.setExtra(newHashMap("feeDataList",feeDataList));
            return result;
        }
        // 单个查询
        return code2FeeDataManagerMap.get(code).getFeeData(params);
    }

    private HashMap newHashMap(String key, Object value) {
        HashMap extra = new HashMap();
        extra.put(key, value);
        return extra;
    }
}
