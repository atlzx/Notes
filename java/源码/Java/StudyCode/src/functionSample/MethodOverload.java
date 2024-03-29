package functionSample;

public class MethodOverload {

    public static void main(String[] args) {
        MethodOverload methodOverload=new MethodOverload();
        methodOverload.add(1,2);  // 这会调用第一个add方法，而不会调用最后一个，在调用方法时，参数本身不会进行自动类型提升
        methodOverload.add(1.0,2);  // 这回调用第二个add方法，因为参数里面有一个double类型的变量，因此后面的变量会发生自动类型提升
        // methodOverload.add("1",1);  这样调用会报错，理由是基本数据类型和引用数据类型间无法进行自动类型提升和强制类型转换，使得编译器找不到对应满足条件的add方法
        methodOverload.add((byte)1,2);  // 即使我们这样做了，它还是调用了接收两个int参数的add方法,因为这本质上和上面那个传浮点数的语句的原因是一样的，进行了自动类型提升

        // 下面演示一道操蛋面试题,题目很简单，请说出下面的输出结果

        int[] arr1 = new int[]{1,2,3,4,5};
        char[] arr2=new char[]{'1','2','3','4','5'};
        String[] arr3=new String[]{"123","456","789","abc","def"};
        System.out.println(arr1); // 输出内存地址
        System.out.println(arr2); // 输出12345。因为这个println方法与它旁边两个println方法不是一个，它们是重载关系，println专门有一个接收char[]类型的对应重载方法
        System.out.println(arr3); // 输出内存地址
    }

    public void add(byte a,byte b){
        System.out.println(a+b);
    }

    public void add(int a,int b){
        System.out.println(a+b);
    }

    public void add(double a,double b){
        System.out.println(a+b);
    }

    /*
    下面的代码编译会报错，因为编译器认为它与上面第一个add方法是一样的
    这是因为编译器只会识别类、方法名和方法列表
    当两个方法对应的上面的三个条件都相同时，编译器就会认为它们是完全相同的，这不符合 类中不能定义两个相同方法 的规则，因此编译器会报错
    也就是说，编译器不会识别方法的返回值类型，那么方法的重载实际上也不会区分方法的返回值类型
    public String add(int i,int j){

    }
    */

    public String add(String a,String b){
        return a+b;
    }

}
