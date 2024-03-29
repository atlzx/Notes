package utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class NewBankDao extends BaseDao{

    public int sub(String subAccount,int money) throws SQLException {
        String sql="update jdbc_test3 set money = money - ? where name = ?;";
        return executeUpdate(sql,money,subAccount);
    }

    public int sum(String sumAccount,int money) throws SQLException{
        String sql="update jdbc_test3 set money = money + ? where name = ?;";
        return executeUpdate(sql,money,sumAccount);
    }

    public List<Bank> lookInfo(String account) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql="select * from jdbc_test3 where name = ?;";
        return executeQuery(Bank.class,sql,account);
    }

    public List<Bank> lookAllInfo() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql="select * from jdbc_test3;";
        return executeQuery(Bank.class,sql);
    }

}
