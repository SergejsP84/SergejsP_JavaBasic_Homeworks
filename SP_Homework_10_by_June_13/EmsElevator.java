package SP_Homework_10_by_June_13;

import java.util.Scanner;

public class EmsElevator {
    public static void main(String[] args) {
        System.out.println("Let's play Elevator! Sounds ominous, doesn't it? You are inside an elevator on floor 1 of a great big building.");
        System.out.println("Each press of ANY button makes the elevator go N floors up and M floors down. You have to make it to the TOP floor.");
        System.out.println("Please enter the number of floors of the building (H). Be generous - make it no less than 2 floors high, al'right?");
        Scanner sc1 = new Scanner(System.in);
        int H = sc1.nextInt();
        if (H < 2) {
            while (H < 2) {
                System.out.println("Oh, come on! We need to build a nice tall building here, not dig into the ground! Enter a number of floors that is greater than 2!");
                H = sc1.nextInt();
            }
        }
        System.out.println("Please enter the number of floors by which the elevator ASCENDS (N): ");
        Scanner sc2 = new Scanner(System.in);
        int N = sc2.nextInt();
        if (N < 0) System.out.println("Negative ascent is actually descent, didn't ya know that? But fine, feel free to play around with it if you want to");
        System.out.println("Please enter the number of floors by which the elevator DESCENDS (M). A quick hint - you really want this value to be SMALLER than N! ");
        Scanner sc3 = new Scanner(System.in);
        int M = sc3.nextInt();
        if (M > N) {
            System.out.println("You know, going down by more floors than you go up won't get you higher. In fact, this can lead to some RATHER unexpected results.");
            System.out.println("Do you wish to reconsider? Please enter YES or NO as your response");
            Scanner sc4 = new Scanner(System.in);
            String confirm1 = sc4.nextLine().toUpperCase();
            if (!(confirm1.equals("YES") || confirm1.equals("NO"))) {
                boolean retype1 = true;
                while (retype1) {
                    System.out.println("Please enter a YES or NO answer.");
                    confirm1 = sc4.nextLine().toUpperCase();
                    if (confirm1.equals("YES")) {
                        retype1 = false;
                    }
                    if (confirm1.equals("NO")) {
                        retype1 = false;
                    }
                }
            }
            if (confirm1.equals("YES")) {
                while (M > N) {
                    System.out.println("Please enter the number of floors by which the elevator DESCENDS (M). Remember, it should be lesser than " + N + "!");
                    M = sc3.nextInt();
                }
            }
            if (confirm1.equals("NO")) System.out.println("OK. You've been warned.");
            sc4.close();
        }
        sc1.close();
        sc2.close();
        sc3.close();


        if (M == 0) System.out.println("Well, a zero-floor descent is the obvious decision :) But it kinda kills the whole idea, doesn't it?");
        if (M < 0) System.out.println("Negative descent is actually ascent, didn't ya know that? But fine, feel free to play around with it if you want to");
        if (N == M) {
            System.out.println("Houston, we have a problem.");
            System.out.println("You could have imagined that going up and down the SAME number of floors each time would result in an INFINITE CYCLE, couldn't you?");
            System.out.println("The price of your mistake is great indeed. The elevator is now going to ride up and down FOREVER - with you trapped inside.");
            System.out.println("After you eventually expire, your restless ghost will keep on haunting this elevator shaft, trapped in an infinite loop of ups and downs. Have a nice eternity.");
        }
        else rideTheElevator(H, N, M);
    }

    public static void rideTheElevator (int H, int N, int M) {
        System.out.println("All right, the elevator is off for the top!");
        int currentFloor = 1;
        int climbCount = 0;
        while (currentFloor < H) {
            currentFloor = currentFloor + N - M;
            climbCount += 1;
            if (currentFloor == 0) System.out.println("You're at the basement floor. Watch out - nothing good lurks below!");
            if (currentFloor < 0) {
                System.out.println("Oh no - the elevator climbs down rather than up! :( Since this cycle won't stop until it reaches the UPPER floor, the elevator is going to keep pounding the ground and finally take you to the Abyss itself. A horrible finale.");
                break;
            }
            if (currentFloor > H) currentFloor = H;
            System.out.println("Having pressed an elevator button " + climbCount + " time(s), you went " + N + " floor(s) up and " + M + " floor(s) down. You're now on floor " + currentFloor + ".");
        }
        if (currentFloor > 0) System.out.println("The elevator ceiling hits the top limiter bar of the shaft, signalling that you have arrived at your destination!");
        if (currentFloor > 0) System.out.println("Congrats! You've reached floor " + H + " by pressing an elevator button " + climbCount + " time(s)!");
    }
}
