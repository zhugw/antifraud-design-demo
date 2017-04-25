package com.zhugw.demo.localdata.service;

import com.zhugw.demo.template.model.ApplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.reflect.ReflectionUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地数据源查询
 * Created by zhuguowei on 4/25/17.
 */
@Service
public class LocalDataQueryService {

    @Resource(name = "ruleDaoProperties")
    private Map<String, String> rule2DaoMap;

    @Autowired
    private ApplicationContext context;

    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    public Map batchQueryLocalData(ApplyInfo applyInfo,List<Integer> ruleIdList){
        Map<Integer, Object> id2LocalDataMap = new HashMap<>();
        for (Integer ruleId : ruleIdList) {
            // 找到该规则对应的配置 1=applyDAO#countApply#idcard,0
            String[] split = rule2DaoMap.get(ruleId.toString()).split("#");
            String daoBean = split[0];
            String method = split[1];
            List paramList = new ArrayList<>();
            if(split.length == 3) {
                String args = split[2];
                // 解析参数
                resolveParamValue(applyInfo, paramList, args);
            }
            Object bean = context.getBean(daoBean);
            // 反射调用
            Object result = ReflectionUtil.invokeMethod(bean, method, paramList.toArray(new Object[]{paramList.size()}));
            id2LocalDataMap.put(ruleId, result);
        }

        return id2LocalDataMap;
    }

    private void resolveParamValue(ApplyInfo applyInfo, List paramList, String args) {
        String[] argArray = args.split(",");
        for (String arg : argArray) {
            Expression expression = expressionParser.parseExpression(arg);
            Object value = expression.getValue(applyInfo);
            paramList.add(value);
        }
    }

    public Map<String, String> getRule2DaoMap() {
        return rule2DaoMap;
    }
}
