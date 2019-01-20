package com.test.kcb.constant;

public class Constant {
	private static int classh;
	private static int classm;

	public static int getHour(int i) {
		switch (i) {
		case 1:
			classh = 8;
			break;
		case 2:
			classh = 8;
			break;
		case 3:
			classh = 10;
			break;
		case 4:
			classh = 10;
			break;
		case 5:
			classh = 13;
			break;
		case 6:
			classh = 14;
			break;
		case 7:
			classh = 15;
			break;
		case 8:
			classh = 16;
			break;
		case 9:
			classh = 19;
			break;
		case 10:
			classh = 20;
			break;
		case 11:
			classh = 21;
			break;
		}
		return classh;
	}

	public static int getMinutes(int i) {
		switch (i) {
		case 1:
			classm = 0;
			break;
		case 2:
			classm = 50;
			break;
		case 3:
			classm = 5;
			break;
		case 4:
			classm = 55;
			break;
		case 5:
			classm = 20;
			break;
		case 6:
			classm = 30;
			break;
		case 7:
			classm = 20;
			break;
		case 8:
			classm = 35;
			break;
		case 9:
			classm = 30;
			break;
		case 10:
			classm = 20;
			break;
		case 11:
			classm = 10;
			break;
		}
		return classm;
	}
}
