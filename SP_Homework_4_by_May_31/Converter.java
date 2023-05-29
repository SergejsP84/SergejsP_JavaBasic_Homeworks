package SP_Homework_4_by_May_31;

import java.util.Scanner;

public class Converter {
    public static void main(String[] args) {
        System.out.print("Please enter a temperature value in degrees Celsius for conversion:");
        System.out.println();
        Scanner scTemp = new Scanner(System.in);
        float curTemp = scTemp.nextFloat();
        voidFahr(curTemp);
        float tempFahr3 = returnFahr(curTemp);
        System.out.println(curTemp  + "°C amounts to " + tempFahr3 + "°F (delivered using a value-return method)");
        voidKelvin(curTemp);
        float tempKelvin3 = returnKelvin(curTemp);
        System.out.println(curTemp  + "°C amounts to " + tempKelvin3 + "°K (delivered using a value-return method)");
    }
    public static void voidFahr(float tempCel) {
        float tempFahr1 = tempCel * 9 / 5 + 32;
        System.out.println(tempCel + "°C amounts to " + tempFahr1 + "°F (delivered using a void method)");
    }
    public static float returnFahr(float tempCel) {
        float tempFahr2 = tempCel * 9 / 5 + 32;
        return tempFahr2;
    }
    public static void voidKelvin(float tempCel) {
        float tempKelvin1 = (float) (tempCel + 273.15);
        System.out.println(tempCel + "°C amounts to " + tempKelvin1 + "°K (delivered using a void method)");
    }
    public static float returnKelvin(float tempCel) {
        float tempKelvin2 = (float) (tempCel + 273.15);
        return tempKelvin2;
    }
}
