package com.tyss.adminstrongame.util;

import java.time.LocalDate;

public class TestClass {
	public static void main(String args[]) {

		// Unicode 10 emojis that are not supported in Java 8
		String cowboyHatFace = "\uD83E\uDD10";
		String orangeHeart = "\uD83E\uDD13";
		String redGift = "\uD83C\uDF81";
		String flyingSaucer = "\uD83D\uDEF8";
		String kangaroo = "\uD83E\uDD98";

		System.out.println("Cowboy hat face: " + cowboyHatFace);
		System.out.println("Orange heart: " + orangeHeart);
		System.out.println("Red gift: " + redGift);
		System.out.println("Flying saucer: " + flyingSaucer);
		System.out.println("Kangaroo: " + kangaroo);
		
		String message = "Hi %s!".formatted(LocalDate.now());
		String ex = "Hi "+" "+LocalDate.now();
		System.err.println(message);
		

	}

}
