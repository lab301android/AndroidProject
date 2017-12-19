import java.util.ArrayList;

/*一个安卓应用是一个TheProject类*/
public class TheProject {
	
	private ArrayList<TheJava> AllJava = new ArrayList<TheJava>();//当前安卓应用包含的所有Java文件列表
	private String name;//当前安卓应用的名称
	/*构造函数*/
	TheProject(String name)
	{
		this.name=name;
	}
	/*加入一个新的Java文件类到当前安卓应用中*/
	public void addjava(ArrayList<TheJava> thejava)
	{
		AllJava.addAll(thejava);
	}
	/*获取当前安卓应用的名称*/
	public String getname()
	{
		return name;
	}
}
