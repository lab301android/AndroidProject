import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class WriteFile {
	/*删除某文件夹及其下面的全部文件*/
	public void deleteFile(File file) {  
		if (file.exists()) {//判断文件是否存在  
			if (file.isFile()) {//判断是否是文件  
				file.delete();//删除文件   
			} else if (file.isDirectory()) {//否则如果它是一个目录  
				File[] files = file.listFiles();//声明目录下所有的文件 files[];  
				for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
					this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
				}  
				file.delete();//删除文件夹  
			}  
		} else {  
			System.out.println("所删除的文件不存在");  
		}  
	}
	/*创建一个txt文件的输入流*/
	public BufferedWriter create_new_file(String write_path)
	{
		BufferedWriter bw = null;
		try
		{
			File file = new File(write_path);  
		    /*如果文件不存在就新建一个，如果已经存在就先删掉再新建*/  
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
	/*把一个string写入当前文件输出流中*/
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
	/*把一个arraylist中所有内容写入当前文件输出流中*/
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
	/*把传入的函数列表中的函数名和函数里的内容一个个写入到当前输出流中*/
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
