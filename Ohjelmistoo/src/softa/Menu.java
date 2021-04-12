package softa;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Menu extends Application {
	private static Stage stg;
	@FXML
	Button nappi;
    public void start(Stage alku) throws IOException {
    	stg=alku;
    	Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
    
    	alku.setTitle("Hieno softa");
    	alku.setScene(new Scene(root,600,400));
    	alku.show();
    }
    
    public void uusiVaraus(ActionEvent event) throws IOException {
		changeScene("Uusivaraus.fxml");
	}
	public void varaustenHallinta(ActionEvent event) throws IOException {
		changeScene("VaraustenHallinta.fxml");
	}

    
    
    
    
    
    
    
    
    public void changeScene(String fxml) throws IOException {                      // tällä vaihdetaan ikkuna esim napin painnalluksella
    	Parent pane = FXMLLoader.load((getClass().getResource(fxml)));
    	stg.getScene().setRoot(pane);
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}

