package com.opera;

import java.io.IOException;

import com.Obj.Channel;
import com.Obj.Channel0;
import com.Obj.DifData;
import com.Obj.DifSeries;
import com.Obj.Series;
import com.distance.DWT;

public class GetSuitStream {
	public void getSuit(int o, int seriesL,int allLong) throws NumberFormatException,
			IOException {
//		o = 5;
//		allLong = 500000;
		
		// int seriesL = 15;
		// float TH = 1000;
		DWT dwt = new DWT();
		Channel all = new Channel0();
		Channel obC = new Channel(o);

		int allB = all.getBeginTFromFile();
		int obCB = obC.getBeginTFromFile();
		int begT = allB > obCB ? allB : obCB;

		all.setSampleFromFile(begT, allLong);
		obC.setSampleFromFile(begT, allLong);

		// all.setTH(10);
		// obC.setTH(20);

		DifSeries allDif = all.sampleDiff();
		DifSeries obCDif = obC.sampleDiff();

		int nowT = obCDif.firstData;
		DifData dd;
		while (true) {
			dd = obCDif.getNo0T(nowT);
			if (dd == null)
				break;
			nowT = dd.preT + 1;
			float minDis = Float.MAX_VALUE;
			Series minSer = null;
			Series obCi = obCDif.cutSeries(nowT, nowT + seriesL);
			// obCDif.channelState.setNowE(obC.getValue(nowT));
			// obCDif.channelState.addData(obCDif.channelState.getNowE());
			for (int i = nowT - 20; i < nowT + 5; i++) {
				if (i < allDif.firstData)
					continue;
				if (i > allDif.lastData - 20)
					break;
				if (allDif.get(i) < 5 && allDif.get(i) > -5)// 这里总是会出现比较奇怪的匹配，干脆去掉以较小数字开头的串
					continue;
				// 没匹配上的原因
				// 用电器本身过小
				// 有多个电器在短时间内同时开关，所以匹配时距离不是最小的
				// 电器出现跳跃的时间段，主线路找不到
				Series alli = allDif.cutSeries(i, i + seriesL);
				alli.dif = dd.dif;
				float distance = dwt.dwt(obCi, alli);
				if (distance < minDis) {
					minDis = distance;
					minSer = alli;
				}
			}
			if (minSer != null) {
				minSer.write("preTrain/" + o + ".txt");
				nowT += 5;
			}
			nowT = dd.stableT;
		}
		// obCDif.writeChannelState("preTrain/" + o + ".txt");
	}
}
