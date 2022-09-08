package com.world;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class WebsocketServer extends WebSocketServer {

    private static int TCP_PORT = 5001;

    private Set<WebSocket> conns;

    Map map;
    LifeManager lm;

    public WebsocketServer(Map map, LifeManager lm) {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
        this.map = map;
        this.lm = lm;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendLifeAll();
            }
        }, 0, 200);
    }

    public void sendMapAll() {
        for (WebSocket s : conns) {
            sendMap(s);
        }
    }

    public void sendLifeAll() {
        for (WebSocket s : conns) {
            sendLife(s);
        }
    }

    public void sendMap(WebSocket con) {
        try {
            ObjectMapper om = new ObjectMapper();

            ObjectWriter ow = om.writer();
            String json = ow.writeValueAsString(map);
            con.send(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendLife(WebSocket con) {
        try {
            ObjectMapper om = new ObjectMapper();

            ObjectWriter ow = om.writer();
            String json = ow.writeValueAsString(lm);
            con.send(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        sendMap(conn);
        sendLife(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }
    }

    @Override
    public void onStart() {
    }
}