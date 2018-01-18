

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WelcomeServlet
 */
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe","SYSTEM","Frostburg");
			System.out.println("Database connection established successfully in Customer welcome servlet");
			
		}
		
		catch(Exception e){
			System.err.println(e);
			
		}
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//String s1=request.getParameter("uname");
		//String s2=request.getParameter("pword");
		
		
		HttpSession hs=request.getSession();
		
		String un=(String)hs.getAttribute("uname");
		String pwd=(String)hs.getAttribute("pword");
		
		
		try{
		PreparedStatement pstmt=con.prepareStatement("select * from customer where userid=? and pword=?");
		
		if( ! un.equals(null) && ! pwd.equals(null))
		{
			pstmt.setString(1, un);
			pstmt.setString(2, pwd);
		}
		
		
		PrintWriter pw=response.getWriter();
		
		
		
		ResultSet rs=pstmt.executeQuery();
	
		rs.next();
		
		
		
		
		Cookie c1=new Cookie("lname",rs.getString(2));
		Cookie c2=new Cookie("AccountNoC",rs.getString(6));
		Cookie c3=new Cookie("bal",rs.getString(8));
		
		response.addCookie(c1);
		response.addCookie(c2);
		response.addCookie(c3);
		
		response.setContentType("text/html");
		
		
		
		pw.println("<strong><a href=aboutus.html>About Us</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=contactus.html>Contact Us</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=careers.html>Careers</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=changecredentials.html >Change User Credentials</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=logout>Log out</a></strong> <br />");
		
		
		pw.println("<hr />");
		pw.println("<html> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h4>Welcome <em>"+rs.getString(2)+"</em></h4>");
		
		
		pw.println("Account Number  : &nbsp;<strong>"+rs.getString(6)+"</strong>");
		pw.println("<br />Your current Balance  : &nbsp;<strong> USD &nbsp; "+rs.getString(8)+"</strong>");
		
		
		
		pw.println("<h3>Menu</h3>");
		pw.println("<ul> <li><a href=FTWithin.html>Funds Transfer With in Bank</a></li>");
		pw.println("<br /> <li> <a href=Ftother.html>Funds Transfer to Other Bank</a></li>");
		pw.println("<br /><li> <a href=complaint.html>Register a Complaint</a></li>");
		pw.println("<br /><li> <a href=viewcomplaint>View Complaints' status</a></li>");
		pw.println("<br /><li> <a href=viewtransactions>View Transactions</a></li>");
		pw.println("<br /><li> <a href=changecredentials.html >Change User Credentials</a></li>");
		pw.println("<br /><li> <a href=logout>Log out</a></li></ul>");
		
		
		}
		catch(Exception e){
			System.err.println(e);
		}
		
	}

}
