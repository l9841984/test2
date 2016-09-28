package com.Obj;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class DifSeries extends Series {
	// ����һ�����ʹ�createTH�仯���˹��������������ﲻ��Ҫ����create

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
	 * �ҵ�����״̬�仯�Ŀ���
	 * 
	 * @param t
	 *            �����ʱ�俪ʼ��
	 * @return �ҵ���ʱ��
	 */
	public DifData getNo0T(int t) {
		DifData dd = new DifData();
		int i = -1;
		boolean isValidChange = false;
		for (i = t; i <= lastData - 50; i++) {
			if (Math.abs(series.get(i)) > 30// ) {
					&& isAbChange(c.get(i - 1), c.get(i), 0.2f)) {// �ҵ��仯����
				dd.preT = i - 1;// �����仯��ǰһ��

				// ͻ����ʱ���ж�
				// // �ж��ǲ���ͻ�䣬��û����̬
				int stableNum = 0;
				for (int j = i + 1; j <= i + 50; j++) {
					if (Math.abs(series.get(j)) > 30// ) {
							&& isAbChange(c.get(j - 1), c.get(j), 0.15f)) {//
						// ���10s֮�����ٴ������Ա仯˵����ͻ��
						stableNum = 0;
						i = j;// ��һ��ͻ�䣬���Ժ�������仯��ֱ������
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
					if (dd.dif < 10 && dd.dif > -10) {// ��ֻ��һ���鶯
						isValidChange = false;
						i = dd.stableT;
						continue;
					}
					i = dd.stableT;
					break;
				}
			}
		}
		if (!isValidChange)// ����Ѿ�����������ʱ�����ƣ�����-1
			return null;
		return dd;
	}

	/**
	 * 
	 * @param time
	 * @param aroudV
	 *            �仯��ֵ
	 * @return
	 */
	public int isState(int time, double aroudV, Channel c, double q) {

		DifData dd;
		int state = 0;
		try {
			int nowT = time - 20;
			dd = getNo0T(nowT);// DifSeries������б����Ѿ����˹��ˣ�����Ҫ��������ֵ
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
