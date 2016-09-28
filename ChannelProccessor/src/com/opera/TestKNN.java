package com.opera;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Obj.Channel;
import com.Obj.DifSeries;
import com.Obj.Series;
import com.knn.KNN;
import com.knn.KNNDataNode;

public class TestKNN {
	/**
	 * 从数据文件中读取数据
	 * 
	 * @param datas
	 *            存储数据的集合对象
	 * @param path
	 *            数据文件的路径
	 * @throws IOException
	 */
	static float testNum = 0;
	static float right = 0;
	static int[] asum = new int[54];
	static int[] asumRight = new int[54];

	public List<Series> readData() throws IOException {
		String filepath = "preTrain";
		File file = new File(filepath);
		List<Series> allData = new ArrayList<Series>();
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++) {
			KNNDataNode node = new KNNDataNode(Integer.parseInt(filelist[i]
					.split("\\.")[0]));
			node.read();
			allData.addAll(node.data);
		}
		return allData;
	}

	/**
	 * 程序执行入口
	 * 
	 * @param args
	 * @throws IOException
	 */
	public int testAData(Series test, int time) throws IOException {
		TestKNN t = new TestKNN();

		List<Series> datas = t.readData();

		KNN knn = new KNN();
		int id = knn.knn(datas, test, 5);
		if (id == -1)// 没有找到对应的电器，可能因为这个电器已经关着了，不能再关。
			return -1;
		Channel channel = new Channel(id);
		channel.setSampleFromFile(time - 30, 150);

		// 判断正误
		String isState = "";
		if (channel.isEmpty()) {
			isState = "未知";
		} else {
			DifSeries channelDiff = channel.sampleDiff();
			int i = test.firstData;// 计算被测数据发生了多少变动（求和来）

			int precise = channelDiff.isState(time, test.dif, channel,
					TestKNN.right / TestKNN.testNum);
			if (precise == -1)// 如果无法预测准确度（因为没有数据供预测） 上面已经测过了，这里保证一下
				return -1;
			isState = precise == 1 ? "正确" : "错误";
			testNum++;
			asum[id]++;
			if (precise == 1) {
				right++;
				TestKNN.asumRight[id]++;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date((time) * 1000L));

		System.out.print("测试元组预测 " + " " + date + " " + time + ":  ");
		if (test.get(test.firstData) > 0) {
			System.out.print("开");
		} else {
			System.out.print("关");
		}

		Channel.getLabels();
		System.out.print(Channel.labelsChinese[id] + ":");
		for (int j = test.firstData; j <= test.lastData; j++) {
			System.out.print(test.get(j) + " ");
		}

		System.out.println("");
		// System.out.println("准确率为： " + TestKNN.right / TestKNN.testNum);
		return id;
	}
}
