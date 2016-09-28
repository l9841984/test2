package com.opera;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.Obj.Channel;
import com.Obj.DifSeries;

public class Operate {
	static String threPath = "threshold/";
	static int[] bigAppList = { 5, 6, 10, 11, 13, 22, 36 };// 39,41

	// static float[] door = { 1500, 1500, 1800, 1000, 1000, 1000, 500 };//
	// 1500,1000

	public static void main(String[] args) throws IOException {

//		int window = 20;
//		Channel.getLabels();
//		makeNewFile();// 创建新文件，储存阈值
//		Channel all = new Channel(0);
//		int startT = 1363549240, n500000 = 1;
//		for (int o = 0; o < n500000; o++) {
//			all.setSampleFromFile(startT + o * 500000, 500000);
//			for (int q = 0; q < bigAppList.length; q++) {
//
//				int i = bigAppList[q];
//				System.out.println(o + "    " + i);
//				Channel nowChannel = new Channel(i);
//				nowChannel.setSampleFromFile(startT + o * 500000, 500000);
//				InputStream is;
//				BufferedReader reader;
//				String line = "";
//				is = new FileInputStream(threPath + "threshold" + i + ".txt");
//				reader = new BufferedReader(new InputStreamReader(is));
//				line = reader.readLine();
//				float max = Float.parseFloat(line.split(" ")[0]);
//				float min = Float.parseFloat(line.split(" ")[1]);
//				int maxT = Integer.parseInt(line.split(" ")[2]);
//				int minT = Integer.parseInt(line.split(" ")[3]);
//
//				line = reader.readLine();
//				float itMax = Float.parseFloat(line.split(" ")[0]);
//				float itMin = Float.parseFloat(line.split(" ")[1]);
//				int itmaxT = Integer.parseInt(line.split(" ")[2]);
//				int itminT = Integer.parseInt(line.split(" ")[3]);
//				reader.close();
//
//				DifSeries diff = nowChannel.sampleDiff();
//				DifSeries allDiff = all.sampleDiff();
//				for (int nowT = nowChannel.firstData + 1; nowT < nowChannel.lastData; nowT++) {// 遍历该电器的每次变化
//
//					if (diff.get(nowT) == 0) {// 如果在该时间点没有改变
//						continue;
//					}
//					float appValue = diff.get(nowT);
//					
//					
//					if (Math.abs(appValue) > itMax) {
//						itMax = Math.abs(appValue);
//						itmaxT = nowT;
//					}
//					if (Math.abs(appValue) < itMin) {
//						itMin = Math.abs(appValue);
//						itminT = nowT;
//					}
//					float closest = getMainDiff(allDiff, nowT - window,
//							nowT + 5, appValue);
//					closest = Math.abs(closest);// 得到最接近的主线路中的变化
//					if (closest > max) {
//						max = closest;
//						maxT = nowT;
//					}
//					if (closest < min) {
//						min = closest;
//						minT = nowT;
//					}
//				}
//				File file = new File(threPath + "threshold" + i + ".txt");
//				FileWriter fw = new FileWriter(file.getAbsoluteFile());
//				BufferedWriter bw = new BufferedWriter(fw);
//				String inLine = "" + max + " " + min + " " + maxT + " " + minT
//						+ "\r\n" + itMax + " " + itMin + " " + itmaxT + " "
//						+ itminT + "\r\n";
//
//				bw.write(inLine);
//				bw.close();
//			}
//
//		}
	}

	/*
	 * 创建文件储存阈值，如果存在就不建设了格式： 第一行，最大值最小值，每行储存一个值
	 */
	public static void makeNewFile() throws IOException {
		for (int q = 0; q < bigAppList.length; q++) {
			int i = bigAppList[q];
			File fileName = new File(threPath + "threshold" + i + ".txt");
			if (fileName.exists())
				fileName.delete();
			fileName.createNewFile();
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("0 9999 0 0\n0 9999 0 0\n");
			bw.close();

		}
	}

	public static float getMainDiff(Map<Integer, Float> allDiff, int beginWin,
			int endWin, float cmp) {
		float sum = 0;
		for (int i = beginWin; i < endWin; i++) {
			if (allDiff.containsKey(i)) {
				if (allDiff.get(i) == 0)
					return getMainDiffStateNone(allDiff, i, endWin, cmp);
				else
					return getMainDiffStateIn(allDiff, i, endWin, cmp);
			}
		}
		return sum;
	}

	public static float getMainDiffStateNone(Map<Integer, Float> allDiff,
			int nowT, int endWin, float cmp) {
		float sum = 0;
		for (int i = nowT + 1; i < endWin; i++) {
			if (allDiff.containsKey(i)) {
				if (allDiff.get(i) != 0)
					return getMainDiffStateIn(allDiff, i, endWin, cmp);
			}
		}
		return sum;
	}

	public static float getMainDiffStateIn(Map<Integer, Float> allDiff,
			int nowT, int endWin, float cmp) {
		float sum = 0, sum2 = 0;
		int i;
		for (i = nowT; i < endWin; i++) {
			if (allDiff.containsKey(i)) {
				if (allDiff.get(i) != 0)
					sum += allDiff.get(i);
				else {
					break;
				}
			}
		}
		sum2 = getMainDiffStateNone(allDiff, i, endWin, cmp);
		return Math.abs(sum - cmp) < Math.abs(sum2 - cmp) ? sum : sum2;
	}
}
