import java.sql.*;
import java.text.DecimalFormat;
public class Order {
    private int currentOrderID = 0;
    private boolean loyaltyChecked = false;
    String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=SoftwareEngineeringProject12345;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
    void returnProductInfoOrder(int productID, boolean currentStatus, String currentProducts){
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            if (currentStatus == false) {
                PreparedStatement insertFirstProduct = connection.prepareStatement("INSERT INTO [dbo].[Order](Products, LoyaltyMemberStatus) VALUES('"+productID+"', 'NotChecked')");
                insertFirstProduct.execute();
            }
            else {
                String selectSql = "SELECT * FROM [dbo].[Order]";
                ResultSet resultSet = statement.executeQuery(selectSql);
                while (resultSet.next()) {
                    if (currentOrderID < resultSet.getInt("OrderID")) {
                        currentOrderID = resultSet.getInt("OrderID");
                    }
                }
                PreparedStatement insertRemainingProducts = connection.prepareStatement("UPDATE [dbo].[Order] SET Products = ? WHERE OrderID = ?");
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
                if (currentOrderID < resultSet.getInt("OrderID")) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
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

    double[] calculateTotalPrice(double subTotal) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectSql = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                if (currentOrderID < resultSet.getInt("OrderID")) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
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
            double[] finalTotals = {subTotal, subTotal + (subTotal * .0625)};
            return finalTotals;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        double[] empty = {0.0};
        return empty;
    }

    void updateOrderTotal(double[] subTotalAndTotal) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectSql = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                if (currentOrderID < resultSet.getInt("OrderID")) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
            }
            DecimalFormat df = new DecimalFormat("#.##");
            PreparedStatement updatedSubTotal = connection.prepareStatement("UPDATE [dbo].[Order] SET OrderSubtotal = ? WHERE OrderID = ?");
            updatedSubTotal.setDouble(1, Double.parseDouble(df.format(subTotalAndTotal[0])));
            updatedSubTotal.setInt(2, currentOrderID);
            updatedSubTotal.execute();
            PreparedStatement updatedTotal = connection.prepareStatement("UPDATE [dbo].[Order] SET OrderTotal = ? WHERE OrderID = ?");
            updatedTotal.setDouble(1, Double.parseDouble(df.format(subTotalAndTotal[1])));
            updatedTotal.setInt(2, currentOrderID);
            updatedTotal.execute();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updatePaymentTypeAmount(String paymentAmount, String paymentType) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectSql = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                if (currentOrderID < resultSet.getInt("OrderID")) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
            }
            PreparedStatement updatedPaymentAmount = connection.prepareStatement("UPDATE [dbo].[Order] SET PaymentAmount = ? WHERE OrderID = ?");
            updatedPaymentAmount.setDouble(1, Double.parseDouble(paymentAmount));
            updatedPaymentAmount.setInt(2, currentOrderID);
            updatedPaymentAmount.execute();
            PreparedStatement updatedPaymentType = connection.prepareStatement("UPDATE [dbo].[Order] SET PaymentType = ? WHERE OrderID = ?");
            updatedPaymentType.setString(1, paymentType);
            updatedPaymentType.setInt(2, currentOrderID);
            updatedPaymentType.execute();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    double updateAuthorizationNumCalculateChange(int authorizationNum){
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectOrder = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectOrder);
            double calculatedChange = 0.0;
            while (resultSet.next()) {
                if (currentOrderID < resultSet.getInt("OrderID")) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
            }
            PreparedStatement updatedPaymentAmount = connection.prepareStatement("UPDATE [dbo].[Order] SET AuthorizationNum = ? WHERE OrderID = ?");
            updatedPaymentAmount.setInt(1, authorizationNum);
            updatedPaymentAmount.setInt(2, currentOrderID);
            updatedPaymentAmount.execute();
            String selectTotalAndPaymentAmount = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSetChange = statement.executeQuery(selectTotalAndPaymentAmount);
            while (resultSetChange.next()) {
                if (resultSetChange.getInt("OrderID") == currentOrderID) {
                    calculatedChange = resultSetChange.getDouble("PaymentAmount") - resultSetChange.getDouble("OrderTotal");
                }
            }
            return calculatedChange;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1.0;
    }
    
    double cashOrCheckChange() {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {
            String selectOrder = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSet = statement.executeQuery(selectOrder);
            double calculatedChange = 0.0;
            while (resultSet.next()) {
                if (currentOrderID < resultSet.getInt("OrderID")) {
                    currentOrderID = resultSet.getInt("OrderID");
                }
            }
            PreparedStatement updatedPaymentAmount = connection.prepareStatement("UPDATE [dbo].[Order] SET AuthorizationNum = ? WHERE OrderID = ?");
            updatedPaymentAmount.setInt(1, 1);
            updatedPaymentAmount.setInt(2, currentOrderID);
            updatedPaymentAmount.execute();
            String selectTotalAndPaymentAmount = "SELECT * FROM [dbo].[Order]";
            ResultSet resultSetChange = statement.executeQuery(selectTotalAndPaymentAmount);
            while (resultSetChange.next()) {
                if (resultSetChange.getInt("OrderID") == currentOrderID) {
                    calculatedChange = resultSetChange.getDouble("PaymentAmount") - resultSetChange.getDouble("OrderTotal");
                }
            }
            return calculatedChange;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1.0;
    }
}
