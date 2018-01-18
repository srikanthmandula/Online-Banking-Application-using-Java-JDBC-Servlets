

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ComplaintBase
 */
public class ComplaintBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComplaintBase() {
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
			System.out.println("Database connection established successfully in retrieviing complaints from Database");
			
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
		
		
		PreparedStatement pstmt;
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		
		pw.println("<strong> <a href=http://localhost:8012/DemoBanking/welcome>Home</a></strong>\t\t");
		
		pw.println("<strong><a href=logout>Log out</a></strong><br /><br />");
		
		Cookie c[]=request.getCookies();
		
		
		try{
			
			pstmt=con.prepareStatement("select * from complaint where actno=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, c[2].getValue());
			
			ResultSet rs=pstmt.executeQuery();
			
		
			if(rs.absolute(1))
			{
			
			
						
						
						ResultSetMetaData rm=rs.getMetaData();
						
						int colcnt=rm.getColumnCount();
						
						for(int i=1;i<=colcnt;i++){
							
							pw.print("<strong>"+ rm.getColumnName(i).toLowerCase()+", </strong>&nbsp;&nbsp;&nbsp;&nbsp;");
							
							
						}//for
						
						rs.first();
						do {
							
							pw.println("<br />");
							for(int i=1;i<=colcnt;i++){
								
								
								pw.print(rs.getString(i)+",&nbsp;&nbsp;&nbsp;&nbsp;");
								
							} //for
							
										
						} while(rs.next());//while
			
			}//if
			
			else{
				pw.println("There are no complaints logged");
			}
		}
		
		catch(Exception e){
			System.err.println(e);
		}
	}

}
