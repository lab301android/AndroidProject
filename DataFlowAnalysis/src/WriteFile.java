import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class WriteFile {
	/*ɾ��ĳ�ļ��м��������ȫ���ļ�*/
	public void deleteFile(File file) {  
		if (file.exists()) {//�ж��ļ��Ƿ����  
			if (file.isFile()) {//�ж��Ƿ����ļ�  
				file.delete();//ɾ���ļ�   
			} else if (file.isDirectory()) {//�����������һ��Ŀ¼  
				File[] files = file.listFiles();//����Ŀ¼�����е��ļ� files[];  
				for (int i = 0;i < files.length;i ++) {//����Ŀ¼�����е��ļ�  
					this.deleteFile(files[i]);//��ÿ���ļ�������������е���  
				}  
				file.delete();//ɾ���ļ���  
			}  
		} else {  
			System.out.println("��ɾ�����ļ�������");  
		}  
	}
	/*����һ��txt�ļ���������*/
	public BufferedWriter create_new_file(String write_path)
	{
		BufferedWriter bw = null;
		try
		{
			File file = new File(write_path);  
		    /*����ļ������ھ��½�һ��������Ѿ����ھ���ɾ�����½�*/  
		    if (!file.exists()) 
		    	file.createNewFile();
		    else
		    {
		    	file.delete();
		    	file.createNewFile();
		    }
		    FileWriter fileWritter = new FileWriter(write_path, true);
		    bw= new BufferedWriter(fileWritter);
		}
	    catch(Exception e){}
		return bw;
	}
	/*��һ��stringд�뵱ǰ�ļ��������*/
	public void write_string(BufferedWriter bw,String text)
	{
		try
		{
			bw.write(text);
			bw.newLine();
			bw.flush();
		}
		catch(Exception e){}
	}
	/*��һ��arraylist����������д�뵱ǰ�ļ��������*/
	public void write_ArrayList(BufferedWriter bw,ArrayList<String> arraylist)
	{
		for(int i=0;i<arraylist.size();i++)
		{
			//System.out.println(arraylist.get(i));
			try
			{
				bw.write(arraylist.get(i));
				bw.newLine();
				bw.flush();
			}
			catch(Exception e){
			}
		}
	}
	/*�Ѵ���ĺ����б��еĺ������ͺ����������һ����д�뵽��ǰ�������*/
	public void write_FunctionData(BufferedWriter bw,ArrayList<FunctionData> functiondata)
	{
		for(int i=0;i<functiondata.size();i++)
		{
			//System.out.println(functiondata.get(i).getname());
			//functiondata.get(i).printLine();
			try
			{
				bw.write(functiondata.get(i).getname());
				bw.newLine();
				ArrayList<String> Line=functiondata.get(i).getLine();
				write_ArrayList(bw,Line);
				bw.newLine();
				bw.flush();
			}
			catch(Exception e){}
			//System.out.println();
		}
	}
}
