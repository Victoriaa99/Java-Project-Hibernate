package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrainSerialize {
    public static void serialize(String filename, List<Train> trains, List<Train> trainsList){
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            for(Train train : trains){
                for(Train t : trainsList){
                    if(train.getName().equals(t.getName())){
                        train = t;
                    }
                }
            }

            out.writeObject(trains);

            file.close();
            out.close();

        } catch(IOException e){

        }
    }
    public static void serialize(String filename, List<Train> trains){
        try{
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(trains);

            file.close();
            out.close();

        } catch(IOException e){

        }
    }
    public static List<Train> deserialize(String filename) throws IOException, ClassNotFoundException, FileNotFoundException{
        List<Train> trains = null;

        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);

        trains =(List<Train>) in.readObject();

        for(int i = 0; i < trains.size();i++){
            trains.get(i).setStan(TrainCondition.NEW);
        }

        file.close();
        in.close();

        return trains;
    }

    public static void main(String[] args) {

    }
}
