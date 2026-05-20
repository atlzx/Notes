package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankDao {
    public static void sub(String subAccount, Connection con, int money) throws SQLException {
        String sql = "update jdbc_test3 set money = money - ? where name = ?;";
        PreparedStatement preSta = con.prepareStatement(sql);
        preSta.setInt(1, money);
        preSta.setString(2, subAccount);
        preSta.executeUpdate();
        preSta.close();
    }


    public static void sum(String sumAccount, Connection con, int money) throws SQLException {
        String sql = "update jdbc_test3 set money = money + ? where name = ?;";
        PreparedStatement preSta = con.prepareStatement(sql);
        preSta.setObject(1, money);
        preSta.setObject(2, sumAccount);
        preSta.executeUpdate();
        preSta.close();
    }
}
