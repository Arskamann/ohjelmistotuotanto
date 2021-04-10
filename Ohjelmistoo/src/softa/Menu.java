package softa;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {

    public void start(Stage alku) {
    	  alku.setTitle("ListView Experiment 1");
    	  Text x = new Text("");
          ListView<String> listView = new ListView<String>();
          Pane ff = new Pane();
          ff.getChildren().add(x);
         
          listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

          listView.getItems().add("Uusi varaus");
          listView.getItems().add("Varaukset ja muokkaus");
          listView.getItems().add("Mökit");
          listView.getItems().add("Toiminta-alueet");
          listView.getItems().add("Laskutus");
          listView.setPrefSize(100, 150);
        
          
          listView.setOnMouseReleased((EventHandler<? super MouseEvent>) new EventHandler<Event>() { // tässä avataan sit eri näkymät ??? esimerkki vasta...

              public void handle(Event event) {
                  		x.setText("Valittuina: "+listView.getSelectionModel().getSelectedItems().toString().replace("]", "").replace("[",""));
             
                  

              }

          });

         BorderPane b = new BorderPane();
         b.setTop(listView);
         b.setCenter(ff);
         
     
     //testii wtf!!
        

        Scene kehys = new Scene(b,300,330);
        alku.setTitle("Mökkivaraus softa !!!");
        alku.setScene(kehys);
        alku.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}

