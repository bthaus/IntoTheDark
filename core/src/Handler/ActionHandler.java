package Handler;

public interface ActionHandler {

    public void  before();
    public void  onStart();
    //return true if finished
    public boolean  execute();
    public void  after();


}
