import java.util.ArrayList;

/*��Ҳ��̫ȷ��������ǲ������ã�һ��TheJava�ʹ�����һ��Java�ļ�*/
public class TheJava {

	ArrayList<FunctionData> Allfunction = new ArrayList<FunctionData>(); //��ǰJava�ļ����������к���
	/*����һ���µĺ�������ǰJava�ļ���*/
	public void addfunction(ArrayList<FunctionData> function)
	{
		Allfunction.addAll(function);
	}
}
