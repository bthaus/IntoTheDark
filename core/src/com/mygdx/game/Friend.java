package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Friend extends Thread{
Socket socket;
    ObjectOutputStream out;
ConcurrentLinkedDeque<LinkedList<Integer>>cld=new ConcurrentLinkedDeque<>();
ConcurrentLinkedDeque<LinkedList<Integer>>cldin=new ConcurrentLinkedDeque<>();

Texture hero2image;
Rectangle hero2;
    @Override
    public void run() {

        hero2=new Rectangle();
        hero2.x=300;
        hero2.y=100;



    }




    public void createConnection(){

            socket= Gdx.net.newClientSocket(Net.Protocol.TCP,"localhost",5000,new SocketHints());
            System.out.println("connected");
            new Receiver().start();

    }
    public void addMessage(Texture text, Rectangle rect){
        int txid=0;
        switch (text.toString()){
            case "hero.png":txid=1;break;
            case "shuriken.png":txid=2;break;
            case "herowolf.png":txid=3;break;

        }
       LinkedList<Integer>temp=new LinkedList<>();
        temp.add(txid);
        temp.add((int) rect.x);
        temp.add((int) rect.y);
        cld.offer(temp);
    }

    class Receiver extends Thread{
        ObjectInputStream in;
        @Override
        public void run() {
            try {
                in=new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(true){
                try {
                    LinkedList<Integer>list=(LinkedList<Integer>) in.readObject();
                    System.out.println(list.getFirst());
                    cldin.offer(list);

                    System.out.println("message received");
                } catch (IOException e) {
                    //e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
