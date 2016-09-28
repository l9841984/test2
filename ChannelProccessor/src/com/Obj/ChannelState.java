package com.Obj;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChannelState {

	@SuppressWarnings("rawtypes")
	class SortByHigh implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			if (((Gear) o1).high <= ((Gear) o2).high) {
				return -1;
			} else {
				return 1;
			}
		}
	};

	List<Gear> gears = new ArrayList<Gear>();
	private float nowE = 0;
	private int level;

	public void setNowE(float electrocity) {
		nowE = electrocity;
	}

	public float getNowE() {
		return nowE;
	}

	public int getLevel() {
		for (int i = 0; i < gears.size(); i++) {
			if (nowE < gears.get(i).high && nowE > gears.get(i).low) {
				level = i;
				return level;
			}
		}
		return -1;
	}

	class Gear {
		public float low;
		public float high;

		Gear(float low, float high) {
			this.low = low;
			this.high = high;
		}
	}

	public void write(String file) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, true)));
		for (int i = 0; i < gears.size(); i++) {
			out.write(gears.get(i).low + " " + gears.get(i).high);
			out.newLine();
		}
		out.close();
	}

	@SuppressWarnings("unchecked")
	public void addData(float data) {
		if (gears.size() == 0) {
			Gear gear = new Gear(data, data);
			gears.add(gear);
		} else {
			boolean isSet = false;
			for (int i = 0; i < gears.size(); i++) {
				Gear g = gears.get(i);
				if (data > g.high * 1.3)
					continue;
				if (data < g.low * 0.7) {
					Gear nG = new Gear(data, data);
					gears.add(nG);
					isSet = true;
					break;
				}
				if (data > g.high)
					g.high = data;
				if (data < g.low)
					g.low = data;
				isSet = true;
				break;
			}
			if (isSet == false) {
				Gear nG = new Gear(data, data);
				gears.add(nG);
			}
		}

		Collections.sort(gears, new SortByHigh());
	}

}
