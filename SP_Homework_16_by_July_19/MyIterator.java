package SP_Homework_16_by_July_19;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyIterator implements ListIterator<String> {

    private Node current;
    private MyLinkedList myLinkedList;
    private Node first;
    private int currentIndex;

    public MyIterator(Node current, Node first) {
        this.current = current;
        this.currentIndex = 0;
        this.first = this.first;
    }

    public MyIterator(MyLinkedList myLinkedList, Node current) {
        this.myLinkedList = myLinkedList;
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current.getNext() != null;
    }

    @Override
    public String next() {
        String value = current.getValue();
        current = current.getNext();
        currentIndex++;
        return value;
    }

    @Override
    public boolean hasPrevious() {
        return current.getPrevious() != null;
    }

    @Override
    public String previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException("No previous element.");
        }
        current = current.getPrevious();
        return current.getValue();
    }

    @Override
    public int nextIndex() {
        return currentIndex;
    }

    @Override
    public int previousIndex() {
        int prevIndex = 0;
        if (current == null || current.getPrevious() == null) {
            prevIndex = -1;
            System.out.println("No previous element here");
        }
        else {
            Node temp = this.first;
        while (temp.getNext() != current.getPrevious()) {
                temp = temp.getNext();
                prevIndex++;
            }
        }
        return prevIndex;
    }

    @Override
    public void remove() {
        if (current == null) {
            System.out.println("No element to remove.");
            return;
        }
        Node forRemoval = current;
        Node following = current.getNext();
        Node preceding = current.getPrevious();

        if (preceding != null) {
            preceding.setNext(following);
        } else {
            first = following;
        }
        if (following != null) {
            following.setPrevious(preceding);
        }
        current = following;
        myLinkedList.setSize(myLinkedList.getSize() - 1);
    }

    @Override
    public void set(String s) {
        if (current == null || current.getPrevious() == null) {
            System.out.println("No element to set, sorry");
        } else {
            current.setValue(s);
        }
    }

    @Override
    public void add(String s) {
        if (current == null) {
            System.out.println("Cannot add element - null iterator position");
        } else {
            Node newNode = new Node(s);
            if (current.getPrevious() != null) {
                current.getPrevious().setNext(newNode);
                newNode.setPrevious(current.getPrevious());
            } else {
                myLinkedList.setFirst(newNode);
                newNode.setPrevious(null);
            }
            newNode.setNext(current);
            current.setPrevious(newNode);
            myLinkedList.setSize(myLinkedList.getSize() + 1);
        }
    }
}
