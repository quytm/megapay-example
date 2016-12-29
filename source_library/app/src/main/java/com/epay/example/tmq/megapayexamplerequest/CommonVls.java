package com.epay.example.tmq.megapayexamplerequest;

/**
 * Created by tmq on 23/12/2016.
 */

public class CommonVls {
    private static final String TAG = CommonVls.class.getSimpleName();

    public static final Provider[] LIST_PROVIDERS = {
            new Provider("Chọn loại thẻ", "", "", ""),
            new Provider("Viettel", "VT", "13-15", "11-15"),
            new Provider("MobiFone", "VMS", "12-14", "9-15"),
            new Provider("Vinaphone", "VNP", "12-15", "9"),
            new Provider("Megacard", "MGC", "12", "12"),
            new Provider("FPT", "GATE", "10", "10"),
            new Provider("Zing Vinagame", "ZNG", "9", "12"),
            new Provider("Oncash", "ONC", "12", "10-12")
    };


    public static boolean checkLength(String length, int inputLength) {
        return true;
//        if (length.isEmpty() || length.equals("")) return false;
//
//        int posDivider = length.indexOf("-");
//        if (posDivider == -1) return Integer.parseInt(length) == inputLength;
//
//        int minLength = Integer.parseInt(length.substring(0, posDivider));
//        int maxLength = Integer.parseInt(length.substring(posDivider + 1));
//        return (inputLength >= minLength && inputLength <= maxLength);
    }

}
