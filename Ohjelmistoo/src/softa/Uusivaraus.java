package softa;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private String mokkiid="";
	private String asiakasid="";
	
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
    TextField toim;
    @FXML
  	RadioButton lask;
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
	             String id=resultSet.getString("asiakas_id");
	             String etuu=resultSet.getString("etunimi");
	             String suku=resultSet.getString("sukunimi");
	           
	            String nro=resultSet.getString("puhelinnro");
	           String mail=resultSet.getString("email");
	          String osoi=resultSet.getString("lahiosoite");
	         String poss=resultSet.getString("postinro");
	         
	         
	         PreparedStatement preparedStatement2=connection.prepareStatement("select * from vn.asiakas,vn.posti where asiakas_id="+id+" and posti.postinro= '"+poss+"'");
	         
	         ResultSet resultSet2=preparedStatement2.executeQuery();
	         while(resultSet2.next()){
	           String toimi=(resultSet2.getString("toimipaikka"));
	         
	             Button x=new Button(etuu+" "+suku);
	             x.setAccessibleText(id);
	            x.setOnAction((event) -> {
	            	
	            etu.setText(etuu);
	   	        suk.setText(suku);
	   	        puh.setText(nro);
	   	        säh.setText(mail);
	   	        oso.setText(osoi);
	   	        pos.setText(poss);
	   	     toim.setText(toimi);
	   	  asiakasid=x.getAccessibleText();
	            });
	
	            lista.getItems().add(x);
	         }
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
	 private ComboBox<String> alueet;
	 @FXML
	 private ComboBox<String> hlomaara;
	 @FXML
	 private CheckBox viiskyt;
	 @FXML
	 private CheckBox sata;
	 @FXML
	 private CheckBox kakssataa;
	 @FXML
	 private RadioButton normaali;
	 @FXML
	 private RadioButton hyva;
	 @FXML
	 private RadioButton luksus;
	 
	 private String a = ".";
	 private String h = ".";
	 
	 
	 public void päivitäalueet() throws SQLException{
		 alueet.getItems().clear();
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=Menu.connection.prepareStatement("select * from toimintaalue");
	      
	    ResultSet resultSet=preparedStatement.executeQuery();
	    String tt = new String("Kaikki");
	    alueet.getItems().add(tt);
	    while(resultSet.next()){
             String nimi=resultSet.getString("nimi");
             String t = new String(nimi);
             
            alueet.getItems().add(t);
           
	    }
	    alueet.setOnAction((event) -> {
            
           if(alueet.getValue().toString()!=""||alueet.getValue().toString()!="Toimialue") {
        	   try {
        		a = alueet.getValue().toString();
				päivitäpalvelut();
				päivitämökit();
				päivitähenkilömäärä();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
           
	    });
	     alueet.setValue("Kaikki");
	 }
	 
	 public void päivitähenkilömäärä() throws SQLException {
		 hlomaara.getItems().clear();
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
			
		PreparedStatement preparedStatement=Menu.connection.prepareStatement("select * from mokki order by henkilomaara asc");
		
		ResultSet resultSet=preparedStatement.executeQuery();
		String xx = new String("2");
		hlomaara.getItems().add(xx);
		 while(resultSet.next()){
             String henkilömäärä=resultSet.getString("henkilomaara");
             String x = new String(henkilömäärä);
            
             
            hlomaara.getItems().add(x);
	    }
		 
		 hlomaara.setOnAction((event) -> {
	            
	           if(hlomaara.getValue().toString()!="") {
	        	   try {
	        		   h = hlomaara.getValue().toString();
	        		   päivitämökit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	           
		    });
		 hlomaara.setValue("2");
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
		 	
		 
	    	String nimi;
	    	
	    	if(a.equals("Kaikki")) {
	  		  nimi="";
	  	  }
	  	  else  {
	  		  nimi= " and toimintaalue.nimi='"+a+"'";
	  	  }
	    	
	    	
	    	String valittuhlomaara;
	    	
		    if(h.equals("2")) {
		    	valittuhlomaara="";
		    }
		    else {
		    	valittuhlomaara=" and mokki.henkilomaara>='"+h+"'";
		    }
		    
		    String hint;
		    
		    if(viiskyt.isSelected()) {
		    	hint=" mokki.hinta between 50 and 100 and";
		    }
		    else if(sata.isSelected()) {
		    	hint=" mokki.hinta between 100 and 200 and";
		    }
		    else if(kakssataa.isSelected()) {
		    	hint=" mokki.hinta between 200 and 1000 and";
		    }
		    else {
		    	hint="";
		    }
		    
		    if(viiskyt.isSelected()&&sata.isSelected()) {
		    	hint=" mokki.hinta between 50 and 200 and";
		    }
		    else if(sata.isSelected()&&kakssataa.isSelected()) {
		    	hint=" mokki.hinta between 100 and 1000 and";
		    }
		    else if(viiskyt.isSelected()&&kakssataa.isSelected()) {
		    	hint=" mokki.hinta not between 100 and 200 and";
		    }
		    
		    if(viiskyt.isSelected()&&sata.isSelected()&&kakssataa.isSelected()) {
		    	hint=" mokki.hinta between 50 and 1000 and";
		    }
		  
		    
		    String varust;
		    if(normaali.isSelected()) {
		    	varust=" and mokki.varustelu = 'normaali'";
		    	hyva.setSelected(false);
		    	luksus.setSelected(false);
		    }
		    else if(hyva.isSelected()) {
		    	varust=" and mokki.varustelu = 'hyvä'";
		    	normaali.setSelected(false);
		    	luksus.setSelected(false);
		    }
		    else if(luksus.isSelected()) {
		    	varust=" and mokki.varustelu = 'luksus'";
		    	hyva.setSelected(false);
		    	normaali.setSelected(false);
		    }
		    else {
		    	varust="";
		    }
		    
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=Menu.connection.prepareStatement(
	    		"SELECT distinct mokki_id, mokkinimi, henkilomaara, varustelu,hinta,nimi FROM vn.mokki,vn.varaus,vn.toimintaalue WHERE "+hint+" vn.toimintaalue.toimintaalue_id=vn.mokki.toimintaalue_id"
	    		+nimi +ajanjakso +valittuhlomaara +varust);
	      
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
                mokkiid=x.getAccessibleText();
           });
          
                  
                   
                   
           

           mökit.getItems().add(x);
          
       }
	    
	    }
	 
	 
	 // palvelut hommeleita
	 
	 @FXML
	 private ListView<Button> palvelut;
	 
	 public void päivitäpalvelut() throws SQLException {
		 palvelut.getItems().clear();
		 
		 String nimi;
	    	
	    	if(a.equals("Kaikki")) {
	  		  nimi="";
	  	  }
	  	  else  {
	  		  nimi= " and t.nimi='"+a+"'";
	  	  }
		 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=Menu.connection.prepareStatement("select p.palvelu_id, p.nimi, p.kuvaus, p.hinta, p.alv from vn.palvelu p, vn.toimintaalue t where p.toimintaalue_id = t.toimintaalue_id"
	    																	+nimi);
	    
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
	 
	 //varauksen luominen
	 
	 
	 @FXML
		private Button varaa;
	 
	 public void varaus() throws SQLException {
		 
		 try {
			 
			 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
				System.out.println("Tiedot saatu!");
				
				
				
				//asiakkaan tiedot (id)
				
	 	       String asiakasiidee = asiakasid;
	 	       
				//aikaväli
	 	       
	 	       String alkupvm = alku.getText();
	 	       String loppupvm = loppu.getText();
				
				//valittu mökki
	 	       
	 	       String mokkiidee = mokkiid;
	 	     
	 		    
				//hinta
				
				String hintaa = hinta.getText();
				
				//tämänhetkinen päivä
				long millisInDay = 60 * 60 * 24 * 1000;
				long currentTime = new Date().getTime();
				long dateOnly = (currentTime / millisInDay) * millisInDay;
				Date clearDate = new Date(dateOnly);
				
				if(asiakasiidee!=""&&alkupvm!=""&&loppupvm!=""&&mokkiidee!=""&&hintaa!="") {
					
				
					PreparedStatement preparedStatement=connection.prepareStatement("insert into varaus set asiakas_id ='"+asiakasiidee+"', mokki_mokki_id ="+mokkiidee+", varattu_alkupvm ='"+alkupvm+"'"
							+ ", varattu_loppupvm ='"+loppupvm+"', varattu_pvm = '"+clearDate+"'");

					preparedStatement.executeUpdate();
					   Alert a = new Alert(AlertType.INFORMATION);
						 a.setContentText("Uusi varaus luotu!");
						 a.setTitle("Huomio");
						 a.show();
						 changeScene("Uusivaraus.fxml");
					 
				}
				
				else {
					Alert a = new Alert(AlertType.INFORMATION);
					 a.setContentText("Täytä kaikki pakolliset kentät!");
					 a.setTitle("Huomio");
					 a.show();
				}
				
				
		 } catch (Exception e) {
			 Alert a = new Alert(AlertType.INFORMATION);
			 a.setContentText("Virhe!");
			 a.setTitle("Huomio");
			 a.show();
		 }
		 
		 
		 
	 }
	 
	 }


