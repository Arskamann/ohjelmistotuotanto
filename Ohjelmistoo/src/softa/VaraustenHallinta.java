package softa;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class VaraustenHallinta extends Menu {

    @FXML
    Button takas;
    @FXML
    private ListView<Button> lista;
    @FXML
    private TextField haku;

    public void menu(ActionEvent event) throws IOException { //tällä  toiminnolla pääsee takasin päävalikkoon. Tätä kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
    }
    public void listapaivitys() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + kanta, nimi, salis);
            System.out.println("Connected With the database successfully");
            //Creating Java ResultSet object
            //käytän Arskan tekemää koodia pienillä muutoksilla
            String hakuID = haku.getText(); //Ottaa haku textfieldin tekstin
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM varaus WHERE asiakas_id = ?"); //SQL haku textfieldin tiedon mukaan
            ps.setString(1, hakuID);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String varaus_id = resultSet.getString("varaus_id");
                String asiakas_id = resultSet.getString("asiakas_id");
                //Printing Results
                Button x = new Button("VarausID: " + varaus_id + " AsiakasID: " + asiakas_id);
                x.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {   // kun varauksesta klikkaa
                        //System.out.println(x.getText());
                        String sisalto = x.getText();
                        String[] sisaltoosissa = sisalto.split(" ");
                        String idd = sisaltoosissa[0];
                        //System.out.println(idd); // tällä id:llä etsitään varaus-taulusta kyseisen henkilön tiedot!

                        System.out.println(x.getText());
                    }
                });
                lista.getItems().clear();
                lista.getItems().add(x);

            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
    }
}
