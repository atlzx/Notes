package keywordSample;

public class SuperKeywords1 {
    String id="父类的id";
    String name;
    int age;
    public SuperKeywords1() {

    }

    public SuperKeywords1(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public SuperKeywords1(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void say(){
        System.out.println("父类的方法");
    }


}
