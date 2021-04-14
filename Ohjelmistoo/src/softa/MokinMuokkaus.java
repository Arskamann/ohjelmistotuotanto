package softa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MokinMuokkaus extends MokkienHallinta {

	    @FXML
	    private Button peru;

	    @FXML
	    private Button tallenna;

	    @FXML
	    private Button tallennapoistu;

	    @FXML
	    private Button poistamokki;


public void takaisinMokkiin(ActionEvent event) throws IOException
{
    changeScene("MokkienHallinta.fxml");
}

public void tallenna() {
    System.out.println("Tästä pääsee tallentamaan");
}

public void tallennaJaPoistu () {
    System.out.println("Tästä tiedot tallentuvat ja palautuu edelliseen");
}

public void poistaMokki () {
    System.out.println("Tästä mökin pääsee poistamaan");
}
}