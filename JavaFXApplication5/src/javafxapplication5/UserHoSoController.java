/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.MoTaNgan;
import entity.Nguoidung;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class UserHoSoController implements Initializable {

    @FXML
    private Label ten;
    @FXML
    private Label pass;
    @FXML
    private Label username;
    @FXML
    private Label email;    
    @FXML
    private Button home;   
    @FXML
    private Button chinh_sua;   
    public void Init(String str){
        username.setText(str);
        Nguoidung n = new Nguoidung();
        List<Nguoidung> l = n.excuteQuery("From Nguoidung where userName = '"+str+"'");
        pass.setText(l.get(0).getPass());
        ten.setText(l.get(0).getTenUser());
        email.setText(l.get(0).getEmail());
    }
    public void ChangeHome(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        UserHomeController controll = loader.getController();
        controll.Init(username.getText());
        s.setScene(scene2);
    }
    public void ChangeChinhSua(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,800,600); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        ChinhSuaHoSoController controll = loader.getController();
        controll.Init(username.getText());
        s.setScene(scene2);
    }
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeHome("UserHome.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(UserHoSoController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    private void ChinhSuaButton(){
        chinh_sua.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeChinhSua("ChinhSuaHoSo.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(UserHoSoController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.HomeButton();
        this.ChinhSuaButton();
    }    
    
}
