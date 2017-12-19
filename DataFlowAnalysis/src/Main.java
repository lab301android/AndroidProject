import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		FindLine findline=new FindLine();
		WriteFile writefile=new WriteFile();
		LineClassify lineclassify=new LineClassify();
		
		String path="";
		String path_copy=path;
		//String Filename=path+"\\a2dp.Vol_93_src\\src\\a2dp\\Vol\\AppChooser.java";
		
		ArrayList<TheProject> AllProject = new ArrayList<TheProject>();
		
		String[] fsname=findline.getFileName(path);
		for(int i=0;fsname[i]!=null;i++)
		{
			path_copy=path+"\\"+fsname[i];
			String write_path=path_copy+"\\function_fast";
			String write_path2=path_copy+"\\analysis";
			File file1 = new File(write_path);
			File file2 = new File(write_path2);
			
//			writefile.deleteFile(file1);
//			writefile.deleteFile(file2);
			file1.mkdirs();
			file2.mkdirs();
			ArrayList<String> path_java=new ArrayList<String>();
			findline.findpath(path_copy,path_java);
			for(String s : path_java){
				//System.out.println("++++"+s);
			}
			ArrayList<TheJava> AllJava = new ArrayList<TheJava>();
			for(int j=0;j<path_java.size();j++)
			{
				//System.out.println("&&"+path2[j]);
				String[] arr = path_java.get(j).split("\\\\");
				String[] arr2 = arr[arr.length-1].split("\\.");
				String write_file=write_path+"\\"+arr2[0]+".txt";
				BufferedWriter bw=writefile.create_new_file(write_file);
				String write_file2=write_path2+"\\"+arr2[0]+"_analysis.txt";
				BufferedWriter bw2=writefile.create_new_file(write_file2);
				
				ArrayList<String> allline =findline.readFileByLine(path_java.get(j));
				ArrayList<FunctionData> Allfunction=lineclassify.handleLine(allline);
				
				for(int k=0;k<Allfunction.size();k++)
				{
					
					ArrayList<String> line = new ArrayList<String>();
					ArrayList<String> editvar = new ArrayList<String>();
					line=Allfunction.get(k).getLine();
					editvar=Allfunction.get(k).getEditVar();
					List<Dictionary> result = new FunctionInnerAnalysis().createVarDictionary(line, editvar);
					writefile.write_string(bw2, Allfunction.get(k).getname());
					for(Dictionary e : result){
						//System.out.println("【变量名】：" + e.var_name + "【调用次数】：" + e.Sum_use);
						writefile.write_string(bw2, "【变量名】：" + e.var_name + "【调用次数】：" + e.Sum_use);
					}
				}
				TheJava nowjava=new TheJava();
				AllJava.add(nowjava);
				AllJava.get(j).addfunction(Allfunction);
				
				writefile.write_FunctionData(bw,Allfunction);
				Allfunction.clear();
				
			}
			TheProject nowproject=new TheProject(fsname[i]);
			AllProject.add(nowproject);
			AllProject.get(i).addjava(AllJava);
			System.out.println(nowproject.getname());
			AllJava.clear();
		}
		
		
		
		
		
	}
}
