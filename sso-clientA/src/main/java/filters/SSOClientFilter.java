package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.HttpUtil;
import util.SSOClientUtil;

/**
 * Servlet Filter implementation class SSoClientFilter
 */
@WebFilter(filterName = "SSOClientFilter", urlPatterns = "/*")
public class SSOClientFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		// 1.�ж��Ƿ��оֲ��ĻỰ
		Boolean isLogin = (Boolean) session.getAttribute("isLogin");
		if (isLogin != null && isLogin) {
			// �оֲ��Ự,ֱ�ӷ���.
			chain.doFilter(request, response);
			return;
		}
		// �жϵ�ַ�����Ƿ���Я��token����.
		String token = req.getParameter("token");
		if (token != null) {
			// token��Ϣ��Ϊnull,˵����ַ�а�����token,ӵ������.
			// �ж�token��Ϣ�Ƿ�����֤���Ĳ�����.
			// ��֤��ַΪ:http://www.sso.com:8443/verify
			String httpURL = SSOClientUtil.SERVER_URL_PREFIX + "/verify";	
			// �ѿͻ��˵�ַ����ӵ���token��Ϣ���ݸ�ͳһ��֤���Ľ���У��
			try {
				String isVerify = HttpUtil.CheckToken(httpURL, token);
				if ("true".equals(isVerify)) {
					// ������ص��ַ�����true,˵�����token����ͳһ��֤���Ĳ�����.
					// �����ֲ��ĻỰ.
					session.setAttribute("isLogin", true);
					// ���иôε�����
					chain.doFilter(request, response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// û�оֲ��Ự,�ض���ͳһ��֤����,����Ƿ���������ϵͳ�Ѿ���¼��.
		// http://locallost:8080/sso-server/checkLogin?redirectUrl=http://localhost:8080/sso-client/index.jsp
		SSOClientUtil.redirectToSSOURL(req, resp);
	}

	public void destroy() {
	}
}
