package sample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class mainHibernate {

    /*public static void main(String[] args) {
        SessionFactory factory = new Configuration().addAnnotatedClass(TrainH.class)
                .configure().buildSessionFactory();

        Session session = factory.getCurrentSession();

        List<TrainH> trains = new ArrayList<>();
        session.beginTransaction();
        trains = session.createQuery("from TrainH").getResultList();

        session.getTransaction().commit();

        for(TrainH train : trains)
            System.out.println(train);

    }*/
    public static List<TrainH> getTrainsFromDB(SessionFactory factory, Session session, List<Rating> ratings){
        session = factory.getCurrentSession();
        List<TrainH> trains = new ArrayList<>();
        session.beginTransaction();
        trains = session.createQuery("from TrainH").getResultList();
        session.getTransaction().commit();

        for(TrainH train : trains){
            List<Rating> trainsRatings = new ArrayList<>();
            for(Rating rating : ratings){
                if(train.getId() == rating.getTrain_id()){
                    trainsRatings.add(rating);
                }
            }
            train.setIlosc_ocen(trainsRatings.size());
            train.setSrednia_ocen(calculateAverage(trainsRatings));
            trainsRatings.clear();
        }

        return trains;
    }
    public static float calculateAverage( List<Rating> trainsRatings){
        float wynik = 0f;
        if(trainsRatings.size() == 0)
            return wynik;
        for(Rating rating : trainsRatings)
            wynik += rating.getOcena();
        wynik /= trainsRatings.size();

        return wynik;
    }
    public static void saveTrainStation(SessionFactory factory, Session session, List<TrainH> trainsList, TrainStation trainStation){
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<TrainH> ts = trainStation.getLista();
        //UPDATE
        for(TrainH t : ts){
            for(TrainH th : trainsList){
                if(t.getName().equals(th.getName())){
                    if(t.getNumber() != th.getNumber()){
                        t.setNumber(th.getNumber());
                    }
                }
            }
        }
        String nameOfStation = trainStation.getNazwa_s();
        int j;
        if(nameOfStation.equals("Warszawa Centralna"))
            j = 0;
        else if(nameOfStation.equals("Krakow Glowny"))
            j = 5;
        else
            j = 10;

        for(int i = 0; i < 5; i++) {
            session.createQuery("update TrainH set numberOfSeats="+ts.get(i).getNumber()+
                    " where id="+(i+1+j)).executeUpdate();
        }


        session.getTransaction().commit();
    }

    public static void saveTrainTickets(SessionFactory factory, Session session,
                                        List<TrainH> ticketsList, List<TrainH> trainHS){

        session = factory.getCurrentSession();
        session.beginTransaction();
        for(int i = 15; i < trainHS.size(); i++){
            TrainH ticket = session.get(TrainH.class, trainHS.get(i).getId());
            session.delete(ticket);
        }
        session.getTransaction().commit();

        session = factory.getCurrentSession();
        session.beginTransaction();
        for(TrainH ticket : ticketsList){
            session.save(new TrainH(ticket.getName(), ticket.getNumber(),
                    ticket.getPrice(), ticket.getIlosc_w(), ticket.getShift_length(),
                    ticket.getGodzinaOdjazdu(), ticket.getStacjaDocelowa(), ticket.getRating()));
        }
        session.getTransaction().commit();
    }

    public static List<TrainH> readTrainTickets(List<TrainH> trainHS){
        List<TrainH> tickets = new ArrayList<>();
        for(int i = 15; i < trainHS.size(); i++){
           tickets.add(trainHS.get(i));
        }
        return tickets;
    }

    public static List<Rating> getRatingsFromDB(SessionFactory factory, Session session) {
        List<Rating> ratings = new ArrayList<>();
        session = factory.getCurrentSession();
        session.beginTransaction();

        ratings = session.createQuery("from Rating").getResultList();

        session.getTransaction().commit();

        return ratings;
    }
}
