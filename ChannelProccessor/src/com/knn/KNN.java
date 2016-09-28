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
	 * ��ȡK����ͬ�������
	 * 
	 * @param k
	 *            ������ĸ���
	 * @param max
	 *            ��������ķ�Χ
	 * @return ���ɵ����������
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
	 * ִ��KNN�㷨����ȡ����Ԫ������
	 * 
	 * @param datas
	 *            ѵ�����ݼ�
	 * @param testData
	 *            ����Ԫ��
	 * @param k
	 *            �趨��Kֵ
	 * @return ����Ԫ������
	 */
	public int knn(List<Series> datas, Series testData, int k) {
		ArrayList<KNNNode> pq = new ArrayList<KNNNode>(k);
		DWT dwt = new DWT();
		List<Integer> randNum = getRandKNum(k, datas.size());
		for (int i = 0; i < k; i++) { // ������k����ȥ
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
	 * ��ȡ���õ���k�������Ԫ��Ķ�����
	 * 
	 * @param pq
	 *            �洢k���������Ԫ������ȼ�����
	 * @return �����������
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
