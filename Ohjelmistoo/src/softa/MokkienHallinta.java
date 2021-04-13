package softa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MokkienHallinta extends Menu {
	
	@FXML
	Button takaisin;
	
	public void menu(ActionEvent event) throws IOException
	{
        changeScene("Menu.fxml");
    }

    public void hakuMokeista () {
        System.out.println("Tästä haetaan kaikista mökeistä");
    }
    
    public void lisaaMokki () {
        System.out.println("Tästä pääsee lisäämään mökin");
    }
    
    public void muutaMokki () {
        System.out.println("Tästä muutetaan mökin tietoja");
    }
    
    public void poistaMokki () {
        System.out.println("Tästä mökki poistuu");
    }
}
