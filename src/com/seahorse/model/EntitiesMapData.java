package com.seahorse.model;

import com.seahorse.controller.SeaHorseController;
import com.seahorse.view.SeaHorseView;

public class EntitiesMapData {
    private EntityType[][] entitiesMap = new EntityType[15][15];

    public EntityType[][] GetEntitiesMap() {
        return entitiesMap;
    }

    private SeaHorseController[][] seaHorsesMap = new SeaHorseController[15][15];

    public SeaHorseController[][] GetSeaHorsesMap() {
        return seaHorsesMap;
    }

    public SeaHorseView seaHorsesView[][] = new SeaHorseView[15][15];
}