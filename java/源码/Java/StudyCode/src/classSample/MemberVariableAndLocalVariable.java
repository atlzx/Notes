package classSample;

public class MemberVariableAndLocalVariable {
    String name;
    int age;
    char gender;

    public static void main(String[] args) {
        int a;  // 局部变量没有初始值，在未赋值之前不能调用，否则编译不通过
        // System.out.println(a); 该段代码编译不通过，因为没有给局部变量赋初值
        MemberVariableAndLocalVariable mvalv=new MemberVariableAndLocalVariable();  // static修饰的方法无法直接调用其它不被static方法修饰的方法，因此需要独自创建一个对象来调用该方法
        mvalv.test("测试");  // 调用方法时传递的实参会给作为局部变量的方法的形参赋值
    }

    // 在方法内，其形参t也是局部变量，但形参在方法执行时会有实参赋值，因此直接调用时不报错
    public void test(String t){
        // 类中的成员变量可以直接调用，因为它们会有初始值
        System.out.println(name);  // 输出null
        System.out.println(age);  // 输出0
        System.out.println(gender);  // 输出 '\u0000'
        System.out.println(t);
    }

}
