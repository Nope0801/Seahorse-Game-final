package com.seahorse.model;

import com.seahorse.controller.SeaHorseController;

public class EntitiesMapData {
    private EntityType[][] entitiesMap = new EntityType[15][15];

    public EntityType[][] GetEntitiesMap() {
        return entitiesMap;
    }

    private SeaHorseController[][] seaHorsesMap = new SeaHorseController[15][15];

    public SeaHorseController[][] GetSeaHorsesMap() {
        return seaHorsesMap;
    }
}