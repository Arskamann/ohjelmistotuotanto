package softa;

import java.awt.Button;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {
	private static Stage stg;
	@FXML
	Button nappi;
    public void start(Stage alku) throws IOException {
    	stg=alku;
    	Parent root = FXMLLoader.load(getClass().getResource("Ikkuna.fxml"));
    
    	alku.setTitle("Hieno softa");
    	alku.setScene(new Scene(root,300,300));
    	alku.show();
    }
    
    public void uusiVaraus(ActionEvent event) throws IOException {
		changeScene("Uusivaraus.fxml");
		
		
		
	}
    
    
    
    
    
    
    
    public void changeScene(String fxml) throws IOException {                      // tällä vaihdetaan ikkuna esim napin painnalluksella
    	Parent pane = FXMLLoader.load((getClass().getResource(fxml)));
    	stg.getScene().setRoot(pane);
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}

