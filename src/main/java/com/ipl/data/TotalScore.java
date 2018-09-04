package com.ipl.data;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vivekpatel on 10-08-2018.
 */
public class TotalScore {
    static void score() {
        BufferedReader matches = null, deliveries = null;
        CSVParser matchdata = null, deliverydata = null;

        try {

            List<String> match_id = new ArrayList();
            List<String> seasonlist = EconomyRate.getSeason();
            List<String> teams = EconomyRate.getTeams();

            System.out.println("YEAR | TEAM_NAME | FOUR_COUNT | SIX_COUNT | TOTAL_SCORE");
            for (int z = 0; z < seasonlist.size(); z++) {
                String team_name = null;
                for (int k = 0; k < teams.size(); k++) {
                    matches = new BufferedReader(new FileReader("src/main/resources/matches.csv"));

                    matches.readLine();
                    matchdata = new CSVParser(matches, CSVFormat.DEFAULT.withIgnoreHeaderCase());

                    int four_count = 0, six_count = 0, total_score = 0;
                    for (CSVRecord matchrecord : matchdata) {
                        if (matchrecord.get(1).equals(seasonlist.get(z)) && (matchrecord.get(4).equals(teams.get(k)) || matchrecord.get(5).equals(teams.get(k)))) {
                            match_id.add(matchrecord.get(0));
                        }
                    }
                    matches.close();
                    matchdata.close();

                    //System.out.println(match_id);
                    if (match_id.isEmpty()) {
                        continue;
                    }
                    team_name = teams.get(k);
                    for (int j = 0; j < match_id.size(); j++) {
                        deliveries = new BufferedReader(new FileReader("src/main/resources/deliveries.csv"));
                        deliverydata = new CSVParser(deliveries, CSVFormat.DEFAULT.withIgnoreHeaderCase());
                        for (CSVRecord deliveryrecord : deliverydata) {
                            if (deliveryrecord.get(0).equals(match_id.get(j)) && (deliveryrecord.get(2).equals(teams.get(k)))) {
                                if (deliveryrecord.get(13).equals("4")) {
                                    four_count++;
                                } else if (deliveryrecord.get(13).equals("6")) {
                                    six_count++;
                                }

                                total_score += Integer.parseInt(deliveryrecord.get(15));

                            }
                        }
                    }

                    System.out.println(seasonlist.get(z) + " | " + team_name + " | " + four_count + " | " + six_count + " | " + total_score);
                    match_id.clear();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException ex) {
            Logger.getLogger(EconomyRate.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                matches.close();
                deliverydata.close();
                deliveries.close();
                matchdata.close();
            } catch (IOException ex) {
                Logger.getLogger(EconomyRate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
