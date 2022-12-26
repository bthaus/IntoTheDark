package Handler;

import Types.STATE;

public interface ActionHandler {

    public void  before();
    public void  onStart();
    //return true if finished
    public STATE execute();
    public void  after();


}
