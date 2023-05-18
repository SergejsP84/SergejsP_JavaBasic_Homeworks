package SP_Homework_1_by_May_23;

import java.util.Scanner;

public class NumericalNotations {
    public static void main(String[] args) {
        System.out.println("Please enter an integer for conversion into a different notations");
        Scanner sc = new Scanner(System.in);
        int enteredNumber = sc.nextInt();
        System.out.println("Decimal notation: " + enteredNumber);
        System.out.println("Binary notation: " + Integer.toBinaryString(enteredNumber));
        System.out.println("Octal notation: " + Integer.toOctalString(enteredNumber));
        System.out.println("Hexadecimal notation: " + Integer.toHexString(enteredNumber));
    }
}
