import java.util.ArrayList;

/*һ����׿Ӧ����һ��TheProject��*/
public class TheProject {
	
	private ArrayList<TheJava> AllJava = new ArrayList<TheJava>();//��ǰ��׿Ӧ�ð���������Java�ļ��б�
	private String name;//��ǰ��׿Ӧ�õ�����
	/*���캯��*/
	TheProject(String name)
	{
		this.name=name;
	}
	/*����һ���µ�Java�ļ��ൽ��ǰ��׿Ӧ����*/
	public void addjava(ArrayList<TheJava> thejava)
	{
		AllJava.addAll(thejava);
	}
	/*��ȡ��ǰ��׿Ӧ�õ�����*/
	public String getname()
	{
		return name;
	}
}
