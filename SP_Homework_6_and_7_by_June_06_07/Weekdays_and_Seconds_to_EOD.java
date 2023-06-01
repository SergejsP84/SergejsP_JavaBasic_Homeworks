// ЗДЕСЬ РЕАЛИЗОВАНЫ ДВЕ ДОМАШНИЕ РАБОТЫ В ОДНОМ КЛАССЕ!


package SP_Homework_6_and_7_by_June_06_07;

import java.util.Random;
import java.util.Scanner;


public class Weekdays_and_Seconds_to_EOD {
    public static void main(String[] args) {
    //Решение задачи от 31-го мая
        Scanner sc = new Scanner(System.in);
        String weekDay = "";
        String weekDayDesc = "";
        Boolean inputCorrect = false;
        while(!inputCorrect) {
            System.out.println("Let's start with entering a number from 1 through 7 to specify the day of the week!");
            byte weekDayNum = sc.nextByte();
            if (weekDayNum == 1) {
                weekDay = "Monday";
                weekDayDesc = ". The weekend's gone by, and it's time to do what you must - have a coffee, chat with colleagues, share your impressions and do the rest of that Monday routine.";
                inputCorrect = true;
            } else if (weekDayNum == 2) {
                weekDay = "Tuesday";
                weekDayDesc = ". The hardest day of the week. Friday seems to be an eternity away. There is no way a man can work in such mood, ain't that right?";
                inputCorrect = true;
            } else if (weekDayNum == 3) {
                weekDay = "Wednesday";
                weekDayDesc = ". The equator of the week. The weekend looms at the horizon, and you actually feel inspired to do some work today!";
                inputCorrect = true;
            } else if (weekDayNum == 4) {
                weekDay = "Thursday";
                weekDayDesc = ". Soon, it's going to be your time to party! Just have to make it through this day, and you're all set for a night in town!";
                inputCorrect = true;
            } else if (weekDayNum == 5) {
                weekDay = "Friday";
                weekDayDesc = "! Feeling elated, you arrive to the morning briefing, where your manager declares this weekend scheduled as 2 days of «voluntary» overtime. Sometimes, you just hate this job.";
                inputCorrect = true;
            } else if (weekDayNum == 6) {
                weekDay = "Saturday";
                weekDayDesc = ". Шолом таки шаббат!";
                inputCorrect = true;
            } else if (weekDayNum == 7) {
                weekDay = "Sunday";
                weekDayDesc = ". If you slow your weekend routine down a little just once, you might find out that there such a lovely thing as a Sunday morning... Aw, who am I kidding...";
                inputCorrect = true;
            } else {
                System.out.println("Sorry, the weekday number you have entered seems invalid");
            }
        }
        for (byte i = 0; i < 6; i++) {
            System.out.println();
        }

        System.out.println("It's " + weekDay + weekDayDesc);
        System.out.println("Regardless of that, you do feel like counting seconds to the end of today's business day. This little neat piece of software is here to help you... quite literally :)");

        //Решение задачи от 30-го мая
        Random r = new Random();
        int secondsLeft = r.nextInt(0, 28801);
        System.out.println("Time until the end of the business day:");
        System.out.println("IN SECONDS:                " + secondsLeft + " seconds left until EOD");
        int hoursLeft = (secondsLeft - (secondsLeft % 3600)) / 3600;
        if (hoursLeft == 1) {System.out.println("IN FULL HOURS:             " + hoursLeft + " full hour left until EOD");}
        else {System.out.println("IN FULL HOURS:             " + hoursLeft + " full hours left until EOD");}
        int minutesLeft = (secondsLeft - (hoursLeft * 3600)) / 60;
        String minutesPrefix = "";
        if (minutesLeft < 10) {minutesPrefix = "0";}
        System.out.println("Conventional format:       0" + hoursLeft + ":" + minutesPrefix + minutesLeft + " left until EOD");
        int justSecondsLeft = secondsLeft - (hoursLeft * 3600) - (minutesLeft * 60);
        String secondsPrefix = "";
        if (justSecondsLeft < 10) {secondsPrefix = "0";}
        System.out.println("Utterly precise:           0" + hoursLeft + ":" + minutesLeft + ":" + secondsPrefix + justSecondsLeft + " left until EOD");
    }

}

