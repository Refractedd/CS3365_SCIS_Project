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

    double[] calculateTotalPrice() {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectSql = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                currentOrderID = resultSet.getInt("OrderID");
            }
            String requestProducts = "SELECT * FROM [dbo].[Order] WHERE OrderID = "+currentOrderID+"";
            ResultSet resultSetProducts = statement.executeQuery(requestProducts);
            String products = "";
            while (resultSetProducts.next()) {
                products = resultSetProducts.getString("Products");
            }
            String [] productsSplit = products.split(", ");
            double productTotal = 0;
            for (int i = 0; i < productsSplit.length; i++) {
                String requestPrice = "SELECT * FROM [dbo].[ProductInventory] WHERE ProductID = "+productsSplit[i]+"";
                ResultSet resultSetPrice = statement.executeQuery(requestPrice);
                while (resultSetPrice.next()) {
                    productTotal += resultSetPrice.getDouble("ProductPrice");
                }
            }
            double[] finalTotals = {productTotal, productTotal + (productTotal * .0625)};
            return finalTotals;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        double[] find = {2.0, 3.0};
        return find;
    }
}
