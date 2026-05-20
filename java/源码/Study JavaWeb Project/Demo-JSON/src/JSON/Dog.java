package JSON;

public class Dog {
    private String name;
    private int age;
    private String description;

    public Dog(){

    }
    public Dog(String name,int age,String description){
        this.name=name;
        this.age=age;
        this.description=description;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                '}';
    }
}
