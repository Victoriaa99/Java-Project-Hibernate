package sample;

import jdk.jfr.Name;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    public static void saveToCSVFile(String stationName, List<Train> trains, List<Train> trainsList) throws FileNotFoundException {
        File csvFile = new File(stationName+".csv");
        PrintWriter out = new PrintWriter(csvFile);
        String line = "";

        for(Train train : trains){
            for(Train t : trainsList){
                if(train.getName().equals(t.getName())){
                    train = t;
                }
            }
        }

        Field[] fields = Train.class.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Name.class)){
                line += field.getName() +", ";
            }
        }
        out.println(line);
        for(int i = 0; i < trains.size();i++){
            out.println(trains.get(i).wypiszDoPliku());
        }
        out.close();
    }
    public static void saveToCSVFile(String trainName, List<Train> trains) throws FileNotFoundException {
        File csvFile = new File(trainName+".csv");
        PrintWriter out = new PrintWriter(csvFile);
        String line = "";


        Field[] fields = Train.class.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Name.class)){
                line += field.getName() +", ";
            }
        }
        out.println(line);
        for(Train train : trains){
            out.println(train.wypiszDoPliku());
        }


        out.close();
    }
    public static List<Train> readCSVFile(String trainName) throws FileNotFoundException {
        List<Train> lista = new ArrayList<>();
        String fileName = trainName+".csv";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        try{
            while((line = br.readLine()) != null){
                String[] values = line.split(", ");
                if(values[0].equals("name")){
                    continue;
                }else{
                    String name = values[0];
                    int number = Integer.parseInt(values[1]);
                    int price = Integer.parseInt(values[2]);
                    int ilosc_w = Integer.parseInt(values[3]);
                    int shift_length = Integer.parseInt(values[4]);
                    String godzinaOdjazdu = values[5];
                    String stacjaDocelowa = values[6];

                    Train tr = new Train(name, number,price, ilosc_w, TrainCondition.NEW, shift_length,
                            godzinaOdjazdu,stacjaDocelowa);
                    lista.add(tr);
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return lista;
    }
    /*public static void readCSVFile(String stationName, TrainStation trainStation) throws FileNotFoundException {
        String fileName = stationName+".csv";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        try{
            while((line = br.readLine()) != null){
                String[] values = line.split(", ");
                if(values[0].equals("name")){
                    continue;
                }else{
                     String name = values[0];
                     int number = Integer.parseInt(values[1]);
                     int price = Integer.parseInt(values[2]);
                     int ilosc_w = Integer.parseInt(values[3]);
                     int shift_length = Integer.parseInt(values[4]);
                     String godzinaOdjazdu = values[5];
                     String stacjaDocelowa = values[6];

                     TrainH tr = new TrainH(name, number,price, ilosc_w, TrainCondition.NEW, shift_length,
                             godzinaOdjazdu,stacjaDocelowa);
                     trainStation.addTrain(tr);
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }*/

}
