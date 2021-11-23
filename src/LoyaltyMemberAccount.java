import java.sql.*;

public class LoyaltyMemberAccount {
    boolean checkLoyalMember(String phoneNum, String memberPIN) {
        String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=Jaredhatessoftwareengineering100;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            
            String selectSql = "SELECT * FROM LoyalMembers";
            ResultSet resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                if ((resultSet.getString("PhoneNum").equals(phoneNum)) & (resultSet.getString("MemberPIN").equals(memberPIN))) {
                    return true;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
