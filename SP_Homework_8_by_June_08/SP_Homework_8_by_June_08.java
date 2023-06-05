package SP_Homework_8_by_June_08;

import java.util.Scanner;

// Представьте, что вы разрабатываете алгоритм для кофейного аппарата, в котором есть всего 4 кнопки.
// Выбор напитков может быть следующий: эспрессо, американо, капучино, чай.
// При нажатии кнопки, ваш напиток должен приготовиться.
// Попробуйте отобразить в консоли процесс приготовления напитков, исходя из нажатой кнопки
// (1-эспрессо, 2-американо, 3-капучино, 4-чай) через условные конструкции.
//
// Помимо этого сформируйте и разделите процесс на отдельные методы для их переиспользования,
// т.к. напитки могут быть разделены на самостоятельные процессы:
//
// Эспрессо  - неразделимый процесс
// Американо - эспрессо + добавление воды
// Капучино  - эспрессо + добавление вспененного молока
//
// Перепишите пример с кофе-машиной в формат switch-case

public class SP_Homework_8_by_June_08 {

        public static void main(String[] args) {
            System.out.println("Please select a drink");
            System.out.println("Press 1 for Espresso");
            System.out.println("Press 2 for Americano");
            System.out.println("Press 3 for Cappuccino");
            System.out.println("Press 4 for Tea");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("One Espresso, coming up.");
                        System.out.println(getEspresso() + "\n" + getFinalMessage());
                        break;
                    case 2:
                        System.out.println("One Americano, coming up.");
                        System.out.println(getAmericano());
                        System.out.println(getFinalMessage());
                        break;
                    case 3:
                        System.out.println("One Cappuccino, coming up.");
                        System.out.println(getCapuccino());
                        System.out.println(getFinalMessage());
                        break;
                    case 4:
                        System.out.println("One Tea, coming up.");
                        System.out.println(getTea());
                        System.out.println(getFinalMessage());
                        break;
                    case 90210:
                        System.out.println("Way to go! You have just unlocked a special Secret Ingredient!");
                        System.out.println("Please select a drink");
                        System.out.println("Press 1 for Espresso");
                        System.out.println("Press 2 for Americano");
                        System.out.println("Press 3 for Cappuccino");
                        System.out.println("Press 4 for Tea");
                        Scanner sc2 = new Scanner(System.in);
                        int choice2 = sc2.nextInt();
                        switch (choice2) {
                            case 1:
                                System.out.println("One Espresso, coming up.");
                                System.out.println(getEspresso() + "\n" + getExtraIngredient() + "\n" + getFinalMessage());
                                break;
                            case 2:
                                System.out.println("One Americano, coming up.");
                                System.out.println(getAmericano());
                                System.out.println(getExtraIngredient());
                                System.out.println(getFinalMessage());
                                break;
                            case 3:
                                System.out.println("One Cappuccino, coming up.");
                                System.out.println(getCapuccino());
                                System.out.println(getExtraIngredient());
                                System.out.println(getFinalMessage());
                                break;
                            case 4:
                                System.out.println("One Tea, coming up.");
                                System.out.println(getTea());
                                System.out.println(getExtraIngredient());
                                System.out.println(getFinalMessage());
                                break;
                            default:
                                System.out.println("No such drinks here, sorry. Still, since you have entered the secret code, you do get your reward - a nice shot of WHISKEY! Enjoy!");
                                break;
                        }
                        break;
                    default:
                        System.out.println("No such drinks here, sorry");
                        break;
                }
                sc.close();
        }
        public static String getEspresso() {
            return "\tPreparing an Espresso...";
        }
        public static String getAmericano() {
            String result = "";
            result += getEspresso();
            result += "\n";
            result += getWater();
            return result;
        }
        public static String getCapuccino() {
            String result = getEspresso();
            result += "\n";
            result += "\tAdding foamed milk...";
            return result;
        }
        public static String getTea() {
            String result = "Deploying a tea package...";
            result += "\n";
            result += getWater();
            return result;
        }
        public static String getWater() {
            return "\tAdding some water...";
        }
        public static String getFinalMessage() {
            return "\tEnjoy your drink!";
        }
        public static String getExtraIngredient() {
            return "\tHaving entered the secret code, you get your drink complemented with a great big shot of WHISKEY! And an unexpected end of this business day, for that matter. Cheers!";
        }
    }
