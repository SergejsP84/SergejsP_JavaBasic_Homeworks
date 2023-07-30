package SP_Homework_16_by_July_19;

import java.util.Iterator;

public class MyLinkedList implements Iterable<String> {

    private int size = 0;
    private Node first;

    /**
     * Добавление элемента в начало списка
     *
     * @param value добавляемое значение
     */
    public void addFirst(String value) {
        Node node = new Node(value);
        if (first != null) {
            node.setNext(first);
            first.setPrevious(node);
        }
        first = node;
        size++;
    }

    /**
     * Добавление элемента в конец списка
     *
     * @param value добавляемое значение
     */
    public void addLast(String value) {
        if (size == 0) {
            addFirst(value);
            return;
        }

        Node current = first;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        Node last = new Node(value);
        current.setNext(last);
        last.setPrevious(current);
        size++;
    }

    public void removeElement(String value) {
        if (size == 0) {
            System.out.println("The list is empty - nothing to remove here!");
        } else {
            Node current = first;
            Node forRemoval = null;
            Node following = null;
            Node preceding = null;
            while (current.getNext() != null) {
                if (value.equals(current.getValue())) {
                    forRemoval = current;
                    following = current.getNext();
                    preceding = current.getPrevious();
                    break;
                }
                current = current.getNext();
            }

            if (forRemoval == null) System.out.println("No such element in the list!");
            if (preceding != null) {
                preceding.setNext(following);
            } else {
                first = following;
            }
            if (following != null) {
                following.setPrevious(preceding);
            }
            size--;
        }
    }


    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        Node current = first;
        while (current != null) {
            builder.append(current.getValue()).append(", ");
            current = current.getNext();
        }
        builder.setLength(builder.length() - 2);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return new MyIterator(first, first);
    }

    public Node getFirst() {
        return first;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFirst(Node first) {
        this.first = first;
    }
}
