package analysis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionInnerAnalysis {
	/**
	 * 判断是否为条件判断语句
	 */
	private boolean isConditionLine(String codeline){
		Pattern patternCond = Pattern.compile("^\\b(if|while|for)\\b\\s*\\(.+?\\)\\s*\\{*$");
		Matcher matcherCond = patternCond.matcher(codeline);
		return matcherCond.find();
	}
	/**
	 * 判断是否为赋值语句
	 */
	private boolean isEvaluationLine(String codeline){
		Pattern patternCond = Pattern.compile("^(.+?)=(.+?);$");
		Matcher matcherCond = patternCond.matcher(codeline);
		return matcherCond.find();
	}
	/**
	 * 一般赋值语句下是否被使用,若返回值不为空，则表明当前变量被使用，并有新变量被定义。若返回值为空，否则该变量未被使用。
	 */
	private String handleEvalExpr(String var, String codeLine){
		String result = "";
		Pattern pattern = Pattern.compile("^(.+?)=(.+?)$");
		Matcher matcher = pattern.matcher(codeLine);
		while(matcher.find()){
			if(isVariableUsed(var, matcher.group(2))){
				String v = matcher.group(1).trim();
				result = v.substring(v.lastIndexOf(" ") + 1, v.length());
			}
		}
		return result;
	}
	/**
	 * 条件语句下的是否变量被使用判断
	 */
	private boolean handleCondExpr(String var, String codeLine){
		boolean result = false;
		Pattern patternCond = Pattern.compile("^.+?\\((.+?)\\)\\s*\\{*$");
		Matcher matcher = patternCond.matcher(codeLine);
		while(matcher.find()){
			String[] useCondBlock = matcher.group(1).split("&&|\\|\\|");
			for(String s : useCondBlock){
				String[] useCondElement = s.split("! | != | == | >= | <= | > | <");
				for(String e : useCondElement){
					if(isVariableUsed(var, e)){
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	/**
	 * 判断当前变量是否在当前语句被使用
	 */
	private boolean isVariableUsed(String var, String codeLine){
		codeLine = " " + codeLine + " ";
		Pattern pattern = Pattern.compile("^.*?(\\+|-|\\*|/|\\s|\\(){1}" + var + "(\\+|-|\\*|/|\\s|\\)|,|\\.|;){1}.*?$");
		Matcher matcher = pattern.matcher(codeLine);
		return matcher.find();
	}
	
	public void reflect(Dictionary var, int sumuse, int sumcond) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class Dic = Dictionary.class;
		Field su = Dic.getDeclaredField("Sum_use");
		Field sc = Dic.getDeclaredField("Sum_cond");
		su.setAccessible(true);
		sc.setAccessible(true);
		su.set(var, sumuse);
		sc.set(var, sumcond);
	}
	/**
	 * 创建数据字典，用于存储EditText类型的变量及其衍生变量，存储类型为Dictionary
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @return 数组第一个元素为use占比，第二个元素为条件语句占比
	 */
	public double[] getVarFunctionPercent(ArrayList<String> func, ArrayList<String> editVar) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		/**
		 * 1.提取所有Edittext类型变量名，作为dictionary存入，成员变量存储结束后，循环抽取直至arraylist空，先抽取第一个变量v
		 * 2a.当前代码行是否为条件语句，是则判断是否使用了v,若是则sumuse++;并记录条件语句包含的总行数 若否则进入2b
		 * 2b.若是一般赋值语句，则以=号划分，判断等号后面是否使用v若是，则sumuse++,并将=号前的变量存入dictionary，否则2c
		 * 2c.一般调用语句，判断是否调用，是则sumuse++；
		 * 3.函数语句执行完毕，存入数据字典
		 * 4.若之前或当前被调用过的变量被重新定义，则该变量的sumuse++即可。
		 * 5.输出所有与edittext及延伸变量的使用总行数，可能变量间会有交叉
		 */
		List<Dictionary> dictionaryList = new CopyOnWriteArrayList<Dictionary>();
		Set<String> storeVarName = new HashSet<String>();//用于防止变量在函数语句中被重定义导致迭代器无限循环
		/**
		 * 将所有的EditText成员变量存入
		 */
		for(String s : editVar){
			Dictionary editMemvar = new Dictionary(s);
			dictionaryList.add(editMemvar);
			storeVarName.add(s);
		}
		int length = dictionaryList.size();
		for(int i = 0; i < length; i++){
			Dictionary var =  dictionaryList.get(i);
			Stack<String> conditionLine = new Stack<String>();
			boolean isStackPush = false;
			String var_name = var.var_name;
			int Sum_use = var.Sum_use;
			int Sum_cond = var.Sum_cond;
			//遍历所有函数语句
			for(String line : func){
				/**
				 * 当前语句为条件判断语句
				 */
				/**
				 * 1.判断是否当前语句为条件语句
				 * 2.如果是，通过正则匹配判断是否以{结尾，若不是，判断下一行是否为{,否则条件语句+1,结束
				 * 3.当检测到{是，建立堆栈，并且经过每一行则sum+1，遇到{压栈，遇到}出栈，直到栈内空间为0
				 * 4.记录sum数，存入对象的成员变量
				 */
				if(isConditionLine(line)){
					if(handleCondExpr(var_name,line)){
						Sum_use++;
						if(!isStackPush){
							isStackPush = true;
						}
					}
				}else if(isEvaluationLine(line)){
				/**
				 * 当前语句为一般赋值语句
				 */
					if(handleEvalExpr(var_name, line).length() != 0){
						Sum_use++;
						if(!storeVarName.contains(handleEvalExpr(var_name, line))){ //如果是新变量而不是重定义
							dictionaryList.add(new Dictionary(handleEvalExpr(var_name, line)));
							length++;
						}
					}
				}else{
				/**
				 * 当前语句为一般使用语句or无效语句
				 */
					if(isVariableUsed(var_name, line)){
						Sum_use++;
					}
				}
				/**
				 * 记录条件语句
				 */
				if(isStackPush){
					Sum_cond++;
					if(line.endsWith("{")){
						conditionLine.push("{");
					}else if(line.endsWith("}")){
						conditionLine.pop();
						if(conditionLine.isEmpty()){
							isStackPush = false;
						}
					}
				}
				//System.out.println("当前调用次数" + Sum_use);
			}
			reflect(var, Sum_use, Sum_cond);
			System.out.println("当前变量为：" + var_name + "  ==>  使用次数为：" + Sum_use + "  ==>  条件语句覆盖数为：" + Sum_cond);
			//System.out.println("-----------------------------------");
		}
		/**
		 * 获得当前函数内edittext占比
		 */
		int sumEditVarUse = 0;
		int sumEditVarCond = 0;
		for(Dictionary d : dictionaryList){
			sumEditVarUse+=d.Sum_use;
			sumEditVarCond+=d.Sum_cond;
		}
		/**
		 * 判断函数内是否调用过edittext成员变量
		 */
		boolean isAnyVarUsed = false;
		if(editVar.size() == dictionaryList.size()){
			for(Dictionary d : dictionaryList){
				if(d.Sum_use > 1){
					isAnyVarUsed = true;
				}
			}
		}else{
			isAnyVarUsed = true;
		}
		System.out.println("所有变量的定义使用次数：" + sumEditVarUse);
		System.out.println("函数总行数：" + func.size());
		double UsePercent = (func.size() == 0 || !isAnyVarUsed) ? 0 : ((double)(sumEditVarUse))/func.size();
		double CondPercent = (func.size() == 0 || !isAnyVarUsed) ? 0 : ((double)(sumEditVarCond))/func.size();
		double[] result = {UsePercent, CondPercent};
		return result;
	}

}
/**
 * 数据字典对象
 * @author yogayang
 *
 */
class Dictionary{
	public String var_name;
	int Sum_use;
	int Sum_cond;
	public Dictionary(String v){
		this.var_name = v; //变量名称
		this.Sum_use = 1; //总定义/使用次数
		this.Sum_cond = 0; //条件语句覆盖的总行数
	}
}
