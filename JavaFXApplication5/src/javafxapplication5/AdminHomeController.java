/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.Hoinghi;
import entity.MoTaNgan;
import entity.ThongKeLichSu;
import entity.Tochuc;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class AdminHomeController implements Initializable {

    @FXML
    private Button sua;   
    @FXML
    private Button danh_sach;  
    @FXML
    private Button them;  
    @FXML
    private Button user;  
    @FXML
    private Button dang_xuat; 
    @FXML
    private Button chi_tiet;  
    @FXML
    private TableView<ThongKeLichSu>table;  
    @FXML
    private TableColumn<ThongKeLichSu,Integer> table_ma;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_ten;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_ngay;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_dia_diem;  
    
    public void ChangeChiTiet() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserHoiNghiChiTiet.fxml"));
        Parent HoiNghiChiTietView = loader.load();
        Scene scene2 = new Scene(HoiNghiChiTietView,800,800); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        ThongKeLichSu m = table.getSelectionModel().getSelectedItem();
        UserHoiNghiChiTietController controll = loader.getController();
        controll.Init(m.getTable_ma(),"admin");
        s.setScene(scene2);
    }
    @FXML
    private void ChiTietButton(){
        chi_tiet.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(table.getSelectionModel().getSelectedItem()!= null)
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
    private void SuaButton(){
        sua.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(table.getSelectionModel().getSelectedItem()!=null)
                {
                if(table.getSelectionModel().getSelectedItem().getTable_ngay().compareTo(String.valueOf(java.time.LocalDate.now()))>0)
                {
                    try {
                        ChangeSua("SuaHoiNghi.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Không thể sửa hội nghị đã diễn ra");
                    alert.show();
                }
                }
           }
        });
    }
    private void ThemHoiNghiButton(){
        them.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                Change("ThemHoiNghi.fxml");
            } catch (IOException ex) {
                Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void DanhSachUserButton(){
        user.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                Change("DanhSachUser.fxml");
            } catch (IOException ex) {
                Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void DanhSachButton(){
        danh_sach.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                ChangeDanhSach("DanhSachYeuCau.fxml");
            } catch (IOException ex) {
                Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void DangXuatButton(){
        dang_xuat.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                Change("FXMLDocument.fxml");
            } catch (IOException ex) {
                Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    public void ChangeSua(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,850,500); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        SuaHoiNghiController controll = loader.getController();
        controll.Init(table.getSelectionModel().getSelectedItem().getTable_ma());
        s.setScene(scene2);
    }
   public void ChangeDanhSach(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,1040,550); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    } 
   public void Change(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    private final ObservableList<ThongKeLichSu> thong_ke = FXCollections.observableArrayList();
   
    @FXML
    private void TableView(){
        int i = 0;
        Hoinghi h = new Hoinghi();
        Tochuc t = new Tochuc();
        List<Tochuc> list1 = t.excuteQuery("FROM Tochuc");
        for(Tochuc o : list1){
            thong_ke.add(new ThongKeLichSu(o.getHoinghi().getMaHoiNghi(),o.getHoinghi().getTen(),String.valueOf(o.getId().getThoiGianToChuc()),o.getDiadiem().getDiaChi()));
            i++;
        }
        table_ma.setCellValueFactory(new PropertyValueFactory<>("table_ma"));
        table_ten.setCellValueFactory(new PropertyValueFactory<>("table_ten"));
        table_ngay.setCellValueFactory(new PropertyValueFactory<>("table_ngay"));
        table_dia_diem.setCellValueFactory(new PropertyValueFactory<>("table_dia_diem"));
        //add your data to the table here.
        table.setItems(thong_ke);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.TableView();
        this.SuaButton();
        this.DangXuatButton();
        this.ThemHoiNghiButton();
        this.DanhSachButton();
        this.DanhSachUserButton();
        this.ChiTietButton();
    }    

}
