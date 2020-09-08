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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Hp
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TableView<MoTaNgan> main_table;
    @FXML
    private Button main_search;
    @FXML
    private TableColumn<MoTaNgan,Integer> table_ma;
    @FXML
    private TableColumn<MoTaNgan,String> table_ten;
    @FXML
    private TableColumn<MoTaNgan,String> table_MoTa;
    @FXML
    private Button main_dangNhap;
    @FXML
    private Button main_dangKy;
    
    @FXML
    private void searchButton(){
        main_search.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(main_table.getSelectionModel().getSelectedItem()!= null)
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
    private void DangNhapButton(){
        main_dangNhap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Change("DangNhap.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    } 
    @FXML
    private void DangKyButton(){
        main_dangKy.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    Change("Dangky.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
    }   
    public void ChangeChiTiet() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HoiNghiChiTiet.fxml"));
        Parent HoiNghiChiTietView = loader.load();
        Scene scene2 = new Scene(HoiNghiChiTietView,790,800); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        MoTaNgan m = main_table.getSelectionModel().getSelectedItem();
        FXMLController controll = loader.getController();
        controll.Init(m.getTable_ma());
        s.setScene(scene2);
    }
    public void Change(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,600,400); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
        
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
        main_table.setItems(MoTaNgans);
    }

               
    private final ObservableList<MoTaNgan> MoTaNgans = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //table
        this.TableView();
        //search
        this.searchButton();
        //dang nhap
        this.DangNhapButton();
        //dang ky
        this.DangKyButton();
    }    
}
