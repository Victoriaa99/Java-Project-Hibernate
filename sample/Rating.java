package sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @Column
    int id;

    @Column
    private int ocena;

    @Column
    private String data;

    @Column
    private int train_id;

    @Override
    public String toString() {
        return "Ocena: "+ocena+"/5"+ "\n"+
                "Data: "+data;
    }

    public int getId() {
        return id;
    }

    public int getOcena() {
        return ocena;
    }

    public String getData() {
        return data;
    }

    public int getTrain_id() {
        return train_id;
    }
}
