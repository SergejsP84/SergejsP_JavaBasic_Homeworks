package SP_Homework_13_by_June_26;

import static java.nio.file.Files.move;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person();
        Person person2 = new Person("Янис Почс", 50);
        person2.move(person2.fullName);
        person2.talk(person2.fullName);
        System.out.println();

        Phone phone1 = new Phone("+371 29242695", "iPhone 11", 150);
        Phone phone2 = new Phone("+371 26099444", "Samsung Galaxy S4 Mini", 100);
        Phone phone3 = new Phone("+371 26442239", "Nokia Cityman", 750);
        System.out.println("Этот " + phone1.model + " весит " + phone1.weight + " граммов. На него можно позвонить, набрав номер " + phone1.number);
        System.out.println("Этот " + phone2.model + " весит " + phone2.weight + " граммов. На него можно позвонить, набрав номер " + phone2.number);
        System.out.println("Этот " + phone3.model + " весит " + phone3.weight + " граммов. На него можно позвонить, набрав номер " + phone3.number);
        System.out.println();

        phone1.receiveCall("Кангарс");
        System.out.println(phone1.getNumber());
        phone2.receiveCall("Лачплесис");
        System.out.println(phone2.getNumber());
        phone3.receiveCall("Ирена");
        System.out.println(phone3.getNumber());
    }
}
