package com.knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Obj.Series;

public class KNNDataNode {
	int id;
	public List<Series> data = new ArrayList<Series>();

	public KNNDataNode(int id) {
		this.id = id;
	}

	public void read() throws IOException {
		String datafile = new File("").getAbsolutePath() + File.separator
				+ "preTrain/" + id + ".txt";
		BufferedReader br = new BufferedReader(new FileReader(
				new File(datafile)));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] datas = line.split(" ");
			Map<Integer, Float> set = new HashMap<Integer, Float>();
			int time = Integer.parseInt(datas[0]);
			float dif = Float.parseFloat(datas[1]);
			for (int i = 2; i <= 16; i++) {
				set.put(time + i - 2, Float.parseFloat(datas[i]));
			}
			Series s = new Series(set, time, time + 14, id);
			s.dif = dif;
			data.add(s);
		}
		br.close();
	}
}
