package softa;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class Uusivaraus extends Menu {
	
	@FXML
	private Button takas;
	
	public void menu(ActionEvent event) throws IOException { //t‰ll‰  toiminnolla p‰‰see takasin p‰‰valikkoon. T‰t‰ kutsutaan uusivaraus.xml tiedostossa
		changeScene("Menu.fxml");

	}
	
	
	
}
