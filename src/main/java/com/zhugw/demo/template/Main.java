package com.zhugw.demo.template;

import com.zhugw.demo.template.model.ApplyInfo;
import com.zhugw.demo.template.model.FeeData;
import com.zhugw.demo.template.model.RuleContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by zhuguowei on 4/21/17.
 */
public class Main {
    public static void main(String[] args) {
        //执行规则一 -- 申请人姓名身份证号实名验证
        FeeDataManagerFacade feeDataManagerFacade = new FeeDataManagerFacade();

        RuleContext ruleContext = new RuleContext();
        String code = ruleContext.getRule().getFeeDataCode();

        final Map params = new HashMap();
        params.put("name", "张三");
        params.put("idcard", "123456199001011233");

        final ApplyInfo applyInfo = new ApplyInfo();

//        try {
//            FeeData feeData = feeDataManagerFacade.getFeeData(code, params);
//            if(!feeData.isPass()){
//                // 校验未通过处理
//                ruleContext.setResult(ruleContext.getRule().getResult()); // 设置决策结果：拒绝
//                ruleContext.setMessage(String.format("申请人姓名: %s 身份证号: %s 实名验证失败",params.get("name"),params.get("idcard")));
//            }
//        } catch (Exception e) {
//            // 接口调用异常 默认为人工复核
//            ruleContext.setResult("REVIEW"); // 设置决策结果：人工复核
//            ruleContext.setMessage(String.format("接口调用失败: %s",e.getMessage()));
//        }

//        new AbstractFeeRuleProcessServiceTemplate(){
//            @Override
//            protected String buildMessage() {
//                return String.format("申请人姓名: %s 身份证号: %s 实名验证失败",params.get("name"),params.get("idcard"));
//            }
//        }.process(params,ruleContext);

        new AbstractFeeRuleProcessServiceTemplate2(){

            @Override
            protected boolean checkFeeData(FeeData feeData) {
                return feeData.isPass();
            }

            @Override
            protected String buildMessage(FeeData feeData) {
                return String.format("申请人姓名: %s 身份证号: %s 实名验证失败",params.get("name"),params.get("idcard"));
            }
        }.process(params,ruleContext);

        // 规则6--户籍地址与身份证号归属地比对
        new AbstractFeeRuleProcessServiceTemplate2(){
            @Override
            protected boolean checkFeeData(FeeData feeData) {
                // 如 河北省 邯郸市 临漳县 、重庆市綦江县
                String location = (String) feeData.getExtra().get("location");
                if (location.contains(applyInfo.getResidenceAddressProvinceName()) || location.contains(applyInfo.getResidenceAddressCountyName())) {
                    return true;
                }
                return false;
            }

            @Override
            protected String buildMessage(FeeData feeData) {
                String message = String.format("自述户籍地址：%s %s %s 与身份证归属地：%s 不一致",
                        applyInfo.getResidenceAddressProvinceName(),applyInfo.getResidenceAddressCityName()
                        ,applyInfo.getResidenceAddressCountyName(),feeData.getExtra().get("location"));
                return message;
            }
        }.process(params,ruleContext);

        // 注册手机号码归属地与申请人身份证号归属地、现居住地、单位地址、家庭地址、户籍地址的交叉验证

        Map param1 = newHashMap("code", "MOBILE_LOCATION_QUERY", "mobile", "13800138000");
        Map param2 = newHashMap("code", "IDCARD_LOCATION_QUERY", "idcard", "123456199001011233");
        Map param = newHashMap("paramList", newArrayList(param1, param2));
        new AbstractFeeRuleProcessServiceTemplate2(){
            @Override
            protected boolean checkFeeData(FeeData feeData) {
                List<FeeData> feeDataList = (List<FeeData>) feeData.getExtra().get("feeDataList");
                String mobileLocation = (String) feeDataList.get(0).getExtra().get("location");
                String idcardLocation = (String) feeDataList.get(1).getExtra().get("location");
                // ... 规则校验
                return false;
            }
            @Override
            protected String buildMessage(FeeData feeData) {
                //... 组装提示信息
                // String message = "注册手机号码归属城市：%s ，注册手机号码归属地城市与申请人一系列地址城市都不一致";
                return null;
            }
        }.process(param,ruleContext);

        // IP地址与三级商户地址的交叉验证
        param1 = newHashMap("code", "IP_ADDRESS_QUERY", "ip", "222.128.42.13");
        param2 = newHashMap("code", "ADDRESSES_DISTANCE_QUERY", "startAddress", applyInfo.getThirdBusinessAddress(),"endAddress","#{extra['address']}");
        param = newHashMap("paramList", newArrayList(param1, param2));

        new AbstractFeeRuleProcessServiceTemplate2(){

            @Override
            protected boolean checkFeeData(FeeData feeData) {
                List<FeeData> feeDataList = (List<FeeData>) feeData.getExtra().get("feeDataList");
                double distance = Double.valueOf(feeDataList.get(1).getExtra().get("distance").toString());
                // 规则校验 ...
                return false;
            }

            @Override
            protected String buildMessage(FeeData feeData) {
                return null;
            }
        }.process(param,ruleContext);

    }

    private static Map newHashMap(String k1, Object v1, String k2, Object v2) {
        Map param1 = new HashMap();
        param1.put(k2, v2);
        param1.put(k1, v1);
        return param1;
    }
    private static Map newHashMap(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        Map param1 = new HashMap();
        param1.put(k2, v2);
        param1.put(k1, v1);
        param1.put(k3, v3);
        return param1;
    }
    private static Map newHashMap(String k1, Object v1) {
        Map param1 = new HashMap();
        param1.put(k1, v1);
        return param1;
    }
}
