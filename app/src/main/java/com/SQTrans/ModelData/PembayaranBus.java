package com.SQTrans.ModelData;

import java.io.Serializable;

public class PembayaranBus implements Serializable {
    private String strDari, strDestinasi, strPergi, strJumlah, strBigBus, strMediumbus, strMiniBus, strHiAce, strQuantityBus, key, strStatus, strHarga, strPenginapan;

    public PembayaranBus() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDari() {
        return strDari;
    }

    public void setDari(String Dari) {
        this.strDari = Dari;
    }

    public String getDestinasi() {
        return strDestinasi;
    }

    public void setDestinasi(String Destinasi) {
        this.strDestinasi = Destinasi;
    }

    public String getPergi() {
        return strPergi;
    }

    public void setPergi(String Pergi) {
        this.strPergi = Pergi;
    }

    public String getBigBus() {
        return strBigBus;
    }

    public void setBigBus(String Bus) {
        this.strBigBus = Bus;
    }

    public String getminiBus() {
        return strMiniBus;
    }

    public void setMiniBus(String Bus) {
        this.strMiniBus = Bus;
    }

    public String getMediumBus() {
        return strMediumbus;
    }

    public void setMediumBus(String Bus) {
        this.strMediumbus = Bus;
    }

    public String getHiAce() {
        return strHiAce;
    }

    public void setStrHiAce(String Bus) {
        this.strHiAce = Bus;
    }

    public String getQuantityBus() {
        return strQuantityBus;
    }

    public void setQuantityBus(String QuantityBus) {
        this.strQuantityBus = QuantityBus;
    }

    public String getStatus() {
        return strStatus;
    }

    public void setStatus(String status) {
        this.strStatus = status;
    }

    public String getHarga() {
        return strHarga;
    }

    public void setHarga(String harga) {
        this.strHarga = harga;
    }

    public String getStrPenginapan() {
        return strPenginapan;
    }

    public void setStrPenginapan(String harga) {
        this.strPenginapan = harga;
    }


    public PembayaranBus(String dari, String destinasi, String pergi, String harga, String bigbus, String mediumbus, String minibus, String hiace, String quantityBus, String status, String penginapan) {
        strDari = dari;
        strDestinasi = destinasi;
        strPergi = pergi;
        strHarga = harga;
        strBigBus = bigbus;
        strMediumbus = mediumbus;
        strMiniBus = minibus;
        strHiAce = hiace;
        strQuantityBus = quantityBus;
        strStatus = status;
        strPenginapan = penginapan;
    }
}
