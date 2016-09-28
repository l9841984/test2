package picture;

import java.util.ArrayList;

public class JsonAdapter {
	String abJName;
	ArrayList<Object> dataa = new ArrayList();
	ArrayList<JsonAdapter> arrayA = new ArrayList<JsonAdapter>();
	
	JsonAdapter(String name) {
		abJName = name;
	}
	public void addDataArray(ArrayList a1){
		dataa.addAll(a1);
	}
	
	
}
