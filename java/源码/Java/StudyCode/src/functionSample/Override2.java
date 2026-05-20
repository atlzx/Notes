package functionSample;

public class Override2 extends Override1{
    /*
        private修饰的方法无法被重写
        @Override
        public int test4(){
            return 1;
        }
    */

    // 重写时，重写的方法名与参数列表必须相同

    @Override
    // 重写非private修饰的方法时，重写方法的权限修饰符权限应该不小于被重写方法的调用权限，即重写后的方法可以被调用的限制更宽松
    public char test5() {
        return super.test5();
    }

    @Override
    // 返回值为void的方法，重写时其返回值也必须是void
    public void test1(int a, double b) {
        super.test1(a, b);
    }

    @Override
    // 返回值为基本数据类型的方法，重写时其返回值需要与父类对应方法的返回值类型一致
    public float test2(String str) {
        return super.test2(str);
    }

    @Override
    // 返回值为引用数据类型的方法，重写时其返回值可以与父类对应方法返回值类型一致，也可以是父类对应方法返回值类型的子类
    public Override2 test3(int[] arr) {
        return null;
    }

    // 使用static修饰的同名同参数的方法不算重写，因为static修饰的方法属于类中的方法，可以被直接调用
    public static void test6(){
        System.out.println("啊啊啊啊");
    }
}
