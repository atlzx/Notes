/**
 * 三个窗口买票：共售100张票
 * 存在线程安全的问题
 * @author wds
 * @date 2021-11-15-20:30
 */
public class ThreadTest04 {
    public static void main(String[] args) {
        Thread04 thread04 = new Thread04();
        Thread thread = new Thread(thread04, "窗口1");
        Thread thread1 = new Thread(thread04, "窗口2");
//        Thread thread2 = new Thread(thread04, "窗口3");
        thread.start();
        thread1.start();
//        thread2.start();
    }
}
class Thread04 extends Thread{
    private static int ticket = 100;        //静态数据共享
    @Override
    public void run() {
        while (true){
            if(ticket>0){
//                try {
//                    Thread.sleep(1);          //模拟售票需要的时间
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName()+"正在售票,票号为："+ticket);
                ticket--;
            }else
            {
                break;
            }
        }

    }
}


