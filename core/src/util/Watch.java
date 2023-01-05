package util;

public class Watch {


    private long currenttime=0;
    private  long goaltime=0;
    public boolean active=false;
    public boolean done(){
        if(!active)return false;
        currenttime = System.currentTimeMillis();
        if(currenttime>goaltime){

            active=false;
            return true;
        }
        else return false;
    }
    public  void start(long duration) {

        active=true;
       currenttime = System.currentTimeMillis();
        this.goaltime=duration+ currenttime;

    }

    public boolean active() {
        return active;
    }
}
