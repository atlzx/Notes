package ThreadSample;

public class ThreadExer2 {
    public static void main(String[] args) {
        BB b = new BB();
        new Thread(b){
            @Override
            public void run() {
                System.out.println("CC");
            }
        }.start();  // 如果子类重写了Thread类的run方法，那么会调用子类重写的run方法，只有当子类中没有run方法时才回去找Thread父类对象的run方法

        new Thread(){}.start();  // 此处没有重写run方法，因此调用的是父类对象的run方法，但是父类对象的run方法里面什么都没有

    }
}

class AA extends Thread{
    @Override
    public void run() {
        System.out.println("AA");
    }
}

class BB implements Runnable{
    @Override
    public void run() {
        System.out.println("BB");
    }
}

