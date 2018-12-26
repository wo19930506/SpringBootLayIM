package com.fyp.im.handler;

import com.fyp.utils.convert.ConvertUtil;
import com.fyp.utils.jwt.JwtUtil;
import com.fyp.utils.jwt.JwtVertifyResult;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Arrays;

/**
 * WebSocket 核心消息处理
 * */
public class MyMsgHandler implements IWsMsgHandler {

    /**
     * 握手
     * */
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String token = httpRequest.getParam("access_token");
        JwtVertifyResult result = JwtUtil.verifyToken(token);
//        if (result.isVertified()) {
//            Tio.bindUser(channelContext, result.getUserId().toString());
//        } else {
//            httpResponse.setStatus(HttpResponseStatus.C401);
//        }
        Tio.bindUser(channelContext,203328+"");
        return httpResponse;
    }

    /**
     * 握手完毕
     * */
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        System.out.println("握手完毕");
    }

    /**
     * 字节传输
     * */
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        WsResponse response = WsResponse.fromBytes(bytes);
       byte[] targetIdBytes = Arrays.copyOf(bytes,4);
       byte[] contents = Arrays.copyOfRange(bytes,5,bytes.length);
       System.out.println("消息体："+new String(contents));
       int targetId = ConvertUtil.byteArrayToInt(targetIdBytes);
       System.out.println("接收人："+targetId);

       System.out.println("消息类型"+bytes[4]);
        //Tio.send(channelContext,response);
        return null;
    }



    /**
     * 关闭
     * */
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        System.out.println("正在关闭");
        return null;
    }

    /**
     * 文本传输
     * */
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        return null;
    }
}
