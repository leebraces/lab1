package code01;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionMap {
	public static Map<String,HttpSession> map=new HashMap<String,HttpSession>();
	public static void add(HttpSession httpsession) {
		map.put(httpsession.getId(), httpsession);
	}
	public static void invalidate(String id)
	{
		HttpSession session=map.get(id);
		session.invalidate();
		map.remove(id);
	}
}
