package com.knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Obj.Channel;
import com.Obj.Series;
import com.distance.DWT;

public class KNN {
	private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
		public int compare(KNNNode o1, KNNNode o2) {
			if (o1.getDistance() >= o2.getDistance()) {
				return -1;
			} else {
				return 1;
			}
		}
	};

	/**
	 * 获取K个不同的随机数
	 * 
	 * @param k
	 *            随机数的个数
	 * @param max
	 *            随机数最大的范围
	 * @return 生成的随机数数组
	 */
	public List<Integer> getRandKNum(int k, int max) {
		List<Integer> rand = new ArrayList<Integer>(k);
		for (int i = 0; i < k; i++) {
			int temp = (int) (Math.random() * max);
			if (!rand.contains(temp)) {
				rand.add(temp);
			} else {
				i--;
			}
		}
		return rand;
	}

	/**
	 * 执行KNN算法，获取测试元组的类别
	 * 
	 * @param datas
	 *            训练数据集
	 * @param testData
	 *            测试元组
	 * @param k
	 *            设定的K值
	 * @return 测试元组的类别
	 */
	public int knn(List<Series> datas, Series testData, int k) {
		ArrayList<KNNNode> pq = new ArrayList<KNNNode>(k);
		DWT dwt = new DWT();
		List<Integer> randNum = getRandKNum(k, datas.size());
		for (int i = 0; i < k; i++) { // 先随便放k个进去
			int index = randNum.get(i);
			Series currData = datas.get(index);
			KNNNode node = new KNNNode(index, dwt.dwt(testData, currData),
					currData.id);
			pq.add(node);
		}
		Collections.sort(pq, comparator);

		boolean isFind = false;
		for (int i = 0; i < datas.size(); i++) {
			Series t = datas.get(i);



			double distance = dwt.dwt(testData, t);
			distance = (testData.dif - t.dif) * (testData.dif - t.dif) * 10;
			KNNNode top = pq.get(0);
			if (top.getDistance() > distance) {
				pq.set(0, new KNNNode(i, distance, t.id));
				Collections.sort(pq, comparator);
				isFind = true;
			}
		}
		if(!isFind)
			return -1;

		return getMostClass(pq);
	}

	/**
	 * 获取所得到的k个最近邻元组的多数类
	 * 
	 * @param pq
	 *            存储k个最近近邻元组的优先级队列
	 * @return 多数类的名称
	 */
	private int getMostClass(ArrayList<KNNNode> pq) {
		Map<Integer, Integer> classCount = new HashMap<Integer, Integer>();
		for (int i = 0; i < pq.size(); i++) {
			KNNNode node = pq.get(i);
			int c = node.getId();
			if (classCount.containsKey(c)) {
				classCount.put(c, classCount.get(c) + 1);
			} else {
				classCount.put(c, 1);
			}
		}
		int maxIndex = -1;
		int maxCount = 0;
		for (Integer key : classCount.keySet()) {
			if (classCount.get(key) > maxCount) {
				maxIndex = key;
				maxCount = classCount.get(key);
			}
		}

		return maxIndex;
	}
}
