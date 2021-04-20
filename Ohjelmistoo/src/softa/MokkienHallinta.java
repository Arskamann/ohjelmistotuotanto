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
	
	@FXML
	Button takaisin;
	@FXML
	Button muutaMokki;
	@FXML
	Button haku;
	@FXML
	Button lisaaMokki;
	@FXML
	Button testinab;
	@FXML
	private ChoiceBox<String> toimipaikkalistaus;
	@FXML
	private void initialize() {
		toimipaikkalistaus.setValue("Valitse toimipaikka");
		toimipaikkalistaus.setItems(toimipaikat);
	}
	@FXML
	private ListView<Button> lista;
	@FXML
	private TextField hakukentta;
	@FXML
	private Pane hakutulos;
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
    private TextField varustelu;
    @FXML
    private TextField kuvaus;
    @FXML
    private Label id;
	@FXML
    private Button paivita;
    static int iddd=1;
	
	//takaisin menuun painamalla 'peruuta'
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        }
	
	//vaihtaa ikkunaan mökin tietojen muokkaukseen painamalla 'muokkaa'
	public void muutaMokki(ActionEvent event) throws IOException {
        changeScene("MokinMuokkaus.fxml");
        }

	// mökin lisäys vie eri ikkunaan
	
	public void mokinLisays (ActionEvent event) throws IOException {
        changeScene("MokinLisays.fxml");
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
			 PreparedStatement preparedStatement2=connection.prepareStatement("Select m.mokki_id, m.mokkinimi, t.nimi, m.postinro, m.katuosoite, m.kuvaus, m.henkilomaara, m.varustelu, m.hinta from mokki m join toimintaalue t using(toimintaalue_id) where mokki_id="+iddd);
		      
			    ResultSet resultSet2=preparedStatement2.executeQuery();
			    while(resultSet2.next()){
			    	mokkinimi.setText(resultSet2.getString("mokkinimi"));
			    	hlo.setText(resultSet2.getString("mokkinimi"));
		            osoite.setText(resultSet2.getString("katuosoite"));
		            varustelu.setText(resultSet2.getString("varustelu"));
		            kuvaus.setText(resultSet2.getString("kuvaus"));
		            alue.setText(resultSet2.getString("nimi"));
		            postinro.setText(resultSet2.getString("postinro"));
		            toimipaikka.setText(resultSet2.getString("varattu_loppupvm"));
		            }
			    }
	    
}
    	   

   

