package Handler;

import Types.HandlerType;
import com.badlogic.gdx.physics.box2d.Body;

public interface TerrainCollisionHandler {
    public void collideWith(Body hitter,Body hit);
    public void detachFrom(Body hitter,Body hit);
    public HandlerType getName();
    //first element of typecombination: what its colliding with. second element: who is colliding. example: 1: floor, 2: hero <- hero gets notified. 1:hero 2:floor <- floor gets notified
    public void setTypeCombination();
}
