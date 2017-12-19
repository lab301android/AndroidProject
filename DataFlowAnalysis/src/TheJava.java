import java.util.ArrayList;

/*我也不太确定这个类是不是有用，一个TheJava就代表了一个Java文件*/
public class TheJava {

	ArrayList<FunctionData> Allfunction = new ArrayList<FunctionData>(); //当前Java文件包含的所有函数
	/*加入一个新的函数到当前Java文件中*/
	public void addfunction(ArrayList<FunctionData> function)
	{
		Allfunction.addAll(function);
	}
}
