import java.sql.*;
public class ProductInventory {
    public int price;
    public int amount;
    public int id;

    public String[] productInfo(int productID){
        String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=Jaredhatessoftwareengineering100;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM ProductInventory";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                /*if (resultSet.getString("BulkProduct") == "True") {
                    return "BulkProduct";
                }*/ 
                if (resultSet.getInt("ProductID") == productID) {
                    String[] results = {resultSet.getString("ProductInfo"), Double.toString(resultSet.getDouble("ProductPrice")), resultSet.getString("BulkProduct"), 
                    Double.toString(resultSet.getDouble("DiscountInfo")), Integer.toString(resultSet.getInt("StockLevel")), resultSet.getString("InventoryMessage")};
                    int newStockLevel = resultSet.getInt("StockLevel") - 1;
                    if (newStockLevel < 10){
                        PreparedStatement createMessage = connection.prepareStatement("UPDATE ProductInventory SET InventoryMessage = ? WHERE ProductID = ?");
                        createMessage.setString(1, "LowStock");
                        createMessage.setInt(2, productID);
                        createMessage.execute();
                    }
                    PreparedStatement update = connection.prepareStatement("UPDATE ProductInventory SET StockLevel = ? WHERE ProductID = ?");
                    update.setInt(1, newStockLevel);
                    update.setInt(2, productID);
                    update.execute();
                    return results;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        String[] empty = {};
        return empty;
    }
}
