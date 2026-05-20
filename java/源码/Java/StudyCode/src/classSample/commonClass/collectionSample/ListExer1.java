package classSample.commonClass.collectionSample;

import org.junit.Test;

import java.util.*;

public class ListExer1 {
        @Test
        public void testListRemove() {
            List list = new ArrayList();
            list.add(1); //自动装箱
            list.add(2);
            list.add(3);
            updateList(list);
            System.out.println(list);//
        }

        private static void updateList(List list) {
            // 此处调用的是List接口的 remove(int index)方法，因为List是Collection接口的子接口，因此自下向上找方法时先找到它
            list.remove(2);  // 删除索引为2的元素
          list.remove(Integer.valueOf(2));  // 此处由于传入的是对象，因此调用的是Collection接口的 remove(Object obj)方法
        }
}
