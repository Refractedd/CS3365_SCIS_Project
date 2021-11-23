import java.sql.*;

public class LoyaltyMemberAccount {
    String connectionUrl =
                "jdbc:sqlserver://projectpain.database.windows.net:1433;"
                + "database=supermarketSystem;"
                + "user=projectpainAdmin@projectpain;"
                + "password=Jaredhatessoftwareengineering100;"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

    boolean checkLoyalMember(String phoneNum, String memberPIN) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            
            String selectSql = "SELECT * FROM LoyaltyMembers";
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

    void updateLoyaltyCreditPoints(double totalPrice, boolean loyaltyStatus, String[] phoneNumMemberPin) {
        int totalCreditPoints = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            String selectSql = "SELECT * FROM LoyaltyMembers";
            ResultSet resultSet = statement.executeQuery(selectSql);
            int memberID = 0;
            // Print results from select statement
            while (resultSet.next()) {
                if ((resultSet.getString("PhoneNum").equals(phoneNumMemberPin[0])) & (resultSet.getString("MemberPIN").equals(phoneNumMemberPin[1]))) {
                    memberID = resultSet.getInt("MemberID");
                    totalCreditPoints = resultSet.getInt("CreditPoints");
                }
            }
            if (loyaltyStatus == true) {
                int creditPoints = (int)(totalPrice / 10);
                totalCreditPoints += creditPoints;
                PreparedStatement updateCreditPoints = connection.prepareStatement("UPDATE [dbo].[LoyaltyMembers] SET CreditPoints = ? WHERE MemberID = ?");
                updateCreditPoints.setInt(1, totalCreditPoints);
                updateCreditPoints.setInt(2, memberID);
                updateCreditPoints.execute();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
