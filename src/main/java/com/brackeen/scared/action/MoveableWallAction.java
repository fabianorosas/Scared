package com.brackeen.scared.action;

import com.brackeen.app.App;
import com.brackeen.scared.Map;
import com.brackeen.scared.SoftTexture;
import com.brackeen.scared.Tile;

public class MoveableWallAction implements Action {
    
    public static final int STATE_DONE = 0;
    public static final int STATE_MOVING = 1;
    
    private static final int TICKS_PER_TILE_MOVE = 60;
    
    private Map map;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private Tile tile;
    private SoftTexture floorTexture;
    private int index;
    private int ticks;
    
    public MoveableWallAction(Map map, int x, int y) {
        int playerTileX = (int)map.getPlayer().getX();
        int playerTileY = (int)map.getPlayer().getY();
        
        this.map = map;
        this.x = x;
        this.y = y;
        this.dx = x - playerTileX;
        this.dy = y - playerTileY;
        
        Tile playerTile = map.getTileAt(playerTileX, playerTileY);
        tile = map.getTileAt(x, y);
        tile.setState(STATE_MOVING);
        
        map.setDefaultFloorTexture(playerTile.getTexture());
        floorTexture = playerTile.getTexture();
        
        App.getApp().getAudio("/sound/wallmove.wav", 1).play();
        index = 0;
        ticks = 0;
    }

    public void unload() {
        // Do nothing
    }

    public boolean isFinished() {
        return (index > 2);
    }
    
    public void tick() {
        if (isFinished()) {
            return;
        }

        if (ticks < TICKS_PER_TILE_MOVE) {
            ticks++;
            tile.setRenderState(Tile.getRenderStateMax() * ticks / TICKS_PER_TILE_MOVE);
        }
        else {
            index++;
            SoftTexture texture = tile.getTexture();
            tile.setTexture(floorTexture);
            tile.setType(0);
            tile.setSubtype(0);
            tile.setState(0);
            tile.setRenderState(0);

            x += dx;
            y += dy;

            tile = map.getTileAt(x, y);
            tile.setTexture(texture);
            tile.setType(Tile.getTypeMovableWall());
            tile.setSubtype(0);
            tile.setState(STATE_MOVING);
            tile.setRenderState(0);

            if (index == 2) {
                index = 3;
                tile.setType(Tile.getTypeWall());
                tile.setState(0);
                tile.setRenderState(0);
            }
            ticks = 0;
        }
    }    
}
