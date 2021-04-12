package softa;


import java.awt.Button;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class Uusivaraus extends Menu {
	
	@FXML
	Button takas;
	
	public void menu(ActionEvent event) throws IOException { //t‰ll‰  toiminnolla p‰‰see takasin p‰‰valikkoon. T‰t‰ kutsutaan uusivaraus.xml tiedostossa
		changeScene("Ikkuna.fxml");
		
		
		
	}
	
	
	
}
