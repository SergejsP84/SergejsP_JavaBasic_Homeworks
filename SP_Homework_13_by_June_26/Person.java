package SP_Homework_13_by_June_26;

public class Person {
    String fullName;
    int age;


    public void move(String fullName) {
        System.out.println(fullName + " идёт.");
    }

    public void talk(String fullName) {
        System.out.println(fullName + " говорит.");
    }

    public Person() {
    }

    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }
}

