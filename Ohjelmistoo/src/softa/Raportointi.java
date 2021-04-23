package softa;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Raportointi extends Menu {
	@FXML
    private TextField hae;
	@FXML
    private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button p‰ivit‰;
	static int iddd=1;
private String a;


	@FXML
	private Button majoit;
	@FXML
	private Button palv;
	@FXML
	private Button koko;
	@FXML
	private ListView<Text> list;
	
	@FXML
	private Pane maj;
	@FXML
	private Pane valinta;
	@FXML
	private Label kaikki;
	@FXML
	private TextField v‰li1;
	@FXML
	private TextField v‰li2;
	@FXML
	private ChoiceBox<String> alue;
	@FXML
	private PieChart ympyr‰;
	
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        
    }
	public void majoit() throws IOException, SQLException {
      maj.setVisible(true);
      valinta.setVisible(false);
       p‰ivit‰alueet();
       listap‰ivitys();
       ympyr‰p‰ivitys();
      
       
    }
	public void palv() throws IOException {
      
       
        
    }
public void takas() throws IOException {
      
	 maj.setVisible(false);
     valinta.setVisible(true);
        
    }
public void p‰ivit‰alueet() throws SQLException{
	 alue.getItems().clear();
	 Menu.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+Menu.kanta, Menu.nimi, Menu.salis);
		System.out.println("Tiedot saatu!");
	
   PreparedStatement preparedStatement=Menu.connection.prepareStatement("select * from toimintaalue");
     
   ResultSet resultSet=preparedStatement.executeQuery();
   String tt = new String("Kaikki");
   alue.getItems().add(tt);
   while(resultSet.next()){
        String nimi=resultSet.getString("nimi");
        String t = new String(nimi);
       alue.getItems().add(t);
       
   }
   alue.setOnAction((event) -> {
	   a=alue.getValue().toString();
   	   listap‰ivitys();
   	   
      
      
   });
   alue.setValue("Kaikki");
}




public void listap‰ivitys(){ 
	String alue;
	 String alkaa = v‰li1.getText();
	 String loppuu = v‰li2.getText();
	 String v‰li=" varattu_alkupvm between '"+alkaa+"' and '"+loppuu+"' and";
	 if(v‰li1.getText().equals("") || v‰li2.getText().equals("")) {
		  v‰li="";
	  }
	  else  {
		 v‰li= " varattu_alkupvm between '"+alkaa+"' and '"+loppuu+"' and";
	  }
	 if(a.equals("Kaikki")) {
		  alue="";
	  }
	  else  {
		  alue= " and toimintaalue.nimi='"+a+"'";
	  }
	   try {
		   list.getItems().clear();
		   
		  
		   
		   
		   
		   
		   
		   
		  
		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			PreparedStatement preparedStatement=connection.prepareStatement("SELECT toimintaalue.nimi,mokkinimi,Count(*) as m‰‰r‰ FROM vn.varaus,vn.mokki,vn.toimintaalue where"+v‰li+" mokki_id=mokki_mokki_id and mokki.toimintaalue_id=toimintaalue.toimintaalue_id"
					+alue
					+" group by mokki_mokki_id"
					+ " order by m‰‰r‰;");
	      
	        ResultSet resultSet=preparedStatement.executeQuery();
	        
	        while(resultSet.next()){
	             String mokki=resultSet.getString("mokkinimi");
	             String aluee=resultSet.getString("nimi");
	             String m‰‰r‰=resultSet.getString("m‰‰r‰");
	        
	             Text x=new Text(mokki+": "+m‰‰r‰+"       ("+aluee+")");
	            
            
                    
                     
                     
             
	
	            list.getItems().add(x);
	           
	        }
	        
	        // P‰ivitet‰‰n kokonaism‰‰r‰ viel‰...
	        
	        PreparedStatement preparedStatement2=connection.prepareStatement("SELECT Count(*) as m‰‰r‰ "
	        		+ "FROM vn.varaus,vn.mokki,vn.toimintaalue where"
	        		+v‰li+" mokki_id=mokki_mokki_id and mokki.toimintaalue_id=toimintaalue.toimintaalue_id"
					+alue
					+";");
	      
	        ResultSet resultSet2=preparedStatement2.executeQuery();
	        while(resultSet2.next()){
	             String kaik=resultSet2.getString("m‰‰r‰");
	        kaikki.setText(kaik);
	        }
	        
			} catch (SQLException e) {
			System.out.println("Error while connecting to the database");
			}
	   
	  
}
public void ympyr‰p‰ivitys() throws SQLException{
	listap‰ivitys();
	String alkaa = v‰li1.getText();
	 String loppuu = v‰li2.getText();
	
	 String v‰li=" varattu_alkupvm between '"+alkaa+"' and '"+loppuu+"' and";
	 if(v‰li1.getText().equals("") || v‰li2.getText().equals("")) {
		  v‰li="";
	  }
	  else  {
		 v‰li= " varattu_alkupvm between '"+alkaa+"' and '"+loppuu+"' and";
	  }
	
	ympyr‰.getData().clear();;
	 ObservableList<PieChart.Data> pieChartData =
             FXCollections.observableArrayList();
     ympyr‰.getData().addAll(pieChartData);
	 PreparedStatement preparedStatement2=connection.prepareStatement("SELECT  toimintaalue.nimi,Count(*) as m‰‰r‰ FROM vn.varaus,vn.mokki,vn.toimintaalue where"+v‰li+" mokki_id=mokki_mokki_id "+
			 "and mokki.toimintaalue_id=toimintaalue.toimintaalue_id Group by toimintaalue.nimi;");
   
     ResultSet resultSet2=preparedStatement2.executeQuery();
     while(resultSet2.next()){
          String nim=resultSet2.getString("nimi");
          int m‰‰r‰=resultSet2.getInt("m‰‰r‰");
          pieChartData.add(new PieChart.Data(nim,m‰‰r‰));
   
     }
	
     ympyr‰.getData().addAll(pieChartData);
     pieChartData.forEach(data ->
     data.nameProperty().bind(
             Bindings.concat(
                     data.getName(), " ",data.pieValueProperty().intValue(), " Varausta"
             )
     )
);
     
	
}

}

    
