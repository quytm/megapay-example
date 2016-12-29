package com.epay.example.tmq.megapayexamplerequest.megapay;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Khoi tao Channel
 */
class MgpRequestInitializer extends ChannelInitializer<SocketChannel> {
    private MgpRequestHandler.OnResultListener listener;

    public MgpRequestInitializer(MgpRequestHandler.OnResultListener listener){
        this.listener = listener;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("codec", new HttpClientCodec());

        pipeline.addLast("inflater", new HttpContentDecompressor());

        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

        pipeline.addLast("handler", new MgpRequestHandler(listener));
    }
}
