package softa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MokkienHallinta extends Menu {
	
	@FXML
	Button takaisin;
	
	@FXML
	Button muutaMokki;
	
	@FXML
	Button haku;
	
	@FXML
	Button lisaaMokki;
	
	public void menu(ActionEvent event) throws IOException
	{
        changeScene("Menu.fxml");
    }
	
	public void muutaMokki(ActionEvent event) throws IOException
	{
        changeScene("MokinMuokkaus.fxml");
    }

    public void hakuMokeista () {
        System.out.println("Tästä haetaan kaikista mökeistä");
    }
    
    public void lisaaMokki (ActionEvent event) throws IOException
    {
        changeScene("MokinLisays.fxml");
    }
    
    public void muutaMokki () {
        System.out.println("Tästä muutetaan mökin tietoja");
    }
    
}
