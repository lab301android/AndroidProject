import java.util.ArrayList;

/*每个FunctionData是一个函数的类*/
public class FunctionData {
	private String name;//函数的名称
	protected ArrayList<String> Line = new ArrayList<String>(); //函数中的每一行代码
	private ArrayList<String> EditVar = new ArrayList<String>(); //该类中所包含的类型为edittext的成员变量的名字的列表，其实感觉有一点冗余，过几天我再看看
	/*构造函数*/
	FunctionData(String name)
	{
		this.name=name;
	}
	/*加入一行到函数最末尾处*/
	public void AddLine(String line)
	{
		Line.add(line);
	}
	/*输出当前函数的每一行*/
	public void printLine()
	{
		for(int i = 0;i < Line.size(); i ++){
            System.out.println(Line.get(i));
        }
	}
	/*获取当前函数中所有行*/
	public ArrayList getLine()
	{
		return Line;
	}
	/*获取当前函数共有多少行*/
	public int getLineNum()
	{
		return Line.size();
	}
	/*获取当前函数所在的类的EditText成员变量*/
	public ArrayList getEditVar()
	{
		return EditVar;
	}
	/*将输入的一个行列表全部加入到当前函数最末*/
	public void addArrayList(ArrayList linelist)
	{
		Line.addAll(linelist);
	}
	/*添加一个EditText成员变量列表到函数中*/
	public void addEditVar(ArrayList linelist)
	{
		EditVar.addAll(linelist);
	}
	/*将输入的成员变量列表全部加入到当前函数的最前面*/
	public void addMemberVariable(ArrayList MemberVariableLine)
	{
		ArrayList<String> Line2 =(ArrayList<String>) Line.clone();
		Line.clear();
		this.addArrayList(MemberVariableLine);
		this.addArrayList(Line2);
	}
	/*获取当前函数的名称*/
	public String getname()
	{
		return name;
	}
}
