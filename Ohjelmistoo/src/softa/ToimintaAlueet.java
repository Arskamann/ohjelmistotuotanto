package softa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class ToimintaAlueet extends Menu {
	
	@FXML
	private Button takas;
	@FXML
    private TextField hae;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button p�ivit�;
	static int iddd=1;
	
	@FXML
	Pane alue;
	@FXML
	Pane list;
	@FXML
	Pane palveluikkuna;
	
	
	public void menu(ActionEvent event) throws IOException { 
		changeScene("Menu.fxml");

	}
	public void uusiToimintaAlue(ActionEvent event) throws IOException {
        changeScene("UusiToimintaAlue.fxml");
	}
	
	public void uusiPalvelu(ActionEvent event) throws IOException {
        changeScene("UusiPalvelu.fxml");
	}
	
	public void takas() throws IOException {
			palveluikkuna.setVisible(false);
	       alue.setVisible(false);
	       list.setVisible(true);
	       listap�ivitys();
	       palvelup�ivitys();
	       tallenna.setStyle("-fx-background-color: #FFFFFF");
	       tallenna.setText("Tallenna");
	       tallenna2.setStyle("-fx-background-color: #FFFFFF");
	       tallenna2.setText("Tallenna");
	       
	        
	    }
	
	public void listap�ivitys(){
 	   try {
 		   lista.getItems().clear();
 		  
 		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement("select * from toimintaalue");
 	      
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	        
 	        while(resultSet.next()){
 	             String id=resultSet.getString("toimintaalue_id");
 	             String nimi=resultSet.getString("nimi");
 	        
 	             Button x=new Button(nimi);
 	             x.setMinWidth(150);
 	             x.setAlignment(Pos.CENTER_LEFT);
 	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
 	            
 	            x.setOnAction((event) -> {
 	                System.out.println(x.getText());
	                     String sis�lt�=x.getText();
	                     String[] sis�lt�osissa= sis�lt�.split(" ");
	                   
	                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
	                   
	                  list.setVisible(false);
					alue.setVisible(true);
					try {
						p�ivit�();
						palvelup�ivitys();
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
	
	public void listaHaku(){            // t�ss� haetaan id:n ja nimen perusteella asiakkaita
    	String hakutext=hae.getText();
    	System.out.println(hakutext);
    	lista.getItems().clear();
 	   try {
 		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement(
 					"select distinct * from toimintaalue where nimi="+"'"+hakutext+"'"
 						+ "or toimintaalue_id="+"'"+hakutext+"'"
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	       while(resultSet.next()){
	             String id=resultSet.getString("toimintaalue_id");
	             String nimi=resultSet.getString("nimi");
	        
	             Button x=new Button(nimi);
	             x.setMinWidth(150);
	             x.setAlignment(Pos.CENTER_LEFT);
	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
	            
	            x.setOnAction((event) -> {
	                System.out.println(x.getText());
                     String sis�lt�=x.getText();
                     String[] sis�lt�osissa= sis�lt�.split(" ");
                   
                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
                   
                    list.setVisible(false);
					alue.setVisible(true);
					try {
						p�ivit�();
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
	
	
	// yksitt�inen toiminta-alue
	
	 @FXML
	    Button ut;
	 @FXML
	    TextField nimiii;
	 @FXML
	    Button tallenna;
	
	 public void tallenna() throws SQLException {
	    	
	    	System.out.println(iddd);
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			
			String tnimi=nimiii.getText();
					
	    PreparedStatement preparedStatement=connection.prepareStatement(
	    		"update toimintaalue set nimi ='"+tnimi+"' where toimintaalue_id="+iddd);
	   preparedStatement.executeUpdate();
	    tallenna.setText("Tallennettu");
	    tallenna.setStyle("-fx-background-color: #00ff00");
			
			
	    }
	 
	 public void p�ivit�() throws SQLException{
	    	
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			 PreparedStatement preparedStatement=connection.prepareStatement("select * from toimintaalue where toimintaalue_id="+iddd); 
	      
	    ResultSet resultSet=preparedStatement.executeQuery();
	    while(resultSet.next()){
	    	System.out.println(resultSet.getString("nimi"));
	    	nimiii.setText(resultSet.getString("nimi"));
	    }
	    
	    }
	 
	 @FXML
	    TextField uusinimi;
	  @FXML
	    Button tallennauusi;
	
	 public void tallennaUusi() {
		 
		 try {
			 
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			
			String nimi=uusinimi.getText();
			PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM toimintaalue");
			preparedStatement.executeQuery();
  
			    
	    PreparedStatement preparedStatement2=connection.prepareStatement(
	    		"insert into toimintaalue set nimi ='"+nimi+"'");
	   preparedStatement2.executeUpdate();
	   Alert a = new Alert(AlertType.INFORMATION);
		 a.setContentText("Uusi toiminta-alue luotu!");
		 a.setTitle("Huomio");
		 a.show();
		 changeScene("ToimintaAlueet.fxml");
			
		
		 }catch (Exception e) {
			 Alert a = new Alert(AlertType.INFORMATION);
			 a.setContentText("Virhe!");
			 a.setTitle("Huomio");
			 a.show();
		 }
		 
	    }
	 
	 @FXML
	    Button poista;
	 
	 public void poista() throws IOException {
			try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			
			

		PreparedStatement preparedStatement=connection.prepareStatement("delete from toimintaalue where toimintaalue_id="+iddd);
		preparedStatement.executeUpdate();
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Toiminta-alue poistettu");
		a.setTitle("Huomio");
		a.show();
		takas();
		listap�ivitys();
			}catch(Exception e) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setContentText("Virhe poistaessa toiminta-aluetta");
				a.setTitle("Huomio");
				a.show();
			}
		}
	 
	 
	 //palvelut
	 
	 @FXML
	    TextField nim;
	 @FXML
	    TextField kuvaus;
	 @FXML
	    TextField tyyppi;
	 @FXML
	    TextField hinta;
	 @FXML
	    TextField alv;
	 
	 public void p�ivit�2() throws SQLException{
	    	
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			 PreparedStatement preparedStatement=connection.prepareStatement("select * from vn.palvelu where toimintaalue_id="+iddd+" and palvelu.toimintaalue_id= "+iddd+""); 
	      
	    ResultSet resultSet=preparedStatement.executeQuery();
	    while(resultSet.next()){
	    	System.out.println(resultSet.getString("nimi"));
	    	nim.setText(resultSet.getString("nimi"));
	    	tyyppi.setText(resultSet.getString("tyyppi"));
	    	kuvaus.setText(resultSet.getString("kuvaus"));
	    	hinta.setText(resultSet.getString("hinta"));
	    	alv.setText(resultSet.getString("alv"));
	    }
	    
	    }
	 
	 
	 
	 @FXML
		private ListView<Button> plista;
	 
	 public void palvelup�ivitys(){
	 	   try {
	 		   plista.getItems().clear();
	 		  String nim=nimiii.getText();
	 		  
	 		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
	 			System.out.println("Tiedot saatu!");
	 			PreparedStatement preparedStatement=connection.prepareStatement("select p.palvelu_id, p.nimi, p.kuvaus, p.hinta, p.alv from vn.palvelu p, vn.toimintaalue t where t.nimi = '"+nim+"' and p.toimintaalue_id = t.toimintaalue_id;");
	 	      
	 	        ResultSet resultSet=preparedStatement.executeQuery();
	 	        
	 	        while(resultSet.next()){
	 	             String id=resultSet.getString("p.palvelu_id");
	 	             String nimi=resultSet.getString("p.nimi");
	 	             String kuvaus=resultSet.getString("p.kuvaus");
	 	             String hinta=resultSet.getString("p.hinta");
	 	             String alv=resultSet.getString("p.alv");
	 	            
	 	        
	 	             Button x=new Button(nimi+" "+kuvaus+" "+hinta+" "+alv);
	 	             x.setMinWidth(150);
	 	             x.setAlignment(Pos.CENTER_LEFT);
	 	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
	 	            
	 	            x.setOnAction((event) -> {
	 	                System.out.println(x.getText());
		                     String sis�lt�=x.getText();
		                     String[] sis�lt�osissa= sis�lt�.split(" ");
		                   
		                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
		                    
		                    
							alue.setVisible(false);
							palveluikkuna.setVisible(true);
						try {
							p�ivit�2();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 	            });
	 	            
		            
		                    
		                     
		                     
		             
	 	
	 	            plista.getItems().add(x);
	 	           
	 	        }
	 	        
	 			} catch (SQLException e) {
	 			System.out.println("Error while connecting to the database");
	 			}
	 	   
	 	   
		}
	 
	 
	 @FXML
	    Button tallenna2;
	 
	 public void tallenna2() throws SQLException {
	    	
	    	System.out.println(iddd);
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			
			String nimi=nim.getText();
			String tyypp=tyyppi.getText();
			String kuv=kuvaus.getText();
			String hint=hinta.getText();
			String al=alv.getText();
					
	    PreparedStatement preparedStatement=connection.prepareStatement(
	    		"update palvelu set nimi ='"+nimi+"', tyyppi ='"+tyypp+"', kuvaus ='"+kuv+"', hinta ='"+hint+"', alv ='"+al+"' where toimintaalue_id="+iddd);
	   preparedStatement.executeUpdate();
	    tallenna2.setText("Tallennettu");
	    tallenna2.setStyle("-fx-background-color: #00ff00");
			
			
	    }
	 
	 @FXML
	    Button poista2;
	 
	 public void poista2() throws IOException {
			try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			
			String nimi = nim.getText();
		
		PreparedStatement preparedStatement=connection.prepareStatement("delete from palvelu where nimi="+nimi);
		preparedStatement.executeUpdate();
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Palvelu poistettu");
		a.setTitle("Huomio");
		a.show();
		takas();
		palvelup�ivitys();
			}catch(Exception e) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setContentText("Virhe poistaessa palvelua");
				a.setTitle("Huomio");
				a.show();
			}
		}
	 
	 @FXML
	    Button up;
	 @FXML
	    TextField uusipnimi;
	 @FXML
	    TextField uusityyppi;
	 @FXML
	    TextField uusikuvaus;
	 @FXML
	    TextField uusihinta;
	 @FXML
	    TextField uusialv;
	 
	 @FXML
	    Button tallennauusi2;
	
	 public void tallennaUusi2() {
		 
		 try {
			 
			 System.out.println(iddd);
			 
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");			
			
			String nimi=uusipnimi.getText();
			String tyyppi=uusityyppi.getText();
			String kuvaus=uusikuvaus.getText();
			String hinta=uusihinta.getText();
			String alv=uusialv.getText();

			    
	    PreparedStatement preparedStatement2=connection.prepareStatement(
	    		"insert into palvelu set nimi ='"+nimi+"', tyyppi ='"+tyyppi+"', kuvaus ='"+kuvaus+"', hinta ='"+hinta+"', alv ='"+alv+"', toimintaalue_id="+iddd+"");
	   preparedStatement2.executeUpdate();
	   Alert a = new Alert(AlertType.INFORMATION);
		 a.setContentText("Uusi palvelu luotu!");
		 a.setTitle("Huomio");
		 a.show();
		 changeScene("ToimintaAlueet.fxml");
			
		
		 }catch (Exception e) {
			 Alert a = new Alert(AlertType.INFORMATION);
			 a.setContentText("Virhe!");
			 a.setTitle("Huomio");
			 a.show();
		 }
		 
	    }
	 
	 
}

