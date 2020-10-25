package tokenset;

import java.util.HashSet;
import java.util.Set;

public class TokenDatabase {
	//模拟数据库中的t_token表
    public  static Set<String> T_TOKEN = new HashSet<String>();
    public static void addToken(String token) {
    	T_TOKEN.add(token);
    	System.out.println("添加token---------T_TOKEN.size="+T_TOKEN.size());
    }
    public static void removeToken(String token) {
    	T_TOKEN.clear();
    	System.out.println("清除token--------T_TOKEN.size="+T_TOKEN.size());
    }
    
}
