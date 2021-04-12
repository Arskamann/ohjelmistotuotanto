package softa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class VaraustenHallinta extends Menu {

    @FXML
    Button takas;

    public void menu(ActionEvent event) throws IOException { //tällä  toiminnolla pääsee takasin päävalikkoon. Tätä kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
    }

    public void testiMetodi () {
        System.out.println("Nabbeli toimii");
    }
}
