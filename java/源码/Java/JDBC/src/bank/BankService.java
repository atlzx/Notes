package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankService {


    public static void main(String[] args) throws Exception {
        if (exchangeAccountMoney("lzx", "ly", 500)) {
            System.out.println("转账成功");
        } else {
            System.out.println("转账失败");
        }
    }

    public static boolean exchangeAccountMoney(String subAccount, String sumAccount, int money) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 由于转账事务的原子性，因此它们的连接必须是一个，把它提取到Service层
        // yyy对应的是密码
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb", "root", "yyy");
        try {
            con.setAutoCommit(false);  // setAutoCommit用来设置不自动提交
            con.commit();  // 必须设置不自动提交后才能手动提交，否则会报错
            BankDao.sub(subAccount, con, money);
            BankDao.sum(sumAccount,con,money);
            con.commit();  // 如果全程没有报错，那么提交事务，只有提交事务后，数据库才会真正发生变化，否则不会发生变化
            con.close();  // 记得关闭流资源
            return true;
        } catch (SQLException e) {
            con.rollback();  // rollback用来回滚
            e.printStackTrace();
            con.close();
            return false;
        }
    }
}
