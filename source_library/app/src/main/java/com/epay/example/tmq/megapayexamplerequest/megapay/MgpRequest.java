package com.epay.example.tmq.megapayexamplerequest.megapay;

import android.support.annotation.NonNull;
import android.util.Log;

import java.net.URI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

/**
 * Created by tmq on 28/12/2016.
 */

public class MgpRequest {

    private static final String TAG = MgpRequest.class.getSimpleName();
    private MgpRequestBuilder builder;

    public MgpRequest(@NonNull MgpRequestBuilder builder) {
        this.builder = builder;
    }

    /**
     * Gui Request toi server
     */
    public void sendRequest() {
        if (builder.getUri() != null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    send();
                }
            });
            thread.start();
        }
    }

    /**
     * Tao Request
     */
    private void send() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new MgpRequestInitializer(builder.getListener()));

            URI uri = builder.getUri();
            Channel channel = bootstrap.connect(uri.getHost(), builder.getPort()).sync().channel();

            Log.i(TAG, "Url = " + uri.toASCIIString());

            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());

            // header ------------------------------------------------------------------------------
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, uri.getHost());
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP + "," + HttpHeaderValues.DEFLATE);

            headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "en");
            headers.set(HttpHeaderNames.REFERER, uri.toString());
            headers.set(HttpHeaderNames.USER_AGENT, "Netty Simple Http Client side");
            headers.set(HttpHeaderNames.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            // -------------------------------------------------------------------------------------

            channel.write(request);
            channel.flush();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public MgpRequestBuilder getMgpRequestBulder() {
        return builder;
    }
}
