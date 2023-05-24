package SP_Homework_3_by_May_29;

import java.util.Scanner;

public class Box {
    public static void main(String[] args) {
        System.out.println("Thou standest near ye object of great rectangular prominence in all of its three blessed dimensions.");
        System.out.println("Please enter the box length in cm:");
        Scanner lenScan = new Scanner(System.in);
        int boxLength = lenScan.nextInt();
        System.out.println("Please enter the box width in cm:");
        Scanner widScan = new Scanner(System.in);
        int boxWidth = widScan.nextInt();
        System.out.println("Please enter the box height in cm:");
        Scanner heiScan = new Scanner(System.in);
        int boxHeight = heiScan.nextInt();
        new Box(boxLength, boxWidth, boxHeight);
    }

    public Box(int boxLength, int boxWidth, int boxHeight) {
        int boxVolume = boxLength * boxWidth * boxHeight;
        System.out.println("Rejoice, oh honourable beholder, as this is indeed a BOX, and its volume is " + boxVolume + " cm³! Amen!");
    }
}
