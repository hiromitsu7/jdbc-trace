package hiromitsu.jdbc.trace;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet(urlPatterns = { "/MyServlet" })
public class MyServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static Logger logger = LoggerFactory.getLogger(MyServlet.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public MyServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String text = request.getParameter("text");

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    DataSource myDB = null;

    try {
      myDB = InitialContext.doLookup("jdbc/db2");
    } catch (NamingException ex) {
      ex.printStackTrace();
    }

    try {
      if (myDB != null) {
        con = myDB.getConnection();
        con.setAutoCommit(false);

        stmt = con.createStatement();

        rs = stmt.executeQuery("SELECT EMPNO FROM EMPLOYEE WHERE EMPNO='200010' WITH RS USE AND KEEP EXCLUSIVE LOCKS");
        while (rs.next()) {
          String empNo = rs.getString(1);
          System.out.println("Employee number = " + empNo);
        }

        con.rollback();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

  }

}
