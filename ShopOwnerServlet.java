import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShopOwnerServlet")
public class ShopOwnerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("register".equals(action)) {
            registerShopOwner(request, response);
        } else if ("retrieveShopOwner".equals(action)) {
            retrieveShopOwner(request, response);
        } else {
            response.getWriter().println("Invalid action specified");
        }
    }

    private void registerShopOwner(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String shopName = request.getParameter("shopName");
        String gstNo = request.getParameter("gstNo");
        String drugLicenceNo = request.getParameter("drugLicenceNo");

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO ShopOwners (S_mail_id, S_fname, S_mname, S_lname, Shop_name, GST_no, Drug_licence_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, firstName);
                statement.setString(3, middleName);
                statement.setString(4, lastName);
                statement.setString(5, shopName);
                statement.setString(6, gstNo);
                statement.setString(7, drugLicenceNo);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().println("Shop owner registered successfully.");
                } else {
                    response.getWriter().println("Failed to register shop owner.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
private void retrieveShopOwner(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int shopOwnerId = Integer.parseInt(request.getParameter("shop_owner_id"));

    try (Connection connection = DBConnection.getConnection()) {
        String sql = "SELECT * FROM ShopOwners WHERE Shop_owner_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shopOwnerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("S_mail_id");
                    String firstName = resultSet.getString("S_fname");
                    String middleName = resultSet.getString("S_mname");
                    String lastName = resultSet.getString("S_lname");
                    String shopName = resultSet.getString("Shop_name");
                    String gstNo = resultSet.getString("GST_no");
                    String drugLicenceNo = resultSet.getString("Drug_licence_no");

                    JsonObject shopOwnerJson = new JsonObject();
                    shopOwnerJson.addProperty("email", email);
                    shopOwnerJson.addProperty("firstName", firstName);
                    shopOwnerJson.addProperty("middleName", middleName);
                    shopOwnerJson.addProperty("lastName", lastName);
                    shopOwnerJson.addProperty("shopName", shopName);
                    shopOwnerJson.addProperty("gstNo", gstNo);
                    shopOwnerJson.addProperty("drugLicenceNo", drugLicenceNo);

                    response.setContentType("application/json");
                    response.getWriter().println(shopOwnerJson.toString());
                } else {
                    response.getWriter().println("Shop owner not found");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.getWriter().println("Error: " + e.getMessage());
    }
}

}
