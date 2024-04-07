import com.alibaba.druid.pool.DruidDataSource;
import com.example.config.Config;
import com.example.dao.JdbcTemplateDao;
import com.example.service.Check;
import com.example.service.JdbcTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:JDBC.xml")
// 使用JdbcTemplateXmlTest类需要暂时把Config的EnableTransactionManagement注解注释掉，并注释掉Config类里面的方法
public class JdbcTemplateXmlTest {
    @Autowired
    private JdbcTemplateService jdbcTemplateService;

    @Autowired
    private JdbcTemplateDao jdbcTemplateDao;

    @Autowired
    private Check check;

    @Autowired
    private DruidDataSource dataSource;
    @Test
    // 测试JdbcTemplate类的增删改查
    public void test1(){
        // 截止到上一次测试完成，自增的id是6
        // 如果想完整测试，请将id都修改为6
        int money = jdbcTemplateDao.selectMoneyByID(1);
        System.out.println("查询结果:"+money);
        int insertRows=jdbcTemplateDao.insertInfo("yy",6000);
        System.out.println("插入影响行数:"+insertRows);
        int money2=jdbcTemplateDao.selectMoneyByID(5);
        System.out.println("修改前的钱数:"+money2);
        int updateRows=jdbcTemplateDao.withDrawMoney(5,2000);
        System.out.println("修改影响行数:"+updateRows);
        int money3=jdbcTemplateDao.selectMoneyByID(5);
        System.out.println("修改后的钱数:"+money3);
        int deleteRows=jdbcTemplateDao.deleteInfo(5);
        System.out.println("删除影响行数:"+deleteRows);
    }

    @Test
    // 测试事务
    public void test2(){
        // 这里模拟id为1的人向id为2和4的人都转帐1000元
        // 由于id为1的人只有1000元，肯定会报错，但是事务只执行到一半，如果未加入事务管理，那么将无法回滚
        // 如果加入了事务，那么程序会根据事务规定好的策略进行处理
        // 该样例也可以作为事务的传播样例
        check.batchExecute(1,new int[]{2,4},1000);
    }

    @Test
    // 测试超时
    public void test3(){
        jdbcTemplateService.transfer2(1,2,1);
    }

    @Test
    // 测试回滚策略
    public void test4() throws Exception{
        System.out.println(dataSource.getDriverClassName());
        System.out.println(dataSource.getUrl());
        System.out.println(dataSource.getUsername());
        System.out.println(dataSource.getPassword());
        // 由于事务设置了出现数值处理异常不会回滚，因此此次转账不会回滚
        jdbcTemplateService.transfer3();
    }
}
