import java.util.ArrayList;

/*ÿ��FunctionData��һ����������*/
public class FunctionData {
	private String name;//����������
	protected ArrayList<String> Line = new ArrayList<String>(); //�����е�ÿһ�д���
	private ArrayList<String> EditVar = new ArrayList<String>(); //������������������Ϊedittext�ĳ�Ա���������ֵ��б���ʵ�о���һ�����࣬���������ٿ���
	/*���캯��*/
	FunctionData(String name)
	{
		this.name=name;
	}
	/*����һ�е�������ĩβ��*/
	public void AddLine(String line)
	{
		Line.add(line);
	}
	/*�����ǰ������ÿһ��*/
	public void printLine()
	{
		for(int i = 0;i < Line.size(); i ++){
            System.out.println(Line.get(i));
        }
	}
	/*��ȡ��ǰ������������*/
	public ArrayList getLine()
	{
		return Line;
	}
	/*��ȡ��ǰ�������ж�����*/
	public int getLineNum()
	{
		return Line.size();
	}
	/*��ȡ��ǰ�������ڵ����EditText��Ա����*/
	public ArrayList getEditVar()
	{
		return EditVar;
	}
	/*�������һ�����б�ȫ�����뵽��ǰ������ĩ*/
	public void addArrayList(ArrayList linelist)
	{
		Line.addAll(linelist);
	}
	/*���һ��EditText��Ա�����б�������*/
	public void addEditVar(ArrayList linelist)
	{
		EditVar.addAll(linelist);
	}
	/*������ĳ�Ա�����б�ȫ�����뵽��ǰ��������ǰ��*/
	public void addMemberVariable(ArrayList MemberVariableLine)
	{
		ArrayList<String> Line2 =(ArrayList<String>) Line.clone();
		Line.clear();
		this.addArrayList(MemberVariableLine);
		this.addArrayList(Line2);
	}
	/*��ȡ��ǰ����������*/
	public String getname()
	{
		return name;
	}
}
