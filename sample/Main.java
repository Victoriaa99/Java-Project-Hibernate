package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.jfr.Name;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    static SessionFactory factory;
    static Session session;
    List<TrainH> ticketsList;
    List<TrainH> trainHS;
    List<Rating> ratings;
    ComboBox startStation;
    ComboBox endStation;
    ComboBox timeOfDeparture;
    Label startStationLabel;
    Label endStationLabel;
    Label timeOfDepartureLabel;
    Button searchBtn;
    Button buyBtn;
    Button reserveBtn;
    TableView<TrainH> table;
    ArrayList<TrainStation> trainStations;
    ArrayList<TrainH> trainsList;
    TrainStation warszawaCentralna;
    TrainStation pozanGlowny;
    TrainStation krakowGLowny;
    public void initializeTrainStations(){
        ticketsList = new ArrayList<>();
        trainsList = new ArrayList<>();
        trainStations = new ArrayList<>();

        ratings = mainHibernate.getRatingsFromDB(factory, session);

        trainHS = mainHibernate.getTrainsFromDB(factory, session, ratings);
        //Stacja Krakow Glowny
        krakowGLowny = new TrainStation("Krakow Glowny", 5);

        /*Train k1 = new Train("Intercity", 62, 30, 3,
                TrainCondition.BROKEN, 3, "05:00", "Warszawa Centralna");
        Train k2 = new Train("Pendolino", 48, 40, 4,
                TrainCondition.NEW, 2, "12:00", "Warszawa Centralna");
        Train k3 = new Train("Express Intercity", 58, 35, 4,
                TrainCondition.NEW, 5, "18:00", "Poznań Główny");
        Train k4 = new Train("TLK", 75, 33, 2,
                TrainCondition.NEW, 4, "01:00", "Warszawa Centralna");
        Train k5 = new Train("Express Intercity Premium", 52, 45, 6,
                TrainCondition.NEW, 4, "21:00", "Poznań Główny");
        krakowGLowny.addTrain(k1); //dodajemy pociag k1 do listy
        krakowGLowny.addTrain(k2);
        krakowGLowny.addTrain(k3);
        krakowGLowny.addTrain(k4);
        krakowGLowny.addTrain(k5);*/
        try {
            for(int i = 5; i <= 9; i++){
                krakowGLowny.addTrain(trainHS.get(i));
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coś poszło nie tak...");
            alert.setHeaderText("Uwaga!");
            alert.setContentText("Nie wczytano pliku. Stacja Kraków Główny jest pusta.");
            alert.showAndWait();
        }

        //Stacja Poznan Glowny
        pozanGlowny = new TrainStation("Poznan Glowny", 8);
        /*Train p1 = new Train("Pendolino", 65, 45, 3,
                TrainCondition.NEW, 3, "05:00", "Kraków Główny");
        Train p2 = new Train("Pendolino", 42, 38, 4,
                TrainCondition.NEW, 1, "18:00", "Warszawa Centralna");
        Train p3 = new Train("Intercity", 68, 27, 4,
                TrainCondition.NEW, 3, "21:00", "Warszawa Centralna");
        Train p4 = new Train("TLK", 75, 28, 5,
                TrainCondition.NEW, 6, "10:00", "Kraków Główny");
        Train p5 = new Train("Pendolino", 42, 38, 4,
                TrainCondition.NEW, 1, "01:00", "Warszawa Centralna");
        Train p6 = new Train("Express Intercity", 53, 40 , 3,
                TrainCondition.NEW, 5, "18:00", "Kraków Główny");
        Train p7 = new Train("Express Intercity Premium", 40, 45, 4,
                TrainCondition.NEW, 4, "21:00", "Kraków Główny");
        Train p8 = new Train("TLK", 72, 25, 5,
                TrainCondition.NEW, 4, "05:00", "Warszawa Centralna");
        pozanGlowny.addTrain(p1);
        pozanGlowny.addTrain(p2);
        pozanGlowny.addTrain(p3);
        pozanGlowny.addTrain(p4);
        pozanGlowny.addTrain(p5);
        pozanGlowny.addTrain(p6);
        pozanGlowny.addTrain(p7);
        pozanGlowny.addTrain(p8);*/

        try {
            for(int i = 10; i <= 14; i++) {
                pozanGlowny.addTrain(trainHS.get(i));
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coś poszło nie tak...");
            alert.setHeaderText("Uwaga!");
            alert.setContentText("Nie wczytano pliku. Stacja Poznań Główny jest pusta.");
            alert.showAndWait();
        }

        //Stacja Warszawa Centralna
        warszawaCentralna = new TrainStation("Warszawa Centralna", 6);
        try{
            for(int i = 0; i <= 4; i++) {
                warszawaCentralna.addTrain(trainHS.get(i));
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coś poszło nie tak...");
            alert.setHeaderText("Uwaga!");
            alert.setContentText("Nie wczytano pliku. Stacja Warszawa Centralna jest pusta.");
            alert.showAndWait();
        }


        trainStations.add(krakowGLowny);
        trainStations.add(pozanGlowny);
        trainStations.add(warszawaCentralna);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeTrainStations();
        startStationLabel = new Label("Stacja początkowa");
        endStationLabel = new Label("Stacja końcowa");
        timeOfDepartureLabel = new Label("Czas odjazdu");

        ObservableList<String> options = FXCollections.observableArrayList(
          "Krakow Glowny",
                "Warszawa Centralna",
                "Poznan Glowny"
        );

        ObservableList<String> hours = FXCollections.observableArrayList(
                "dowolny",
                "00:00",
                "01:00",
                "05:00",
                "10:00",
                "12:00",
                "18:00",
                "21:00"
        );
        //dodanie biletow
        ticketsList.addAll(mainHibernate.readTrainTickets(trainHS));
        for(int i = 0; i < ticketsList.size(); i++)
          boughtTicketWindow(ticketsList.get(i));

        //ticketsList.addAll(TrainSerialize.deserialize("tickets.txt"));
        //for(int i = 0; i < ticketsList.size(); i++)
          // boughtTicketWindow(ticketsList.get(i));

        startStation = new ComboBox(options);
        endStation = new ComboBox(options);
        timeOfDeparture = new ComboBox(hours);

        searchBtn = new Button("Wyszukaj");
        searchBtn.setOnAction(e -> searchBtnClicked());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(startStationLabel,startStation,endStationLabel,
                endStation, timeOfDepartureLabel, timeOfDeparture,searchBtn);
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(10);
        primaryStage.setTitle("Rozkład PKP");
        primaryStage.setScene(new Scene(vbox, 300, 275));
        primaryStage.show();
    }
    public void executeNewWindow(String startingStat, String endingStat, String timeOfD){
        TextField szukajNazwy = new TextField();
        Stage stage = new Stage();
        stage.setOnCloseRequest(e -> saveCurrentInfo());
        table = new TableView<>();

        List<String> fieldName = new ArrayList<>();
        List<String> annotationName = new ArrayList<>();
        Field[] fields = TrainH.class.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Name.class)){
                annotationName.add(field.getAnnotation(Name.class).value());
                fieldName.add(field.getName());
            }
        }

        TableColumn<TrainH, String> nameColumn = new TableColumn<>(annotationName.get(0));
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName.get(0)));

        TableColumn<TrainH, Integer> priceColumn = new TableColumn<>(annotationName.get(2));
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName.get(2)));

        TableColumn<TrainH, Integer> numberOfSeatsColumn = new TableColumn<>(annotationName.get(1));
        numberOfSeatsColumn.setMinWidth(100);
        numberOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName.get(1)));

        TableColumn<TrainH, Integer> shiftLengthColumn = new TableColumn<>(annotationName.get(4));
        shiftLengthColumn.setMinWidth(100);
        shiftLengthColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName.get(4)));

        TableColumn<TrainH, String> godzinaOdjColumn = new TableColumn<>(annotationName.get(5));
        godzinaOdjColumn.setMinWidth(100);
        godzinaOdjColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName.get(5)));

        TableColumn<TrainH, Integer> iloscOcenColumn = new TableColumn<>("Ilosc Ocen");
        iloscOcenColumn.setMinWidth(100);
        iloscOcenColumn.setCellValueFactory(new PropertyValueFactory<>("ilosc_ocen"));

        TableColumn<TrainH, Float> sredniaOcenColumn = new TableColumn<>("Srednia Ocen");
        sredniaOcenColumn.setMinWidth(100);
        sredniaOcenColumn.setCellValueFactory(new PropertyValueFactory<>("srednia_ocen"));

        table.getColumns().addAll(nameColumn, priceColumn, numberOfSeatsColumn,
                shiftLengthColumn,godzinaOdjColumn, iloscOcenColumn, sredniaOcenColumn);
        table.setItems(getTrains(startingStat, endingStat, timeOfD));
        table.setRowFactory(tv -> {
            TableRow<TrainH> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){

                    TrainH t = row.getItem();
                    showTrainDescription(t);

                }else if(!row.isEmpty()){

                    TrainH train = row.getItem();
                    String info = train.getName() + " " + train.getShift_length()  +
                            " " + train.getIlosc_w() + " " + train.getNumber();

                    Tooltip tooltip = new Tooltip(info);
                    row.setTooltip(tooltip);
                }
            });
            return row;
        });

        Button searchButton = new Button("Szukaj");
        searchButton.setOnAction(e -> {
            ObservableList<TrainH> tlist = FXCollections.observableArrayList();
            table.getItems().clear();
            if(szukajNazwy.getText().equals("")){
                tlist.addAll(trainsList);
            }
            else {
                for (TrainH t : trainsList) {
                    if (t.getName().equals(szukajNazwy.getText())) {
                        tlist.add(t);

                    }
                }

            }
            table.setItems(tlist);
        });
        buyBtn = new Button("Kup bilet");
        buyBtn.setOnAction(e -> buyBtnClicked());

        reserveBtn = new Button("Zarezerwuj");
        reserveBtn.setOnAction(e -> reserveBtnClicked());

        VBox vboxBtn = new VBox();
        vboxBtn.setPadding(new Insets(10,10,10,10));
        vboxBtn.setSpacing(10);
        vboxBtn.getChildren().addAll(buyBtn, reserveBtn, szukajNazwy, searchButton);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(table, vboxBtn);

        Scene scene = new Scene(hbox);
        stage.setTitle("Lista pasujących pociągów");
        stage.setScene(scene);
        stage.show();
    }

    public void saveCurrentInfo() {
        Label questionLabel = new Label("Czy chcesz zapisać dane do pliku?");
        Button saveBtn = new Button("Zapisz dane");
        Button cancelBtn = new Button("Nie zapisuj");
        Stage stage = new Stage();
        saveBtn.setOnAction(e -> savedBtnClicked(stage));
        cancelBtn.setOnAction(e -> {
            stage.close();
        });

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(questionLabel,saveBtn, cancelBtn);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("Komunikat");
        stage.show();

    }
    private void savedBtnClicked(Stage stage) {
        String startingStat = String.valueOf(startStation.getValue());
        try {
            //zapisywanie biletow
            mainHibernate.saveTrainTickets(factory, session, ticketsList, trainHS);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (startingStat.equals("Warszawa Centralna")) {
            mainHibernate.saveTrainStation(factory, session, trainsList, warszawaCentralna);
        } else if (startingStat.equals(("Krakow Glowny"))) {
            try {
                mainHibernate.saveTrainStation(factory, session, trainsList, krakowGLowny);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                mainHibernate.saveTrainStation(factory, session, trainsList, pozanGlowny);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stage.close();
    }

    private void reserveBtnClicked() {
        int i = table.getSelectionModel().getSelectedIndex();
        if(trainsList.get(i).getStan().equals(TrainCondition.BROKEN)){
            //System.out.println("Pociąg zepsuty");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coś poszło nie tak...");
            alert.setHeaderText("Uwaga!");
            alert.setContentText("Pociąg jest zepsuty");
            alert.showAndWait();
        }
        else if(i >= 0){
            TrainH t = trainsList.get(i);
            reservedTicketWindow(t, i);
        }else{
            System.out.println("Nie wybrano pociągu!");
        }
    }

    private void reservedTicketWindow(TrainH t, int i) {
        Stage stage = new Stage();
        Label reserve = new Label("Zarezerwowano bilet na pociag "+ t.getName());
        Label number = new Label("Liczba dostępnych miejsc "+ t.getNumber());
        Label timeOfD = new Label("Godzina odjazdu "+t.getGodzinaOdjazdu());
        Button reserveBtn = new Button("Kup");
        reserveBtn.setOnAction(e -> boughtTicketWindow(i));
        Button cancelBtn = new Button("Anuluj");
        cancelBtn.setOnAction(e -> {
            stage.close();
        });

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(reserve, number, timeOfD, reserveBtn, cancelBtn);
        Scene scene = new Scene(vBox);
        stage.setTitle("Rezerwacja biletu");
        stage.setScene(scene);
        stage.show();

    }

    private void buyBtnClicked() {
        int i = table.getSelectionModel().getSelectedIndex();
        if(trainsList.get(i).getStan().equals(TrainCondition.BROKEN)){
            //System.out.println("Pociąg zepsuty");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coś poszło nie tak...");
            alert.setHeaderText("Uwaga!");
            alert.setContentText("Pociąg jest zepsuty");
            alert.showAndWait();
        }
        else if(i >= 0){
            boughtTicketWindow(i);

        }else{
            System.out.println("Nie wybrano pociągu!");
        }
    }

    private void boughtTicketWindow(TrainH train) {
        ObservableList<TrainH> trains = FXCollections.observableArrayList();

        Stage stage = new Stage();
        Label reserve = new Label("Kupiono bilet na pociag "+ train.getName());
        Label number = new Label("Liczba dostępnych miejsc "+ train.getNumber());
        Label timeOfD = new Label("Godzina odjazdu "+train.getGodzinaOdjazdu());
        Button cancelBtn = new Button("Anuluj");
        cancelBtn.setOnAction(e -> {
            ticketsList.removeIf(t -> t.getName().equals(train.getName()));
            System.out.println(ticketsList);
            trains.clear();
            stage.close();
            trains.addAll(trainsList);
        });

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(reserve, number, timeOfD, cancelBtn);
        Scene scene = new Scene(vBox);
        stage.setTitle("Kupno biletu");
        stage.setScene(scene);
        stage.show();
    }
    private void boughtTicketWindow(int i) {
        ticketsList.add(trainsList.get(i));

        ObservableList<TrainH> trains = FXCollections.observableArrayList();

        trainsList.get(i).addedSeat();
        trains.addAll(trainsList);
        table.getItems().clear();
        table.setItems(trains);
        Stage stage = new Stage();
        Label reserve = new Label("Kupiono bilet na pociag "+ trainsList.get(i).getName());
        Label number = new Label("Liczba dostępnych miejsc "+ trainsList.get(i).getNumber());
        Label timeOfD = new Label("Godzina odjazdu "+trainsList.get(i).getGodzinaOdjazdu());
        Button cancelBtn = new Button("Anuluj");
        cancelBtn.setOnAction(e -> {
            ticketsList.remove(trainsList.get(i));
            table.getItems().clear();
            trains.clear();
            stage.close();
            trainsList.get(i).canceledSeat();
            trains.addAll(trainsList);
            table.setItems(trains);
        });

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(reserve, number, timeOfD, cancelBtn);
        Scene scene = new Scene(vBox);
        stage.setTitle("Kupno biletu");
        stage.setScene(scene);
        stage.show();
    }

    private void showTrainDescription(TrainH t) {

        Label name = new Label("Nazwa pociągu: "+t.getName());
        Label number = new Label("Liczba miejsc: " +t.getNumber());
        Label price = new Label("Cena biletu: " +t.getPrice());
        Label wagons = new Label("Liczba wagonów: " +t.getIlosc_w());
        Label condition = new Label("Stan pociągu: " +t.getStan());
        Label shift = new Label("Długość trasy: " +t.getShift_length());
        Label timeOfD = new Label("Czas odjazdu: " +t.getGodzinaOdjazdu());
        Label endStat = new Label("Stacja docelowa: " +t.getStacjaDocelowa());

        int trainsID = t.getId();
        List<Rating> trainsRatings = new ArrayList<>();
        for(Rating rating : ratings){
            if(rating.getTrain_id() == trainsID){
                trainsRatings.add(rating);
            }
        }


        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(name, number, price, wagons, condition, shift, timeOfD, endStat);

        for(Rating rating : trainsRatings){
            Label label = new Label(""+rating);
            vBox.getChildren().add(label);
        }
        Scene scene = new Scene(vBox);
        stage.setTitle("Informacje o pociągu "+t.getName());
        stage.setScene(scene);
        stage.show();

        //rating
    }

    public ObservableList<TrainH> getTrains(String startingStat,String endingStat, String timeOfD ){
        trainsList.clear();
        ObservableList<TrainH> trains = FXCollections.observableArrayList();
        for (int i = 0; i < trainStations.size(); i++) {
            if(startingStat.equals(trainStations.get(i).getNazwa_s())){
                for (int j = 0; j < trainStations.get(i).getLista().size(); j++) {
                    if(trainStations.get(i).getLista().get(j).getStacjaDocelowa().equals(endingStat)
                    && (trainStations.get(i).getLista().get(j).getGodzinaOdjazdu().equals(timeOfD)
                            || timeOfD.equals("dowolny")))
                    {
                        trains.add(trainStations.get(i).getLista().get(j));
                        trainsList.add(trainStations.get(i).getLista().get(j));
                    }
                }
            }
        }

        return trains;
    }
    public void searchBtnClicked(){
        String startingStat = String.valueOf(startStation.getValue());
        String endingStat = String.valueOf(endStation.getValue());
        String timeofD = String.valueOf(timeOfDeparture.getValue());


        if(timeofD == null || endingStat == null || startingStat == null){
            System.out.println("Prosze wybrać pola!");
        } else if(startingStat.equals(endingStat)){
            System.out.println("Stacja początkowa nie może być równa końcowej!");
        }else{
            executeNewWindow(startingStat, endingStat, timeofD);

        }


    }

    public static void main(String[] args) {
        factory = new Configuration().addAnnotatedClass(TrainH.class)
                .addAnnotatedClass(Rating.class)
                .configure().buildSessionFactory();

        launch(args);
    }
}
