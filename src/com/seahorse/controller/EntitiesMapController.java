package com.seahorse.controller;

import com.seahorse.model.EntitiesMapData;
import com.seahorse.model.EntityType;
import com.seahorse.utils.PaintComponent;
import com.seahorse.view.SeaHorseView;
import java.awt.Graphics;

public class EntitiesMapController implements PaintComponent{
    private EntitiesMapData entitiesMapData = new EntitiesMapData();
    
    public EntitiesMapController() {
        PaintComponent.AddPaint(this);
    }

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
        entitiesMapData.seaHorsesView[x][y] = seaHorse.getSeaHorseView();
    }

    public void DeleteSeaHorseFromTile(int x, int y) {
        entitiesMapData.GetSeaHorsesMap()[x][y] = null;
        entitiesMapData.seaHorsesView[x][y] = null;
    }

    @Override
    public void Paint(Graphics g) {
        for (SeaHorseView[] row : entitiesMapData.seaHorsesView) {
            for (SeaHorseView paint : row) {
                if (paint == null) continue;
                paint.Paint(g);
            }
        }
    }
}
