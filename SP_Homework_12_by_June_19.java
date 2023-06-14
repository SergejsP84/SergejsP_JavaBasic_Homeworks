package DoingTheScaryTest;

import java.util.Arrays;
import java.util.Scanner;

public class SP_Homework_12_by_June_19 {
    public static void main(String[] args) {
        String[] weekdays = new String[7];
        for (byte i = 0; i < weekdays.length; i++) {
            String appender = new String();
            switch (i+1) {
                case 1:
                    appender = "Monday";
                    break;
                case 2:
                    appender = "Tuesday";
                    break;
                case 3:
                    appender = "Wednesday";
                    break;
                case 4:
                    appender = "Thursday";
                    break;
                case 5:
                    appender = "Friday";
                    break;
                case 6:
                    appender = "Saturday";
                    break;
                case 7:
                    appender = "Sunday";
                    break;
            }
            weekdays[i] = appender;
        }
        System.out.println("The entire array now looks as follows: " + Arrays.toString(weekdays));
        System.out.println("The last weekday array element is " + weekdays[weekdays.length-1]);
        System.out.println();
        System.out.println("Are these results correct? Please select one of the two responses (enter 1 or 2)");
        System.out.println("1 - Yes, good job! You've just created a good traditional week with Sunday at the end.");
        System.out.println("2 - Bloody madness! Sheer folly, I say! Your output's no good for us lads from the British Isles!'!");
        boolean choiceMade = false;
        while (!choiceMade) {
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.println("Thank you, good Sir");
                choiceMade = true;
            }
            else if (choice == 2) {
                System.out.println("Aaaw, I do beg your forgiveness, my lord! How dare I forget the English manner of starting a week with Sunday!");
                makeItEnglish(weekdays);
                System.out.println("The entire array now looks as follows: " + Arrays.toString(weekdays));
                System.out.println("The last weekday array element is " + weekdays[weekdays.length-1]);
                System.out.println("There. Tis' all English now. Weather's terrible, food's lousy, and weeks starts with Sundays. Hope everyone's happy now.");
                choiceMade = true;
            }
            else System.out.println("Please enter 1 or 2!");
        }
    }
    public static void makeItEnglish(String[] input) {
        String spareString = input[input.length-1];
        for (int i = (input.length-1); i > 0; i--) {
            input[i] = input [i-1];
        }
        input[0] = spareString;
        }
}


