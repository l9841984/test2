package picture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Obj.Channel;
import com.Obj.Series;
import com.knn.KNNDataNode;

public class Feature {
	public static void main(String[] args) throws IOException {
		String filepath = "preTrain";
		File file = new File(filepath);
		String[] filelist = new String[file.list().length];
		for (int i = 0; i < file.list().length; i++) {
			filelist[i] = file.list()[i].split("\\.")[0];
		}

		// legend
		Channel.getLabels();
		String[] names = Channel.labelsChinese;
		JSONArray legenddata = new JSONArray();
		String[] namelabel = new String[filelist.length];
		for (int i = 0; i < filelist.length; i++) {
			namelabel[i] = names[Integer.parseInt(filelist[i])];
		}
		legenddata = JSONArray.fromObject(namelabel);
		JSONObject legend = new JSONObject();
		legend.put("data", legenddata);// legend

		// 数据
		JSONArray color = new JSONArray();
		JSONArray seriesL = new JSONArray();
		String[] colorA = { "#91c7ae", "#749f83", "#ca8622", "#bda29a",
				"#6e7074", "#546570", "#c4ccd3", "000000" };
		ArrayList<String> colorar = new ArrayList<String>();
		for (int i = 0; i < filelist.length; i++) {
			KNNDataNode node = new KNNDataNode(Integer.parseInt(filelist[i]
					.split("\\.")[0]));
			node.read();
			List<Series> l = node.data;
			int n = 7<l.size()?7:l.size();
			for (int j = 0; j < n; j++) {
				
				JSONObject aline = new JSONObject();
				aline.put("name", namelabel[i]);
				aline.put("type", "line");
				JSONArray nums = new JSONArray();
				nums = JSONArray.fromObject(l.get(j).toArray());
				aline.put("data", nums);
				colorar.add(colorA[i]);
				seriesL.add(aline);
			}
		}
		color = JSONArray.fromObject(colorar);

		// 拼合
		JSONObject all = new JSONObject();
		all.put("color", color);
		all.put("series", seriesL);
		all.put("legend", legend);

		// 写入文件
		String picPath = "show";
		FileOutputStream out = new FileOutputStream(new File(picPath
				+ "/feature.txt"));
		out.write(all.toString().getBytes());

		out.close();
	}
}
