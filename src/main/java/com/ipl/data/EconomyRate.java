package com.ipl.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vivekpatel on 10-08-2018.
 */
public class EconomyRate {
    static void economyrate(){
        BufferedReader matches,bowler;
        CSVParser season,bowler_name;
        List<String> years = getSeason();
        List<String> bowlers = bowlers();
        Map<String,Double> map = new HashMap();
        List<String> match_id = new ArrayList();
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("YEAR | PLAYER | Economy");
        try{
            for(int i=0; i<years.size(); i++){
                matches = new BufferedReader(new FileReader("src/main/resources/matches.csv"));
                matches.readLine();
                season = new CSVParser(matches,CSVFormat.DEFAULT.withIgnoreHeaderCase());

                for(CSVRecord records:season){
                    if(records.get(1).equals(years.get(i))){
                        match_id.add(records.get(0));
                    }
                }

                for(int j=0;j<bowlers.size();j++){
                    bowler = new BufferedReader(new FileReader("src/main/resources/deliveries.csv"));
                    bowler.readLine();
                    int wide_run=0,noball_run=0,penalty_run=0,bats_run=0,overs_count=0,total_runs = 0;
                    double economy_rate;
                    String player=bowlers.get(j);
                    bowler_name = new CSVParser(bowler,CSVFormat.DEFAULT.withIgnoreHeaderCase());
                    for(CSVRecord record:bowler_name){
                        for(int k=0;k<match_id.size();k++){
                            if(record.get(0).equals(match_id.get(k))&&record.get(7).equals(bowlers.get(j))){
                                wide_run += Integer.parseInt(record.get(8));
                                noball_run += Integer.parseInt(record.get(11));
                                penalty_run += Integer.parseInt(record.get(12));
                                bats_run += Integer.parseInt(record.get(13));
                                if(record.get(5).equals("1")){
                                    overs_count++;
                                }
                            }
                        }}
                    if(overs_count>=10){

                        total_runs = wide_run+noball_run+penalty_run+bats_run;
                        economy_rate = Double.valueOf((df.format((double)total_runs/overs_count)));

                        map.put(player, economy_rate);

                        //System.out.println(years.get(i)+" "+player+" "+total_runs+" "+overs_count+" "+format.format(economy_rate));
                        //System.out.println(years.get(i)+" "+player+" "+economy_rate);
                    }

                }
                List<Map.Entry<String,Double>> top = findGreatest(map,10);
                Collections.sort(top, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

                for(Map.Entry<String,Double> entry:top){
                    System.out.println(years.get(i)+" - "+entry.getKey()+" - "+entry.getValue());
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>>
    findGreatest(Map<K, V> map, int n)
    {
        Comparator<? super Map.Entry<K, V>> comparator =
                new Comparator<Map.Entry<K, V>>()
                {

                    public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1)
                    {
                        V v0 = e0.getValue();
                        V v1 = e1.getValue();
                        return v0.compareTo(v1);
                    }
                };
        PriorityQueue<Map.Entry<K, V>> highest =
                new PriorityQueue<Map.Entry<K,V>>(n, comparator);
        for (Map.Entry<K, V> entry : map.entrySet())
        {
            highest.offer(entry);
            while (highest.size() > n)
            {
                highest.poll();
            }
        }

        List<Map.Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
        while (highest.size() > 0)
        {
            result.add(highest.poll());
        }
        return result;
    }

    static List<String> getSeason() {
        BufferedReader matches;
        CSVParser matchdata;
        Set<String> useason = new HashSet();
        List<String> seasons = new ArrayList();
        try {
            matches = new BufferedReader(new FileReader("src/main/resources/matches.csv"));
            matches.readLine();
            matchdata = new CSVParser(matches, CSVFormat.DEFAULT.withIgnoreHeaderCase());

            for (CSVRecord season : matchdata) {
                useason.add(season.get(1));
            }
            seasons.addAll(useason);
            Collections.sort(seasons);

        } catch (Exception e) {

        }
        return seasons;
    }

    static List<String> bowlers(){
        BufferedReader br = null;
        CSVParser csv = null;
        Set<String> uniquebowlers = new HashSet();
        List<String> bowler_name = new ArrayList();

        try{
            br = new BufferedReader(new FileReader("src/main/resources/deliveries.csv"));
            br.readLine();
            csv = new CSVParser(br, CSVFormat.DEFAULT.withIgnoreHeaderCase());

            for(CSVRecord record:csv){
                uniquebowlers.add(record.get(7));
            }
            bowler_name.addAll(uniquebowlers);
        }catch(FileNotFoundException e){

        } catch (IOException ex) {
            Logger.getLogger(EconomyRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                br.close();
                csv.close();
            } catch (IOException ex) {
                Logger.getLogger(EconomyRate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return bowler_name;
    }

    static List<String> getTeams() {
        BufferedReader matches;
        CSVParser matchdata;
        Set<String> useason = new HashSet();
        List<String> seasons = new ArrayList();
        try {
            matches = new BufferedReader(new FileReader("src/main/resources/matches.csv"));
            matches.readLine();
            matchdata = new CSVParser(matches, CSVFormat.DEFAULT.withIgnoreHeaderCase());

            for (CSVRecord season : matchdata) {
                useason.add(season.get(4));
            }
            seasons.addAll(useason);

        } catch (IOException e) {

        }
        return seasons;
    }
}
