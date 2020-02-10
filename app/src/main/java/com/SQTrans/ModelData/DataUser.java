package com.SQTrans.ModelData;

import java.io.Serializable;

public class DataUser implements Serializable {
    private String strNamaLengkap, strEmail, strNoTelepon, key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNamaLengkap() {
        return strNamaLengkap;
    }

    public String getEmail() {
        return strEmail;
    }

    public String getNoTelepon() {
        return strNoTelepon;
    }

    public void setStrNoTelepon(String strNoTelepon) {
        this.key = strNoTelepon;
    }

    @Override
    public String toString() {
        return " " + strNamaLengkap + "\n" +
                " " + strEmail + "\n" +
                " " + strNoTelepon;
    }

    public DataUser(String namaLengkap, String Email, String NoTelepon) {
        strNamaLengkap = namaLengkap;
        strEmail = Email;
        strNoTelepon = NoTelepon;
    }
}
