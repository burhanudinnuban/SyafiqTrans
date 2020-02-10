package com.SQTrans.ModelData;

import java.io.Serializable;

public class PemesananBus implements Serializable {
    private String strDari, strDestinasi, strPergi, strHarga, strBigBus, strMediumbus, strMiniBus, strHiAce, strQuantityBus, strHargaPenginapan, key;
    public PemesananBus(){
    }
    public String getKey(){
       return key;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getDari(){
        return strDari;
    }
    public void setDari(String Dari){
        this.strDari = Dari;
    }
    public String getDestinasi(){
        return strDestinasi;
    }
    public void setDestinasi(String Destinasi){
        this.strDestinasi = Destinasi;
    }
    public String getPergi(){
        return strPergi;
    }
    public void setPergi(String Pergi){
        this.strPergi = Pergi;
    }
    public String getHarga(){
        return strHarga;
    }
    public void setHarga(String Pulang){
        this.strHarga = Pulang;
    }
    public String getBigBus(){
        return strBigBus;
    }
    public void setBigBus(String Bus){
        this.strBigBus = Bus;
    }
    public String getminiBus(){
        return strMiniBus;
    }
    public void setMiniBus(String Bus){
        this.strMiniBus = Bus;
    }
    public String getMediumBus(){
        return strMediumbus;
    }
    public void setMediumBus(String Bus){
        this.strMediumbus = Bus;
    }
    public String getHiAce(){
        return strHiAce;
    }
    public void setHiAce(String Bus){
        this.strHiAce = Bus;
    }
    public void setHargaPenginapan(String Penginapan){
        this.strHargaPenginapan = Penginapan;
    }
    public String getHargaPenginapan(){
        return strHargaPenginapan;
    }

    public PemesananBus(String dari, String destinasi, String pergi, String harga, String bigbus, String mediumbus, String minibus, String hiace, String penginapan){
        strDari = dari;
        strDestinasi = destinasi;
        strPergi = pergi;
        strHarga = harga;
        strBigBus = bigbus;
        strMediumbus = mediumbus;
        strMiniBus = minibus;
        strHiAce = hiace;
        strHargaPenginapan = penginapan;
    }
}
