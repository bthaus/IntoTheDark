package com.mygdx.game.Network;

import Types.ActionType;
import Types.Slot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import com.mygdx.game.Action;
import com.mygdx.game.Character;
import com.mygdx.game.Universe;
import util.Log;
import util.global;

import java.io.*;
import java.util.Objects;

import static util.utilMethods.getCharacter;

public class Friend extends Thread{

Socket socket;
BufferedReader in;
BufferedWriter out;
Character character;


    public Friend(Socket socket) {
        this.socket = socket;
        this.out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        try {

            socket.getOutputStream().write(5);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    @Override
    public void run() {
        Action msg;
        while(true){
            try {
              parseAction(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    private void parseAction(String readLine) {
        String arr[]=readLine.split(" ");

        Action action=Action.createAction(Objects.requireNonNull(ActionType.getType(arr[0])),global.universe.getBodyByID(Integer.parseInt(arr[1])));
        Character actor=getCharacter(global.universe.getBodyByID(Integer.parseInt(arr[1])));
        int x=Integer.parseInt(arr[2]);
        int y=Integer.parseInt(arr[3]);
        action.setX(x);
        action.setY(y);

        //todo: fix ambiguity concerning linking of actoins
        //in equip the actoin is already linked, in every other case it is not
        switch (action.getType()){
            case ATTACK:actor.addAttackAction(x,y);break;
            case EQUIP: actor.equipArmament(global.universe.holder.getArmamentByID(y), Objects.requireNonNull(Slot.getSlot(x)));break;
            case JUMP: //same as move
            case MOVE: action.link();break;
        }


    }


    public void writeObject(Action msg) {
        try {
            out.write(unimparse(msg));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //msgstyle: seperator " "
    //example msg   actiontype|ActorID|floatx|floaty|ItemID|
    //example move      MOVE|12|1|0|0
    //example attack    ATTACK|1|512|-452|0
    //example equip     EQUIP|2|slotID|32|0
    //example jump      JUMP|1|0|0|0
    //example open      OPEN|3| //tbd: entweder ItemID von gameobject oder x y koordinaten von item ID TODO: wenn cc entschieden hat wie sie gameobjects implementieren will
    //example
    static long volume=0;
    private String unimparse(Action msg) {
       String str=msg.getType().toString().concat(" ").concat(Integer.toString(msg.getActor().getID())).concat(" ");
       switch (msg.getType()){
           case MOVE: //movedirection is stored in x  so no special case is neccessary. if optimized y could store if the hero starts moving or stops moving.
           case EQUIP: //slot is stored in x, no special case neccessary
           case OPEN: //tbd
           case JUMP://nothing is stored in x and y in jump, it wont even be read
           case ATTACK:str=str.concat(Integer.toString(msg.getX())).concat(" ").concat(Integer.toString(msg.getY())).concat(" ");break;

           //no special cases neccessary so far, making this kinda obsolete

       }
        str=str.concat(Integer.toString(msg.getActionID())).concat(" ");
       Log.n(str +" sent");
        Log.n("msgsize: "+ sizeof(str) +"        total volume: "+volume);
       return str.concat("\n");

    }
    public static int sizeof(Object obj)  {

        ByteArrayOutputStream byteOutputStream = null;
        try {
            byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size=byteOutputStream.toByteArray().length;
        volume+=size;
        return size;
    }

}
