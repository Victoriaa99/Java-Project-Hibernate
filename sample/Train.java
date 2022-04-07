package sample;

import java.io.Serializable;
import jdk.jfr.Name;

import javax.persistence.*;

@Entity
@Table(name = "train")
public class Train implements Comparable<Train>, Serializable {
    private TrainCondition stan;
    @Id
    @Column
    int id;

    @Column
    @Name("Nazwa pociągu")
    private String name;

    @Column(name = "numberOfSeats")
    @Name("Liczba miejsc")
    private int number;

    @Column
    @Name("Cena biletu")
    private int price;

    @Column(name = "numberOfWagons")
    @Name("Ilość wagonów")
    private int ilosc_w;

    @Column
    @Name("Długość przejazdu")
    private int shift_length;

    @Column(name = "departureTime")
    @Name("Godzina odjazdu")
    private String godzinaOdjazdu;

    @Column(name = "destination")
    @Name("Stacja docelowa")
    private String stacjaDocelowa;

    @Column
    private int rating;

    public Train(String name, int number, int price, int ilosc_w, TrainCondition stan, int shift_length, String godzinaOdjazdu, String stacjaDocelowa) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.ilosc_w = ilosc_w;
        this.stan = stan;
        this.shift_length = shift_length;
        this.godzinaOdjazdu = godzinaOdjazdu;
        this.stacjaDocelowa = stacjaDocelowa;
    }

    public Train(String name, int number, int price, int ilosc_w, int shift_length, String godzinaOdjazdu, String stacjaDocelowa, int rating) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.ilosc_w = ilosc_w;
        this.shift_length = shift_length;
        this.godzinaOdjazdu = godzinaOdjazdu;
        this.stacjaDocelowa = stacjaDocelowa;
        this.rating = rating;
    }

    public String getGodzinaOdjazdu() {
        return godzinaOdjazdu;
    }

    public String getStacjaDocelowa() {
        return stacjaDocelowa;
    }

    public Train(String name, int number, int price, int ilosc_w, TrainCondition stan, int shift_length){
        this.name = name;
        this.number = number;
        this.price = price;
        this.ilosc_w = ilosc_w;
        this.stan = stan;
        this.shift_length = shift_length;
    }

    public int getPrice() {
        return price;
    }

    public int getIlosc_w() {
        return ilosc_w;
    }

    public Train(){

    }

    public void wypisz(){
        System.out.println("Nazwa pociagu: " + this.name);
        System.out.println("Ilosc miejsc: " + this.number);
        System.out.println("Cena biletu: " + this.price);
        System.out.println("Ilosc wagonow: "+ this.ilosc_w);
        System.out.println("Stan pociagu: " + this.stan);
        System.out.println("Dlugosc zmiany: " + this.shift_length);
    }

    @Override
    public int compareTo(Train pociag) {
        if (this.name.equals(pociag.getName()))
            return 1;
        return 0;

    }
    public int compareTo(String pociag) {
        if (this.name.equals(pociag))
            return 1;
        return 0;

    }

    @Override
    public String toString() {
        return "Train{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", ilosc_w=" + ilosc_w +
                ", stan=" + stan +
                ", shift_length=" + shift_length +
                ", godzinaPrzyjazdu=" + godzinaOdjazdu +
                ", stacjaDocelowa='" + stacjaDocelowa + '\'' +
                '}';
    }

    public String wypiszDoPliku(){
        return name + ", " + number + ", " + price + ", " + ilosc_w + ", " + shift_length + ", "
                + godzinaOdjazdu + ", " + stacjaDocelowa;
    }

    public void addedSeat(){
        this.number--;
    }
    public void canceledSeat(){
        this.number++;
    }

    public String getName() {
        return name;
    }

    public TrainCondition getStan() {
        return stan;
    }

    public int getShift_length() {
        return shift_length;
    }


    public int getNumber() {
        return number;


    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStan(TrainCondition stan) {
        this.stan = stan;
    }

    public void setShift_length(int shift_length) {
        this.shift_length = shift_length;
    }
}


