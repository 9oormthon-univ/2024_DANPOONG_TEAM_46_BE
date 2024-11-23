package com.goormthon.bookduchilseong.domain.zodiacsign.entity;

public enum Zodiacsigns {
	ARIES("templates.zodiacsigns/ARIES.png"),
	TAURUS("templates.zodiacsigns/TAURUS.png"),
	GEMINI("templates.zodiacsigns/GEMINI.png"),
	CANCER("templates.zodiacsigns/CANCER.png"),
	LEO("templates.zodiacsigns/LEO.png"),
	VIRGO("templates.zodiacsigns/VIRGO.png"),
	LIBRA("templates.zodiacsigns/LIBRA.png"),
	SCORPIO("templates.zodiacsigns/SCORPIO.png"),
	SAGITTARIUS("templates.zodiacsigns/SAGITTARIUS.png"),
	CAPRICORN("templates.zodiacsigns/CAPRICORN.png"),
	AQUARIUS("templates.zodiacsigns/AQUARIUS.png"),
	PISCES("templates.zodiacsigns/PISCES.png");

	private final String imagePath;

	Zodiacsigns(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePath() {
		return imagePath;
	}
}