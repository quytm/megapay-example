package com.epay.example.tmq.megapayexamplerequest.megapay;

import android.app.Activity;

import com.epay.example.tmq.megapayexamplerequest.megapay.entity.ItemKV;
import com.epay.example.tmq.megapayexamplerequest.megapay.entity.MgpMessageCover;
import com.epay.example.tmq.megapayexamplerequest.megapay.entity.MgpScratchCardRequest;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.netty.handler.codec.http.QueryStringEncoder;

/**
 * MgpRequestBuilder
 * Chuan bi cac tham so truoc khi gui request toi server
 */
public class MgpRequestBuilder {

    private static final String TAG = MgpRequestBuilder.class.getSimpleName();

//    private static final String BASE_URL = "http://172.16.10.61:9092/megapay_server";
//    private static final int PORT = 9092;
    private static final String BASE_URL = "http://megapay.com.vn:8080/megapay_server";
    private static final int PORT = 8080;
    private static final String PAYMENT_CHANNEL = "1" ;
    private static final String PROCESSING_CODE = "10002" ;


    private MgpRequestHandler.OnResultListener listener;
    private URI uri;

    private String url;
    private int port;

    private String provider,
            code,
            serial;

    private String projectId,
            account,
            username,
            paymentChannel,
            processingCode;

    public MgpRequestBuilder(){
        this.url = BASE_URL;
        this.port = PORT;
        this.paymentChannel = PAYMENT_CHANNEL;
        this.processingCode = PROCESSING_CODE;
    }

    /**
     * Them thong tin nguoi dung
     * Require: Bat buoc
     * @param projectId Id nguoi dung
     * @param targetAcc Ten tai khoan
     * @param username Username
     * @return MgpRequestBuilder
     */
    public MgpRequestBuilder withUserInfo(String projectId, String targetAcc, String username) {
        this.projectId = projectId;
        this.account = targetAcc;
        this.username = username;
        return this;
    }

    /**
     * Them thong tin the nap
     * Require: Bat buoc
     * @param provider Ma nha cung cap: VNP (Vinaphone), VTT (Viettel), ...
     * @param code Ma so sau lop giay bac
     * @param serial Ma serial
     * @return MgpRequestBuilder
     */
    public MgpRequestBuilder withCardInfo(String provider, String code, String serial) {
        this.provider = provider;
        this.code = code;
        this.serial = serial;
        return this;
    }

    /**
     * Them interface, lam nhiem vu xu li response khi no duoc tra ve
     * Require: Bat buoc
     * @param listener Listener
     * @return MgpRequestBuilder
     */
    public MgpRequestBuilder withResultListener(MgpRequestHandler.OnResultListener listener){
        this.listener = listener;
        return this;
    }

    public MgpRequest build() {
        try {
            this.uri = queryStringUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            this.uri = null;
        }
        MgpRequest mgpRequest = new MgpRequest(this);
        return mgpRequest;
    }

    /**
     * Them cac truong query vao trong url
     * @return URI
     * @throws URISyntaxException
     * E.g:
     *      http://172.16.10.61:9092/megapay_server?request={"processing_code":"10002","project_id":"101792","data":"{\"serial\":\"123456789\",\"mpin\":\"123456789\",\"transid\":\"101792201612230800193708\",\"telcocode\":\"VNP\",\"username\":\"test1995\",\"account\":\"phuongvd\",\"payment_channel\":\"1\"}"}
     */
    private URI queryStringUri() throws URISyntaxException {
        List<ItemKV> urlParams = getUrlParams();
        if (urlParams == null || urlParams.isEmpty()) return new URI(url);

        QueryStringEncoder encoder = new QueryStringEncoder(url);
        for (ItemKV query : urlParams) {
            encoder.addParam(query.getKey(), query.getValue());
        }

        return new URI(encoder.toString());
    }

    /**
     * Chuyen cac thuoc tinh, thong tin sang array -> de add vao URL (trong ham queryStringUri)
     * @return Array: List<ItemKV>
     *
     * Chuyen cac thuoc tinh vao object -> Su dung Gson, convert sang dang Json (string)
     */
    private List<ItemKV> getUrlParams() {
        Gson gson = new Gson();

        MgpMessageCover msCover = new MgpMessageCover();
        MgpScratchCardRequest mgpScratchCardRequest = new MgpScratchCardRequest();

        mgpScratchCardRequest.setSerial(serial);
        mgpScratchCardRequest.setMpin(code);
        mgpScratchCardRequest.setTelcocode(provider);

        // projectid + [datetime in format yyMMddHHmmss] + [chuỗi ngẫu nhiên độ dài bằng 5].
        mgpScratchCardRequest.setTransid(projectId + "_" + dateToString(new Date(), "yyyyMMddHHmmss") + ranDom(5));
        mgpScratchCardRequest.setPayment_channel(paymentChannel);
        mgpScratchCardRequest.setUsername(username);
        mgpScratchCardRequest.setAccount(account);
        msCover.setProject_id(projectId);
        msCover.setProcessing_code(processingCode);

        msCover.setData(gson.toJson(mgpScratchCardRequest));
        String request = gson.toJson(msCover);

        ArrayList<ItemKV> listQuery = new ArrayList<>();
        listQuery.add(new ItemKV("request", request));

        return listQuery;
    }

    private static String ranDom(int len) {
        Random ran = new Random();
        return String.format("%05d", ran.nextInt(99999));
    }

    private static String dateToString(Date date, String formatString) {
        DateFormat dateFormat = new SimpleDateFormat(formatString);// "yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    // ---------------------------------------------------------------------------------------------

    public MgpRequestHandler.OnResultListener getListener(){
        return listener;
    }

    public URI getUri(){
        return uri;
    }

    public int getPort(){
        return port;
    }

    public String getUrl() {
        return url;
    }

    public String getProvider() {
        return provider;
    }

    public String getCode() {
        return code;
    }

    public String getSerial() {
        return serial;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public String getProcessingCode() {
        return processingCode;
    }
}
