package genericity;

import java.util.Objects;

public class GenericitySample3 {
    public static void main(String[] args) {

    }
}


// 类的泛型约束声明，声明多个泛型时使用类型隔开
class GenericitySample3_1<T,E>{
    private T key;  // 属性的泛型类型声明
    private E value;

    public T getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericitySample3_1<?, ?> that = (GenericitySample3_1<?, ?>) o;

        if (!Objects.equals(key, that.key)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}