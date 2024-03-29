package classSample.commonClass.collectionSample;

import java.util.HashSet;

public class SetExer1 {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        SetExer1_1 s1 = new SetExer1_1(1001,"AA");
        SetExer1_1 s2 = new SetExer1_1(1002,"BB");

        set.add(s1);
        set.add(s2);
        System.out.println(set);  // 可以发现输出时与添加顺序不一致，是乱序的

        s1.name = "CC";
        // 调用remove方法时，会根据当前对象再次计算哈希值，但由于之前对象的属性发生了变化，因此得到的哈希值与对象原来的哈希值不同，导致没有移除
        set.remove(s1);
        System.out.println(set);

        // 每次调用操作方法时仅会计算参数的哈希值
        // 由于原来的元素虽然属性发生了变化，但是其哈希值不会随之改变，因此该添加的对象相当于其哈希值的映射地址还为空，直接加入了Set
        set.add(new SetExer1_1(1001,"CC"));
        System.out.println(set);

        // 添加该对象时，它的哈希值与s1对象在set内的哈希值冲突，因此双方使用equals方法(调用的是这个想添加的对象的，而不是s1的)比较大小，因为s1属性发生过变化，因此发现不相等，把这玩意也加了进来
        set.add(new SetExer1_1(1001,"AA"));
        System.out.println(set);

    }
}

class SetExer1_1 {
    int id;
    String name;

    public SetExer1_1(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //其中Person类中重写了hashCode()和equal()方法

    @Override
    public boolean equals(Object o) {
        System.out.println("Person equals()....");
        if (this == o) return true;
        if (!(o instanceof SetExer1_1)) return false;

        SetExer1_1 setExer1_1 = (SetExer1_1) o;

        if (id != setExer1_1.id) return false;
        return name != null ? name.equals(setExer1_1.name) : setExer1_1.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}