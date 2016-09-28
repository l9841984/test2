package com.knn;

public class KNNNode {
	private int index; // 元组标号
	private double distance; // 与测试元组的距离
	private int id; // 所属类别

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
