package tokenset;

import java.util.HashSet;
import java.util.Set;

public class TokenDatabase {
	//ģ�����ݿ��е�t_token��
    public  static Set<String> T_TOKEN = new HashSet<String>();
    public static void addToken(String token) {
    	T_TOKEN.add(token);
    	System.out.println("���token---------T_TOKEN.size="+T_TOKEN.size());
    }
    public static void removeToken(String token) {
    	T_TOKEN.clear();
    	System.out.println("���token--------T_TOKEN.size="+T_TOKEN.size());
    }
    
}
