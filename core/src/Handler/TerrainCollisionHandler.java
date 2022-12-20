package Handler;

import Types.HandlerType;
import com.badlogic.gdx.physics.box2d.Body;

public interface TerrainCollisionHandler {
    public void collideWith(Body a);
    public void detachFrom(Body a);
    public HandlerType getName();
    public void setTypeCombination();
}
