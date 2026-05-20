package JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJson {
    private Dog dog;
    private Person person;

    public TestJson(){

    }

    @Test
    public void ClassObjectToJson() throws Exception{
        this.dog=new Dog("大黄",18,"是一条狗");
        this.person=new Person("lzx",20,dog);
        ObjectMapper objectMapper=new ObjectMapper();
        String string = objectMapper.writeValueAsString(this.person);
        System.out.println("转换后的JSON串为:"+string);
    }

    @Test
    public void JsonToString() throws Exception{
        String string="{\"name\":\"lzx\",\"age\":20,\"dog\":{\"name\":\"大黄\",\"age\":18,\"description\":\"是一条狗\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(string, Person.class);
        System.out.println("person对象内容为:"+person);
    }

    @Test
    public void MapToJson() throws Exception{
        Map map=new HashMap();
        map.put("name","李子轩");
        map.put("property",new Person("lzx",20,new Dog("大黄",10,"一条狗")));
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(map);
        System.out.println("转换Map的结果:"+string);
    }

    @Test
    public void ListArrayToJson() throws Exception{
        List list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add("aaaa");
        int []arr={1,2,3};
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(list);
        String arrString=objectMapper.writeValueAsString(arr);
        System.out.println("转换list的结果:"+string);
        System.out.println("转换数组的结果:"+arrString);
    }
}
