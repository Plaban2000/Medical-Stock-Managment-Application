import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addAdmin".equals(action)) {
            addAdmin(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adminEmail = request.getParameter("adminEmail");
        String adminPhone = request.getParameter("adminPhone");
        String adminFirstName = request.getParameter("adminFirstName");
        String adminMiddleName = request.getParameter("adminMiddleName");
        String adminLastName = request.getParameter("adminLastName");

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Admin (Admin_mail_id, Admin_phone_no, Admin_fname, Admin_mname, Admin_lname) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, adminEmail);
                statement.setString(2, adminPhone);
                statement.setString(3, adminFirstName);
                statement.setString(4, adminMiddleName);
                statement.setString(5, adminLastName);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Admin added successfully.");
                } else {
                    response.getWriter().println("Failed to add admin.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
