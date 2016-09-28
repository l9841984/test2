package com.Obj;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Series {
	// 序列第一个的时间
	public int firstData;
	public int lastData;
	protected Map<Integer, Float> series;
	private float changeTH = 0.2f;
	public int id;
	public float dif;

	public Series(Map<Integer, Float> series, int b, int e, int id) {
		this.series = series;
		firstData = b;
		lastData = e;
		this.id = id;
	}

	public Series cutSeries(int b, int e) {
		Map<Integer, Float> s = new HashMap<Integer, Float>();
		if (b < firstData)
			b = firstData;
		if (e > lastData)
			e = lastData;

		for (int i = b; i <= e; i++) {
			s.put(i, series.get(i));
		}
		return new Series(s, b, e, id);
	}

	public float get(int i) {
		return series.get(i);
	}

	public int size() {
		return series.size();
	}

	public void write(String file) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, true)));
		out.write(firstData + " " + dif + " ");
		int size = series.size();
		int j = 0;
		for (int i = 0; i < size; i++) {
			while (!series.containsKey(firstData + j))
				j++;
			out.write(series.get(firstData + j) + " ");
			j++;
		}
		out.newLine();
		out.close();
	}

	public boolean isAbChange(float a, float b, float rate) {
		if(a == 0 && b <= 10)
			return false;

		if (a == 0 && b != 0)
			return true;
		if ((b - a) / a < rate && (b - a) / a > -rate)
			return false;
		else
			return true;
	}
	
	public float sum(int b,int e){
		float s = 0;
		for(int i = b ;i <= e;i++){
			s+= this.get(i);
		}
		return s;
	}
	
	public ArrayList toArray(){
		ArrayList a = new ArrayList();
		for(int i = firstData;i < lastData;i++){
			a.add(series.get(i));
		}
		return a;
	}
}
