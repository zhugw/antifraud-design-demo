package com.zhugw.demo.localdata.dao;


import org.springframework.stereotype.Repository;

/**
 * Created by zhuguowei on 4/25/17.
 */
@Repository
public class ApplyDAO {
    public int countApply(String idCard,int daysAgo) {
        return 2;
    }
    public int countRefusedApply(String idcard, int daysAgo){
        return 1;
    }
    public int sumCreditAmount(String idcard, int daysAgo){
        return 1000;
    }

}
