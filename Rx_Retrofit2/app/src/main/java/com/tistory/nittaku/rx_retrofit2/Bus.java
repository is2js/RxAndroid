
package com.tistory.nittaku.rx_retrofit2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bus {

        @SerializedName("RESULT")
        @Expose
        private RESULT rESULT;
        @SerializedName("BUSSTOP_LIST")
        @Expose
        private List<Object> bUSSTOPLIST = null;
        @SerializedName("ROW_COUNT")
        @Expose
        private Integer rOWCOUNT;

        public RESULT getRESULT() {
            return rESULT;
        }

        public void setRESULT(RESULT rESULT) {
            this.rESULT = rESULT;
        }

        public List<Object> getBUSSTOPLIST() {
            return bUSSTOPLIST;
        }

        public void setBUSSTOPLIST(List<Object> bUSSTOPLIST) {
            this.bUSSTOPLIST = bUSSTOPLIST;
        }

        public Integer getROWCOUNT() {
            return rOWCOUNT;
        }

        public void setROWCOUNT(Integer rOWCOUNT) {
            this.rOWCOUNT = rOWCOUNT;
        }


    public class RESULT {

        @SerializedName("RESULT_MSG")
        @Expose
        private String rESULTMSG;
        @SerializedName("RESULT_CODE")
        @Expose
        private String rESULTCODE;

        public String getRESULTMSG() {
            return rESULTMSG;
        }

        public void setRESULTMSG(String rESULTMSG) {
            this.rESULTMSG = rESULTMSG;
        }

        public String getRESULTCODE() {
            return rESULTCODE;
        }

        public void setRESULTCODE(String rESULTCODE) {
            this.rESULTCODE = rESULTCODE;
        }

    }
}
