package com.knn;

public class KNNNode {
	private int index; // Ԫ����
	private double distance; // �����Ԫ��ľ���
	private int id; // �������

	public KNNNode(int index, double distance, int id) {
		super();
		this.index = index;
		this.distance = distance;
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getId() {
		return id;
	}

}
