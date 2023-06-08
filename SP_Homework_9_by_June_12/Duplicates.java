package SP_Homework_9_by_June_12;

import java.util.Scanner;

public class Duplicates {
    public static void main(String[] args) {
        System.out.println("This exquisite piece of software engineering uses a FOR cycle to multiply each subsequent number by 2! Behold the operation of this masterpiece.");
        int myNumber = 1;
        for (byte i = 0; i < 10; i++) {
            System.out.print(myNumber + " ");
            myNumber = myNumber * 2;
        }
        boolean wannaQuit = false;
        System.out.println();
        while (!wannaQuit) {
            if (myNumber < Integer.MAX_VALUE/2) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Would you like to keep on iterating and see MORE duplicate numbers? Please enter YES or NO as a reply");
                String reply = sc.nextLine().toUpperCase();
                switch (reply) {
                    case "YES":
                        System.out.println("Here's another extra number, oh lucky user! It is " + myNumber);
                        myNumber = myNumber * 2;
                        break;
                    case "NO":
                        System.out.println("OK, we're done! Another AMAZING piece of code has brilliantly completed its operation. Bye!");
                        wannaQuit = true;
                        break;
                    default:
                        System.out.println("That ain't a yes/no reply. Let's try that again - YES or NO?");
                        break;
                }
            }
            else {
                System.out.println("We have reached the glorious end! Further iterations are going to exceed the permitted Integer values - if this happens, the program will crash, the device will burn, the city will be eradicated and the Earth will be consumed by a black hole originating right from your device. We probably do not want that, so that is the end of the program! Thank you!");
                break;
            }
        }
    }
}
