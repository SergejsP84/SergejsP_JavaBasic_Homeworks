package SP_Homework_15_by_July_17;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        // objective 1
        List<Integer> myList = new ArrayList<>();
        for (int i = 1; i <= 10000000; i++) {
            // objective 2
            myList.add(i);
        }
        long startTime;
        long endTime;

        // objective 3
        int temp = 0;
        startTime = System.currentTimeMillis();
        for (Integer element : myList) {
            temp = element;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time elapsed, objective 3: " + (endTime - startTime) + " milliseconds");

        // objective 4
        startTime = System.currentTimeMillis();
        for (int i = 0; i < myList.size(); i++) {
            temp = myList.get(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time elapsed, objective 4: " + (endTime - startTime) + " milliseconds");

        // objective 5
        startTime = System.currentTimeMillis();
        int listSize = myList.size();
        for (int i = 0; i < listSize; i++) {
            temp = myList.get(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time elapsed, objective 5: " + (endTime - startTime) + " milliseconds");

        // objective 6
        startTime = System.currentTimeMillis();
        for (int i = listSize-1; i >= 0; i--) {
            temp = myList.get(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time elapsed, objective 6: " + (endTime - startTime) + " milliseconds");

        // objective 7
        startTime = System.currentTimeMillis();
        Iterator<Integer> myIterator = myList.iterator();
        while (myIterator.hasNext()) {
            temp = myIterator.next();
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time elapsed, objective 7: " + (endTime - startTime) + " milliseconds");

        // objective 8
        startTime = System.currentTimeMillis();
        ListIterator<Integer> myListIterator = myList.listIterator();
        while (myListIterator.hasNext()) {
            temp = myListIterator.next();
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time elapsed, objective 8: " + (endTime - startTime) + " milliseconds");

    }
}
