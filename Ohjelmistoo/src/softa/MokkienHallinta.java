package softa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MokkienHallinta extends Menu {
	
	//ensimmäisen näkymät napit ja listat
	@FXML
	Button takaisin;
	@FXML
	Button haku;
	@FXML
	Button lisaaMokki;
	@FXML
	private ChoiceBox<String> toimipaikkalistaus;
	@FXML
	private ListView<Button> lista;
	@FXML
	private TextField hakukentta;
	@FXML
    private Button paivita;
	
	//mökin tietojen muokkauksen napit
	@FXML
	private Pane hakutulos;
	@FXML
	private TextField mokkiID;
	@FXML
    private TextField mokkinimi;
    @FXML
    private TextField alue;
    @FXML
    private TextField osoite;
    @FXML
    private TextField postinro;
    @FXML
    private TextField toimipaikka;
    @FXML
    private TextField hlo;
    @FXML
    private ChoiceBox<String> varustelu;
    @FXML
    private TextField kuvaus;
    @FXML
    private Label id;
    @FXML
    private Button uusivaraus;
    @FXML
    private ChoiceBox<String> toimialue;
	@FXML
	private TextField hinta;
	@FXML
	private Button poista;
	@FXML
	private Button tallenna;
	@FXML
	private Button tallennaJaPoistu;
	@FXML
	private ListView<Button> tulevatVaraukset;
	@FXML
	private Button back;
	
	//uuden mökin napit
	@FXML
    private Pane uusiMokki;
	@FXML
	private Button lisaaAlue1;
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
    private TextField kuvaus1;
    @FXML
    private Label id1;
    @FXML
    private Button tallenna1;
    @FXML
    private ListView<?> tulevatVaraukset1;
    @FXML
    private Button lisaaVaraus1;
    @FXML
    private Button tallennaJaPoistu1;
    @FXML
    private ChoiceBox<String> toimialue1;
    @FXML
    private ChoiceBox<String> varustelu1;
	
    //mökin id:n poimimista varten
    static int iddd=1;
	
    //dropdown-listaukset
    @FXML
	private void initialize() {
    	toimipaikkalistaus.setValue("Valitse toimipaikka");
		toimipaikkalistaus.setItems(toimipaikat);
    	toimialue.setValue("Valitse toimipaikka");
		toimialue.setItems(toimipaikat);
		toimialue1.setValue("Valitse toimipaikka");
		toimialue1.setItems(toimipaikat);
		varustelu.setValue("Valitse varustelutaso");
		varustelu.setItems(varustelut);
		varustelu1.setValue("Valitse varustelutaso");
		varustelu1.setItems(varustelut);
	}
    
	//takaisin menuun painamalla 'peruuta'
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        }
	
	//takaisin kaikkiin mökkeihin yksittäisestä mökistä
	public void takaisinMokkeihin (ActionEvent event) throws IOException {
		changeScene("MokkienHallinta.fxml");
	}
	
	// kaikkien mökkien näkyminen listassa painamalla 'näytä kaikki'
	public void listapaivitys() {
    	try {
    		lista.getItems().clear();
    		  
    		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Tiedot saatu!");
    			PreparedStatement preparedStatement=connection.prepareStatement("Select m.mokki_id, m.mokkinimi, t.nimi from mokki m join toimintaalue t using(toimintaalue_id);");
    	      
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	        while(resultSet.next()){
    	             String nimi=resultSet.getString("mokkinimi");
    	             String alue=resultSet.getString("nimi");
    	             String id=resultSet.getString("mokki_id");
    	        
    	             Button x=new Button(nimi+" "+"(" + alue +")");
    	             x.setMinWidth(150);
    	             x.setAlignment(Pos.CENTER_LEFT);
    	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
    	            
    	            x.setOnAction((event) -> {
    	                System.out.println(x.getText());
  	                     String sisalto=x.getText();
  	                     String[] sisaltoosissa= sisalto.split(" ");
  	                   
  	                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
  	                   
  	                    hakutulos.setVisible(true);
					try {
						paivita();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
    	            });
    	
    	            lista.getItems().add(x);
    	            }
    	        
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
	   }
	
	//dropdown-valikossa näkyvät toimipaikat
	ObservableList<String> toimipaikat = FXCollections.
			observableArrayList("Valitse toimipaikka", "Ruka", "Levi", "Ylläs", "Äkäslompolo");
	ObservableList<String> varustelut = FXCollections.
			observableArrayList("Valitse varustelutaso", "Perus", "Öky");
	
	// mökkien näyttäminen toimipaikoittain
		public void hakuToimipaikoilla() {
			String valittuToimipaikka = toimipaikkalistaus.getValue();
			String toimialue_id;
			System.out.println(valittuToimipaikka);
			
	    	try {
	    		lista.getItems().clear();
	    		  
	    		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
	    			System.out.println("Tiedot saatu!");
	    			PreparedStatement preparedStatement=connection.prepareStatement("Select toimintaalue_id from toimintaalue where nimi="+"'"+valittuToimipaikka+"'");
	    	      
	    	        ResultSet toimialue_idResult=preparedStatement.executeQuery();
	    	        
	    	        toimialue_idResult.next();
	    	        toimialue_id = toimialue_idResult.getString("toimintaalue_id");
	    	        
	    	        preparedStatement = connection.prepareStatement("select * from mokki where toimintaalue_id = "+"'"+toimialue_id+"'");
	                ResultSet resultSet = preparedStatement.executeQuery();
	    	        
	    	        while(resultSet.next()){
	    	             String nimi=resultSet.getString("mokkinimi");
	    	             String id=resultSet.getString("mokki_id");
	    	        
	    	             Button x=new Button(nimi+" "+"(" + id +")");

	    	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
	    	            
	    	            x.setOnAction((event) -> {
	    	                System.out.println(x.getText());
	  	                   
	    	                iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
	    	                
	    	                hakutulos.setVisible(true);
	  	                   
						try {
							paivita();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
	    	            });
	    	
	    	            lista.getItems().add(x);
	    	            }
	    	        
	    			} catch (SQLException e) {
	    			System.out.println("Error while connecting to the database");
	    			}
		   }
	
	//uuden mökin lisäys
	public void mokinLisays() {
		uusiMokki.setVisible(true);
	}
	
		
	// mökkien haku nimellä, id:llä
    public void hakuMokeista () {
    	String hakutext=hakukentta.getText();
    	System.out.println(hakutext);
    	lista.getItems().clear();
 	   try {
 		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement(
 					"select distinct * from mokki where mokkinimi like "+"'"+"%"+hakutext+"%"+"'"
 					+ "or mokki_id like "+"'"+"%"+hakutext+"%"+"'" +" and m.toimintaalue_id=t.toimintaalue_id "
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	       while(resultSet.next()){
	             String id=resultSet.getString("mokki_id");
	             String nimi=resultSet.getString("mokkinimi");
	             String alue=resultSet.getString("toimintaalue");
	        
	             Button x=new Button(nimi+" "+"(" + alue +")");
	             x.setMinWidth(150);
	             x.setAlignment(Pos.CENTER_LEFT);
	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
	            
	            x.setOnAction((event) -> {
	                System.out.println(x.getText());
                     String sisalto=x.getText();
                     String[] sisaltoosissa= sisalto.split(" ");
                   
                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
                   
                    hakutulos.setVisible(true);
                    
					try {
						paivita();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            });
	                
	             
	
	            lista.getItems().add(x);
	           
	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
    }
	   
	   
	   //mökin tarkemmat tiedot
	    public void paivita() throws SQLException{
	    	
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			 PreparedStatement preparedStatement2=connection.prepareStatement("Select * from mokki m join toimintaalue t using(toimintaalue_id) where mokki_id="+iddd);
		      
			    ResultSet resultSet2=preparedStatement2.executeQuery();
			    while(resultSet2.next()){
			    	mokkiID.setText(resultSet2.getString("mokki_id"));
			    	mokkinimi.setText(resultSet2.getString("mokkinimi"));
			    	toimialue.setValue(resultSet2.getString("nimi"));
		            osoite.setText(resultSet2.getString("katuosoite"));
		            postinro.setText(resultSet2.getString("postinro"));
		            //toimipaikka.setText(resultSet2.getString(iddd)); //muuta, täytyy hakea posti-taulusta
		            hlo.setText(resultSet2.getString("henkilomaara"));
		            varustelu.setValue(resultSet2.getString("varustelu"));
		            hinta.setText(resultSet2.getString("hinta"));
		            kuvaus.setText(resultSet2.getString("kuvaus"));


		            }
			    }
	    
}
    	   

   

