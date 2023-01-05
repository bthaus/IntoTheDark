package Handler;

import Types.STATE;

import java.io.Serializable;

public interface ActionHandler extends Serializable {

    public void  before();
    public void  onStart();
    //return true if finished
    public STATE execute(float destinationX, float destinationY);
    public void  after();


}
