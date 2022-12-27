package Handler;

import Types.STATE;

public interface ActionHandler {

    public void  before();
    public void  onStart();
    //return true if finished
    public STATE execute(float destinationX, float destinationY);
    public void  after();


}
