package com.brackeen.app.view;

import java.awt.image.BufferedImage;

public class Pressed extends ButtonState{
	private Pressed(BufferedImage img) {
		super(img);
	}
	
	public static synchronized Pressed getInstance(BufferedImage image) {
		if (instance == null)
			instance = new Pressed(image);

		return (Pressed)instance;
	}
}
