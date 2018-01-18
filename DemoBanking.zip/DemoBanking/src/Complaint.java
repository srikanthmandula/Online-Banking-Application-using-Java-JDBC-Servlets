

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Complaint
 */
public class Complaint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Complaint() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    
    Connection con;
    long complNo;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		


		super.init(config);
			
try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe","SYSTEM","Frostburg");
			System.out.println("Database connection established successfully in registering complaint servlet");
			
			complNo=1009;
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
		
		
		String s1=request.getParameter("subject");
		String s2=request.getParameter("desc");
		
		Cookie c[]=request.getCookies();
		
		response.setContentType("text/html");
		PreparedStatement pstmt;
		
		try{
			
			
			complNo++;
	       pstmt=con.prepareStatement("insert into complaint(complaint_no,actno,subject,description) values(?,?,?,?)");
			
	       System.out.println("complaint No is :"+complNo);
	        pstmt.setLong(1, complNo);
	        pstmt.setString(2, c[2].getValue());
			pstmt.setString(3, s1);
			pstmt.setString(4, s2);
	     
			pstmt.executeUpdate();
			
			PrintWriter pw=response.getWriter();
			
			pw.println("<a href=http://localhost:8012/DemoBanking/welcome>Home</a>\t\t");
			
			pw.println("<a href=logout>Log out</a><br /><br />");
			
						
			pw.println("<br />Your complaint is registered successfully and the complaint number is "+ complNo);
		}
		
		catch(Exception e){
			System.err.println(e);
		}
		
	}

}
