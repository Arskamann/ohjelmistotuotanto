package softa;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private Button paivita;
	static int iddd=1;
	private String mokkiid="";
	private String asiakasid="";
	static boolean uusiposti=true;
	
	@FXML
    TextField etu;
    @FXML
    TextField suk;
    @FXML
    TextField puh;
    @FXML
    TextField sah;
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
    
    int daysBetween;
    static Double palveluthinta=0.0;
    static int mokkihinta=0;
    
	public void menu(ActionEvent event) throws IOException { 
		changeScene("Menu.fxml");

	}
	
	public void listapaivitys(){
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
	   	        sah.setText(mail);
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
	
	public void listaHaku(){            // tassa haetaan id:n ja nimen perusteella asiakkaita
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
 	   	        sah.setText(mail);
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
		
	 
	 
	 //mokin filtterit!!!
	 
	 @FXML
	private ListView<Button> mokit;
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
		String IIDEE = null;
	 
	 public void paivitaalueet() throws SQLException{
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
				paivitapalvelut();
				paivitamokit();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
           
	    });
	     alueet.setValue("Kaikki");
	     paivitahenkilomaara();
	 }
	 
	 public void paivitahenkilomaara() throws SQLException {
		 hlomaara.getItems().clear();
		 
		String xx = new String("2");
		String xxx = new String("3");
		String xxxx = new String("4");
		String xxxxx = new String("5");
		String xxxxxx = new String("6");
		String xxxxxxx = new String("7");
		String xxxxxxxx = new String("8");
		
		hlomaara.getItems().addAll(xx,xxx,xxxx,xxxxx,xxxxxx,xxxxxxx,xxxxxxxx);
		 
		 hlomaara.setOnAction((event) -> {
	            
	           if(hlomaara.getValue().toString()!="") {
	        	   try {
	        		   h = hlomaara.getValue().toString();
	        		   paivitamokit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	           
		    });
		 hlomaara.setValue("2");
	 }
	 
	 public void paivitamokit() throws SQLException{
		 mokit.getItems().clear();
		 String alkaa = alku.getText();
		 String loppuu = loppu.getText();
		 String ajanjakso="";
		 
		 if(alkaa!=""&&loppuu!="") {
			 ajanjakso= " and mokki_id NOT IN (SELECT mokki_mokki_id FROM vn.varaus WHERE '"+alkaa+" 14:00:00'<= varattu_loppupvm AND '"+loppuu+" 12:00:00'>=varattu_alkupvm)";
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
       
            Button x=new Button(nim+" | Henkilomaara:"+henk+" Varustelu:"+var+" Hinta/yo:"+hin+" Alue:"+alue);
          x.setAccessibleText(id);
          
          
           x.setOnAction((event) -> {
               System.out.println("mokin id:"+x.getAccessibleText());
      		 
      		 LocalDate ldA = LocalDate.parse( alkaa );
      		 LocalDate ldB = LocalDate.parse( loppuu );
      		 daysBetween = (int) ChronoUnit.DAYS.between( ldA , ldB );
               
                mokkihinta=Integer.parseInt(hin);
                hinta.setText((Double.toString(palveluthinta+(mokkihinta*daysBetween))));
                mokkiid=x.getAccessibleText();
           });
          
                  
                   
                   
           

           mokit.getItems().add(x);
          
       }
	    
	    }
	 
	 
	 // palvelut hommeleita
	 
	 @FXML
	 private ListView<Button> palvelut;
	 
	 public void paivitapalvelut() throws SQLException {
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
	    		 String alkaa = alku.getText();
	    		 String loppuu = loppu.getText();
	    		 LocalDate ldA = LocalDate.parse( alkaa );
	      		 LocalDate ldB = LocalDate.parse( loppuu );
	      		 daysBetween = (int) ChronoUnit.DAYS.between( ldA , ldB );
	    		
	    	       System.out.println(x.getText());
                   String sisalto=x.getText();
                   String[] sisaltoosissa= sisalto.split(" ");
                   palveluthinta+=phinta;
                   int maara=Integer.parseInt(sisalto.substring(sisalto.lastIndexOf(" ")+1))+1;
	    		 String y=pnimi+" "+pkuvaus+" "+phinta+" "+maara;
	         x.setText(y);
	         hinta.setText((Double.toString(palveluthinta+(mokkihinta*daysBetween))));
				
	            });
	    	palvelut.getItems().add(x);
	    }
	    palveluthinta=0.0;
	    mokkihinta=0;
	    hinta.setText((Double.toString(palveluthinta+mokkihinta)));
	    
	 }
	 
	 //varauksen luominen
	 
	 
	 @FXML
		private Button varaa;
	 
	 public void varaus() throws SQLException {
		 
		 try {
			 
			 uusiposti = true;
			 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
				System.out.println("Tiedot saatu!");
				
				
				
				//asiakkaan tiedot (id)
				
	 	       String asiakasiidee = asiakasid;
	 	       
	 	       String etunimi=etu.getText();
	 	       String sukunimi=suk.getText();
	 	       String numero=puh.getText();
	 	       String sahkoposti=sah.getText();
	 	       String osoite=oso.getText();
	 	       String posti=pos.getText();
	 	       String toimip = toim.getText();
	 	       
				//aikaväli
	 	       
	 	       String alkupvm = alku.getText();
	 	       String loppupvm = loppu.getText();
				
				//valittu mökki
	 	       
	 	       String mokkiidee = mokkiid;
	 	     
	 		    
				//hinta
				
				String hintaa = hinta.getText();
				
				//tämänhetkinen päivä
				Date date = new Date();  
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
			    String strDate= formatter.format(date);  
				
				if(asiakasiidee!=""&&alkupvm!=""&&loppupvm!=""&&mokkiidee!=""&&hintaa!=""&&etunimi!=""&&sukunimi!=""&&posti!=""&&toimip!=""&&osoite!="") {	
					
				
					PreparedStatement preparedStatement=connection.prepareStatement("insert into varaus set asiakas_id ='"+asiakasiidee+"', mokki_mokki_id ="+mokkiidee+", varattu_alkupvm ='"+alkupvm+" 14:00:00'"
							+ ", varattu_loppupvm ='"+loppupvm+" 12:00:00', varattu_pvm = '"+strDate+"', vahvistus_pvm = '"+strDate+"'");

					preparedStatement.executeUpdate();
					
					//---------------------------------
					
					 PreparedStatement preparedStatement3=connection.prepareStatement("select * from varaus where mokki_mokki_id="+mokkiidee+" and varattu_alkupvm ='"+alkupvm+" 14:00:00' and varattu_loppupvm ='"+loppupvm+" 12:00:00' and varattu_pvm = '"+strDate+"'");
				      
					    ResultSet resultSet3=preparedStatement3.executeQuery();
					    while(resultSet3.next()){
					    	IIDEE=resultSet3.getString("varaus_id");
					    
					
					    }
					
					
					
					String sähkk;
					if(lask.isSelected()) {
						sähkk="kyllä";
					}
					else {
						sähkk="ei";
					}
					
					
					
					PreparedStatement preparedStatement2=connection.prepareStatement("insert into lasku set varaus_id ='"+IIDEE+"', summa ="+Double.parseDouble(hintaa.replace("€",""))+", alv ="+0.17+
							 ", maksettu ='ei', sähköpostilla = '"+sähkk+"', muistutus = 'false'");

					preparedStatement2.executeUpdate();
					
					
					
					
					
					//-----------------------------
					
					
				
					
					
					   Alert a = new Alert(AlertType.INFORMATION);
						 a.setContentText("Uusi varaus luotu!");
						 a.setTitle("Huomio");
						 a.show();
						 changeScene("Uusivaraus.fxml");
						 	
				}
				
				//jos uusi asiakas
				
				else if(asiakasiidee==""&&alkupvm!=""&&loppupvm!=""&&mokkiidee!=""&&hintaa!=""&&etunimi!=""&&sukunimi!=""&&posti!=""&&toimip!=""&&osoite!="") {
					
					PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM posti");
					preparedStatement.executeQuery();
					
					 ResultSet resultSet=preparedStatement.executeQuery();
					    while(resultSet.next()){
					    	String num =resultSet.getString("postinro");
					    	
					        if(num.equals(posti)) {
					        	uusiposti=false;
					        }
					    }
					    if(uusiposti==true) {
							
							
							PreparedStatement preparedStatement3=connection.prepareStatement(
						    		"insert into posti set postinro ='"+posti+"',toimipaikka= '"+toimip+"'");
						   preparedStatement3.executeUpdate();
						}
					    
					    PreparedStatement preparedStatement2=connection.prepareStatement(
					    		"insert into asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
					    				+ ", email='"+sahkoposti+"', lahiosoite='"+osoite+"', postinro='"+posti+"'");
					   preparedStatement2.executeUpdate();
					   
					   PreparedStatement preparedStatement5=connection.prepareStatement("SELECT * FROM asiakas WHERE etunimi ='"+etunimi+"' and sukunimi ='"+sukunimi+"' and puhelinnro ='"+numero+"'");
						preparedStatement5.executeQuery();
						
						 ResultSet resultSet2=preparedStatement5.executeQuery();
						    while(resultSet2.next()){
						    	String idd =resultSet2.getString("asiakas_id");
						    	
						    	PreparedStatement preparedStatement6=connection.prepareStatement("insert into varaus set asiakas_id ='"+idd+"', mokki_mokki_id ="+mokkiidee+", varattu_alkupvm ='"+alkupvm+" 14:00:00'"
										+ ", varattu_loppupvm ='"+loppupvm+" 12:00:00', varattu_pvm = '"+strDate+"', vahvistus_pvm = '"+strDate+"'");

								preparedStatement6.executeUpdate();
								
								   Alert a = new Alert(AlertType.INFORMATION);
									 a.setContentText("Uusi varaus luotu!");
									 a.setTitle("Huomio");
									 a.show();
									 changeScene("Uusivaraus.fxml");
						    }
						    
						    PreparedStatement preparedStatement3=connection.prepareStatement("select * from varaus where mokki_mokki_id="+mokkiidee+" and varattu_alkupvm ='"+alkupvm+" 14:00:00' and varattu_loppupvm ='"+loppupvm+" 12:00:00' and varattu_pvm = '"+strDate+"'");
						      
						    ResultSet resultSet3=preparedStatement3.executeQuery();
						    while(resultSet3.next()){
						    	IIDEE=resultSet3.getString("varaus_id");
						    
						
						    }
						
						
						
						String sähkk;
						if(lask.isSelected()) {
							sähkk="kyllä";
						}
						else {
							sähkk="ei";
						}
						
						
						
						PreparedStatement preparedStatement4=connection.prepareStatement("insert into lasku set varaus_id ='"+IIDEE+"', summa ="+Double.parseDouble(hintaa.replace("€",""))+", alv ="+0.17+
								 ", maksettu ='ei', sähköpostilla = '"+sähkk+"', muistutus = 'false'");

						preparedStatement4.executeUpdate();
						
						
						
					   
				}
				
				
				else {
					Alert a = new Alert(AlertType.INFORMATION);
					 a.setContentText("Täytä kaikki pakolliset kentät!");
					 a.setTitle("Huomio");
					 a.show();
				}
				
				
		 } catch (Exception e) {
			 e.printStackTrace();
			 Alert a = new Alert(AlertType.INFORMATION);
			 a.setContentText("Virhe!");
			 a.setTitle("Huomio");
			 a.show();
		 }
		 
		 
		 
	 }
	 
	 }


