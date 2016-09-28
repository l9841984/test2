package com.distance;

import com.Obj.Series;

public class DWT {
	public float dwt(Series a, Series b) {
		int tb = a.firstData - 1;
		int rb = b.firstData - 1;
		int tl = a.size();
		int rl = b.size();
		float[][] d = new float[tl + 1][rl + 1];
		float d_dist = 0;
		for (int i = 1; i <= tl; i++) {
			for (int j = 1; j <= rl; j++) {
				d[i][j] = (a.get(tb + i) - b.get(rb + j))
						* (a.get(tb + i) - b.get(rb + j));
			}
		}
		for (int i = 2; i <= tl; i++) {
			d[i][1] = d[i][1] + d[i - 1][1];
		}
		for (int j = 2; j <= rl; j++) {
			d[1][j] = d[1][j] + d[1][j - 1];
		}

		for (int i = 2; i <= tl; i++) {
			for (int j = 2; j <= rl; j++) {
				d[i][j] = d[i][j]
						+ min(d[i - 1][j - 1], d[i - 1][j], d[i][j - 1]);
			}
		}

		d_dist = d[tl][tl];

		return d_dist;
	}

	public float ous(Series a, Series b, int num) {
		float sum = 0;
		int tb = a.firstData - 1;
		int rb = b.firstData - 1;
		for (int i = 1; i <= num; i++) {
			sum += (a.get(tb + i) - b.get(rb + i))
					* (a.get(tb + i) - b.get(rb + i));
		}
		return sum;
	}

	float min(float a, float b, float c) {
		return (a = a < b ? a : b) < c ? a : c;
	}
	
}
