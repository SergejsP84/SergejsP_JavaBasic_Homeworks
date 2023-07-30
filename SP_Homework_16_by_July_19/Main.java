package SP_Homework_16_by_July_19;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MyLinkedList list = new MyLinkedList();

        System.out.println(list);

        list.addLast("Pear");

        list.addFirst("Apple");
        list.addFirst("Peach");
        list.addFirst("Orange");

        System.out.println(list);

        list.addLast("Pineapple");

        System.out.println(list);

        List<String> strings = new ArrayList<>();
        Iterator<String> iter = strings.iterator();

        System.out.println("Цикл for-each");
        for (String current : list) {
            System.out.println(current);
        }
    }
}