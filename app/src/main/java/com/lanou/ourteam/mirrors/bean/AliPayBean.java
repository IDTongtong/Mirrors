package com.lanou.ourteam.mirrors.bean;

/**
 * Created by ZHDelete on 16/4/14.
 */
public class AliPayBean {


    /**
     * msg :
     * data : {"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"14605949836Ng\"&subject=\"\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"0.01\"&body=\"\"&it_b_pay =\"30m\"&sign=\"VbkQ9FOLIxqD39q3H2NpOa8MAVoT5MOQzyUXD0qERWvi5waygtUrkAsbwpn78ZXusVJSzPjJwnPMdeCGdflKley42vRx3PjQVpyCjyyUO6kGlQ62ez3rDQ9S7ULm7h7%2BQMgHXo1zWjTp5tyxT3bpJ%2BILOG1HZVYF4qPUf4ASo%2Bs%3D\"&sign_type=\"RSA\""}
     */

    private String msg;
    /**
     * str : service="mobile.securitypay.pay"&partner="2088021758262531"&_input_charset="utf-8"&notify_url="http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify"&out_trade_no="14605949836Ng"&subject=""&payment_type="1"&seller_id="2088021758262531"&total_fee="0.01"&body=""&it_b_pay ="30m"&sign="VbkQ9FOLIxqD39q3H2NpOa8MAVoT5MOQzyUXD0qERWvi5waygtUrkAsbwpn78ZXusVJSzPjJwnPMdeCGdflKley42vRx3PjQVpyCjyyUO6kGlQ62ez3rDQ9S7ULm7h7%2BQMgHXo1zWjTp5tyxT3bpJ%2BILOG1HZVYF4qPUf4ASo%2Bs%3D"&sign_type="RSA"
     */

    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String str;

        public void setStr(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}
