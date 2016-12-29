package com.epay.tmq.megapayrequesttestlibs;

/**
 * Created by tmq on 23/12/2016.
 */

/**
 * Thong tin Nha cung cap: Ten, Ma
 */
public class Provider {
    private String name, code;

    public Provider(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
