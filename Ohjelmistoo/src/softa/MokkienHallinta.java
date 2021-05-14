package softa;

import java.io.IOException;
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

public class MokkienHallinta extends Menu {
	
	//ensimmäisen näkymän tarpeelliset napit, listat ja kentät
	@FXML
	Button lisaaMokki;
	@FXML
	private ChoiceBox<String> toimipaikkalistaus;
	@FXML
	private ListView<Button> lista;
	@FXML
	private TextField hakukentta;
	@FXML
	private Pane list;
	@FXML
	private Label eituloksia;	
	
	//mökin tietojen muokkauksen napit, listat ja kentät
	@FXML
	private Pane hakutulos;
	@FXML
	private TextField mokkiID;
	@FXML
    private TextField mokinnimi;
    @FXML
    private TextField alue;
    @FXML
    private TextField osoite;
    @FXML
    private TextField postinro;
    @FXML
    private TextField toim;
    @FXML
    private TextField hlo;
    @FXML
    private ChoiceBox<String> varustelu;
    @FXML
    private TextArea kuvaus;
    @FXML
    private Label id;
    @FXML
    private ChoiceBox<String> toimialue;
	@FXML
	private TextField hinta;
	@FXML
	private ListView<Text> tulevatVaraukset;
	@FXML
	private Button tallenna;
	
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
	
    //choicebox-listat (eri näkymiin: mökkien haku, mökin lisäys, mökin muokkaus)
    @FXML
	private void initialize() throws SQLException {
    	toimipaikkalistaus.getItems().clear();
		Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
		System.out.println("Tiedot saatu!");
		
		PreparedStatement preparedStatement=Menu.connection.prepareStatement(
				"select nimi from toimintaalue order by nimi asc");
	     
		ResultSet resultSet=preparedStatement.executeQuery();
		String ensin = new String("Valitse toiminta-alue");
		toimipaikkalistaus.getItems().add(ensin);
		
		while(resultSet.next()){
	        String nimi=resultSet.getString("nimi");
	        String t = new String(nimi);
	        toimipaikkalistaus.getItems().add(t);

	        toimipaikkalistaus.setValue("Valitse toiminta-alue");
	       	}
		
		toimialue.getItems().clear();
	     
		ResultSet resultSet2=preparedStatement.executeQuery();
		toimialue.getItems().add(ensin);
		
		while(resultSet2.next()){
	        String nimi=resultSet2.getString("nimi");
	        String t = new String(nimi);
	        toimialue.getItems().add(t);

	        toimialue.setValue("Valitse toiminta-alue");
	       	}
		
		toimialue1.getItems().clear();
	     
		ResultSet resultSet3=preparedStatement.executeQuery();
		toimialue1.getItems().add(ensin);
		
		while(resultSet3.next()){
	        String nimi=resultSet3.getString("nimi");
	        String t = new String(nimi);
	        toimialue1.getItems().add(t);

	        toimialue1.setValue("Valitse toiminta-alue");
	       	}
		
		varustelu.setValue("Valitse varustelutaso");
		varustelu.setItems(varustelut);
		varustelu1.setValue("Valitse varustelutaso");
		varustelu1.setItems(varustelut);
	}
    
    
	//takaisin menuun painamalla 'takaisin'
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        }
	
	//takaisin kaikkiin mökkeihin yksittäisestä mökistä
	public void takaisinMokkeihin (ActionEvent event) throws IOException {
		changeScene("MokkienHallinta.fxml");
		}
	
	//uuden mökin lisäysnäkymä
	public void mokinLisays() throws SQLException {
		uusiMokki.setVisible(true);
		}
	
	// kaikkien mökkien näkyminen listassa painamalla 'näytä kaikki'
	public void listapaivitys() {
    	try {
    		lista.getItems().clear();
    		eituloksia.setVisible(false);
    		  
    		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    		System.out.println("Tiedot saatu!");
    		PreparedStatement preparedStatement=connection.prepareStatement(
    				"Select m.mokki_id, m.mokkinimi, t.nimi from mokki m join toimintaalue t using(toimintaalue_id);");
    	      
    	    ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	    while(resultSet.next()){
    	    	String nimi=resultSet.getString("mokkinimi");
    	        String alue=resultSet.getString("nimi");
    	        String id=resultSet.getString("mokki_id");
    	        
    	        Button btn=new Button(nimi+" "+"(" + alue +")");
    	        btn.setAccessibleText(id);               //  napin id:n poimiminen
    	            
    	        btn.setOnAction((event) -> {
    	        	System.out.println(btn.getText());
  	                String sisalto=btn.getText();
  	                String[] sisaltoosissa= sisalto.split(" ");
  	                
  	                iddd=Integer.parseInt(btn.getAccessibleText()); // mökin id:n poimiminen
  	                hakutulos.setVisible(true);
  	                
					try {
						paivita();
					} catch (SQLException e) {
						e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
    	            });
    	        lista.getItems().add(btn);
    	            }
    	    } catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	}

	
    //lista varusteluista
    ObservableList<String> varustelut = FXCollections.
			observableArrayList("Valitse varustelutaso", "Normaali", "Hyvä", "Luksus");
    
	
	// mökkien näyttäminen toimipaikoittain
	public void hakuToimipaikoilla() {
		String valittuToimipaikka = toimipaikkalistaus.getValue();
		String toimialue_id;
		System.out.println(valittuToimipaikka);
			
	    try {
	    	lista.getItems().clear();
	    		  
	    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
	    	System.out.println("Tiedot saatu!");
	    	PreparedStatement preparedStatement=connection.prepareStatement(
	    			"Select toimintaalue_id from toimintaalue where nimi="+"'"+valittuToimipaikka+"'");
	    	      
	    	ResultSet toimialue_idResult=preparedStatement.executeQuery();
    
	    	toimialue_idResult.next();
	    	toimialue_id = toimialue_idResult.getString("toimintaalue_id");

	    	preparedStatement = connection.prepareStatement(
	    			"Select * from mokki where toimintaalue_id = "+"'"+toimialue_id+"'");
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if(!resultSet.isBeforeFirst()) {
	    		System.out.println("Ei tuloksia");
	    		eituloksia.setVisible(true);
	    		eituloksia.setText("Ei hakutuloksia");
	    		} else {
	    			while(resultSet.next()){
	    				eituloksia.setVisible(false);
    		    		String nimi=resultSet.getString("mokkinimi");
    		    	    String id=resultSet.getString("mokki_id");
    		    	    Button btn=new Button(nimi+" "+"(" + id +")");

    		    	    btn.setAccessibleText(id);               //  napin id:n poimiminen    	             
    		    	    btn.setOnAction((event) -> {
    		    	    	System.out.println(btn.getText());
    		    	  	    iddd=Integer.parseInt(btn.getAccessibleText()); // mökin id:n poimiminen
    		    	  	    hakutulos.setVisible(true);
    		    	  	    
    		    	  	    try {
    		    	  	    	paivita();
    		    	  	    	} catch (SQLException e) {
    		    	  	    		e.printStackTrace();
    		    	  	    		} catch (ParseException e) {
    		    	  	    			e.printStackTrace();
    		    	  	    			}
    		    	  	    });
    		    	    lista.getItems().add(btn);
    		    	    }
	    			}
	    	} catch (SQLException e) {
	    		System.out.println("Error while connecting to the database");
	    		}
	    }
	
		
	// mökkien haku nimellä tai id:llä
    public void hakuMokeista () {
    	String hakutext=hakukentta.getText();
    	System.out.println(hakutext);
    	lista.getItems().clear();
    	
    	try {
    		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement(
 					"Select distinct * from mokki m, toimintaalue t where mokkinimi like "+"'%"+hakutext+"%'"
 					+ " and m.toimintaalue_id=t.toimintaalue_id"
 					+ " or m.mokki_id="+"'"+hakutext+"'"
 					+ " and m.toimintaalue_id=t.toimintaalue_id");							
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	        
 	       if(!resultSet.isBeforeFirst()) {
	    		System.out.println("Ei tuloksia");
	    		eituloksia.setVisible(true);
	    		eituloksia.setText("Ei hakutuloksia");
	    		} else {
	    			while(resultSet.next()){
	    				eituloksia.setVisible(false);
	     	        	String id=resultSet.getString("mokki_id");
	    	            String nimi=resultSet.getString("mokkinimi");
	    	            
	    	            Button btn=new Button(nimi+" "+"(" + id +")");
	    	            btn.setAccessibleText(id);               //  mökin id talteen
	    	            
	    	            btn.setOnAction((event) -> {
	    	            	System.out.println(btn.getText());
	                        String sisalto=btn.getText();
	                        String[] sisaltoosissa= sisalto.split(" ");
	                       
	                        iddd=Integer.parseInt(btn.getAccessibleText()); // poimitaan id
	                       
	                        list.setVisible(false);
	                        hakutulos.setVisible(true);
	                       
	    					try {
	    						paivita();
	    						} catch (SQLException e) {
	    						e.printStackTrace();
	    						} catch (ParseException e) {
	    							e.printStackTrace();
	    						}
	    					});
	    	            lista.getItems().add(btn);
	    	            }
	    			}
 	       } catch (SQLException e) {
 	    	   System.out.println("Error while connecting to the database");
 				}
    	}
    
    
	//yksittäisen mökin tarkempien tietojen hakeminen kannasta näkyviin
	public void paivita() throws SQLException, ParseException{
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
			
		PreparedStatement preparedStatement=connection.prepareStatement(
				"Select * from mokki m join toimintaalue t using(toimintaalue_id) where mokki_id="+iddd);
		      
		ResultSet resultSet=preparedStatement.executeQuery();
		String postinumero=null;

		while(resultSet.next()){
			mokkiID.setText(resultSet.getString("mokki_id"));
		    mokinnimi.setText(resultSet.getString("mokkinimi"));
		    toimialue.setValue(resultSet.getString("nimi"));
		    osoite.setText(resultSet.getString("katuosoite"));
		    postinro.setText(resultSet.getString("postinro"));
		    kuvaus.setText(resultSet.getString("kuvaus"));
		    hlo.setText(resultSet.getString("henkilomaara"));
		    varustelu.setValue(resultSet.getString("varustelu"));
	        hinta.setText(resultSet.getString("hinta"));
	        
	        postinumero=resultSet.getString("postinro");
	        
		    }			
			
		PreparedStatement preparedStatement2=connection.prepareStatement(
				"Select postinro, toimipaikka from posti where postinro= '"+postinumero+"'");
		ResultSet resultSet2=preparedStatement2.executeQuery();
		
		while(resultSet2.next()){
			toim.setText(resultSet2.getString("toimipaikka"));
			}
		
		PreparedStatement preparedStatement3=connection.prepareStatement(
				"Select * from varaus join asiakas using(asiakas_id) where mokki_mokki_id='"+iddd+"'");							
        
        ResultSet resultSet3=preparedStatement3.executeQuery(); //mökin tulevat varaukset haetaan erikseen
        
        while(resultSet3.next()){
        	DateFormat formatoi = new SimpleDateFormat("dd.MM.yyyy");
        	
        	Date alku=resultSet3.getDate("varattu_alkupvm");
        	Date loppu=resultSet3.getDate("varattu_loppupvm");
        	String etunimi=resultSet3.getString("etunimi");
        	String sukunimi=resultSet3.getString("sukunimi");
        	
        	String alkuPVM = formatoi.format(alku); //kannasta otetun päivämäärän muotoilu
            String loppuPVM =formatoi.format(loppu);
        	
        	Text varattu=new Text(alkuPVM+"-"+loppuPVM+" "+"("+etunimi+" "+sukunimi+")");
        	
        	tulevatVaraukset.getItems().add(varattu);
        	
        	}
		
		}
	
	//muutosten tallentaminen olemassaolevaan mökkiin
	public void tallenna() throws SQLException, IOException {
		if (validoiTallentaessa()==true) {
			//tarkistetaan, onko postinumero ja toimipaikka jo kannassa
			try {
				uusiposti=true;
				System.out.println(iddd);
			    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
				System.out.println("Tiedot saatu!");
					
				String mokkinimi=mokinnimi.getText();
				String os=osoite.getText();
				String nro=postinro.getText();
				String kaupunki=toim.getText();
				String henkilot=hlo.getText();
				String varusteet=varustelu.getValue();
				String eurot=hinta.getText();
				String teksti=kuvaus.getText();
				
				PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM posti");
				preparedStatement.executeQuery();
				
				ResultSet resultSet=preparedStatement.executeQuery();
				
				while(resultSet.next()){
					String num =resultSet.getString("postinro");
					if(num.equals(nro)) {
						uusiposti=false;
						}
					}
				
				//jos postinumeroa ja toimipaikkaa ei ole, lisätään ne kantaan
				if(uusiposti==true) {
					PreparedStatement preparedStatement2=connection.prepareStatement(
							"insert into posti set postinro ='"+nro+"',toimipaikka= '"+kaupunki+"'");
					preparedStatement2.executeUpdate();
					
					Alert b = new Alert(AlertType.INFORMATION);
					b.setContentText("Postitietoja lisätty tietokantaan!");
					b.setTitle("Huomio");
					b.setHeaderText("Huomio:");
					b.show();
					}
				 
				PreparedStatement preparedStatement3=connection.prepareStatement(
						"update mokki set mokkinimi ='"+mokkinimi+"', katuosoite='"+os+"', postinro='"+nro+"', kuvaus='"+teksti
						+"', henkilomaara='"+henkilot+"', varustelu='"+varusteet+"', hinta='"+eurot
						+"' where mokki_id="+iddd);
				
				preparedStatement3.executeUpdate();
				
				String valittuToimipaikka = toimialue.getValue();
				String toimialue_id;
				
				PreparedStatement preparedStatement4=connection.prepareStatement(
						"Select toimintaalue_id from toimintaalue where nimi="+"'"+valittuToimipaikka+"'");
			    	      
			    ResultSet toimialue_idResult=preparedStatement4.executeQuery();
			    	        
			    toimialue_idResult.next();
			    toimialue_id = toimialue_idResult.getString("toimintaalue_id");
			    	        
			    PreparedStatement preparedStatement5 = connection.prepareStatement(
			    		"Update mokki set toimintaalue_id='"+toimialue_id+"' where mokki_id="+iddd);
			    preparedStatement5.executeUpdate();
			    
				tallenna.setText("Tallennettu");
				tallenna.setStyle("-fx-background-color: #00ff00");
				
				} catch (Exception e) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setContentText("Virhe!");
					a.setHeaderText("Huomio:");
					a.setTitle("Huomio");
					a.show();
					}
			}
		}
			
	//tallentaminen ja poistuminen saman tien takaisin mökkienhallintanäkymään
	public void tallennaJaPoistu() throws SQLException, IOException {
		if (validoiTallentaessa()==true) {
			try {
				tallenna();
				changeScene("MokkienHallinta.fxml");
				} catch (Exception e) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setContentText("Virhe!");
					a.setHeaderText("Huomio:");
					a.setTitle("Huomio");
					a.show();
					}
			}
		}
	
	//varmistus ennen mökin poistamista
	@FXML
	private void varmistus(ActionEvent event) throws IOException {
		
		Alert.AlertType type = Alert.AlertType.CONFIRMATION;
		Alert alert = new Alert(type, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.getDialogPane().setContentText("Haluatko varmasti poistaa mökin?");
		alert.getDialogPane().setHeaderText("Huomio");
		((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Poista mökki");
		((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Peruuta");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			poistaMokki();
		} else if(result.get() == ButtonType.CANCEL) {
			System.out.println("Peruttu");
		}
		
	}
	
	//mökin poistaminen
	public void poistaMokki() throws IOException {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			
			PreparedStatement preparedStatement=connection.prepareStatement(
					"Delete from mokki where mokki_id="+iddd);
			preparedStatement.executeUpdate();

			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Mökki on poistettu");
			a.setHeaderText("Huomio");
			a.setTitle("Huomio");
			a.show();
			
			changeScene("MokkienHallinta.fxml");
			listapaivitys();
		
			//huomautus: mökkiä ei voi poistaa, jos siinä on voimassaolevia varauksia
			} catch(Exception e) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setContentText("Mökkiin on kiinnitetty varauksia! Poista varaukset ensin.");
				a.setTitle("Huomio");
				a.setHeaderText("Huomio");
				a.show();
				}
		}

	//uuden mökin luominen
	public void luoUusi() throws SQLException, IOException {
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
					
				while(resultSet.next()){
					String num =resultSet.getString("postinro");
					if(num.equals(nro)) {
						uusiposti=false;
						}
					}
				
				//jos postinumero ja toimipaikka eivät ole jo kannassa, lisätään ne
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
	
	//tarkistetaan että kaikki kentät on täytetty uutta mökkiä luodessa
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
		
	//tarkistetaan että kaikki kentät on täytetty tallentaessa
	public boolean validoiTallentaessa() {
		StringBuilder virheet = new StringBuilder();

		if (mokinnimi.getText().trim().isEmpty()) {
			virheet.append("Syötä mökin nimi.\n");
			}
		if (toimialue.getValue().equals("Valitse toiminta-alue")) {
			virheet.append("Valitse toimialue.\n");
			}
		if (osoite.getText().trim().isEmpty()) {
			virheet.append("Syötä osoite.\n");
			}
		if (postinro.getText().trim().isEmpty()) {
			virheet.append("Syötä postinumero.\n");
			}
		if (toim.getText().trim().isEmpty()) {
			virheet.append("Syötä toimipaikka.\n");
			}
		if (hlo.getText().trim().isEmpty()) {
			virheet.append("Syötä henkilöiden määrä.\n");
			}
		if (varustelu.getValue().equals("Valitse varustelutaso")) {
			virheet.append("Valitse varustelu.\n");
			}
		if (hinta.getText().trim().isEmpty()) {
			virheet.append("Syötä hinta/yö.\n");
			}
		if (kuvaus.getText().trim().isEmpty()) {
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
