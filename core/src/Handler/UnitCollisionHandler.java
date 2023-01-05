package Handler;

import Types.HandlerType;
import com.badlogic.gdx.physics.box2d.Body;

public interface UnitCollisionHandler {

    public void collideWith(Body hitter,Body hit);
    public void detachFrom(Body hitter,Body hit);
    public HandlerType getName();
    public void setTypeCombination();
}
