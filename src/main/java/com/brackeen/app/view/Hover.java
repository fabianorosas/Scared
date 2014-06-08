package com.brackeen.app.view;

import java.awt.image.BufferedImage;

public class Hover extends ButtonState{
	private Hover(BufferedImage img){
		super(img);
	}
	
	public static synchronized Hover getInstance(BufferedImage image) {
		if (instance == null)
			instance = new Hover(image);

		return (Hover)instance;
	}
}
