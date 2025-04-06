package com.seahorse.controller;

import com.seahorse.model.EntitiesMapData;
import com.seahorse.model.EntityType;

public class EntitiesMapController {
    private EntitiesMapData entitiesMapData = new EntitiesMapData();
    
    public EntityType GetTileEntityType(int x, int y) {
        return entitiesMapData.GetEntitiesMap()[x][y];
    }

    public void AddEntityToTile(EntityType entityType, int x, int y) {
        entitiesMapData.GetEntitiesMap()[x][y] = entityType;
    }

    public void DeleteEntityFromTile(int x, int y) {
        entitiesMapData.GetEntitiesMap()[x][y] = EntityType.Null;
    }

    public SeaHorseController GetTileSeaHorse(int x, int y) {
        return entitiesMapData.GetSeaHorsesMap()[x][y];
    }

    public void AddSeaHorseToTile(SeaHorseController seaHorse, int x, int y) {
        entitiesMapData.GetSeaHorsesMap()[x][y] = seaHorse;
    }

    public void DeleteSeaHorseFromTile(int x, int y) {
        entitiesMapData.GetSeaHorsesMap()[x][y] = null;
    }
}
