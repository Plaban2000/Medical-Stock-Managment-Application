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
        String action = request.getParameter("action");//add-medicine
        if ("addMedicine".equals(action)) {
            addMedicine(request, response);

	if ("retrieveMedicine".equals(action)) {
            retrieveMedicine(request, response);

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
private void retrieveMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html"); // Set the content type to HTML
        PrintWriter out = response.getWriter();

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Medicines"; // SQL query to retrieve all medicine stock details
            try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
                // Output retrieved data as HTML
                out.println("<html><body><h2>Medicine Stock</h2><table border='1'><tr><th>ID</th><th>Batch No</th><th>Name</th><th>Manufacturer</th><th>Expiry Date</th><th>Manufacture Date</th><th>Buying Cost</th><th>Selling Cost</th><th>Quantity</th><th>Shop Owner ID</th></tr>");
                while (resultSet.next()) {
                    out.println("<tr><td>" + resultSet.getInt("MED_id") + "</td><td>" + resultSet.getString("Batch_no") + "</td><td>" + resultSet.getString("MED_name") + "</td><td>" + resultSet.getString("MED_manf_name") + "</td><td>" + resultSet.getDate("MED_exp_date") + "</td><td>" + resultSet.getDate("MED_manf_date") + "</td><td>" + resultSet.getDouble("Buying_cost") + "</td><td>" + resultSet.getDouble("Selling_cost") + "</td><td>" + resultSet.getInt("Quantity") + "</td><td>" + resultSet.getInt("Shop_owner_id") + "</td></tr>");
                }
                out.println("</table></body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("Error: " + e.getMessage());
        }
    }

}
