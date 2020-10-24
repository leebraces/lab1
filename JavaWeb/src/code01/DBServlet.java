package code01;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DBServlet
 */
@WebServlet({ "/DBServlet", "/addStudent", "/deleteStudent", "/updateStudent", "/login", "/logout" })
public class DBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getRequestURI().endsWith("/deleteStudent")) {
			doDeleteStudent(request, response);
		} else if (request.getRequestURI().endsWith("/addStudent")) {
			doAddStudent(request, response);
		} else if (request.getRequestURI().endsWith("/login")) {
			doLogin(request, response);
		} else if (request.getRequestURI().endsWith("/logout")) {
			doLogout(request, response);
		}
	}

	void doDeleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		DB.deleteStudent(id);
		response.sendRedirect("index.jsp");
	}

	void doAddStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		DB.addStudent(id, name, password);
		response.sendRedirect("index.jsp");
	}

	public static void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		if (DB.Login(id, password)) {
			HttpSession httpSession=request.getSession();
			 SessionMap.add(httpSession);
			response.sendRedirect("index.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

	void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession=request.getSession();
		 SessionMap.invalidate(httpSession.getId());
		 System.out.println("-------------×¢Ïú--------------");
		response.sendRedirect("login.jsp");
	}
}
