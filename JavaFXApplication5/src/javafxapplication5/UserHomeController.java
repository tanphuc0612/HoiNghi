/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.Hoinghi;
import entity.MoTaNgan;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class UserHomeController implements Initializable {

    @FXML
    private Label user_home_name;
    @FXML
    private Button user_search;
    @FXML
    private Button xem_ho_so;   
    @FXML
    private Button chinh_sua; 
    @FXML
    private Button lich_su;     
    @FXML
    private Button dang_xuat;    
    @FXML
    private TableView<MoTaNgan> user_table;  
    @FXML
    private TableColumn<MoTaNgan,Integer> table_ma;
    @FXML
    private TableColumn<MoTaNgan,String> table_ten;
    @FXML
    private TableColumn<MoTaNgan,String> table_MoTa;
    
    public void Init(String str){
        user_home_name.setText(str);
    }
    
    @FXML
    private void searchButton(){
        user_search.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(user_table.getSelectionModel().getSelectedItem()!= null)
                {
                    try {
                        ChangeChiTiet();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
           }
        });
    }
    @FXML
    private void ChinhSuaButton(){
        chinh_sua.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeChinhSua("ChinhSuaHoSo.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    private void LichSuButton(){
        lich_su.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeLichSu("LichSuDangKy.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    
    private void DangXuatButton(){
        dang_xuat.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeDangXuat("FXMLDocument.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    @FXML
    private void HoSoButton(){
        xem_ho_so.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    ChangeHoSo("UserHoSo.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }
    public void ChangeDangXuat(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    public void ChangeHoSo(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,600,400); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        UserHoSoController controll = loader.getController();
        controll.Init(user_home_name.getText());
        s.setScene(scene2);
    }
    public void ChangeLichSu(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,800,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        LichSuDangKyController controll = loader.getController();
        controll.Init(user_home_name.getText());
        s.setScene(scene2);
    }
    public void ChangeChinhSua(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,700,500); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        ChinhSuaHoSoController controll = loader.getController();
        controll.Init(user_home_name.getText());
        s.setScene(scene2);
    }
    public void ChangeChiTiet() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserHoiNghiChiTiet.fxml"));
        Parent HoiNghiChiTietView = loader.load();
        Scene scene2 = new Scene(HoiNghiChiTietView,800,800); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        MoTaNgan m = (MoTaNgan) user_table.getSelectionModel().getSelectedItem();
        UserHoiNghiChiTietController controll = loader.getController();
        controll.Init(m.getTable_ma(),user_home_name.getText());
        s.setScene(scene2);
    }
    private final ObservableList<MoTaNgan> MoTaNgans = FXCollections.observableArrayList();
    
    @FXML
    private void TableView(){
        Hoinghi a = new Hoinghi();
        List<Hoinghi> list = a.excuteQuery("FROM Hoinghi");
        for(Hoinghi o : list){
            MoTaNgans.add(new MoTaNgan(o.getMaHoiNghi(),o.getTen(),o.getMoTaNgan()));
        }
        table_ma.setCellValueFactory(new PropertyValueFactory<>("table_ma"));
        table_ten.setCellValueFactory(new PropertyValueFactory<>("table_ten"));
        table_MoTa.setCellValueFactory(new PropertyValueFactory<>("table_MoTa"));
        //add your data to the table here.
        user_table.setItems(MoTaNgans);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableView();
        this.searchButton();
        this.HoSoButton();
        this.ChinhSuaButton();
        this.LichSuButton();
        this.DangXuatButton();
    }    
    
}
