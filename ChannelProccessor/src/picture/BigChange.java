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
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

public class BigChange {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NumberFormatException,
			IOException {

		ArrayList<String> times = new ArrayList<String>();
		ArrayList<Float> num = new ArrayList<Float>();
		InputStream is = new FileInputStream("orig/" + "channel_10.dat");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = ""; // ��������ÿ�ж�ȡ������
		int time1 = 0;
		float nf = 0;
		while ((line = reader.readLine()) != null) { // ��� line Ϊ��˵��������
			int time = Integer.parseInt((line.split(" ")[0]).split("\\.")[0]);
			if (time < 1372002037)
				continue;
			if (time > 1372002267)
				break;
			
			if(time1!=0){
				for(int i = time1 + 1;i < time;i++){
					num.add(nf);
				}
			}
			time1 = time;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String date = sdf.format(new Date((time) * 1000L));
			nf = Float.parseFloat(line.split(" ")[1]);
			times.add(date);
			num.add(nf);
		}
		JSONArray t = JSONArray.fromObject(times);
		JSONArray n = JSONArray.fromObject(num);

		// д���ļ�
		String picPath = "show";
		FileOutputStream out = new FileOutputStream(new File(picPath
				+ "/bigChange.txt"));
		out.write(t.toString().getBytes());
		out.write("\n".getBytes());
		out.write(n.toString().getBytes());

		out.close();
	}
}
