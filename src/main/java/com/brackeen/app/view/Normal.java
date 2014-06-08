package com.brackeen.app.view;

import java.awt.image.BufferedImage;

public class Normal extends ButtonState {
	private Normal(BufferedImage img){
		super(img);
	}
	
	public static synchronized Normal getInstance(BufferedImage image) {
		if (instance == null)
			instance = new Normal(image);

		return (Normal)instance;
	}
}
