import java.sql.*;
import java.util.Random;
public class BankInterface {
    String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=SoftwareEngineeringProject12345;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

        ResultSet resultSet = null;

    int authorizeCreditDebit(String cardNumber, String expDate, int CVV, int billingZipCode) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            String selectCreditDebit = "SELECT * FROM Bank";
            resultSet = statement.executeQuery(selectCreditDebit);

            while (resultSet.next()) {
                if (resultSet.getString("CardNumber").equals(cardNumber) & resultSet.getString("ExpDate").equals(expDate) & resultSet.getInt("CVV") == CVV & resultSet.getInt("ZipCode") == billingZipCode) {
                    Random rnd = new Random();
                    return rnd.nextInt(999999);
                }
                else {
                    return -1;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
