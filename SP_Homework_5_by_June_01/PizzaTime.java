package SP_Homework_5_by_June_01;


public class PizzaTime {
    public static void main(String[] args) {
        System.out.println("The caloric count for a 24 cm pizza is " + caloryCount(24));
        System.out.println("The caloric count for a 28 cm pizza is " + caloryCount(28));
        System.out.println("Eating the larger one is going to yield " + (caloryCount(28) - caloryCount(24)) + " extra calories!");

    }
    public static int caloryCount(int pizzaDiameter) {
        float pizzaArea = (float) Math.PI * (pizzaDiameter/2) * (pizzaDiameter/2);
        return Math.round(pizzaArea * 40);
    }

}
