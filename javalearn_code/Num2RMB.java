public class Num2RMB {
	private String[] handArr={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
	private String[] unitArr={"十","佰","仟","万"};
	private String[] unit2Arr={"角","分"};
	//分割double类型整数和小数部分，分成两个String类型
	static public String[] divide(double num){
		int formerNum=(int)num;
		int latterNum=(int)((num-formerNum)*100);
		if(Integer.toString(latterNum).length()<2){
		return new String[]{String.valueOf(formerNum),"0"+latterNum+""};
	}else{
		return new String[]{String.valueOf(formerNum),latterNum+""};
	}
	}
	//将分割后的double类型转换成汉字表达
	public String toHandStr(String[] numStr){
		String result="";
		//转义整数部分
		int numLen=numStr[0].length();
		for (int i=0; i<numLen; i++) {
			int num=numStr[0].charAt(i)-48;
			if (i!=numLen-1&&num!=0) {
				result+=handArr[num]+unitArr[numLen-2-i];
			}else {
				result+=handArr[num];
			}
		}
		result+="圆";
		//转义小数部分
		numLen=numStr[1].length();
		for(int i=0;i<numLen;i++){
			int num=numStr[1].charAt(i)-48;
			if (num!=0) {
				result+=handArr[num]+unit2Arr[i];
			}else {
				continue;
			}
		}

		return result;
	}
	public static void main(String[] args) {
		//Num2RMB nr=new Num2RMB();
		String[] ns=Num2RMB.divide(12300.123);
		System.out.println(ns[0]+";"+ns[1]);
		//System.out.println(nr.toHandStr(ns));
	}
}