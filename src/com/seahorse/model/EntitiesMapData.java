package com.seahorse.model;

public class EntitiesMapData {
    private EntityType[][] entitiesMap = new EntityType[15][15];

    public EntityType[][] GetEntitiesMap() {
        return entitiesMap;
    }

    private SeaHorse[][] seaHorsesMap = new SeaHorse[15][15];

    public SeaHorse[][] GetSeaHorsesMap() {
        return seaHorsesMap;
    }
}