package functionSample;

public class VariableParameter {
    public static void main(String[] args) {
        VariableParameter variableParameter=new VariableParameter();
        // 虽然对编译器来说，可变参数可以参与到方法重载中去，但是，下面的代码却出现了问题，因为编译器不知道该段代码应该调用哪个重载方法，因为两个test1方法同时都满足实参传入的条件
        // variableParameter.test1(1,2,3,4);
    }

    public int test1(int... x) {
        for(int i=0;i<x.length;i++){
            System.out.println("输出可变参数的对应值:"+x[i]);
        }
        return 1;
    }


    /*
        编译器会认为该方法与上面的第一个test1方法是一样的，导致无法通过编译
        public int test1(int[] x){
            return 0;
        }
    */

    // 下面的方法与上面第一个test1方法构成重载关系，但是这样做实际上参数列表可以直接用一个可变参数替代，这样写虽然能够构成重载，但是在实际方法调用时会出现问题，详情见上面的主方法内的注释
    public int test1(int a,int ...x){
        System.out.println("输出对应变量"+a);
        for (int i = 0; i < x.length; i++) {
            System.out.println("输出可变参数的对应值:"+x[i]);
        }
        return 1;
    }

    /*
        下面的代码因为可变参数写在了前面导致编译错误。可变参数必须写在参数列表的最后
        public int test1(int ...x,double b){
            return 0;
        }
    */
}
