package com.epay.example.tmq.megapayexamplerequest.megapay;

import android.util.Log;

import com.epay.example.tmq.megapayexamplerequest.megapay.entity.ResultRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

/**
 * Handler nhan Response tu server
 */
public class MgpRequestHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static final String TAG = MgpRequestHandler.class.getSimpleName();

    // Nhan response, neu response bi Chuck thi append cho den khi nhan het response
    private StringBuffer responseString = new StringBuffer();

    public MgpRequestHandler(OnResultListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpContent) {
            HttpContent chunk = (HttpContent) msg;
            responseString.append(chunk.content().toString(CharsetUtil.UTF_8));

            // Khi response tra ve day du
            if (chunk instanceof LastHttpContent) {
                Log.i(TAG, "Response = " + responseString);
                JSONObject json = new JSONObject(responseString.toString());

                try {
                    /**
                     * Giao dich thanh cong: response tra ve dang Json:
                     *      {
                     *          status: String,
                     *          data: {
                     *              status: String,
                     *              transid: String,
                     *              payment_amount: String,
                     *              message: String
                     *          },
                     *          signature: String
                     *      }
                     */
                    JSONObject data = new JSONObject(json.getString("data"));
                    mListener.onResultListener(new ResultRequest(json.getString("status"), responseString.toString(), data.getString("payment_amount")));

                } catch (JSONException e) {
                    /**
                     * Giao dich that bai: response tra ve dang Json:
                     *      {
                     *          status: String
                     *      }
                     * Truong payment_amount = NULL
                     */
                    mListener.onResultListener(new ResultRequest(json.getString("status"), responseString.toString(), null));
                    System.out.println(TAG + ": No data");
                }

                responseString = new StringBuffer(); // Reset
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }

    // ---------------------------------------------------------------------------------------------

    private OnResultListener mListener;

    /**
     * Interface su dung cho viec trao doi du lieu
     * Khi nhan duoc response, OnResultListener duoc goi va lay ra trong MainActivity
     */
    public interface OnResultListener {
        void onResultListener(ResultRequest result);
    }
}
