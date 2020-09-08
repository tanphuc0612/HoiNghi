/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import database.NewHibernateUtil;
import entity.Lichsudangky;
import entity.Nguoidung;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class DanhSachUserController implements Initializable {
    @FXML
    private TableView<Nguoidung>table;  
    @FXML
    private TableColumn<Nguoidung,String> userName;
    @FXML
    private TableColumn<Nguoidung,String> tenUser;
    @FXML
    private TableColumn<Nguoidung,String> email;
    @FXML
    private TableColumn<Nguoidung,String> tinhTrang;
    @FXML
    private Button home;
    @FXML
    private Button cam;
    @FXML
    private Button bo_cam;
    @FXML
    private Button search;
    @FXML
    private TextField ten_text;
    @FXML
    private TextField email_text;
    @FXML
    private TextField username_text;
    private void HomeButton(){
        home.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                ChangeHome("AdminHome.fxml");
            } catch (IOException ex) {
                Logger.getLogger(DanhSachYeuCauController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void SearchButton(){
        search.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            String s = "From Nguoidung where 1 = 1";
            if(!ten_text.getText().isEmpty()){
                s += " and tenUser = '" + ten_text.getText() + "'";
            }
            if(!email_text.getText().isEmpty()){
                s += " and email = '" + email_text.getText() + "'";
            }
            if(!username_text.getText().isEmpty()){
                s += " and userName = '" + username_text.getText() + "'";
            }
            Nguoidung n = new Nguoidung();
            List<Nguoidung> list = n.excuteQuery(s);
            ObservableList<Nguoidung> danh_sach_moi = FXCollections.observableArrayList();            
            for(Nguoidung o:list){
                danh_sach_moi.add(o);
            }
            table.setItems(danh_sach_moi);
        });
    }
    private void CamButton(){
        cam.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if(table.selectionModelProperty().get().getSelectedItem()!=null)
            {
                Nguoidung n = table.selectionModelProperty().get().getSelectedItem();
                Session s = NewHibernateUtil.getSessionFactory().openSession();   
                s.beginTransaction();
                Nguoidung l = (Nguoidung) s.get(Nguoidung.class, n.getUserName());
                if (l != null) {
                    l.setTinhTrang(false);
                    s.update(l);
                }
                s.getTransaction().commit();
                s.close();
                TableView();
            }
        });
    } 
    private void BoCamButton(){
        bo_cam.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if(table.selectionModelProperty().get().getSelectedItem() != null)
            {
                Nguoidung n = table.selectionModelProperty().get().getSelectedItem();
                Session s = NewHibernateUtil.getSessionFactory().openSession();   
                s.beginTransaction();
                Nguoidung l = (Nguoidung) s.get(Nguoidung.class, n.getUserName());
                if (l != null) {
                    l.setTinhTrang(true);
                    s.update(l);
                }
                s.getTransaction().commit();
                s.close();
                TableView();
            }
        });
    } 
    public void ChangeHome(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    @FXML
    private void TableView(){
        ObservableList<Nguoidung> danh_sach = FXCollections.observableArrayList();
        Nguoidung n = new Nguoidung();
        List<Nguoidung> list = n.excuteQuery("FROM Nguoidung");
        for(Nguoidung o : list){          
            danh_sach.add(new Nguoidung(o.getUserName(),o.getTenUser(),o.getPass(),o.getEmail(),o.getTinhTrang()));
        }
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tenUser.setCellValueFactory(new PropertyValueFactory<>("tenUser"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        //add your data to the table here.
        table.setItems(danh_sach);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableView();
        this.HomeButton();
        this.CamButton();
        this.BoCamButton();
        this.SearchButton();
    }    
    
}
