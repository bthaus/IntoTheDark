package Handler;

import Types.STATE;

import java.io.Serializable;

public interface ActionHandler  {

    public void  before();
    public void  onStart();
    //return true if finished
    public STATE execute(float destinationX, float destinationY);
    public void  after();
    public void perFrame(float x, float y);



}
