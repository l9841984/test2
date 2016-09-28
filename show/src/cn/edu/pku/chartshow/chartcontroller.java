package cn.edu.pku.chartshow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import net.sf.json.JSONArray;



@Controller
public class chartcontroller {
	
	static int line1=1;
	static int line2=1;
	static int line3=1;
	static int txtnumber=1;
	static boolean excavate=false; 
	static double pervalue=-1;
	String proPath="D:/Clanguge/tmp2/ChannelProccessor/";
	
	private static String readLine(int lineNumber,BufferedReader reader)throws Exception{
	       String line="";
	       int i=0;
	       while(i<lineNumber){
	           line=reader.readLine();
	           i++;
	       }
	       return line;
	   }
	
	@RequestMapping(value="/addData.do")
	public void initDataJson(HttpServletRequest request,HttpServletResponse response){  
		
		 response.setContentType("text/html");  
	     try {
			PrintWriter out = response.getWriter(); 
	        RawData data=new RawData();  
	        data.setName("Main Line");  
	        data.setValueX("study"); 
	        data.setValueY(0);
	        List<RawData> list=new ArrayList<RawData>();  
		    list.add(data);    
		    JSONArray ja=JSONArray.fromObject(list);          
	        out.print(ja);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
    }
	
	@RequestMapping(value="/updateData.do")
	public void updateDataJson(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html");
		 try {
			PrintWriter out = response.getWriter();
			 File file = new File(proPath+"data/main.txt");
		     BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				double value=-1;
				try {
					RawData data=new RawData();
					data.setName("Main Line");  
				    data.setValueX("study"); 
				    
					String []temp = readLine(line1,reader).split(" ");
					value=Double.valueOf(temp[1]);
					data.setValueY(value);
					List<RawData> list=new ArrayList<RawData>();  
				    list.add(data);    
				    JSONArray ja=JSONArray.fromObject(list);          
			        out.print(ja); 
			        //System.out.println(ja);
					line1++;
				    reader.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//读取第line行
			       
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	}
	
	@RequestMapping(value="/updateDiff.do")
	public void updateDiffJson(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html");
		 try {
			PrintWriter out = response.getWriter();
			 File file = new File(proPath+"data/mainDiff.txt");
		     BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				double value=-1;
				try {
					RawData data=new RawData();
					data.setName("Main Line");  
				    data.setValueX("study"); 
				    
					String []temp = readLine(line2,reader).split(" ");
					value=Double.valueOf(temp[1]);
					data.setValueY(value);
					List<RawData> list=new ArrayList<RawData>();  
				    list.add(data);    
				    JSONArray ja=JSONArray.fromObject(list);          
			        out.print(ja);  
			        //System.out.println("*************************"+ja);
					line2++;
				    reader.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//读取第line行
			       
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}  
	}
	
	@RequestMapping(value="/updateApp.do")
	public void updateAppJson(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html");
		line3=line1;
		//System.out.println(line1+" "+line2+" "+line3);
		if(!excavate)/////////////////////////////////////////////////////////////
		 {
			try {
				PrintWriter out = response.getWriter();
				File file = new File(proPath+"data/"+txtnumber+".txt");
			    BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader(file));
					double value=-1;
					int state=0;
					try {
						RawData data=new RawData();
						data.setName(txtnumber+"th machine");  
					    data.setValueX("study");
						String []temp = readLine(line3,reader).split(" ");
						value=Double.valueOf(temp[1]);
						data.setValueY(value);
						state=Integer.valueOf(temp[2]);
						data.setValueZ(state);
						List<RawData> list=new ArrayList<RawData>();  
					    list.add(data);    
					    JSONArray ja=JSONArray.fromObject(list);          
				        out.print(ja);  
				       // System.out.println(ja);
						line3++;
					    reader.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//读取第line行
				       
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
		 }
		else////////////////////////////////////////////////////////////////
		{
			try {
				PrintWriter out = response.getWriter();
				File file = new File(proPath+"data/main.txt");
			    BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader(file));
					double value=pervalue;
					int state=0;
					try {
						RawData data=new RawData();
						data.setName(txtnumber+"th machine");  
					    data.setValueX("excavate");
						String []temp = readLine(line3,reader).split(" ");
						double t=Double.valueOf(temp[2]);
						System.out.println(txtnumber);
						if(t==(0-txtnumber)||t==(txtnumber))
						{
							if(Double.valueOf(temp[2])<0)
							{
								value=-1;
								pervalue=-1;
								state=-1;
							}
							else
							{
								value=1;
								pervalue=1;
								state=1;
							}
						}
						data.setValueY(value);
						data.setValueZ(state);
						List<RawData> list=new ArrayList<RawData>();  
					    list.add(data);    
					    JSONArray ja=JSONArray.fromObject(list);          
				        out.print(ja);  
				       // System.out.println(ja);
						line3++;
					    reader.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//读取第line行
				       
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	
	
	@RequestMapping(value="/txtcheck.do")
	public void txtcheck(HttpServletRequest request,HttpServletResponse response){  
		String context = request.getParameter(  
                "check"); 
		if(Integer.parseInt(context)>0)
		{
			txtnumber=Integer.parseInt(context);
			excavate=false;
		}
		else
			excavate=true;
		System.out.println(txtnumber);
		System.out.println(excavate);
    }
}
