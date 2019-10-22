package com.github.vitalz.epoi.font.factory.decorator;

import org.apache.poi.ss.usermodel.Font;

import com.github.vitalz.epoi.font.IFontFactory;

public class FontName implements IFontFactory {
	private final IFontFactory factory;
	private final String name;

	public FontName(String name, IFontFactory factory) {
		this.factory = factory;
		this.name = name;
	}

	@Override
	public Font font() {
		Font font = factory.font();
		font.setFontName(name);
		return font;
	}

}
