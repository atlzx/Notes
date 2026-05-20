package WrapperSample;

public class WrapperSample1 {
    public static void main(String[] args) {
        int n=1;
        boolean b=false;
        char c='c';
        String str="tRuE";
        // 基本数据类型转换为包装类对象

        Integer integer1=new Integer(n);  // 使用构造器直接转换
        Integer integer2=Integer.valueOf(n);  // 使用静态方法进行转换
        Integer integer3=Integer.valueOf(c);  // 也可以支持传入字符
        Integer integer4=new Integer(c);  // 没啥问题

        /*
            转换char的包装类时，无法传入int类型的值进行转换
            Character c1=new Character(n);
            Character c2=Character.valueOf(n);
        */
        Character c1=new Character(c);
        Character c2=Character.valueOf(c);
        Boolean b1=new Boolean(str);
        Boolean b2=new Boolean(b);
        Boolean b3=Boolean.valueOf(str);
        Boolean b4=Boolean.valueOf(b);

        // 下面是使用String.valueOf将包装类对象或基本数据类型转换为字符串的示例
        String str1=String.valueOf(b);
        String str2=String.valueOf(c);
        String str3=String.valueOf(n);
        String str4=String.valueOf(integer1);
        String str5=String.valueOf(c1);
        String str6=String.valueOf(b1);

        // 下面是将字符串转换为基本数据类型的示例

        int n1=Integer.parseInt("123");
        boolean b5=Boolean.parseBoolean("FAlSe");  //转换时会与true进行不区分大小写的比较，如果通过返回true,否则全返回false
        // char类型没有对应的parse方法，因为它只有一个字符


        print(integer1,integer2,integer3,integer4,c1,c2,b1,b2,b3,b4,str1,str2,str3,str4,str5,str6,n1,b5);



    }

    public static void print(Object ...arr){
        for(int i=0;i<arr.length;i++){
            // 下面演示了使用包装类对象的xxxValue方法将包装类对象转换为基本数据类型
            if(arr[i] instanceof Integer integer){
                System.out.println(integer.intValue());
            }else if(arr[i] instanceof Character character){
                System.out.println(character.charValue());
            }else if(arr[i] instanceof Boolean bool){
                System.out.println(bool.booleanValue());
            }else{
                System.out.println(arr[i]);
            }
        }
    }
}
