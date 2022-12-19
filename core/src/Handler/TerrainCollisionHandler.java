package Handler;

import com.badlogic.gdx.physics.box2d.Body;

public interface TerrainCollisionHandler {
    public void collideWith(Body a);
    public void detachFrom(Body a);
}
