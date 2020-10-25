package util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSOClientUtil {
	public static String SERVER_URL_PREFIX = "http://localhost:8080/sso-server";// 统一认证中心地址
	public static String CLIENT_HOST_URL = "http://localhost:8080/sso-appA";// 当前客户端地址

	/**
	 * 当客户端请求被拦截,跳往统一认证中心,需要带redirectUrl的参数,统一认证中心登录后回调的地址 通过Request获取这次请求的地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRedirectUrl(HttpServletRequest request) {
		// 获取请求URL
		return CLIENT_HOST_URL + request.getServletPath();
	}

	/**
	 * 根据request获取跳转到统一认证中心的地址
	 * http://localhost:8080/sso-server/checkLogin?redirectUrl=http://localhost:8080/sso-appB/index.jsp
	 * 通过Response跳转到指定的地址
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
		System.out.println("网址为：" + url.toString() + "----------------------");
		response.sendRedirect(url.toString());
	}
}
