package com.brackeen.app.view;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Button extends View implements MouseListener {
	
	public interface Listener {
        public void buttonClicked(Button button);
    }

    private boolean armed = false;
    private View rootWhenArmed;
    private Listener buttonListener;
    private ButtonState normal;
    private ButtonState hover;
    private ButtonState pressed;
    private ButtonState currentState;
    
    public Button(BufferedImage normalImage, BufferedImage hoverImage, BufferedImage pressedImage) {
    	normal = new Normal(normalImage);
    	hover = new Hover(hoverImage);
    	pressed = new Pressed(pressedImage);
    	    	
    	currentState = normal;
        setMouseListener(this);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sizeToFit();
    }
    
    @Override
    public void sizeToFit() {
        BufferedImage image = getDisplayedImage();
        if (image == null) {
            setSize(0, 0);
        } else {
            setSize(image.getWidth(), image.getHeight());
        }
    }

    public Listener getButtonListener() {
        return buttonListener;
    }

    public void setButtonListener(Listener buttonListener) {
        this.buttonListener = buttonListener;
    }
    
    private BufferedImage getDisplayedImage() {
		if(currentState.isSelected()){
			return currentState.getSelectedImage();
		} else{
			return currentState.getImage();
		}
	}

    @Override
    public void onDraw(Graphics2D g) {
    	g.drawImage(getDisplayedImage(), null, null);
    }

    public void mouseEntered(MouseEvent me) {
        if (armed && me.getID() == MouseEvent.MOUSE_DRAGGED) {
            currentState = pressed;
        }
        
        if (me.getID() == MouseEvent.MOUSE_MOVED || me.getID() == MouseEvent.MOUSE_ENTERED) {
            currentState = hover;
        }
    }

    public void mouseExited(MouseEvent me) {
        currentState = normal;
    }

    public void mouseClicked(MouseEvent me) {
        // Do nothing - press+release events sent
    }

    public void mousePressed(MouseEvent me) {
        currentState = pressed;
        rootWhenArmed = getRoot();
        armed = true;
    }

    public void mouseReleased(MouseEvent me) {
        View root = getRoot();
        View view = root.pick(me.getX(), me.getY());
        boolean isOver = isAncestorOf(view);
        boolean isSameRoot = root != null && rootWhenArmed == root;
        boolean isTap = armed && currentState == pressed && isOver && isSameRoot;

        if (isOver) {
            currentState = hover;
        } else {
            currentState = normal;
        }
        rootWhenArmed = null;
        armed = false;

        if (isTap && buttonListener != null) {
            buttonListener.buttonClicked(this);
        }    
    }
}
