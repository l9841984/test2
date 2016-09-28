package cn.edu.pku.chartshow;

public class RawData {
	String name;
	String valueX;//用电器状态
	double valueY;//用电量
	int valueZ=0;//开关状态
	public RawData(String s, String i, double j) {
		// TODO Auto-generated constructor stub
		name=s;
		valueX=i;
		valueY=j;
	}
	public RawData() {
		// TODO Auto-generated constructor stub
	}
	public void setName(String s) {
		// TODO Auto-generated method stub
		name=s;
	}
	public void setValueX(String i) {
		// TODO Auto-generated method stub
		valueX=i;
	}
	public void setValueY(double i) {
		// TODO Auto-generated method stub
		valueY=i;
	}
	public void setValueZ(int i) {
		// TODO Auto-generated method stub
		valueZ=i;
	}
	public String getName()
	{
		return name;
	}
	public String getValueX()
	{
		return valueX;
	}
	public double getValueY()
	{
		return valueY;
	}
	public int getValueZ()
	{
		return valueZ;
	}
}
