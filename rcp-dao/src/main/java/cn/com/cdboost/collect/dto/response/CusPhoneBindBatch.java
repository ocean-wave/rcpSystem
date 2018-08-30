package cn.com.cdboost.collect.dto.response;

import java.util.LinkedList;

public class CusPhoneBindBatch {

    private String customerNo;
    private LinkedList<CustomerPhoneArray> mobilePhones;
    public LinkedList<CustomerPhoneArray> getMobilePhones() {
        return mobilePhones;
    }

    public void setMobilePhones(LinkedList<CustomerPhoneArray> mobilePhones) {
        this.mobilePhones = mobilePhones;
    }




    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }




}