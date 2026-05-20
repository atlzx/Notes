package switchSample;

public class SwitchCase {
    public static void main(String[] args) {
        String str = "aaa";
        // switch中表达式的值必须是以下类型:
        // int、byte、short、char、enum(JDK5.0新增)、String(JDK7.0新增规范)
        switch (str) {
            // 这个default写在哪里都行，它的位置与它的执行顺序无关，无论如何，它都会在其同级case都不匹配的情况下执行
            // 但是，当default后的代码并没有break时，且它本身并没有写在结构的最后
            // 代码会继续向下执行，如本例，它会执行到case 为 spring的代码后的break处，因为一旦代码执行，会一直执行到break处或结构结束
            default:
                System.out.println("季节错误");
            case "spring":
                System.out.println("春天");
                break;
            case "summer":
                System.out.println("夏天");
                break;
            case "autumn":
                System.out.println("秋天");
                break;
            case "winter":
                System.out.println("冬天");
                break;
        }
    }
}
