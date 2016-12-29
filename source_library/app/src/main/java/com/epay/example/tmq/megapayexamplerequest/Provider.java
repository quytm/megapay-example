package com.epay.example.tmq.megapayexamplerequest;

/**
 * Created by tmq on 23/12/2016.
 */

public class Provider {
    String name, code, codeLength, serialLegth;

    public Provider(String name, String code, String codeLength, String serialLegth) {
        this.name = name;
        this.code = code;
        this.codeLength = codeLength;
        this.serialLegth = serialLegth;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCodeLength() {
        return codeLength;
    }

    public String getSerialLegth() {
        return serialLegth;
    }

}
