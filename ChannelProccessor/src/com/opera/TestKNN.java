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
	 * �������ļ��ж�ȡ����
	 * 
	 * @param datas
	 *            �洢���ݵļ��϶���
	 * @param path
	 *            �����ļ���·��
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
	 * ����ִ�����
	 * 
	 * @param args
	 * @throws IOException
	 */
	public int testAData(Series test, int time) throws IOException {
		TestKNN t = new TestKNN();

		List<Series> datas = t.readData();

		KNN knn = new KNN();
		int id = knn.knn(datas, test, 5);
		if (id == -1)// û���ҵ���Ӧ�ĵ�����������Ϊ��������Ѿ������ˣ������ٹء�
			return -1;
		Channel channel = new Channel(id);
		channel.setSampleFromFile(time - 30, 150);

		// �ж�����
		String isState = "";
		if (channel.isEmpty()) {
			isState = "δ֪";
		} else {
			DifSeries channelDiff = channel.sampleDiff();
			int i = test.firstData;// ���㱻�����ݷ����˶��ٱ䶯���������

			int precise = channelDiff.isState(time, test.dif, channel,
					TestKNN.right / TestKNN.testNum);
			if (precise == -1)// ����޷�Ԥ��׼ȷ�ȣ���Ϊû�����ݹ�Ԥ�⣩ �����Ѿ�����ˣ����ﱣ֤һ��
				return -1;
			isState = precise == 1 ? "��ȷ" : "����";
			testNum++;
			asum[id]++;
			if (precise == 1) {
				right++;
				TestKNN.asumRight[id]++;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date((time) * 1000L));

		System.out.print("����Ԫ��Ԥ�� " + " " + date + " " + time + ":  ");
		if (test.get(test.firstData) > 0) {
			System.out.print("��");
		} else {
			System.out.print("��");
		}

		Channel.getLabels();
		System.out.print(Channel.labelsChinese[id] + ":");
		for (int j = test.firstData; j <= test.lastData; j++) {
			System.out.print(test.get(j) + " ");
		}

		System.out.println("");
		// System.out.println("׼ȷ��Ϊ�� " + TestKNN.right / TestKNN.testNum);
		return id;
	}
}
