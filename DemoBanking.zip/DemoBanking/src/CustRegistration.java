

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustRegistration
 */
public class CustRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustRegistration() {
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
			System.out.println("Database connection established successfully in customer registration servlet");
			
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
		
		String s1=request.getParameter("fname");
		String s2=request.getParameter("lname");
		String s3=request.getParameter("bdate");
		String s4=request.getParameter("userid");
		String s5=request.getParameter("pword");
		String s6=request.getParameter("actno");
		String s7=request.getParameter("gender");
		String s8=request.getParameter("bal");
		
		try {
			PreparedStatement pstmt=con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,s1);
			pstmt.setString(2,s2);
			pstmt.setString(3,s3);
			pstmt.setString(4,s4);
			pstmt.setString(5,s5);
			pstmt.setString(6,s6);
			pstmt.setString(7,s7);
			pstmt.setString(8,s8);
			
			pstmt.executeUpdate();
			
			
			PrintWriter pw=response.getWriter();
			pw.println("<html> <h4>");
					
			pw.println(s1.toUpperCase()+"&nbsp;"+s2.toUpperCase()+"&nbsp; is successfully registered for Online Banking</h4>");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
