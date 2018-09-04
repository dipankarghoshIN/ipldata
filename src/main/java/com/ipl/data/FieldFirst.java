package com.ipl.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by vivekpatel on 10-08-2018.
 */
public class FieldFirst {

    static void top() {
        try {
            BufferedReader reader;
            CSVParser csvParser;
            List<String> team = new ArrayList();

            System.out.println("YEAR | Team | Count");
            for (int z = 6; z < 8; z++) {
                reader = new BufferedReader(new FileReader("src/main/resources/matches.csv"));
                String year = "201" + z;

                csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("SEASON", "TOSS_WINNER", "TOSS_DECISION").withIgnoreHeaderCase().withTrim());

                for (CSVRecord csvRecord : csvParser) {
                    if (csvRecord.get(1).equalsIgnoreCase(year) && csvRecord.get(7).equalsIgnoreCase("field")) {
                        team.add(csvRecord.get(6));
                    }
                }

                for (int i = 0; i < 4; i++) {
                    Set<String> unique = new HashSet(team);
                    int max = 0;
                    int curr = 0;
                    String currKey = null;
                    for (String key : unique) {
                        curr = Collections.frequency(team, key);
                        if (max < curr) {
                            max = curr;
                            currKey = key;
                        }
                    }
                    List<String> dup = new ArrayList();
                    dup.add(currKey);
                    team.removeAll(dup);
                    dup.clear();
                    System.out.println(year + " | " + currKey  + " | " + max);
                }

                team.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

