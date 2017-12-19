

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionInnerAnalysis {
	/**
	 * �ж��Ƿ�Ϊ�����ж����
	 */
	private boolean isConditionLine(String codeline){
		Pattern patternCond = Pattern.compile("^\\b(if|while|for)\\b\\s*\\(.+?\\)\\s*\\{*$");
		Matcher matcherCond = patternCond.matcher(codeline);
		return matcherCond.find();
	}
	/**
	 * �ж��Ƿ�Ϊ��ֵ���
	 */
	private boolean isEvaluationLine(String codeline){
		Pattern patternCond = Pattern.compile("^(.+?)=(.+?);$");
		Matcher matcherCond = patternCond.matcher(codeline);
		return matcherCond.find();
	}
	/**
	 * һ�㸳ֵ������Ƿ�ʹ��,������ֵ��Ϊ�գ��������ǰ������ʹ�ã������±��������塣������ֵΪ�գ�����ñ���δ��ʹ�á�
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
	 * ��������µ��Ƿ������ʹ���ж�
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
	 * �жϵ�ǰ�����Ƿ��ڵ�ǰ��䱻ʹ��
	 */
	private boolean isVariableUsed(String var, String codeLine){
		codeLine = " " + codeLine + " ";
		Pattern pattern = Pattern.compile("^.*?(\\+|-|\\*|/|\\s|\\(){1}" + var + "(\\+|-|\\*|/|\\s|\\)|,|\\.|;){1}.*?$");
		Matcher matcher = pattern.matcher(codeLine);
		return matcher.find();
	}
	
	public void reflect(Dictionary var, int sumuse) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class Dic = Dictionary.class;
		Field f = Dic.getDeclaredField("Sum_use");
		f.setAccessible(true);
		f.set(var, sumuse);
	}
	/**
	 * ���������ֵ䣬���ڴ洢EditText���͵ı������������������洢����ΪDictionary
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public List<Dictionary> createVarDictionary(ArrayList<String> func, ArrayList<String> editVar) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		/**
		 * 1.��ȡ����Edittext���ͱ���������Ϊdictionary���룬��Ա�����洢������ѭ����ȡֱ��arraylist�գ��ȳ�ȡ��һ������v
		 * 2a.��ǰ�������Ƿ�Ϊ������䣬�����ж��Ƿ�ʹ����v,������sumuse++;����¼������������������ ���������2b
		 * 2b.����һ�㸳ֵ��䣬����=�Ż��֣��жϵȺź����Ƿ�ʹ��v���ǣ���sumuse++,����=��ǰ�ı�������dictionary������2c
		 * 2c.һ�������䣬�ж��Ƿ���ã�����sumuse++��
		 * 3.�������ִ����ϣ����������ֵ�
		 * 4.��֮ǰ��ǰ�����ù��ı��������¶��壬��ñ�����sumuse++���ɡ�
		 * 5.���������edittext�����������ʹ�������������ܱ�������н���
		 */
		List<Dictionary> dictionaryList = new CopyOnWriteArrayList<Dictionary>();
		Set<String> storeVarName = new HashSet<String>();//���ڷ�ֹ�����ں�������б��ض��嵼�µ���������ѭ��
		/**
		 * �����е�EditText��Ա��������
		 */
		for(String s : editVar){
			Dictionary editMemvar = new Dictionary(s);
			dictionaryList.add(editMemvar);
			storeVarName.add(s);
		}
		int length = dictionaryList.size();
		for(int i = 0; i < length; i++){
			Dictionary var =  dictionaryList.get(i);
			String var_name = var.var_name;
			int Sum_use = var.Sum_use;
			//�������к������
			for(String line : func){
				/**
				 * ��ǰ���Ϊ�����ж����
				 */
				//System.out.println("����ǰ��䡿��" + line);
				if(isConditionLine(line)){
					if(handleCondExpr(var_name,line)){
						Sum_use++;
					}
				}else if(isEvaluationLine(line)){
				/**
				 * ��ǰ���Ϊһ�㸳ֵ���
				 */
					if(handleEvalExpr(var_name, line).length() != 0){
						Sum_use++;
						if(!storeVarName.contains(handleEvalExpr(var_name, line))){ //������±����������ض���
							dictionaryList.add(new Dictionary(handleEvalExpr(var_name, line)));
							length++;
						}
					}
				}else{
				/**
				 * ��ǰ���Ϊһ��ʹ�����or��Ч���
				 */
					if(isVariableUsed(var_name, line)){
						Sum_use++;
					}
				}
				//System.out.println("��ǰ���ô���" + Sum_use);
			}
			reflect(var, Sum_use);
			//System.out.println("��ǰ����Ϊ��" + var_name + "  ==>  ʹ�ô���Ϊ��" + Sum_use);
			//System.out.println("-----------------------------------");
		}
		return dictionaryList;
	}
}
/**
 * �����ֵ����
 * @author yogayang
 *
 */
class Dictionary{
	public String var_name;
	int Sum_use;
	public Dictionary(String v){
		this.var_name = v;
		this.Sum_use = 1;
	}
}
