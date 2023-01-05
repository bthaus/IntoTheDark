package util;

import Handler.ActionHandler;
import Types.ActionType;
import Types.TriggerType;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Action;

public class AdditionalAction {
    Watch cooldown=new Watch();
    long cooldowntime;

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    TriggerType triggerType;

    public AdditionalAction(ActionHandler handler, long cooldown, TriggerType triggerType) {
        this.cooldowntime=cooldown;
        this.handler = handler;
        this.triggerType=triggerType;
    }


    ActionHandler handler;

    public void execute(Body wielder) {

        if (!cooldown.active||cooldown.done()) {
            Action additionalAttack=Action.createAction(ActionType.PASSIVEATTACK,wielder);
            additionalAttack.setActionHandler(handler);
            additionalAttack.setDuration(0);
            additionalAttack.actionFilter.clear();
            additionalAttack.link();
            cooldown.start(cooldowntime);
        }
    }
}
