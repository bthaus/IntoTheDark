package com.mygdx.game.Network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;
import com.mygdx.game.Action;
import util.Log;
import util.global;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Host extends Thread {
    public  int port;
    public boolean running;
    ServerSocket server;
    LinkedList<Friend>friends=new LinkedList<>();
    ConcurrentLinkedDeque<Action>actions=new ConcurrentLinkedDeque<>();

    public Host(int port) {
        this.port=port;
        global.host=this;
        ServerSocketHints hints=new ServerSocketHints();
        hints.acceptTimeout=Integer.MAX_VALUE;

        try {
            this.server= Gdx.net.newServerSocket(Net.Protocol.TCP,port,hints);



            running=true;
            Log.n("server running");
        } catch (Exception ignored) {

        }
    }

    @Override
    public void run() {

            while(true){
                try {
                    SocketHints hints=new SocketHints();
                    hints.tcpNoDelay=true;


                    Friend friend=new Friend(server.accept(hints));
                    friends.add(friend);
                    Log.n("New Friend connected");
                   new Messenger().start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }





    }

    public void addAction(Action action) {
        switch (action.getType()){
            case PASSIVEATTACK:return;
        }
        Log.t(action.getType()+"sent");
        actions.offer(action);
    }

    class Messenger extends Thread{
        @Override
        public void run() {
            Action msg;
            while(true){
                msg=actions.poll();
                if(msg!=null){
                    for (Friend friend:friends
                         ) {

                        friend.writeObject(msg);

                    }
                }
            }
        }
    }

    private void doHandshake(Friend friend) {

    }
}
