package com.ipl.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by vivekpatel on 10-08-2018.
 */
public class Runs {
    static void runs() {
        BufferedReader matches;
        BufferedReader deliveries;
        CSVParser csvdata, scoredata;
        try {
            matches = new BufferedReader(new FileReader("src/main/resources/matches.csv"));
            deliveries = new BufferedReader(new FileReader("src/main/resources/deliveries.csv"));
            deliveries.readLine();
            matches.readLine();

            System.out.println("YEAR | TEAM_NAME | FOUR_COUNT | SIX_COUNT | TOTAL_SCORE");

            csvdata = new CSVParser(matches, CSVFormat.DEFAULT.withHeader("MATCH_ID").withIgnoreHeaderCase().withTrim());
            scoredata = new CSVParser(deliveries, CSVFormat.DEFAULT.withHeader("MATCH_ID").withIgnoreHeaderCase().withTrim());
            String matchid = "";
            String year = null, team_name = null, team2_name = null;

            for (CSVRecord record : csvdata) {
                matchid = record.get(0);
                int four_count = 0, six_count = 0, total_score = 0, sfour_count = 0, ssix_count = 0, stotal_count = 0;
                for (CSVRecord score : scoredata) {
                    if (score.get(0).equals(matchid)) {
                        if (score.get(1).equals("1")) {
                            year = record.get(1);
                            team_name = score.get(2);
                            if (score.get(13).equals("4")) {
                                four_count++;
                            } else if (score.get(13).equals("6")) {
                                six_count++;
                            }
                            total_score += Integer.parseInt(score.get(15));

                        } else {
                            team2_name = score.get(2);
                            if (score.get(13).equals("4")) {
                                sfour_count++;
                            } else if (score.get(13).equals("6")) {
                                ssix_count++;
                            }
                            stotal_count += Integer.parseInt(score.get(15));
                        }
                    } else {
                        break;
                    }
                }
                System.out.println(year + " | " + team_name + " | " + four_count + " | " + six_count + " | " + total_score);
                System.out.println(year + " | " + team2_name + " | " + sfour_count + " | " + ssix_count + " | " + stotal_count);

            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
