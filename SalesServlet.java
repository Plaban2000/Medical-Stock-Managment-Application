import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SalesServlet")
public class SalesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("showSale".equals(action)) {
            showAllSales(request, response);
        } else if ("threeMonthsSale".equals(action)) {
            showSalesForPeriod(request, response, 3);
        } else if ("sixMonthsSale".equals(action)) {
            showSalesForPeriod(request, response, 6);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void showAllSales(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Sales";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<html><body><h2>All Sales Records</h2><table border='1'><tr><th>ID</th><th>Date</th><th>Amount</th></tr>");

            while (resultSet.next()) {
                out.println("<tr><td>" + resultSet.getInt("sale_id") + "</td><td>" + resultSet.getDate("sale_date") + "</td><td>" + resultSet.getDouble("amount") + "</td></tr>");
            }

            out.println("</table></body></html>");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    private void showSalesForPeriod(HttpServletRequest request, HttpServletResponse response, int months) throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate fromDate = currentDate.minusMonths(months);

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Sales WHERE sale_date >= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(fromDate));
            ResultSet resultSet = statement.executeQuery();

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<html><body><h2>Sales Records for Last " + months + " Months</h2><table border='1'><tr><th>ID</th><th>Date</th><th>Amount</th></tr>");

            while (resultSet.next()) {
                out.println("<tr><td>" + resultSet.getInt("sale_id") + "</td><td>" + resultSet.getDate("sale_date") + "</td><td>" + resultSet.getDouble("amount") + "</td></tr>");
            }

            out.println("</table></body></html>");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
