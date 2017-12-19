import java.util.ArrayList;

/*一个TheClass代表一个类*/
public class TheClass {
	
	private String name;
	private int layer; //类的层数，最外层为1，依次递增，暂时没什么用
	private ArrayList<FunctionData> FunctionList = new ArrayList<FunctionData>(); //该类中所包含的函数的列表
	private ArrayList<TheClass> ClassList = new ArrayList<TheClass>(); //该类中所包含的类的列表
	private ArrayList<String> VariableList = new ArrayList<String>(); //该类中所包含的成员变量的列表
	private ArrayList<String> EditVar = new ArrayList<String>(); //该类中所包含的类型为edittext的成员变量的名字的列表
	/*构造函数*/
	TheClass(int layer,String name)
	{
		this.layer=layer;
		this.name=name;
	}
	/*将包含在当前类内部的类存储在当前类包含的类列表中*/
	public void putInClass(TheClass nextclass)
	{
		ClassList.add(nextclass);
	}
	/*存入一个函数到当前类中*/
	public void putInFunction(FunctionData Function)
	{
		FunctionList.add(Function);
	}
	/*存入一个成员变量到当前类中*/
	public void putInVariable(String Variable)
	{
		VariableList.add(Variable);
	}
	/*整合函数，将当前类所包含的成员变量加入到当前类包含的类的成员函数中，
	 * 并将当前类包含的成员变量加入到当前类包含的每个函数的最前面(已去掉这一步)*/
	public void integration()
	{
		for(int i=0;i<ClassList.size();i++)
		{
			ClassList.get(i).putInVariableList(VariableList);
		}
		this.putInVariable(" ");
//		for(int i=0;i<FunctionList.size();i++)
//		{
//			FunctionList.get(i).addMemberVariable(VariableList);
//		}
		
	}
	/*找到当前类的成员变量中所有EditText变量，将其加入到EditVar列表中*/
	public void findeditvar()
	{
		for(int i=0;i<VariableList.size();i++)
		{
			String[] arr2 = VariableList.get(i).split(" ");
			if(arr2.length>1)
			{
				if(arr2[0].equals("public") || arr2[0].equals("private") || arr2[0].equals("protected"))
				{
					if(arr2[1].equals("EditText"))
					{
						//System.out.println(VariableList.get(i));
						EditVar.add(arr2[2]);
					}
				}
				else
				{
					if(arr2[0].equals("EditText"))
					{
						//System.out.println(VariableList.get(i));
						EditVar.add(arr2[1]);
					}
						
				}
			}
			
		}
		for(int i=0;i<FunctionList.size();i++)
		{
			FunctionList.get(i).addEditVar(EditVar);
		}
	}
	/*将外部输入的成员变量列表加入当前类的成员变量的列表最前面*/
	public void putInVariableList(ArrayList theVariableList)
	{
		ArrayList<String> VariableListclone =(ArrayList<String>) VariableList.clone();
		VariableList.clear();
		VariableList.addAll(theVariableList);
		VariableList.addAll(VariableListclone);
	}
	/*输出当前类包含的所有类名、成员变量、函数*/
	public void printson()
	{
		for(int i=0;i<FunctionList.size();i++)
		{
			System.out.println("函数"+i+":");
			System.out.println(FunctionList.get(i).getname());
			FunctionList.get(i).printLine();
		}
		for(int i=0;i<ClassList.size();i++)
		{
			System.out.println("包含的类"+i+ClassList.get(i).getname());
		}
		for(int i=0;i<VariableList.size();i++)
		{
			System.out.println("成员变量"+i+":"+VariableList.get(i));
		}
	}
	/*获取当前类所包含的所有函数*/
	public ArrayList getFunctionList()
	{
		return FunctionList;
	}
	/*获取当前类的类名*/
	public String getname()
	{
		return name;
	}
}
