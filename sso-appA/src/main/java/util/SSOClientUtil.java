package util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSOClientUtil {
	public static String SERVER_URL_PREFIX = "http://localhost:8080/sso-server";// ͳһ��֤���ĵ�ַ
	public static String CLIENT_HOST_URL = "http://localhost:8080/sso-appA";// ��ǰ�ͻ��˵�ַ

	/**
	 * ���ͻ�����������,����ͳһ��֤����,��Ҫ��redirectUrl�Ĳ���,ͳһ��֤���ĵ�¼��ص��ĵ�ַ ͨ��Request��ȡ�������ĵ�ַ
	 * 
	 * @param request
	 * @return
	 */
	public static String getRedirectUrl(HttpServletRequest request) {
		// ��ȡ����URL
		return CLIENT_HOST_URL + request.getServletPath();
	}

	/**
	 * ����request��ȡ��ת��ͳһ��֤���ĵĵ�ַ
	 * http://localhost:8080/sso-server/checkLogin?redirectUrl=http://localhost:8080/sso-appB/index.jsp
	 * ͨ��Response��ת��ָ���ĵ�ַ
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void redirectToSSOURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String redirectUrl = getRedirectUrl(request);
		StringBuilder url = new StringBuilder(50).append(SERVER_URL_PREFIX).append("/checkLogin?redirectUrl=")
				.append(redirectUrl);
		response.sendRedirect(url.toString());
		return;
	}

	public static void redirectToSSOURLCkeckToken(HttpServletRequest request, HttpServletResponse response,
			String token) throws IOException {
		String redirectUrl = getRedirectUrl(request);
		StringBuilder url = new StringBuilder(80).append(SERVER_URL_PREFIX).append("/verify?redirectUrl=")
				.append(redirectUrl).append("&token=").append(token);
		System.out.println("��ַΪ��" + url.toString() + "----------------------");
		response.sendRedirect(url.toString());
	}
}
