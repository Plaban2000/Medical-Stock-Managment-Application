import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addMedicine".equals(action)) {
            addMedicine(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void addMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String medName = request.getParameter("medName");
        String batchNo = request.getParameter("batchNo");
        String manfName = request.getParameter("manfName");
        String manfDate = request.getParameter("manfDate");
        String expDate = request.getParameter("expDate");
        double buyingCost = Double.parseDouble(request.getParameter("buyingCost"));
        double sellingCost = Double.parseDouble(request.getParameter("sellingCost"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int shopOwnerId = Integer.parseInt(request.getParameter("shopOwnerId"));

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO Medicines (MED_name, Batch_no, MED_manf_name, MED_manf_date, MED_exp_date, Buying_cost, Selling_cost, Quantity, Shop_owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, medName);
                statement.setString(2, batchNo);
                statement.setString(3, manfName);
                statement.setString(4, manfDate);
                statement.setString(5, expDate);
                statement.setDouble(6, buyingCost);
                statement.setDouble(7, sellingCost);
                statement.setInt(8, quantity);
                statement.setInt(9, shopOwnerId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Medicine added successfully.");
                } else {
                    response.getWriter().println("Failed to add medicine.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
