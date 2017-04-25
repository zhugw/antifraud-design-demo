package com.zhugw.demo.template.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuguowei on 4/21/17.
 */
public class FeeData {
    private HashMap extra;

    public boolean isPass() {
        return false;
    }

    public Map<String, Object> getExtra() {
        return new HashMap();
    }

    public void setExtra(HashMap extra) {
        this.extra = extra;
    }


}
