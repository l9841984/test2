package com.opera;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.Obj.Channel;
import com.Obj.Channel0;
import com.Obj.DifData;
import com.Obj.DifSeries;

/**
 * 给网页产生数据
 * @author linweilan
 *
 */
public class GetDataStream {

	public static void main(String args[]) throws IOException {

		// int appNum = 5;
		int[] bigAppList = { 5, 6, 8, 9, 10 };// ,39,41
		Channel channel = new Channel0();

		channel.setSampleFromFile(1370950000, 10000);
		FileWriter fw = new FileWriter("seriesData/main.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = channel.firstData; i < channel.lastData; i++) {
			int randState = (int) (Math.random() * 11) - 5;
			bw.write(i + " " + channel.getValue(i) + " " + randState);
			bw.newLine();
		}
		bw.close();

		DifSeries diff = channel.sampleDiff();
		fw = new FileWriter("seriesData/mainDiff.txt");
		bw = new BufferedWriter(fw);
		for (int i = channel.firstData + 1; i < channel.lastData - 1; i++) {
			bw.write(i + " " + diff.get(i));
			bw.newLine();
		}
		bw.close();

		// 用电器
		for (int q = 0; q < bigAppList.length; q++) {
			Channel channel5 = new Channel(bigAppList[q]);
			channel5.setSampleFromFile(1370950000, 10000);
			fw = new FileWriter("seriesData/" + bigAppList[q]+".txt");
			bw = new BufferedWriter(fw);
			int stateT = channel5.firstData;
			DifSeries d = channel5.sampleDiff();
			DifData dd;
			dd = d.getNo0T(stateT + 1);
			stateT = dd.preT + 1;
			for (int i = channel5.firstData; i < channel5.lastData; i++) {
				int nowState = 0;
				if (i == stateT) {
					if (d.get(i) > 0)
						nowState = 1;
					if (d.get(i) < 0)
						nowState = -1;
					stateT += 10;
					dd = d.getNo0T(stateT);
					if(dd== null)
						stateT = -1;
					else
						stateT = dd.preT +1;
				}
				bw.write(i + " " + channel5.getValue(i) + " " + nowState);
				bw.newLine();
			}
			bw.close();
		}
	}
}
