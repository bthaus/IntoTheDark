package com.mygdx.game;

import Types.TriggerType;
import util.AdditionalAction;

import java.util.LinkedList;

public class Equipment {

    public Torch torch=new Torch();
    Armament rightHand;
    Armament leftHand;
    Gear gear;


    public LinkedList<AdditionalAction> getAllAdditionalAttacks() {
        LinkedList<AdditionalAction>all=new LinkedList<>();

        if(rightHand!=null)  for (AdditionalAction action: rightHand.additionalActions ) if(action.getTriggerType().equals(TriggerType.ONATTACK)) all.add(action);

        if(leftHand!=null) for(AdditionalAction action: leftHand.additionalActions) if(action.getTriggerType().equals(TriggerType.ONATTACK)) all.add(action);

        if(gear!=null) all.addAll(gear.getAllAdditionalAttacks());
        return all;
    }
}
