package com.Obj;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Channel {
	private String name;
	private String chineseName;
	protected int id;
	private float TH = 0;
	public int firstData;
	public int lastData;
	private Map<Integer, Float> dataSample = new HashMap<Integer, Float>();
	public static String[] labels = new String[54];
	public static String[] labelsChinese = new String[54];
	public static String filePath = "orig/";

	public void setTH(float th) {
		TH = th;
	}

	public boolean isEmpty() {
		return dataSample.isEmpty();
	}

	public static void getLabels() throws IOException {
		InputStream is = new FileInputStream(filePath + "labels.dat");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String line = ""; // 用来保存每行读取的内容
		int o = 1;
		while ((line = reader.readLine()) != null) { // 如果 line 为空说明读完了
			labels[o] = line.split(" ")[1];
			labelsChinese[o] = line.split(" ")[2];
			o++;
		}
		reader.close();
	}

	public void clearSample() {
		dataSample.clear();
	}

	public int getBeginTFromFile() throws IOException {
		InputStream is;
		BufferedReader reader;
		String line = "";
		is = new FileInputStream(filePath + "channel_" + id + ".dat");
		reader = new BufferedReader(new InputStreamReader(is));

		line = reader.readLine();
		int time = Integer.parseInt((line.split(" ")[0]).split("\\.")[0]);
		reader.close();
		return time;
	}

	public void setSampleFromFile(int timeStamp, int timeLong)
			throws NumberFormatException, IOException {
		InputStream is;
		BufferedReader reader;
		String line = "";
		is = new FileInputStream(filePath + "channel_" + id + ".dat");
		reader = new BufferedReader(new InputStreamReader(is));
		boolean firstDataFlag = true;
		while ((line = reader.readLine()) != null) { // 如果 line 为空说明读完了

			int time = Integer.parseInt((line.split(" ")[0]).split("\\.")[0]);

			if (time < timeStamp)
				continue;
			if (time > timeStamp + timeLong)
				break;
			if (firstDataFlag) {
				firstData = time;
				firstDataFlag = false;
			}
			if (id == 0)
				dataSample.put(time, Float.parseFloat(line.split(" ")[1]));
			else
				dataSample.put(time, Float.parseFloat(line.split(" ")[1]));
			lastData = time;
		}
		reader.close();
		is.close();
		if (dataSample.isEmpty())// 这个时间段没有数字
			return;
		fullData();
		// if (id != 0)
		// getTH();
	}

	public void fullData() {
		int i1 = firstData;
		for (int i = i1 + 1; i < lastData; i++) {
			if (dataSample.containsKey(i)) {
				continue;
			}
			int nextP = nextNValueDis(i, 1);
			int preP = preNValueDis(i, 1);
			setAllData(preP, nextP);
			i = nextP;
		}

	}

	private void setAllData(int preP, int nextP) {
		int d = nextP - preP;
		float PP = dataSample.get(preP);
		float NP = dataSample.get(nextP);
		for (int i = 1; i < d / 2; i++) {
			dataSample.put(preP + i, PP);
		}
		for (int i = d / 2; i < d; i++) {
			dataSample.put(preP + i, NP);
		}

	}

	public Channel(int id) {
		this.id = id;
		name = labels[id];
		chineseName = labelsChinese[id];
	}

	public float getNStepMinus(int timeStamp, int step) {
		float[] sample = new float[step + 1];
		for (int i = 0; i <= step; i++) {
			if (dataSample.containsKey(timeStamp - i))
				sample[i] = dataSample.get(timeStamp - i);
			else
				return -9999F;// 主线路在这一点之前6秒没有记录，所有没有n阶差分

		}
		return loop(sample, step);
	}

	// public int isChanged(int timeStamp) {
	// if (!dataSample.containsKey(timeStamp)
	// || !dataSample.containsKey(timeStamp - dis)
	// || !dataSample.containsKey(timeStamp + dis))
	// return 0;
	// float nowV = dataSample.get(timeStamp);
	// float preV = dataSample.get(timeStamp - dis);
	// float nextV = dataSample.get(timeStamp + dis);
	// if ((nowV - preV) * (nextV - nowV) < 0)// 三点不是一个变化趋势，说明是一个暂时的变化，不算
	// return 0;
	// if (nowV != 0 && preV == 0) {
	// return 1;
	// }
	// if (nowV == 0 && preV == 1)
	// return -1;
	// if (nowV == 0 && preV == 0)
	// return 0;
	// if (Math.abs((nowV - preV) / preV) > changeTH
	// && Math.abs((nextV - nowV) / nowV) < changeTH) {
	// if (nowV - preV > 0)
	// return 1;
	// else
	// return -1;
	// } else
	// return 0;
	// }

	// 递归求差分值
	private float loop(float[] series, int num) {
		if (num == 1)
			return series[0] - series[1];
		float[] sample = new float[num];
		for (int i = 0; i < num; i++) {
			sample[i] = series[i] - series[i + 1];
		}
		return loop(sample, num - 1);
	}

	// /**
	// * 判断上一个有值得时间大概是在几个dis
	// *
	// * @param timeStamp
	// * @return
	// */
	// private int disNum(int timeStamp) {
	// return (int) Math.round(preNValueDis(timeStamp, 1) * 1.0 / dis);
	// }

	// 查看不为空的前n个,返回值：时间差
	private int preNValueDis(int timeStamp, int num) {
		int j = 0;
		for (int i = 1; i <= num; i++) {
			j++;
			while (!dataSample.containsKey(timeStamp - j))
				j++;
		}
		return timeStamp - j;
	}

	private int nextNValueDis(int timeStamp, int num) {
		int j = 0;
		for (int i = 1; i <= num; i++) {
			j++;
			while (!dataSample.containsKey(timeStamp + j))
				j++;
		}
		return timeStamp + j;
	}

	public String getChineseName() {
		return chineseName;
	}

	public String getName() {
		return name;
	}


	public float getValue(int t) {
		return dataSample.get(t);
	}

	public float get(int t) {
		return dataSample.get(t);
	}

	public DifSeries sampleDiff() {
		Map<Integer, Float> diff = new HashMap<Integer, Float>();
		for (int i = firstData + 1; i <= lastData - 1; i++) {
			float dif = dataSample.get(i) - dataSample.get(i - 1);
			if (Math.abs(dif) < TH) {
				dif = 0;
				diff.put(i, dif);
				continue;
			}
			// 突变判断留到DifSeries类里自己完成
			// // 本节点有阶跃，判断是否是突变
			// if (!validChange(dataSample.get(i - 1), dataSample.get(i + 1),
			// 0.2F)) {
			// dataSample.put(i, dataSample.get(i - 1));
			// diff.put(i, 0f);
			// continue;
			// }
			// 不是突变
			diff.put(i, dif);
		}
		DifSeries s = new DifSeries(diff, firstData + 1, lastData - 1, TH, id,
				this);
		return s;
	}

	// private void getTH() {
	// float sum = 0;
	// int num = 0;
	// for (int i = firstData; i <= lastData; i++) {
	// sum += dataSample.get(i);
	// if (dataSample.get(i) > 50)
	// num++;
	// }
	// // if(num == 0)
	// // System.out.println("电器" + id + "是小功率电器");
	// TH = sum / num;
	// }

}
