package controller;



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

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/RecordPages/*")
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor.
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res =  (HttpServletResponse) response;
		
		if(!isLoggedIn(req)){
			res.sendRedirect("../LoginPage.jsp");
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	protected boolean isLoggedIn(HttpServletRequest request){
		
		Boolean isLoggedIn;
		HttpSession session = request.getSession();
		
		
		if(session.getAttribute("isLoggedIn") == null){
			session.setAttribute("isLoggedIn", "false");
			isLoggedIn = false;
			
		}
		
		if(session.getAttribute("isLoggedIn").equals("true")){
			isLoggedIn = true;
		}else{
			isLoggedIn = false;
		}
		
		return isLoggedIn;
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
