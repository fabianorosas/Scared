package com.brackeen.app.view;

import java.awt.image.BufferedImage;

public abstract class ButtonState {
	private BufferedImage image;
	private BufferedImage selectedImage;
	private boolean selected;
	
	protected static ButtonState instance;
	
	protected ButtonState(BufferedImage image) {
        setImage(image);
    }
	
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getSelectedImage() {
		return selectedImage;
	}

	public void setSelectedImage(BufferedImage selectedImage) {
		this.selectedImage = selectedImage;
	}
}

