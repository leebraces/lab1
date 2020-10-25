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
		String requestContent = request.getServletPath(); // ���������
		String redirectUrl = request.getParameter("redirectUrl");
		HttpSession session = request.getSession();
		if (requestContent.equals("/checkLogin")) {
			// 1.�ж��Ƿ���ȫ�ֵĻỰ
			// �ӻỰ�л�ȡ������Ϣ,���ȡ����˵��û��ȫ�ֻỰ,�����ȡ��˵����ȫ�ֻỰ
			String token = (String) session.getAttribute("token");
			if (token == null) {
				// ��ʾû��ȫ�ֻỰ,����ַ����login.jsp��
				request.setAttribute("redirectUrl", redirectUrl);
				// ��ת��ͳһ��֤���ĵĵ�½ҳ��
				// ��ת��login.jsp
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			} else {
				// ��ȫ�ֻỰ
				// ȡ��������Ϣ,�ض���redirectUrl,�����ƴ���
				// http://localhost/sso-client/WEB-INF/index.jsp?token=

				response.sendRedirect(redirectUrl + "?" + "token" + "=" + token);
			}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestContent = request.getServletPath(); // ���������
		HttpSession session = request.getSession();
		/**
		 * ��½����
		 * 
		 * @param username    ǰ̨��½���û���
		 * @param password    ǰ̨��½������
		 * @param redirectUrl �ͻ��˱����صĵ�ַ
		 * @param session     ����˻Ự����
		 */
		if (requestContent.equals("/login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			String redirectUrl = request.getParameter("redirectUrl");
			if (username.equals("lee") && password.equals("123")) { // ���û��������˺�����Ϊlee��123
				// �˺�����ƥ��
				// 1.����������Ϣ,ֻҪ��֤Ψһ����,���Ǿ�ʹ��UUID.
				String token = UUID.randomUUID().toString();
				// 2.����ȫ�ֵĻỰ,��������Ϣ����Ự��.
				session.setAttribute("token", token);
				// 3.��Ҫ��������Ϣ�ŵ����ݿ���.
				TokenDatabase.addToken(token);
				// 4.�ض���redirectUrl,��������Ϣ����.
				// ��ʽΪ��http://localhost/sso-client/WEB-INF/index.jsp?token=
				response.sendRedirect(redirectUrl + "?" + "token" + "=" + token);		
			} else // ����˺���������
			{
				request.setAttribute("error_msg", "�˺������������������룡"); // ����ҳ�ϴ�ӡ������Ϣ
				request.setAttribute("redirectUrl", redirectUrl);
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}

		} else if (requestContent.equals("/verify")) {
			// ����url���Ӵ�����token��Ϣ
			ServletInputStream inStream = request.getInputStream();
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[8192]; // buff���ڴ��ѭ����ȡ����ʱ����
			int rc = 0;
			while ((rc = inStream.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in_b = swapStream.toByteArray(); // in_bΪת��֮��Ľ��
			String token = new String(in_b, "utf-8");
			ServletOutputStream outStream = response.getOutputStream();
			if (TokenDatabase.T_TOKEN.contains(token)) { // ��token��Ϣ����token�����ݿ���ȶ�
				outStream.write("true".getBytes()); // ��token���ݿ����ҵ�token��Ϣ���򷵻�true
			} else {
				outStream.write("false".getBytes()); // û�У��򷵻�true
			}
			outStream.flush();
		}
	}

}
