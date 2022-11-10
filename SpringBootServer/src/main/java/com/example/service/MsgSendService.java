package com.example.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.entity.Message;
import com.example.entity.MsgSession;
import com.example.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgSendService {

    private static final Map<UUID, SocketIOClient> CLIENT_MAP = new ConcurrentHashMap<>();

    private static final Map<UUID,String> openIdMap= new HashMap<UUID,String>();
    private final SocketIOServer socketIOServer;

    @Resource
    MessageService messageService;

    @Resource
    MsgSessionService msgSessionService;

    /**
     * 启动的时候会被调用一次
     */
    @PostConstruct
    private void autoStart() {
        log.info("start ws");
        socketIOServer.addConnectListener(client -> {
            String token = getClientToken(client, "token");
//            System.out.println("token:"+ token);
//            MyThread mThread1=new MyThread();
//            mThread1.start();
            Claims claims=null;
            if ( (claims = checkToken(token)) !=null) {
                openIdMap.put(client.getSessionId(),claims.getId());
                log.info("check success");
                CLIENT_MAP.put(client.getSessionId(), client);
            } else {
                client.disconnect();
            }
        });
        socketIOServer.addDisconnectListener(client -> {
            CLIENT_MAP.remove(client.getSessionId());
            client.disconnect();
            log.info("移除client:{}", client.getSessionId());
        });
        socketIOServer.start();
        log.info("start finish");
    }

    private Claims checkToken(String token) {
        Claims claims = JwtUtils.validateJWT(token).getClaims();
        if (claims == null) {
            throw new RuntimeException("websocket token鉴权失败！");
        }
        log.info("检查token是否有效:{}", token);
        return claims;
    }

    private String getClientToken(SocketIOClient client, String key) {
        HttpHeaders httpHeaders = client.getHandshakeData().getHttpHeaders();
        return httpHeaders.get(key);
    }

    @PreDestroy
    private void onDestroy() {
        if (socketIOServer != null) {
            socketIOServer.stop();
        }
    }

    public int sendMsg(Map<String,Object> SendPacket,HttpServletRequest request) {
        HttpSession session=request.getSession();
        Claims claims =(Claims) session.getAttribute("claims");
        String Sender =claims.getId();
        String MyName = claims.getSubject();
        System.out.println(SendPacket);
//        System.out.println(MyopneId+"    "+MyName);
//        System.out.println("openIdMap --"+openIdMap);
//        System.out.println(message);
        int msId=-1;

        MsgSession msgSession=msgSessionService.get(Sender,SendPacket.get("receiverOpenId").toString());

        if(msgSession == null){
            //创建聊天会话
            MsgSession create_msgSession=new MsgSession(Sender, SendPacket.get("receiverOpenId").toString());
            msgSessionService.add(create_msgSession);
            msId=create_msgSession.getMs_id();
        }
        else msId =msgSession.getMs_id();

        //存储聊天纪录
        Message message= new Message(null,Sender,SendPacket.get("receiverOpenId").toString(),
                SendPacket.get("msg").toString(),new Date(),msId);
        System.out.println("Message Data:"+message);
        messageService.addMessage(message);

        CLIENT_MAP.forEach((key, value) -> {
            // System.out.println(openIdMap.get(key)+" ***** "+SendPacket.get("receiverOpenId"));

            // System.out.println("比较mapkey--"+openIdMap.get(key)+"===???" + SendPacket.get("receiverOpenId")  );
            // if(openIdMap.get(key).equals(SendPacket.get("receiverOpenId"))){

            //    System.out.println(Sender+" 发送给 "+SendPacket.get("receiverOpenId")
            //            +" "+SendPacket.get("msg")+"  成功！！");
            // System.out.println("接收者：key"+key+"   value :"+value+"  openId:"+ openIdMap.get(key));
            value.sendEvent("server_event", SendPacket);


            // }
            log.info("发送数据成功:{}", key);

        });
        return CLIENT_MAP.size();
    }

    class  MyThread extends Thread {
        public void run(){

            for (int i = 0; i < 10; i++) {
                while(true){
                    try {
                        Thread.sleep(2000);
                        AtomicInteger countNum= new AtomicInteger();
                        CLIENT_MAP.forEach((key, value) -> {
                            countNum.getAndIncrement();
                            System.out.print(key+"----");
                        });
//                        CLIENT_MAP.forEach((key, value) -> {
//                            value.sendEvent("total", countNum);
//                        });
                        System.out.println("当前聊天室共有 "+countNum+" 人");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }


}
