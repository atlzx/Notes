package utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NewBankService {
    private static final NewBankDao bankDao=new NewBankDao();
    public static void main(String[] args) {
        try{
            exchangeMoney("ly","lzx",500);
            System.out.println("转账成功");
        }catch(Exception e){
            System.out.println("转账失败");
            e.printStackTrace();
        }

        try{
            lookInfo("lzx");
            System.out.println("查询成功");
        }catch(Exception e){
            System.out.println("查询失败");
            e.printStackTrace();
        }

        try{
            lookAllInfo();
            System.out.println("查询成功");
        }catch(Exception e){
            System.out.println("查询失败");
            e.printStackTrace();
        }


    }

    public static void exchangeMoney(String subAccount,String sumAccount,int money) throws SQLException {
        Connection con = JDBCUtils.getConnection();

        try{
            con.setAutoCommit(false);
            con.commit();
            bankDao.sub(subAccount,money);
            bankDao.sum(sumAccount,money);
            con.commit();
        }catch(Exception e){
            con.rollback();
            e.printStackTrace();
        }finally{
            JDBCUtils.releaseConnection();
        }
    }

    public static void lookInfo(String account) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        Connection con = JDBCUtils.getConnection();
        List<Bank> banks = bankDao.lookInfo(account);
        for(Bank bank:banks){
            System.out.println(bank.getId()+" "+bank.getMoney()+" "+bank.getName());
        }
    }

    public static void lookAllInfo() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection con = JDBCUtils.getConnection();
        List<Bank> banks = bankDao.lookAllInfo();
        for(Bank bank:banks){
            System.out.println(bank.getId()+" "+bank.getMoney()+" "+bank.getName());
        }
    }
}
