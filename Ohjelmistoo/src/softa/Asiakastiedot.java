package softa;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Asiakastiedot extends Menu {
	@FXML
    private TextField hae;
	@FXML
    private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button päivitä;
	static int iddd=1;
	public void menu(ActionEvent event) throws IOException { //tÃ¤llÃ¤  toiminnolla pÃ¤Ã¤see takasin pÃ¤Ã¤valikkoon. TÃ¤tÃ¤ kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
        
    }
	public void uusiAsiakas(ActionEvent event) throws IOException { //tÃ¤llÃ¤  toiminnolla pÃ¤Ã¤see takasin pÃ¤Ã¤valikkoon. TÃ¤tÃ¤ kutsutaan uusivaraus.xml tiedostossa
        changeScene("Uusiasiakas.fxml");
        
    }
   public void listapäivitys(){
    	   try {
    		   lista.getItems().clear();
    		  
    		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Tiedot saatu!");
    			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
    	      
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	        while(resultSet.next()){
    	             int id=resultSet.getInt("asiakas_id");
    	             String etu=resultSet.getString("etunimi");
    	             String suku=resultSet.getString("sukunimi");
    	        
    	             Button x=new Button(id+" "+etu+" "+suku);
    	            
    	            x.setOnAction((event) -> {
    	                System.out.println(x.getText());
  	                     String sisältö=x.getText();
  	                     String[] sisältöosissa= sisältö.split(" ");
  	                    iddd = Integer.parseInt(sisältöosissa[0]);
  	                   
  	                  try {
  	                	  
  							changeScene("asiakas.fxml");
  							asiakas(iddd);
  							
  						} catch (IOException e) {
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
    public void listaHaku(){            // tässä haetaan id:n ja nimen perusteella asiakkaita
    	String hakutext=hae.getText();
    	System.out.println(hakutext);
    	lista.getItems().clear();
 	   try {
 		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement(
 					"select * from asiakas where etunimi="+"'"+hakutext+"'"
 							+ "or asiakas_id="+"'"+hakutext+"'"
 							+"or sukunimi="+"'"+hakutext+"'"
 					
 					
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	        while(resultSet.next()){
 	             int id=resultSet.getInt("asiakas_id");
 	             String etu=resultSet.getString("etunimi");
 	             String suku=resultSet.getString("sukunimi");
 	        
 	             Button x=new Button(id+" "+etu+" "+suku);
 	            x.setOnAction(new EventHandler<ActionEvent>() {
	                 @Override
	                 public void handle(ActionEvent event) {   // kun henkilöstä klikkaa...
	                     System.out.println(x.getText());
	                     String sisältö=x.getText();
	                     String[] sisältöosissa= sisältö.split(" ");
	                     int idd = Integer.parseInt(sisältöosissa[0]);
	                     System.out.println(idd); // tällä id:llä sitten etsitään asiakas-taulusta kyseisen henkilön tiedot!
	                     try {
							asiakas(idd);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                     
	                     
	                 }
	             });
 	
 	            lista.getItems().add(x);
 	           
 	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
 	   
 	   
	}
    
    
   // ----------------------------------------------------------------
    
	//yksittäisen asiakkaan asiat...
  
    @FXML
    TextField etu;
    @FXML
    TextField suk;
    @FXML
    TextField puh;
    @FXML
    TextField säh;
    @FXML
    TextField oso;
    @FXML
    TextField pos;
    @FXML
    Button tallenna;
    
    @FXML
    Button päiv;
    @FXML
    Label d;
    @FXML
    Button ua;
    @FXML
    Button poista;
    
   
    public void asiakas(int id) throws IOException {
        changeScene("asiakas.fxml");
       
        try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
		
        PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas where asiakas_id="+id);
	      
        ResultSet resultSet=preparedStatement.executeQuery();
        
        while(resultSet.next()){
        	System.out.println(resultSet.getString("etunimi"));
            etu.setText(resultSet.getString("etunimi"));
            suk.setText(resultSet.getString("sukunimi"));
            puh.setText(resultSet.getString("puhelinnro"));
            säh.setText(resultSet.getString("email"));
            oso.setText(resultSet.getString("lahiosoite"));
            pos.setText(resultSet.getString("postinro"));
            
        }
        
        
       
        
        } catch (SQLException e) {
			System.out.println("Error while connecting to the database");
			e.printStackTrace();
		}
     
    }
    public void tallenna() throws SQLException {
    	
    	System.out.println(iddd);
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		
		String etunimi=etu.getText();
		String sukunimi=suk.getText();
		String numero=puh.getText();
		String sähköposti=säh.getText();
		String osoite=oso.getText();
		String posti=pos.getText();
		
		
	
    PreparedStatement preparedStatement=connection.prepareStatement(
    		"update asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
    				+ ", email='"+sähköposti+"', lahiosoite='"+osoite+"', postinro='"+posti+"' where asiakas_id="+iddd);
   preparedStatement.executeUpdate();
    tallenna.setText("Tallennettu");
    }
    public void päivitä() throws SQLException{
    	
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
	
    PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas where asiakas_id="+iddd);
      
    ResultSet resultSet=preparedStatement.executeQuery();
    while(resultSet.next()){
    	System.out.println(resultSet.getString("etunimi"));
    	d.setText(resultSet.getString("asiakas_id"));
    	etu.setText(resultSet.getString("etunimi"));
        suk.setText(resultSet.getString("sukunimi"));
        puh.setText(resultSet.getString("puhelinnro"));
        säh.setText(resultSet.getString("email"));
        oso.setText(resultSet.getString("lahiosoite"));
        pos.setText(resultSet.getString("postinro"));
        
    }
    
    }
    
    @FXML
    TextField uusietu;
    @FXML
    TextField uusisuk;
    @FXML
    TextField uusipuh;
    @FXML
    TextField uusisäh;
    @FXML
    TextField uusioso;
    @FXML
    TextField uusipos;
    @FXML
    Button tallennauusi;
 public void tallennaUusi() {
	 try {
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		
		String etunimi=uusietu.getText();
		String sukunimi=uusisuk.getText();
		String numero=uusipuh.getText();
		String sähköposti=uusisäh.getText();
		String osoite=uusioso.getText();
		String posti=uusipos.getText();
		
		
	
    PreparedStatement preparedStatement=connection.prepareStatement(
    		"insert into asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
    				+ ", email='"+sähköposti+"', lahiosoite='"+osoite+"', postinro='"+posti+"'");
   preparedStatement.executeUpdate();
   Alert a = new Alert(AlertType.INFORMATION);
	 a.setContentText("Uusi asiakas luotu!");
	 a.setTitle("Huomio");
	 a.show();
	 changeScene("Asiakastiedot.fxml");
	 }catch (Exception e) {
		 Alert a = new Alert(AlertType.INFORMATION);
		 a.setContentText("Täytä kaikki kentät!");
		 a.setTitle("Huomio");
		 a.show();
	 }
    }

public void poista() throws SQLException, IOException {
	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
	System.out.println("Tiedot saatu!");
	
	

PreparedStatement preparedStatement=connection.prepareStatement("delete from asiakas where asiakas_id="+iddd);
preparedStatement.executeUpdate();
Alert a = new Alert(AlertType.INFORMATION);
a.setContentText("Asiakas poistettu");
a.setTitle("Huomio");
a.show();
changeScene("Asiakastiedot.fxml");
}
}
