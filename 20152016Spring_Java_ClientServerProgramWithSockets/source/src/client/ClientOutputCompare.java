package client;

import java.util.Comparator;

public class ClientOutputCompare implements Comparator<String>{
	@Override
	public int compare(String s1, String s2){
		String[] Temp1 = s1.split(":");
		String[] Temp2 = s2.split(":");
		if(Integer.parseInt(Temp1[1])<= Integer.parseInt(Temp2[1])){
			return -1;
		}
		return	1;
	}
}
