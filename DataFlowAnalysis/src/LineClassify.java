import java.util.ArrayList;
import java.util.Stack;


public class LineClassify {
	/*输入一个按行分开的文件，将其按类，成员变量，函数划分出来，并将每个函数可以调用的成员变量加入到函数最前面，最后返回所有函数列表*/
	public ArrayList handleLine(ArrayList line)
	{
		ArrayList<TheClass> AllClass = new ArrayList<TheClass>(); 
		ArrayList<FunctionData> Allfunction = new ArrayList<FunctionData>(); 
		Stack<Character> stack = new Stack<Character>(); 
		TheClass nowclass=new TheClass(1,"");
		FunctionData nowfunction=new FunctionData("");
		boolean functionflag=false;
		int functionstacknum=0;
		for(int i = 0;i < line.size(); i ++)
		 {
			 String lastline="";
			 String nowline=(String) line.get(i);
			 if(i!=0)
				 lastline=(String) line.get(i-1);
			 if(nowline.indexOf("{")!=-1 && nowline.indexOf("}")!=-1)
			 {
				 if(stack.size()<functionstacknum)
				 {
					 functionflag=false;
					 functionstacknum=0;
					 nowclass.putInFunction(nowfunction);
				 }
			 }
			 else if(nowline.indexOf("{")!=-1)
			 {
				 int num=nowline.indexOf("{");
				 stack.push('{');
			 }
			 else if(nowline.indexOf("}")!=-1)
			 {
				 if(stack.size()>1)
				 {
					 stack.pop();
					 if(stack.size()<functionstacknum)
					 {
						 functionflag=false;
						 functionstacknum=0;
						 nowclass.putInFunction(nowfunction);
					 }
				 }
			 }
	         if(nowline.indexOf("{")!=-1 && nowline.indexOf("class")!=-1 || nowline.indexOf("{")!=-1 && lastline.indexOf("class")!=-1)
	         {
	         	//System.out.println("class"+nowline);
	         	if(stack.size()==1 || stack.size()==0)
	         	{
	         		nowclass=new TheClass(1,nowline);
	         		AllClass.add(nowclass);
	         	}
	         	else
	         	{
	         		nowclass=new TheClass(stack.size(),nowline);
	         		if(AllClass.size()>0)
	         			AllClass.get(0).putInClass(nowclass);
	         		AllClass.add(nowclass);
	         	}
	         }
	         else if(nowline.indexOf("{")!=-1 && (nowline.indexOf("private")!=-1 || nowline.indexOf("public")!=-1 || nowline.indexOf("protected")!=-1))
	         {
	        	 //System.out.println("function"+nowline);
	        	 functionstacknum=stack.size();
	        	 functionflag=true;
	        	 nowfunction=new FunctionData(nowline);
	         }
	         else 
	         {
	        	 if(functionflag==true)
	        	 {
	        		 nowfunction.AddLine(nowline);
	        	 }
	        	 else if(functionflag==false && nowline.indexOf("}")==-1 && nowline.indexOf("@Override")==-1)
		         {
	        		 nowclass.putInVariable(nowline);
		         }
	         }
	     }
		for(int i = 0;i < AllClass.size(); i ++){
        	AllClass.get(i).integration();
        	AllClass.get(i).findeditvar();
        }
		for(int i = 0;i < AllClass.size(); i ++){
//        	System.out.println("类："+i+":");
//        	System.out.println(AllClass.get(i).getname());
//        	AllClass.get(i).printson();
        	Allfunction.addAll(AllClass.get(i).getFunctionList());
        }
		for(int i = 0;i < Allfunction.size(); i ++){
//        	System.out.println("总函数："+i+":");
//        	System.out.println(Allfunction.get(i).getname());
//        	Allfunction.get(i).printLine();
        }
		return Allfunction;
		
	}
}
