import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("makePayment".equals(action)) {
            makePayment(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void makePayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int shopOwnerId = Integer.parseInt(request.getParameter("shopOwnerId"));
        double amount = Double.parseDouble(request.getParameter("amount"));

        // Simulate payment processing
        boolean paymentSuccess = true; // Assume payment is successful

        if (paymentSuccess) {
            try (Connection connection = DBConnection.getConnection()) {
                String sql = "INSERT INTO Payments (Shop_owner_id, Amount, Payment_date) VALUES (?, ?, NOW())";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, shopOwnerId);
                    statement.setDouble(2, amount);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        response.getWriter().println("Payment successful.");
                    } else {
                        response.getWriter().println("Failed to record payment.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            }
        } else {
            response.getWriter().println("Payment failed.");
        }
    }
}
