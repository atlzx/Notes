package com.springboot.example.springbootmessageconverter.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

// 我们需要手动创建一个负责转换指定格式的转换器对象，并让它实现HttpMessageConverter接口
// 不过SpringBoot专门为我们提供了一个AbstractHttpMessageConverter类，供我们更简便的实现HttpMessageConverter接口，我们只需继承该类即可

public class MyHttpMessageConverter extends AbstractHttpMessageConverter {
    private ObjectMapper mapper;
    public MyHttpMessageConverter() {
        // 我们需要告知我们这个转换器类是转换yaml格式的，因此我们需要调用父类的构造器方法来使SpringBoot认识我们转换器支持的转换类型
        // 调用super方法并向里面添加MediaType类型对象
        super(new MediaType("application","yaml", StandardCharsets.UTF_8));

        YAMLFactory factory = new YAMLFactory();
        //
        factory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        mapper=new ObjectMapper(factory);
    }

    // 继承了AbstractHttpMessageConverter类要实现三个方法
        //protected boolean supports(Class clazz)这里用来筛选我们的转换器对象能够把什么类型的对象转换为我们期望的格式
        //protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException:这玩意是用来将前端传来的参数转换为被@RequestBody注解作用的变量对象的
        //protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException:这玩意是用来将我们的handler执行结果转换为我们期望的格式
    @Override
    protected boolean supports(Class clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // 该方法的第一个参数是待转换对象，第二个参数是被封装起来的向response输出的输出流对象封装对象
        OutputStream out = outputMessage.getBody();  // 调用getBody方法来得到向response输出的输出流对象
        mapper.writeValue(out,o);  // 调用writeValue来向response中写入值
    }
}
