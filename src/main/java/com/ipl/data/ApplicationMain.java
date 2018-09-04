package com.ipl.data;
import java.util.Scanner;

/**
 * Created by vivekpatel on 10-08-2018.
 */
public class ApplicationMain {
    public static void main(String args[]) {
        System.out.println("Command Options: ");
        System.out.println("1: Top 4 teams which elected to field first after winning toss in the year 2016 and 2017");
        System.out.println("2: List total number of fours, sixes, total score with respect to team and year");
        System.out.println("3: Top 10 best economy rate bowler with respect to year who bowled at least 10 overs");
        System.out.println("4: Find the team name which has Highest Net Run Rate with respect to year");
        System.out.println("0: QUIT");
        Scanner scan = new Scanner(System.in);
        String choice = null;

        do {
            choice = scan.nextLine();
            switch (choice) {
                case "1": FieldFirst.top();
                    break;
                case "2": TotalScore.score();
                    break;
                case "3": EconomyRate.economyrate();
                    break;
                case "4": Runs.runs();
                         break;
                case "0":
                    System.out.println("Command Options: ");
                    System.out.println("1: Top 4 teams which elected to field first after winning toss in the year 2016 and 2017");
                    System.out.println("2: List total number of fours, sixes, total score with respect to team and year");
                    System.out.println("3: Top 10 best economy rate bowler with respect to year who bowled at least 10 overs");
                    System.out.println("4: Find the team name which has Highest Net Run Rate with respect to year");
                    System.out.println("0: QUIT");
                    break;
            }
        }while (choice != "0");
    }
}

