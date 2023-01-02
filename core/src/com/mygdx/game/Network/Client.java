package com.mygdx.game.Network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.mygdx.game.Universe;
import util.Log;
import util.global;

import java.io.*;

public class Client extends Thread{
    String adress;
    int port;
    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public Client(String adress, int port) {
        this.adress = adress;
        this.port = port;
    }

    @Override
    public void run() {

        socket= Gdx.net.newClientSocket(Net.Protocol.TCP,adress,port,new SocketHints());
       Log.n("connected, yay :D");
        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        try {
            System.out.println(socket.getInputStream().read());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("started listening");
        while(true){
            try {

                System.out.println(in.readLine());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
