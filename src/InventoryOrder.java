import java.sql.*;

public class InventoryOrder {
    String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=SoftwareEngineeringProject12345;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
    void createAndSendOrder(String productsToOrder) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            PreparedStatement createOrder = connection.prepareStatement("INSERT INTO [dbo].[InventoryOrder](ProductsAndQuantities) VALUES('"+productsToOrder+"')");
            createOrder.execute();
            String selectSql = "SELECT * FROM [dbo].[InventoryOrder]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            int currentOrderID = 0;
            while (resultSet.next()) {
                if (currentOrderID < resultSet.getInt("InventoryOrderID")) {
                    currentOrderID = resultSet.getInt("InventoryOrderID");
                }
            }
            PreparedStatement sendOrder = connection.prepareStatement("UPDATE [dbo].[InventoryOrder] SET OrderStatus = 'Sent' WHERE InventoryOrderID = ?");
            sendOrder.setInt(1, currentOrderID);
            sendOrder.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
