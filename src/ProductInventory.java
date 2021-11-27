import java.sql.*;
public class ProductInventory {
    public int price;
    public int amount;
    public int id;
    String connectionUrl =
        "jdbc:sqlserver://projectpain.database.windows.net:1433;"
        + "database=supermarketSystem;"
        + "user=projectpainAdmin@projectpain;"
        + "password=SoftwareEngineeringProject12345;"
        + "encrypt=true;"
        + "trustServerCertificate=false;"
        + "loginTimeout=30;";

    public String[] productInfo(int productID){
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            String selectSql = "SELECT * FROM ProductInventory";
            resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                if (resultSet.getInt("ProductID") == productID) {
                    String[] results = {resultSet.getString("ProductInfo"), String.format("%.2f", resultSet.getDouble("ProductPrice")), resultSet.getString("BulkProduct"), 
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

    String readInventoryMessages() {
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            String selectSql = "SELECT * FROM ProductInventory";
            resultSet = statement.executeQuery(selectSql);
            String productsToOrder = "";
            while (resultSet.next()) {
                if (resultSet.getString("InventoryMessage").equals("LowStock")) {
                    if (resultSet.getInt("ProductID") == 1 | resultSet.getInt("ProductID") == 3) {
                        productsToOrder += resultSet.getString("ProductInfo") + "(200), ";
                    }
                    else if (resultSet.getInt("ProductID") == 2) {
                        productsToOrder += resultSet.getString("ProductInfo") + "(200lbs), ";
                    }
                    else if (resultSet.getInt("ProductID") == 4 | resultSet.getInt("ProductID") == 5) {
                        productsToOrder += resultSet.getString("ProductInfo") + "(150), ";
                    }
                }
            }
            System.out.println(productsToOrder);
            return productsToOrder;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}