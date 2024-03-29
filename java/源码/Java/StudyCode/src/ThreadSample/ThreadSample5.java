package ThreadSample;

public class ThreadSample5 {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Runnable p1 = new Producer(clerk);
        Runnable p2 = new Producer(clerk);
        Runnable c1 = new Consumer(clerk);
        Runnable c2 = new Consumer(clerk);
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(c1);
        Thread t4 = new Thread(c2);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}


class Clerk {
    private int source = 0;

    public synchronized void addSource() {
        if (source >= 0 && source < 20) {
            source++;
            System.out.println(Thread.currentThread().getName() + "生产了一份资源，目前资源总量为" + source);
            this.notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public synchronized void decreaseSource() {
        if (source > 0) {
            source--;
            System.out.println(Thread.currentThread().getName() + "消费了一份资源，目前资源总量为" + source);
            this.notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Producer implements Runnable {
    private Clerk clerk;

    public Producer() {

    }

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            clerk.addSource();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer() {

    }

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            clerk.decreaseSource();
        }
    }
}