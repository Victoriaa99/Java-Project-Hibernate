package sample;

import jdk.jfr.Name;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "train")
public class TrainH implements Comparable<TrainH>, Serializable {
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

    @Column
    private int ilosc_ocen;

    @Column
    private float srednia_ocen;

    public int getId() {
        return id;
    }

    public TrainH(String name, int number, int price, int ilosc_w, int shift_length, String godzinaOdjazdu, String stacjaDocelowa, int rating) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.ilosc_w = ilosc_w;
        this.shift_length = shift_length;
        this.godzinaOdjazdu = godzinaOdjazdu;
        this.stacjaDocelowa = stacjaDocelowa;
        this.rating = rating;
    }

    public TrainH(String name, int number, int price, int ilosc_w, int shift_length, String godzinaOdjazdu, String stacjaDocelowa, int rating, int ilosc_ocen, int srednia_ocen) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.ilosc_w = ilosc_w;
        this.shift_length = shift_length;
        this.godzinaOdjazdu = godzinaOdjazdu;
        this.stacjaDocelowa = stacjaDocelowa;
        this.rating = rating;
        this.ilosc_ocen = ilosc_ocen;
        this.srednia_ocen = srednia_ocen;
    }

    public int getIlosc_ocen() {
        return ilosc_ocen;
    }

    public float getSrednia_ocen() {
        return srednia_ocen;
    }

    public void setIlosc_ocen(int ilosc_ocen) {
        this.ilosc_ocen = ilosc_ocen;
    }

    public void setSrednia_ocen(float srednia_ocen) {
        this.srednia_ocen = srednia_ocen;
    }

    public int getRating() {
        return rating;
    }

    public String getGodzinaOdjazdu() {
        return godzinaOdjazdu;
    }

    public String getStacjaDocelowa() {
        return stacjaDocelowa;
    }


    public int getPrice() {
        return price;
    }

    public int getIlosc_w() {
        return ilosc_w;
    }

    public TrainH(){

    }

    public void wypisz(){
        System.out.println("Nazwa pociagu: " + this.name);
        System.out.println("Ilosc miejsc: " + this.number);
        System.out.println("Cena biletu: " + this.price);
        System.out.println("Ilosc wagonow: "+ this.ilosc_w);
        System.out.println("Dlugosc zmiany: " + this.shift_length);
    }

    @Override
    public int compareTo(TrainH pociag) {
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


    public int getShift_length() {
        return shift_length;
    }


    public int getNumber() {
        return number;


    }

    public void setNumber(int number) {
        this.number = number;
    }


    public void setShift_length(int shift_length) {
        this.shift_length = shift_length;
    }

    public static TrainCondition getStan(){
        return TrainCondition.NEW;
    }
}


