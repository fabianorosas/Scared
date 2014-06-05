package com.brackeen.scared;

import com.brackeen.scared.entity.Entity;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    
    private static final int RENDER_STATE_MAX = (1 << 16);
    
    private static final int TYPE_NOTHING = 0;
    private static final int TYPE_WALL = 1;
    private static final int TYPE_DOOR = 2;  //(subtype: key #)
    private static final int TYPE_WINDOW = 3;  //(subtype: 1=west/east 2=north/south)
    private static final int TYPE_GENERATOR = 4;
    private static final int TYPE_MOVABLE_WALL = 5;
    private static final int TYPE_EXIT = 6;
    private static final int NUM_TYPES = 7;
    
    private int type;
    private int subtype;
    private int state;
    private int renderState;
    private int renderVisible;
    private SoftTexture texture;
    private List<Entity> entities;
    
    /* Checks if the tile is solid for collision purposes. */
    public boolean isSolid() {
        if (getType() == getTypeDoor()) {
            return getRenderState() < getRenderStateMax() * 3 / 4;
        }
        else {
            return (getType() != getTypeNothing());
        }
    }
    
    public List<Entity> getEntities() {
        return entities;
    }
    
    public boolean hasEntities() {
        return (entities != null && entities.size() > 0);
    }

    public SoftTexture getTexture() {
        return texture;
    }

    public void setTexture(SoftTexture texture) {
        if (!texture.isPowerOfTwo()) {
            throw new IllegalArgumentException("Texture not a power of two");
        }
        this.texture = texture;
    }
    
    public void addEntity(Entity entity) {
        if (entity.getTile() != null) {
            entity.getTile().removeEntity(entity);
        }
        if (entities == null) {
            entities = new ArrayList<Entity>();
        }
        entities.add(entity);
        entity.setTile(this);
    }
    
    public void removeEntity(Entity entity) {
        if (entity.getTile() == this) {
            entity.setTile( null);
        }
        if (entities != null) {
            entities.remove(entity);
        }
    }

	public static int getRenderStateMax() {
		return RENDER_STATE_MAX;
	}

	public static int getTypeNothing() {
		return TYPE_NOTHING;
	}

	public static int getTypeWall() {
		return TYPE_WALL;
	}

	public static int getTypeDoor() {
		return TYPE_DOOR;
	}

	public static int getTypeWindow() {
		return TYPE_WINDOW;
	}

	public static int getTypeGenerator() {
		return TYPE_GENERATOR;
	}

	public static int getTypeMovableWall() {
		return TYPE_MOVABLE_WALL;
	}

	public static int getTypeExit() {
		return TYPE_EXIT;
	}

	public static int getNumTypes() {
		return NUM_TYPES;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubtype() {
		return subtype;
	}

	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRenderState() {
		return renderState;
	}

	public void setRenderState(int renderState) {
		this.renderState = renderState;
	}

	public int getRenderVisible() {
		return renderVisible;
	}

	public void setRenderVisible(int renderVisible) {
		this.renderVisible = renderVisible;
	}
}
