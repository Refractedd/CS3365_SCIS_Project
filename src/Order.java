import java.sql.*;
public class Order {
    private int currentOrderID = 0;
    private boolean loyaltyChecked = false;
    String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=Jaredhatessoftwareengineering100;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
    void returnProductInfoOrder(int productID, boolean currentStatus, String currentProducts){
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            System.out.println(currentStatus);
            if (currentStatus == false) {
                PreparedStatement insertFirstProduct = connection.prepareStatement("INSERT INTO [dbo].[Order](Products, LoyaltyMemberStatus) VALUES('"+productID+"', 'NotChecked')");
                insertFirstProduct.execute();
            }
            else {
                String selectSql = "SELECT * FROM [dbo].[Order]";
                ResultSet resultSet = statement.executeQuery(selectSql);
                while (resultSet.next()) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
                PreparedStatement insertRemainingProducts = connection.prepareStatement("UPDATE [dbo].[Order] SET Products = ? WHERE OrderID = ?");
                System.out.println(currentProducts);
                System.out.println(currentOrderID);
                insertRemainingProducts.setString(1, currentProducts);
                insertRemainingProducts.setInt(2, currentOrderID);
                insertRemainingProducts.execute();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean updatedLoyaltyChecked(boolean memberStatus) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectSql = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                currentOrderID = resultSet.getInt("OrderID");
            }
            if (memberStatus == true) {
                PreparedStatement updatedLoyalty = connection.prepareStatement("UPDATE [dbo].[Order] SET LoyaltyMemberStatus = 'Loyal' WHERE OrderID = ?");
                updatedLoyalty.setInt(1, currentOrderID);
                updatedLoyalty.execute();
                return true;
            }
            else {
                PreparedStatement updatedLoyalty = connection.prepareStatement("UPDATE [dbo].[Order] SET LoyaltyMemberStatus = 'Not Loyal' WHERE OrderID = ?");
                updatedLoyalty.setInt(1, currentOrderID);
                updatedLoyalty.execute();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
