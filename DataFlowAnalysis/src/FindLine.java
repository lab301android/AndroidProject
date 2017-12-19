import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;



public class FindLine {
	/*读入文件夹的路径，输出该文件夹下所有文件的文件名*/
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
	/*读入一个路径，找到该路径下所有java文件所处的路径（一般位于src文件夹下）*/

	public void findpath(String path_sourcefile,ArrayList<String> path_java)
	{
		//System.out.println(path_sourcefile);
		String new_path_soursefile=path_sourcefile;
		int srcflag=0;
		try
		{
			String[] fsname=getFileName(new_path_soursefile);
			/*如果不想提升速度想找到不在SRC文件里的文件就把下面一整段注释掉*/
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
			/*注释到这里为止*/
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
	/*读入一个文件，分函数将每个函数的内容按行存入ArrayList*/
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
