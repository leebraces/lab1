package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tokenset.TokenDatabase;

/**
 * Servlet implementation class SSOServlet
 */
@WebServlet({ "/checkLogin", "/login", "/verify" })
public class SSOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SSOServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestContent = request.getServletPath(); // 请求的内容
		String redirectUrl = request.getParameter("redirectUrl");
		HttpSession session = request.getSession();
		if (requestContent.equals("/checkLogin")) {
			// 1.判断是否有全局的会话
			// 从会话中获取令牌信息,如果取不到说明没有全局会话,如果能取到说明有全局会话
			String token = (String) session.getAttribute("token");
			if (token == null) {
				// 表示没有全局会话,将网址传到login.jsp中
				request.setAttribute("redirectUrl", redirectUrl);
				// 跳转到统一认证中心的登陆页面
				// 跳转到login.jsp
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			} else {
				// 有全局会话
				// 取出令牌信息,重定向到redirectUrl,把令牌带上
				// http://localhost/sso-client/WEB-INF/index.jsp?token=

				response.sendRedirect(redirectUrl + "?" + "token" + "=" + token);
			}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestContent = request.getServletPath(); // 请求的内容
		HttpSession session = request.getSession();
		/**
		 * 登陆方法
		 * 
		 * @param username    前台登陆的用户名
		 * @param password    前台登陆的密码
		 * @param redirectUrl 客户端被拦截的地址
		 * @param session     服务端会话对象
		 */
		if (requestContent.equals("/login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			String redirectUrl = request.getParameter("redirectUrl");
			if (username.equals("lee") && password.equals("123")) { // 将用户密码与账号设置为lee，123
				// 账号密码匹配
				// 1.创建令牌信息,只要保证唯一即可,我们就使用UUID.
				String token = UUID.randomUUID().toString();
				// 2.创建全局的会话,把令牌信息放入会话中.
				session.setAttribute("token", token);
				// 3.需要把令牌信息放到数据库中.
				TokenDatabase.addToken(token);
				// 4.重定向到redirectUrl,把令牌信息带上.
				// 形式为：http://localhost/sso-client/WEB-INF/index.jsp?token=
				response.sendRedirect(redirectUrl + "?" + "token" + "=" + token);		
			} else // 如果账号密码有误
			{
				request.setAttribute("error_msg", "账号密码有误，请重新输入！"); // 在网页上打印错误信息
				request.setAttribute("redirectUrl", redirectUrl);
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}

		} else if (requestContent.equals("/verify")) {
			// 接受url连接传来的token信息
			ServletInputStream inStream = request.getInputStream();
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[8192]; // buff用于存放循环读取的临时数据
			int rc = 0;
			while ((rc = inStream.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in_b = swapStream.toByteArray(); // in_b为转换之后的结果
			String token = new String(in_b, "utf-8");
			ServletOutputStream outStream = response.getOutputStream();
			if (TokenDatabase.T_TOKEN.contains(token)) { // 将token信息与存放token的数据库相比对
				outStream.write("true".getBytes()); // 在token数据库中找到token信息，则返回true
			} else {
				outStream.write("false".getBytes()); // 没有，则返回true
			}
			outStream.flush();
		}
	}

}
