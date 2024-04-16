package JSON;

public class Person {
    private String name;
    private int age;
    private Dog dog;

    public Person(String name, int age, Dog dog) {
        this.name = name;
        this.age = age;
        this.dog=dog;
    }

    public Person(){

    }


    public void setName(String name) {
        this.name = name;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", dog=" + dog +
                '}';
    }
}
