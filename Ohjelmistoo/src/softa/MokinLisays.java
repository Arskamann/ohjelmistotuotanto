package softa;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

public class MokinLisays extends MokkienHallinta {

	//uuden mökin napit, listat ja kentät
	@FXML
	private Pane uusiMokki;
	@FXML
	private TextField mokkiID1;
	    @FXML
	    private TextField mokkinimi1;
	    @FXML
	    private TextField alue1;
	    @FXML
	    private TextField osoite1;
	    @FXML
	    private TextField postinro1;
	    @FXML
	    private TextField hlo1;
	    @FXML
	    private TextField hinta1;
	    @FXML
	    private TextField kuvaus1;
	    @FXML
	    private Label id1;
	    @FXML
	    private ChoiceBox<String> toimialue1;
	    @FXML
	    private ChoiceBox<String> varustelu1;
	    @FXML
	    private Button tallenna1;
	    
	  //muuttujat mökin id:n ja toimipaikan id:n poimimista varten sekä postinumeron tarkistusta varten
	    static int iddd=1;
	    static int paikanID=1;
	    static boolean uusiposti=true;
		
	    //choicebox-listat (mökkien haku, mökin lisäys, mökin muokkaus)
	    @FXML
		private void initialize() throws SQLException {
	    	toimialue1.getItems().clear();
			Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
			
			PreparedStatement preparedStatement=Menu.connection.prepareStatement(
					"select nimi from toimintaalue order by nimi asc");
		     
			ResultSet resultSet=preparedStatement.executeQuery();
			String ensin = new String("Valitse toiminta-alue");

			toimialue1.getItems().add(ensin);
			
			while(resultSet.next()){
		        String nimi=resultSet.getString("nimi");
		        String t = new String(nimi);
		        toimialue1.getItems().add(t);

		        toimialue1.setValue("Valitse toiminta-alue");
		       	}

			varustelu1.setValue("Valitse varustelutaso");
			varustelu1.setItems(varustelut);
		}

	    
	  //lista varusteluista
	    ObservableList<String> varustelut = FXCollections.
				observableArrayList("Valitse varustelutaso", "Normaali", "Hyvä", "Luksus");
	    
	//takaisin mökkihakuun painamalla 'peruuta'
    public void takaisinMokkeihin(ActionEvent event) throws IOException
    {
        changeScene("MokkienHallinta.fxml");
    }

  //uuden mökin luominen
  	public void luoUusi() throws SQLException, IOException {
  		//tarkistetaan, onko kaikki kentät täytetty
  		if (validoi()) {
  			try {
  				uusiposti=true;
  				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
  				System.out.println("Tiedot saatu!");
  						
  				String mokkinimi=mokkinimi1.getText();
  				String os=osoite1.getText();
  				String nro=postinro1.getText();
  				String kaupunki=alue1.getText();
  				String henkilot=hlo1.getText();
  				String varusteet=varustelu1.getValue();
  				String eurot=hinta1.getText();
  				String teksti=kuvaus1.getText();
  					
  				PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM posti");
  				preparedStatement.executeQuery();
  					
  				ResultSet resultSet=preparedStatement.executeQuery();
  				
  				//tarkistetaan, onko postinumero jo kannassa
  				while(resultSet.next()){
  					String num =resultSet.getString("postinro");
  					if(num.equals(nro)) {
  						uusiposti=false;
  						}
  					}
  				
  				//jos postinumero ei ole kannassa, lisätään se
  				if(uusiposti==true) {
  					PreparedStatement preparedStatement2=connection.prepareStatement(
  							"insert into posti set postinro ='"+nro+"',toimipaikka= '"+kaupunki+"'");
  					preparedStatement2.executeUpdate();
  						
  					Alert b = new Alert(AlertType.INFORMATION);
  					b.setContentText("Postitietoja lisätty tietokantaan!");
  					b.setTitle("Huomio");
  					b.setHeaderText("Huomio");
  					b.show();
  					}
  				
  				PreparedStatement preparedStatement3=connection.prepareStatement(
  						"insert into mokki set mokkinimi='"+mokkinimi+"', katuosoite='"+os+"', postinro='"+nro+"', kuvaus='"
  						+teksti+"', henkilomaara='"+henkilot+"', varustelu='"+varusteet+"', hinta='"+eurot+"'");
  					
  				preparedStatement3.executeUpdate();
  					
  				String valittuToimipaikka = toimialue1.getValue();
  				String toimialue_id;
  					
  				PreparedStatement preparedStatement4=connection.prepareStatement(
  						"Select toimintaalue_id from toimintaalue where nimi="+"'"+valittuToimipaikka+"'");
  				    	      
  				ResultSet toimialue_idResult=preparedStatement4.executeQuery();
  				    	        
  				toimialue_idResult.next();
  				toimialue_id = toimialue_idResult.getString("toimintaalue_id");
  				    	        
  				PreparedStatement preparedStatement5 = connection.prepareStatement(
  				    	"Update mokki set toimintaalue_id='"+toimialue_id+"' where mokkinimi='"+mokkinimi+"'");
  				preparedStatement5.executeUpdate();
  				    
  				validoi();
  				tallenna1.setText("Tallennettu");
  				tallenna1.setStyle("-fx-background-color: #00ff00");
  					
  				Alert a = new Alert(AlertType.INFORMATION);
  				a.setContentText("Uusi mökki luotu!");
  				a.setHeaderText("Huomio");
  				a.setTitle("Huomio");
  				a.show();
  					 
  				changeScene("MokkienHallinta.fxml");
  					 
  				 } catch (Exception e) {
  					 Alert a = new Alert(AlertType.INFORMATION);
  					 a.setContentText("Virhe!");
  					 a.setHeaderText("Huomio");
  					 a.setTitle("Huomio");
  					 a.show();
  					 }
  				 }
  			}
  	
  	//metodi tarkistamaan, että kaikki kentät on täytetty uutta mökkiä luodessa
  	public boolean validoi() {
  		StringBuilder virheet = new StringBuilder();
  		
  		if (mokkinimi1.getText().trim().isEmpty()) {
  			virheet.append("Syötä mökin nimi.\n");
  	        }
  	    if (toimialue1.getValue().trim().equals("Valitse toiminta-alue")) {
  	        virheet.append("Valitse toimialue.\n");
  	        }
  	    if (osoite1.getText().trim().isEmpty()) {
  	    	virheet.append("Syötä osoite.\n");
  	        }
  	    if (postinro1.getText().trim().isEmpty()) {
  	        virheet.append("Syötä postinumero.\n");
  	        }
  	    if (alue1.getText().trim().isEmpty()) {
  	        virheet.append("Syötä toimipaikka.\n");
  	        }
  	    if (hlo1.getText().trim().isEmpty()) {
  	        virheet.append("Syötä henkilöiden määrä.\n");
  	        }
  	    if (varustelu1.getValue().trim().equals("Valitse varustelutaso")) {
  	        virheet.append("Valitse varustelu.\n");
  	        }
  	    if (hinta1.getText().trim().isEmpty()) {
  	        virheet.append("Syötä hinta/yö.\n");
  	        }
  	    if (kuvaus1.getText().trim().isEmpty()) {
  	        virheet.append("Syötä kuvaus mökistä.\n");
  	        }

  	    if (virheet.length() > 0) {
  	    	Alert alert = new Alert(Alert.AlertType.WARNING);
  	        alert.setTitle("Huomio");
  	        alert.setHeaderText("Tyhjiä kenttiä");
  	        alert.setContentText(virheet.toString());

  	        alert.showAndWait();
  	        return false;
  	        }
  	    
  	    // ei virheitä
  	    return true;
  		}

    
    }



