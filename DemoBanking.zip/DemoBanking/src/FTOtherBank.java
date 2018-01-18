

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FTOtherBank
 */
public class FTOtherBank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FTOtherBank() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		

		super.init(config);
			
try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe","SYSTEM","Frostburg");
			System.out.println("Database connection established successfully in Funds transfer to Other bank servlet");
			
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
	
		String s1=request.getParameter("actno");
		String s2=request.getParameter("amt");
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		
		pw.println("<strong><a href=http://localhost:8012/DemoBanking/welcome>Home</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=logout>Log out</a></strong><br />");
		
		pw.println("Recipient Account number is : "+s1);
		pw.println("<br />Balance to be transferred is : "+s2);
		Cookie c[]=request.getCookies();
			
		PreparedStatement pstmt;
		
        ResultSet rs1;
		
		long tranid=0;
		String remarks="NA",transtatus="NA",trandesc="Funds Transfer to other Bank",actno=c[2].getValue();
		
		try {
			pstmt =con.prepareStatement("select tran_seq.nextval from dual");
			
			rs1=pstmt.executeQuery();
			rs1.next();
			tranid=rs1.getLong(1);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
			if(Long.parseLong(c[3].getValue())<Long.parseLong(s2)){
			
			pw.println("<br />Funds transfer can not be initiated as available balance is less than the to be transferred amount");
			remarks="Funds transfer can not be initiated as available balance is less than the to be transferred amount";
			transtatus="fail";
		}
		
		else{
			
			
			
			try {
				
				long newBal= Long.parseLong(c[3].getValue())-Long.parseLong(s2);
				
				
				pstmt = con.prepareStatement("update customer set balance=? where actno=?");
							
				pstmt.setLong(1, newBal);
				pstmt.setString(2,actno);
				
				pstmt.executeUpdate();
				
				pw.println("<br /> Funds USD "+s2+" transfer initiated to the account number "+s1);
				
				pw.println("<br />Available balance in the account is USD "+newBal);
								
				remarks="Funds transferred successfully";
				transtatus="pass";
			} 
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			System.out.println("New balance is updated successfully in database");
			
		}
		
		try {
		pstmt=con.prepareStatement("insert into transaction(tranid,actno,trandesc,transtatus,remarks) values(?,?,?,?,?)");
		
		pstmt.setLong(1,tranid);
		pstmt.setString(2, actno);
		pstmt.setString(3, trandesc);
		pstmt.setString(4, transtatus);
		pstmt.setString(5, remarks);
		
		pstmt.executeUpdate();
		
		System.out.println("Transaction table updated successfully");
	
		}
		catch(Exception e){
			
		}
	}

}
