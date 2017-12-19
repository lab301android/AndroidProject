import java.util.ArrayList;

/*һ��TheClass����һ����*/
public class TheClass {
	
	private String name;
	private int layer; //��Ĳ����������Ϊ1�����ε�������ʱûʲô��
	private ArrayList<FunctionData> FunctionList = new ArrayList<FunctionData>(); //�������������ĺ������б�
	private ArrayList<TheClass> ClassList = new ArrayList<TheClass>(); //������������������б�
	private ArrayList<String> VariableList = new ArrayList<String>(); //�������������ĳ�Ա�������б�
	private ArrayList<String> EditVar = new ArrayList<String>(); //������������������Ϊedittext�ĳ�Ա���������ֵ��б�
	/*���캯��*/
	TheClass(int layer,String name)
	{
		this.layer=layer;
		this.name=name;
	}
	/*�������ڵ�ǰ���ڲ�����洢�ڵ�ǰ����������б���*/
	public void putInClass(TheClass nextclass)
	{
		ClassList.add(nextclass);
	}
	/*����һ����������ǰ����*/
	public void putInFunction(FunctionData Function)
	{
		FunctionList.add(Function);
	}
	/*����һ����Ա��������ǰ����*/
	public void putInVariable(String Variable)
	{
		VariableList.add(Variable);
	}
	/*���Ϻ���������ǰ���������ĳ�Ա�������뵽��ǰ���������ĳ�Ա�����У�
	 * ������ǰ������ĳ�Ա�������뵽��ǰ�������ÿ����������ǰ��(��ȥ����һ��)*/
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
	/*�ҵ���ǰ��ĳ�Ա����������EditText������������뵽EditVar�б���*/
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
	/*���ⲿ����ĳ�Ա�����б���뵱ǰ��ĳ�Ա�������б���ǰ��*/
	public void putInVariableList(ArrayList theVariableList)
	{
		ArrayList<String> VariableListclone =(ArrayList<String>) VariableList.clone();
		VariableList.clear();
		VariableList.addAll(theVariableList);
		VariableList.addAll(VariableListclone);
	}
	/*�����ǰ�������������������Ա����������*/
	public void printson()
	{
		for(int i=0;i<FunctionList.size();i++)
		{
			System.out.println("����"+i+":");
			System.out.println(FunctionList.get(i).getname());
			FunctionList.get(i).printLine();
		}
		for(int i=0;i<ClassList.size();i++)
		{
			System.out.println("��������"+i+ClassList.get(i).getname());
		}
		for(int i=0;i<VariableList.size();i++)
		{
			System.out.println("��Ա����"+i+":"+VariableList.get(i));
		}
	}
	/*��ȡ��ǰ�������������к���*/
	public ArrayList getFunctionList()
	{
		return FunctionList;
	}
	/*��ȡ��ǰ�������*/
	public String getname()
	{
		return name;
	}
}
