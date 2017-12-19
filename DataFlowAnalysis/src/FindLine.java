import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;



public class FindLine {
	/*�����ļ��е�·����������ļ����������ļ����ļ���*/
	public String[] getFileName(String path) {
		File f = new File(path);
		String[] fsname=new String[10000];
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return null;
		}
		File fa[] = f.listFiles();
		if (fa==null) {
			System.out.println(path + " not exists");
			return null;
		}
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			fsname[i]=fs.getName();
			//System.out.println(fsname[i]);
		}
		return fsname;
	}
	/*����һ��·�����ҵ���·��������java�ļ�������·����һ��λ��src�ļ����£�*/

	public void findpath(String path_sourcefile,ArrayList<String> path_java)
	{
		//System.out.println(path_sourcefile);
		String new_path_soursefile=path_sourcefile;
		int srcflag=0;
		try
		{
			String[] fsname=getFileName(new_path_soursefile);
			/*������������ٶ����ҵ�����SRC�ļ�����ļ��Ͱ�����һ����ע�͵�*/
			for(int i=0;fsname[i]!=null;i++)
			{
				if(fsname[i].equals("src"))
				{
					srcflag=1;
					new_path_soursefile=path_sourcefile+"\\"+fsname[i];
					findpath(new_path_soursefile,path_java);
					break;
				}
			}
			/*ע�͵�����Ϊֹ*/
			if(srcflag==0)
			{
				for(int i=0;fsname[i]!=null;i++)
				{
					if(fsname[i].endsWith(".java"))
					{
						path_java.add(path_sourcefile+"\\"+fsname[i]);
						System.out.println("====="+ path_java.get(path_java.size()-1));
					}
					else 
					{
						if(new File(path_sourcefile+"\\"+fsname[i]).isDirectory())
						{
							new_path_soursefile=path_sourcefile+"\\"+fsname[i];
							findpath(new_path_soursefile,path_java);
							
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*����һ���ļ����ֺ�����ÿ�����������ݰ��д���ArrayList*/
	public ArrayList readFileByLine(String Filename)
	{
		File file = new File(Filename);
		ArrayList<String> allline = new ArrayList<String>(); 
        Reader reader = null;
        try 
        {
        	reader = new InputStreamReader(new FileInputStream(file));
        	String line="";
        	String word="";
            int tempchar;
            while ((tempchar = reader.read()) != -1) 
            {
            	if (((char) tempchar) != '\n') 
            	{
	                if (((char) tempchar) != ' ' && ((char) tempchar) != '\r') 
	                {
	                	word=word+(char)tempchar;
	                }
	                else
	                {
	                	if(word!="")
	                	{
	                		line=line+" "+word;
	                	}
	                	word="";
	                }
            	}
            	else 
        		{
            		line=line+" "+word;
            		line=line.trim();
            		if(!line.trim().isEmpty() && line.indexOf("/*")==-1 && line.indexOf("*/")==-1 && !line.startsWith("//") && !line.startsWith("*"))
            		{
            			allline.add(line);
	            		//System.out.println(line);
            		}
            		word="";
                	line="";
        		}
        	}
            reader.close();
//            for(int i = 0;i < allline.size(); i ++){
//            	System.out.println(allline.get(i));
//            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return allline;
	}

}
