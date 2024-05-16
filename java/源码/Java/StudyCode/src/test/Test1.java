package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

public class Test1 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream=new FileInputStream("StudyCode/src/test/aaa.txt");
        byte[] b=new byte[10];
        while(fileInputStream.read(b)!=-1){
            System.out.println(new String(b));
        }
    }
}
