import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SalesServlet")
public class SalesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addSale".equals(action)) {
            addSale(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void addSale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int medId = Integer.parseInt(request.getParameter("medId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String sellingDate = request.getParameter("sellingDate");
        int shopOwnerId = Integer.parseInt(request.getParameter("shopOwnerId"));

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Sales (MED_Id, Customer_id, Amount, Selling_date, Shop_owner_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, medId);
                statement.setInt(2, customerId);
                statement.setDouble(3, amount);
                statement.setString(4, sellingDate);
                statement.setInt(5, shopOwnerId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Sale added successfully.");
                } else {
                    response.getWriter().println("Failed to add sale.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
