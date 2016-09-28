package com.Obj;

import java.util.Map;

public class No0Series extends Series {
	public float seiresSum;

	public No0Series(Map<Integer, Float> series, int b, int e, float s, int id) {
		super(series, b, e, id);
		seiresSum = s;
	}
}
