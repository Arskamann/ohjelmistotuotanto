package softa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MokinLisays extends MokkienHallinta {

    @FXML
    private Button peru;

    @FXML
    private Button tallenna;

    @FXML
    private Button tallennapoistu;

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
}
