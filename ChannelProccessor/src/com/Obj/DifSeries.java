package com.Obj;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class DifSeries extends Series {
	// 这是一个经故宫createTH变化过滤过的数列所以类里不需要再有create

	private float createTH;
	public ChannelState channelState = new ChannelState();
	Channel c;

	public float getTH() {
		return createTH;
	}

	public DifSeries(Map<Integer, Float> series, int a, int b, float TH,
			int id, Channel c1) {
		super(series, a, b, id);
		c = c1;
		createTH = TH;
	}

	public No0Series cumN0(int time, int endT) {
		float sum = 0f;
		int i;
		Map<Integer, Float> s = new HashMap<Integer, Float>();
		for (i = time; i <= endT; i++) {
			if (series.get(i) == 0)
				// if ((series.get(i + 10) - series.get(i)) / series.get(i) <
				// 0.3
				// && (series.get(i + 10) - series.get(i)) / series.get(i) >
				// -0.3)
				break;
			s.put(i, series.get(i));
			sum += series.get(i);
		}
		No0Series ns = new No0Series(s, time, i - 1, sum, id);
		return ns;
	}

	/**
	 * 找到电器状态变化的开端
	 * 
	 * @param t
	 *            从这个时间开始找
	 * @return 找到的时间
	 */
	public DifData getNo0T(int t) {
		DifData dd = new DifData();
		int i = -1;
		boolean isValidChange = false;
		for (i = t; i <= lastData - 50; i++) {
			if (Math.abs(series.get(i)) > 30// ) {
					&& isAbChange(c.get(i - 1), c.get(i), 0.2f)) {// 找到变化开端
				dd.preT = i - 1;// 发生变化的前一点

				// 突变暂时不判断
				// // 判断是不是突变，有没有稳态
				int stableNum = 0;
				for (int j = i + 1; j <= i + 50; j++) {
					if (Math.abs(series.get(j)) > 30// ) {
							&& isAbChange(c.get(j - 1), c.get(j), 0.15f)) {//
						// 如果10s之内有再次有明显变化说明是突变
						stableNum = 0;
						i = j;// 是一个突变，所以忽略这个变化，直接跳过
						continue;
					}
					stableNum++;
					if (stableNum > 30) {
						dd.stableT = j - 29;
						break;
					}
				}
				if (stableNum > 30) {
					isValidChange = true;
					dd.dif = c.get(dd.stableT) - c.get(dd.preT);//sum(dd.preT, dd.stableT);
					if (dd.dif < 10 && dd.dif > -10) {// 这只是一个抽动
						isValidChange = false;
						i = dd.stableT;
						continue;
					}
					i = dd.stableT;
					break;
				}
			}
		}
		if (!isValidChange)// 如果已经超出了最后的时间限制，返回-1
			return null;
		return dd;
	}

	/**
	 * 
	 * @param time
	 * @param aroudV
	 *            变化阈值
	 * @return
	 */
	public int isState(int time, double aroudV, Channel c, double q) {

		DifData dd;
		int state = 0;
		try {
			int nowT = time - 20;
			dd = getNo0T(nowT);// DifSeries这个序列本身已经过滤过了，不需要再设置阈值
			if (dd == null) {
				if (q < (45 + Math.random() * (53 - 40 + 1))/100)
					return 1;
				return 0;
			}
			nowT = dd.preT + 1;
			state = 1;

			return state;
		} catch (Exception e) {
			return -1;
		}
	}

	public void writeChannelState(String file) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, true)));
		out.write("" + 0);
		out.newLine();
		out.close();
		channelState.write(file);
	}

}
