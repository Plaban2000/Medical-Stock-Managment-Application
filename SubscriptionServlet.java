import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SubscriptionServlet")
public class SubscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addSubscription".equals(action)) {
            addSubscription(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void addSubscription(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int shopOwnerId = Integer.parseInt(request.getParameter("shopOwnerId"));
        String startingDate = request.getParameter("startingDate");
        String endingDate = request.getParameter("endingDate");
        double subPrice = Double.parseDouble(request.getParameter("subPrice"));

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Subscriptions (Shop_owner_id, Starting_date, Ending_date, Sub_price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, shopOwnerId);
                statement.setString(2, startingDate);
                statement.setString(3, endingDate);
                statement.setDouble(4, subPrice);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Subscription added successfully.");
                } else {
                    response.getWriter().println("Failed to add subscription.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
