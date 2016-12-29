package com.epay.example.tmq.megapayexamplerequest.megapay.entity;

import java.io.Serializable;

public class ResultRequest implements Serializable {

    private String status;
    private String resultRaw;
    private String amount;

    /**
     * Response tra ve duoc cho vao object ResultRequest
     * @param status trang thai cua giao dich
     * @param resultRaw response nhan duoc duoi dang Json
     * @param amount gia tri the nap. Neu response bi loi thi Amount = NULL
     */
    public ResultRequest(String status, String resultRaw, String amount) {
        this.status = status;
        this.resultRaw = resultRaw;
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public String getResultRaw() {
        return resultRaw;
    }

    public String getAmount() {
        return amount;
    }
}
