package com.zhugw.demo.template.model;

/**
 * Created by zhuguowei on 4/25/17.
 */
public class ApplyInfo {
    private CharSequence residenceAddressProvinceName;
    private CharSequence residenceAddressCountyName;
    private Object residenceAddressCityName;
    private Object thirdBusinessAddress;
    private String idcard;

    public CharSequence getResidenceAddressProvinceName() {
        return residenceAddressProvinceName;
    }

    public CharSequence getResidenceAddressCountyName() {
        return residenceAddressCountyName;
    }

    public Object getResidenceAddressCityName() {
        return residenceAddressCityName;
    }

    public Object getThirdBusinessAddress() {
        return thirdBusinessAddress;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcard() {
        return idcard;
    }
}
