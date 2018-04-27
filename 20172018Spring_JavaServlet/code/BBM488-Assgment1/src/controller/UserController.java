package controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private List<User> Users = new ArrayList<User>(); 
	
    public UserController() {
        super();
        List<User> temp = new ArrayList<User>();
        temp.add(new User("admin", "1234"));
        setUsers(temp);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect("../index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet(request, response);
		String PathInfo = request.getPathInfo();
		if(PathInfo.equals("/LogIn")){
			LogIn(request);
			response.sendRedirect("../RecordPages/RecordEdit.jsp");
		}else if(PathInfo.equals("/Register")){
			Register(request);
			response.sendRedirect("../LoginPage.jsp");
		}else if(PathInfo.equals("/LogOut")){
			LogOut(request);
			response.sendRedirect("../LoginPage.jsp");
		}
	}
	protected void LogOut(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("isLoggedIn").equals("true") && session.getAttribute("isLoggedIn") != null){
			session.setAttribute("userName", "");
			session.setAttribute("password", "");
			session.setAttribute("isLoggedIn", "false");
			session.setAttribute("loginInfo", "true");
			session.setAttribute("LoginPageInfo", "Your are logged out!");
		}
	}
	protected void Register(HttpServletRequest request){
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		List<User> temp = getUsers();
		temp.add(new User(userName, password));
		setUsers(temp);
		
		HttpSession session = request.getSession();
		
		session.setAttribute("loginInfo", "true");
		session.setAttribute("LoginPageInfo", "Your new account is created, now you can Login!");
	}
	
	protected void LogIn(HttpServletRequest request){
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		List<User> Userlist = getUsers();
		
		session.setAttribute("isLoggedIn","false");
		session.setAttribute("loginInfo", "true");
		session.setAttribute("LoginPageInfo", "Your username or password is wrong!");
		
		for(int i=0; i < Userlist.size(); i++){
			User temp = Userlist.get(i);
			
			if(userName.equals(temp.getUserName()) && password.equals(temp.getPassword())){
				session.setAttribute("isLoggedIn", "true");
				session.setAttribute("loginInfo", "false");
				session.setAttribute("userName", userName);
				session.setAttribute("password", password);
				break;
			}
		}
	}

	public List<User> getUsers() {
		return Users;
	}

	public void setUsers(List<User> users) {
		Users = users;
	}

}
