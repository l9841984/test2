package com.Obj;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Channel0 extends Channel {

	private Map<Integer, FloatPair> dataSample = new HashMap<Integer, FloatPair>();

	public Channel0() {
		super(0);

	}

//	public void setSampleFromFile(int timeStamp, int timeLong)
//			throws NumberFormatException, IOException {
//
//		InputStream is;
//		BufferedReader reader;
//		String line = "";
//		is = new FileInputStream(filePath + "channel_" + id + ".dat");
//		reader = new BufferedReader(new InputStreamReader(is));
//		boolean firstDataFlag = true;
//		while ((line = reader.readLine()) != null) { // ��� line Ϊ��˵��������
//
//			int time = Integer.parseInt((line.split(" ")[0]).split("\\.")[0]);
//
//			if (time < timeStamp)
//				continue;
//			if (time > timeLong + timeStamp)
//				break;
//			if (firstDataFlag) {
//				firstData = time;
//				firstDataFlag = false;
//			}
//			dataSample.put(
//					time,
//					new FloatPair(Float.parseFloat(line.split(" ")[1]), Float
//							.parseFloat(line.split(" ")[2])));
//
//			lastData = time;
//		}
//		reader.close();
//		is.close();
//		if (dataSample.isEmpty())// ���ʱ���û������
//			return;
//		fullData();
//		// if (id != 0)
//		// getTH();
//	}
//
//	public DifSeries sampleDiff() {
//		Map<Integer, Float> diff = new HashMap<Integer, Float>();
//		for (int i = firstData + 1; i <= lastData - 1; i++) {
//			float dif = dataSample.get(i) - dataSample.get(i - 1);
//			if (Math.abs(dif) < TH) {
//				dif = 0;
//				diff.put(i, dif);
//				continue;
//			}
//			// ͻ���ж�����DifSeries�����Լ����
//			// // ���ڵ��н�Ծ���ж��Ƿ���ͻ��
//			// if (!validChange(dataSample.get(i - 1), dataSample.get(i + 1),
//			// 0.2F)) {
//			// dataSample.put(i, dataSample.get(i - 1));
//			// diff.put(i, 0f);
//			// continue;
//			// }
//			// ����ͻ��
//			diff.put(i, dif);
//		}
//		DifSeries s = new DifSeries(diff, firstData + 1, lastData - 1, TH, id,
//				this);
//		return s;
//	}
	
}

class FloatPair {
	FloatPair(float aa, float bb) {
		a = aa;
		b = bb;
	}

	float a;
	float b;
}