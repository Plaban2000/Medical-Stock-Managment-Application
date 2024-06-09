import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addCustomer".equals(action)) {
            addCustomer(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerEmail = request.getParameter("customerEmail");
        String customerPhone = request.getParameter("customerPhone");
        String customerName = request.getParameter("customerName");
        int shopOwnerId = Integer.parseInt(request.getParameter("shopOwnerId"));

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Customers (Cust_mail_id, Cust_phone_no, Cust_name, Shop_owner_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customerEmail);
                statement.setString(2, customerPhone);
                statement.setString(3, customerName);
                statement.setInt(4, shopOwnerId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Customer added successfully.");
                } else {
                    response.getWriter().println("Failed to add customer.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
