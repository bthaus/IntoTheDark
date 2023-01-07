package com.mygdx.game;

import Types.TriggerType;
import util.AdditionalAction;

import java.util.LinkedList;

public class Equipment {

    Armament rightHand;
    Armament leftHand;
    Gear gear;


    public LinkedList<AdditionalAction> getAllAdditionalActions(TriggerType type) {

        LinkedList<AdditionalAction>all=new LinkedList<>();

        if(rightHand!=null)  for (AdditionalAction action: rightHand.additionalActions ) if(action.getTriggerType().equals(type)) all.add(action);

        if(leftHand!=null) for(AdditionalAction action: leftHand.additionalActions) if(action.getTriggerType().equals(type)) all.add(action);

        if(gear!=null) all.addAll(gear.getAllAdditionalActions(type));

        return all;
    }
}
