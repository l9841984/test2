package com.opera;

import java.io.IOException;
import java.util.Scanner;

public class GetAllStream {
	static int[] bigAppList = { 5, 6, 8, 9, 10, 11, 12, 13, 22, 25, 39, 40, 53 };// {2,3,4,8,9,10,11};

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		GetSuitStream g = new GetSuitStream();
		// for (int i = 0; i < bigAppList.length; i++) {
		// g.getSuit(bigAppList[i], 15);
		// }
		System.out.println("ÇëÌØÕ÷É¨Ãè·¶Î§Ê±³¤");
		Scanner in=new Scanner(System.in);
		int allLong = in.nextInt();
		//int allLong = 500000;
		for (int j = 2; j < 54; j++) {
			g.getSuit(j, 15, allLong);
		}
		System.out.println("É¨Ãè½áÊø\n");
	}
}
