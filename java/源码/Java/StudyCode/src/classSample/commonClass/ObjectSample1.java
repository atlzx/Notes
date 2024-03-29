package classSample.commonClass;

import static java.lang.Thread.sleep;

public class ObjectSample1 implements Cloneable {
    private String name;
    private int age;

    public ObjectSample1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ObjectSample1() {
    }

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        int number1 = 65;
        char c1 = 'A';
        System.out.println(number1 == c1);  // 输出true，因为char类型在运算时发生自动类型提升转换为65

        float number2 = 12.0F;
        int number3 = 12;
        System.out.println(number2 == number3);  // 输出true,int类型在运算时发生自动类型转换

        int[] arr1 = new int[]{1, 2, 3, 4, 5};
        int[] arr2 = new int[]{1, 2, 3, 4, 5};
        System.out.println(arr1 == arr2);  // 比较引用数据类型时，比较内存地址，输出false
        System.out.println(arr1.equals(arr2));  // 使用equals，数组类型没有重写过equals方法，因此调用的是Object最开始的equals方法，也是比较内存地址，返回false
        ObjectSample1 objectSample1 = new ObjectSample1("aaa", 15);
        ObjectSample1 objectSample2 = new ObjectSample1("aaa", 15);
        System.out.println(objectSample1 == objectSample2);  // 比较内存地址，因此返回false
        System.out.println(objectSample1.equals(objectSample2));  // 调用的是重写过后的equals,因此返回true
        System.out.println(objectSample1.toString());  // 调用的是重写过后的toString
        ObjectSample1 objectSample3 = (ObjectSample1) objectSample1.clone();
        System.out.println(objectSample3 == objectSample1);  // 调用clone方法是新创建的对象，直接比较必定返回false
        objectSample1 = null;
        objectSample2 = null;
        System.gc();// 强制调用GC进行垃圾回收
        sleep(1000);
    }

    @Override
    // 通过重写equals方法。可以指定对应对象比较时的比较规则
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ObjectSample1) {
            ObjectSample1 objectSample = (ObjectSample1) obj;
            return this.name.equals(objectSample.name) && this.age == objectSample.age;  // 字符串比较时，可能会出现NullPointerException
        }
        return false;
    }

    @Override
    public String toString() {
        // getClass方法可以返回运行时对象所属的类型
        // hashCode方法可以返回对象对应的哈希值
        return this.getClass().getName() + '@' + this.hashCode() + " 名字是" + this.name + "，年龄为" + this.age + "岁";
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("对象 " + this + " 被回收了");
    }
}
