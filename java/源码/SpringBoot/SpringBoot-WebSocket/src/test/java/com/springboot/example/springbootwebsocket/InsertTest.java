import com.springboot.example.springbootwebsocket.entity.User;
import com.springboot.example.springbootwebsocket.mapper.LoginDao;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class InsertTest {
    @Test
    public void test1(){
        log.info("haha");
    }
//    @Resource
//    private LoginDao loginDao;
//    @Test
//    public void test1(){
//        List<User> list=new ArrayList<>();
//        list.add(new User(1,"lzx","123456"));
//        list.add(new User(1,"test","123456"));
//        loginDao.addUser(list);
//    }
}
