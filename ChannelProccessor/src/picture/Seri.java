package picture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.json.JSONArray;

public class Seri {

	public static void main(String[] args) throws IOException {

		int beginT = 1371558884, endT = 1371558972;
		JSONArray jdata = new JSONArray();
		float[] data = new float[endT - beginT + 1];
		int[] dataI = new int[endT - beginT + 1];
		ArrayList<String> dataAll = new ArrayList<String>();
		ArrayList<String> xtime = new ArrayList<String>();
		InputStream is = new FileInputStream("orig/" + "channel_5.dat");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = ""; // ��������ÿ�ж�ȡ������

		// ţ�ٲ�ֵ
		float[] newdata = new float[endT - beginT + 1];

		float nf = 0;
		int i = 0;

		// int time1 = 0;
		// float num1 = 0;
		// int ii = 0;

		while ((line = reader.readLine()) != null) { // ��� line Ϊ��˵��������
			int time = Integer.parseInt((line.split(" ")[0]).split("\\.")[0]);
			if (time < beginT)
				continue;
			if (time > endT)
				break;
			if (i == 0)
				beginT = time;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String date = sdf.format(new Date((time) * 1000L));
			nf = Float.parseFloat(line.split(" ")[1]);
			xtime.add(date);
			ArrayList<Float> num = new ArrayList<Float>();
			// num.add((float) i++);
			// num.add(nf);
			dataI[i++] = time - beginT;
			data[time - beginT] = nf;

			// ţ�ٲ�ֵ
			newdata[i - 1] = nf;

			// // һ�����Բ�ֵ
			// if (time1 != 0) {
			// for (int o = ii + 1; o < dataI[i - 1]; o++) {
			// data[o] = num1 + (nf - num1) / (time - time1) * (o - ii - 1);
			// }
			// }
			// time1 = time;
			// num1 = nf;
			// ii = dataI[i - 1];
		}

		// ţ�ٲ�ֵ
		int m = endT - beginT + 1;
		int n = i;
		int j = 0;
		int i1;
		/* ��������� */
		float[][] ad = new float[n][n];
		for (i1 = 0; i1 < n; i1++)
			ad[i1][0] = newdata[i1];

		for (i1 = 1; i1 < n; i1++)
			ad[i1][1] = (ad[i1][0] - ad[i1 - 1][0])
					/ (dataI[i1] - dataI[i1 - 1]);
		for (i1 = 2; i1 < n; i1++)
			for (j = i1; j < n; j++) {
				ad[j][i1] = (ad[j][i1 - 1] - ad[j - 1][i1 - 1])
						/ (dataI[j] - dataI[j - i1]);
			}
		for (int i11 = 0; i11 < m; i11++) {// ����X0
			/* ���ֵ��� */
			float temp = newdata[0];
			for (int i111 = 0; i111 <= n - 2; i111++) {
				double u = 1;
				int jj = 0;
				while (jj <= i111) {
					u *= (i11 - dataI[jj]);
					jj++;
				}
				temp += ad[i111 + 1][i111 + 1] * u;
			}

			data[i11] = temp;
		}

		// �����

		// // ��������
		// int m = i;
		// int n = endT - beginT + 1;
		// double Y0[] = new double[n];
		// for (int i1 = 0; i1 < n; i1++) {// ����X0
		// double t = 0;
		// for (int i2 = 0; i2 < m; i2++) {// ����X
		// double u = 1;
		// for (int i3 = 0; i3 < m; i3++) {// ����X
		// if (i2 != i3) {
		// u = u * (i1 - dataI[i3]) / (dataI[i2] - dataI[i3]);
		// }
		// }
		// u = u * data[dataI[i2]];
		// t = t + u;
		// }
		// Y0[i1] = t;
		// }

		jdata = JSONArray.fromObject(data);
		// д���ļ�
		String picPath = "show";
		FileOutputStream out = new FileOutputStream(new File(picPath
				+ "/series.txt"));
		byte[] c = new byte[2];
		c[0] = 0x0d;
		c[1] = 0x0a;// �������뻻�з����ֽ���
		String t = new String(c);// �����ֽ���ת��Ϊ�ַ�������
		out.write((jdata + t).toString().getBytes());
		out.write(JSONArray.fromObject(xtime).toString().getBytes());

		out.close();
	}

}
