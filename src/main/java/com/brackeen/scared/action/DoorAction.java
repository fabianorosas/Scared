package com.brackeen.scared.action;

import com.brackeen.scared.Map;
import com.brackeen.scared.SoundPlayer3D;
import com.brackeen.scared.Tile;
import com.brackeen.scared.entity.Player;

public class DoorAction implements Action {
    
    public static final int DONE = 0;
    public static final int OPENING = 1;
    public static final int OPEN = 2;
    public static final int CLOSING = 3;
    public static final int STAY_OPEN_FOREVER = 4;

    // Fast enough to not stop the player
    private static final int TICKS_TO_OPEN = 12;
    private static final int TICKS_TO_CLOSE = 24;
    private static final int TICKS_WAIT_BEFORE_CLOSING = 200;
    
    private final Map map;
    private final int x;
    private final int y;
    private final Tile tile;
    private int state;
    private int startRenderState;
    private int ticks;
    
    public DoorAction(Map map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        tile = map.getTileAt(x, y);
        
        SoundPlayer3D.play("/sound/doorwoosh.wav", map.getPlayer(), x, y);
        setState(OPENING);
    }
    
    public int getTileX() {
        return x;
    }
    
    public int getTileY() {
        return y;
    }
    
    private void setState(int state) {
        this.state = state;
        tile.setState(state);
        startRenderState = tile.getRenderState();
        ticks = 0;
    }

    public void unload() {
       //Do nothing 
    }

    public boolean isFinished() {
        return state == tile.getState() && (state == DONE || state == STAY_OPEN_FOREVER);
    }

    public void tick() {
    
        if (isFinished()) {
            return;
        }
        
        ticks++;
        
        // State set outside of this handler
        if (state != tile.getState()) {
            setState(tile.getState());
        }
        
        switch (state) {
            case OPENING:
                tile.setRenderState(startRenderState + ticks * Tile.getRenderStateMax() / TICKS_TO_OPEN);
                if (tile.getRenderState() >= Tile.getRenderStateMax()) {
                    tile.setRenderState(Tile.getRenderStateMax());
                    setState(OPEN);
                }
                break;

            case OPEN:
                if (ticks >= TICKS_WAIT_BEFORE_CLOSING && shouldClose()) {
                	setState(CLOSING);
                }
                break;

            case CLOSING:
                if (!shouldClose()) {
                    setState(OPENING);
                } else {
                    if (tile.getRenderState() == Tile.getRenderStateMax()) {
                        SoundPlayer3D.play("/sound/doorwoosh.wav", map.getPlayer(), x, y);
                    }

                    tile.setRenderState(startRenderState - ticks * Tile.getRenderStateMax() / TICKS_TO_CLOSE);
                    if (tile.getRenderState() <= 0) {
                        tile.setRenderState(0);
                        setState(DONE);
                        SoundPlayer3D.play("/sound/doorclose.wav", map.getPlayer(), x, y);
                    }
                }
                break;

            case STAY_OPEN_FOREVER:
                tile.setRenderState(0);
                break;
            default:
            	break;
        }
    }
    
    private boolean shouldClose() {
        if (tile.hasEntities()) {
            return false;
        }
        Player p = map.getPlayer();
        float dx = Math.abs(p.getX() - (x + 0.5f));
        float dy = Math.abs(p.getY() - (y + 0.5f));

        return dx < 1.5f && dy < 1.5f;
    }
}
