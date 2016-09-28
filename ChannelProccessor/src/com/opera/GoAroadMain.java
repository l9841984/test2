package com.opera;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.Obj.Channel;
import com.Obj.Channel0;
import com.Obj.DifData;
import com.Obj.DifSeries;
import com.Obj.Series;

public class GoAroadMain {
	public static void main(String[] args) throws NumberFormatException,
			IOException {

		System.out.println("请选择开始时间");
		Scanner in=new Scanner(System.in);
		int begT = 1368445878;
		begT = in.nextInt();
		in.close();
		int j = 0;
		int allL = 1000000;
		int seriesL = 14;
		// float searchTH = 10F;
		Channel all = new Channel0();
		all.setSampleFromFile(begT + 50, allL);
		// all.setTH(searchTH);
		DifSeries allDif = all.sampleDiff();

		int nowT = allDif.firstData;
		DifData dd;
		int i = nowT;
		while (true) {
			dd = allDif.getNo0T(nowT);
			if (dd == null)
				break;
			nowT = dd.preT + 1;
			Series alli = allDif.cutSeries(nowT, nowT + seriesL);
			TestKNN knn = new TestKNN();
			alli.dif = dd.dif;
			int id = knn.testAData(alli, alli.firstData);

			// 总功率展示
			FileWriter fw;
			BufferedWriter bw = null;
			while (i != nowT) {
				fw = new FileWriter("data/main.txt",true);
				bw = new BufferedWriter(fw);
				bw.write(i + " " + all.getValue(i) + " " + 0);
				bw.newLine();
				i++;
				bw.close();
			}
			fw = new FileWriter("data/main.txt",true);
			bw = new BufferedWriter(fw);
			id = dd.dif > 0 ? id : -id;
			bw.write(i + " " + all.getValue(i) + " " + id);
			bw.newLine();
			i++;
			bw.close();

			nowT = dd.stableT + 1;
			
			j++;
			if(j > 100){
				System.out.println("准确率为： " + TestKNN.right / TestKNN.testNum);
				j = 0;
			}
		}
	}
}
