package com.tistory.nittaku.rx_eventbus;

import java.io.Serializable;

/**
 * Created by cho on 2017-10-04.
 */

public class Product implements Serializable {


    private int resId;
    private String mKorea;
    private String mEng;
    private String mNumber;



    public Product() {
        super();
    }



    public Product(int resId, String mKorea, String mEng, String mNumber) {
        this.resId = resId;
        this.mKorea = mKorea;
        this.mEng = mEng;
        this.mNumber = mNumber;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getmKorea() {
        return mKorea;
    }

    public void setmKorea(String mKorea) {
        this.mKorea = mKorea;
    }

    public String getmEng() {
        return mEng;
    }

    public void setmEng(String mEng) {
        this.mEng = mEng;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (resId != other.resId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [숫자=" + mNumber + ", 한글=" + mKorea + ", 영어="
                + mEng + ", 이미지주소=" + resId + "]";
    }
}