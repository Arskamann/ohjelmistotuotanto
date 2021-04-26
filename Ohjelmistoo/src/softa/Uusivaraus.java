package softa;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class Uusivaraus extends Menu {
	
	@FXML
    private TextField hae;
	@FXML
	private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button päivitä;
	static int iddd=1;
	
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
    Label hinta;
    
    
    static Double palveluthinta=0.0;
    static int mökkihinta=0;
    
	public void menu(ActionEvent event) throws IOException { 
		changeScene("Menu.fxml");

	}
	
	public void listapäivitys(){
 	   try {
 		   lista.getItems().clear();
 		  
 		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
 	      
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	        
 	       while(resultSet.next()){
	             int id=resultSet.getInt("asiakas_id");
	             String etuu=resultSet.getString("etunimi");
	             String suku=resultSet.getString("sukunimi");
	           
	            String nro=resultSet.getString("puhelinnro");
	           String mail=resultSet.getString("email");
	          String osoi=resultSet.getString("lahiosoite");
	         String poss=resultSet.getString("postinro");
	             Button x=new Button(etuu+" "+suku);
	            x.setOnAction((event) -> {
	            etu.setText(etuu);
	   	        suk.setText(suku);
	   	        puh.setText(nro);
	   	        säh.setText(mail);
	   	        oso.setText(osoi);
	   	        pos.setText(poss);
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
 		  Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=Menu.connection.prepareStatement(
 					"select * from asiakas where etunimi="+"'"+hakutext+"'"
 							+ "or asiakas_id="+"'"+hakutext+"'"
 							+"or sukunimi="+"'"+hakutext+"'"
 					
 					
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	        while(resultSet.next()){
 	             int id=resultSet.getInt("asiakas_id");
 	             String etuu=resultSet.getString("etunimi");
 	             String suku=resultSet.getString("sukunimi");
 	           
 	            String nro=resultSet.getString("puhelinnro");
 	           String mail=resultSet.getString("email");
 	          String osoi=resultSet.getString("lahiosoite");
 	         String poss=resultSet.getString("postinro");
 	             Button x=new Button(etuu+" "+suku);
 	            x.setOnAction((event) -> {
 	            etu.setText(etuu);
 	   	        suk.setText(suku);
 	   	        puh.setText(nro);
 	   	        säh.setText(mail);
 	   	        oso.setText(osoi);
 	   	        pos.setText(poss);
	            });
 	
 	            lista.getItems().add(x);
 	           
 	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
 	   
 	   
	}
		
	

	 
	 
	 //mökin filtterit!!!
	 
	 @FXML
	private ListView<Button> mökit;
	 @FXML
	 TextField alku;
	 @FXML
	 TextField loppu;
	 @FXML
	 ComboBox<String> alueet;
	 
	
	 
	 public void päivitäalueet() throws SQLException{
		 alueet.getItems().clear();
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=Menu.connection.prepareStatement("select * from toimintaalue");
	      
	    ResultSet resultSet=preparedStatement.executeQuery();
	    while(resultSet.next()){
             String nimi=resultSet.getString("nimi");
             
            alueet.getItems().add(nimi);
            
	    }
	    alueet.setOnAction((event) -> {
            
           if(alueet.getValue().toString()!=""||alueet.getValue().toString()!="Toimialue") {
        	   try {
				päivitämökit();
				päivitäpalvelut();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
           
	    });
	 }
	 
	 public void päivitämökit() throws SQLException{
		 mökit.getItems().clear();
		 String alkaa = alku.getText();
		 String loppuu = loppu.getText();
		 String ajanjakso="";
		 if(alkaa!=""&&loppuu!="") {
			 ajanjakso= " and mokki_id NOT IN (SELECT mokki_mokki_id FROM vn.varaus WHERE '"+alkaa+"'<= varattu_loppupvm AND '"+loppuu+"'>=varattu_alkupvm)";
		 }
		 else {
			 ajanjakso="";
		 }
	    	String nimi=alueet.getValue().toString();
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=Menu.connection.prepareStatement(
	    		"SELECT distinct mokki_id, mokkinimi, henkilomaara, varustelu,hinta,nimi FROM vn.mokki,vn.varaus,vn.toimintaalue WHERE nimi='"+nimi+"'and vn.toimintaalue.toimintaalue_id=vn.mokki.toimintaalue_id" +ajanjakso);
	      
	    ResultSet resultSet=preparedStatement.executeQuery();
	    while(resultSet.next()){
            String id=resultSet.getString("mokki_id");
            String nim=resultSet.getString("mokkinimi");
            String henk=resultSet.getString("henkilomaara");
            String var=resultSet.getString("varustelu");
            String hin=resultSet.getString("hinta");
            String alue=resultSet.getString("nimi");
       
            Button x=new Button(nim+" | Henkilömäärä:"+henk+" Varustelu:"+var+" Hinta/yö:"+hin+" Alue:"+alue);
          x.setAccessibleText(id);
          
          
           x.setOnAction((event) -> {
               System.out.println("mökin id:"+x.getAccessibleText());
                mökkihinta=Integer.parseInt(hin);
                hinta.setText((Double.toString(palveluthinta+mökkihinta)));
           });
          
                  
                   
                   
           

           mökit.getItems().add(x);
          
       }
	    
	    }
	 
	 
	 // palvelut hommeleita
	 
	 @FXML
	 private ListView<Button> palvelut;
	 
	 public void päivitäpalvelut() throws SQLException {
		 palvelut.getItems().clear();
		 
		 String nimi=alueet.getValue().toString();
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=Menu.connection.prepareStatement("select p.palvelu_id, p.nimi, p.kuvaus, p.hinta, p.alv from vn.palvelu p, vn.toimintaalue t where t.nimi = '"+nimi+"' and p.toimintaalue_id = t.toimintaalue_id;");
	    
	    ResultSet resultSet=preparedStatement.executeQuery();
	    while(resultSet.next()) {
	    	String id= resultSet.getString("p.palvelu_id");
	    	String pnimi = resultSet.getString("p.nimi");
	    	String pkuvaus = resultSet.getString("p.kuvaus");
	    	double phinta = resultSet.getDouble("p.hinta");
	    	boolean valittu = false;
	    	Button x = new Button(pnimi+" "+pkuvaus+" "+phinta+"€ 0");
	    	x.setAccessibleText(id);
	    	 x.setOnAction((event) -> {
	    		
	    	       System.out.println(x.getText());
                   String sisältö=x.getText();
                   String[] sisältöosissa= sisältö.split(" ");
                   palveluthinta+=phinta;
                   int määrä=Integer.parseInt(sisältö.substring(sisältö.lastIndexOf(" ")+1))+1;
	    		 String y=pnimi+" "+pkuvaus+" "+phinta+" "+määrä;
	         x.setText(y);
	         hinta.setText((Double.toString(palveluthinta+mökkihinta)));
				
	            });
	    	palvelut.getItems().add(x);
	    }
	    palveluthinta=0.0;
	    hinta.setText((Double.toString(palveluthinta+mökkihinta)));
	    
	 }
	
	 }


