/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.Diadiem;
import entity.Hoinghi;
import entity.Lichsudangky;
import entity.ThongKeLichSu;
import entity.Tochuc;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class FXMLController implements Initializable {

    @FXML
    private Label ma;
    
    @FXML
    private Label ten;
    
    @FXML
    private Label mo_ta;
    
    @FXML
    private Label dia_diem;
    @FXML
    private Label so_luong;
    
    @FXML
    private Label time;
    
    @FXML
    private ImageView anh;
    
    @FXML
    private Button danh_sach;
    
    @FXML
    private Button dang_ky_tham_gia;
    
    @FXML
    private Button dang_ky;
    
    @FXML
    private Button dang_nhap;
    @FXML
    private TableColumn<ThongKeLichSu,Integer> table_ma;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_ten;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_ngay;
    @FXML
    private TableColumn<ThongKeLichSu,String> table_dia_diem;
    @FXML
    private TableView<ThongKeLichSu> table;
    
    private int n = 0;
    /**
     * Initializes the controller class.
     */
    private void HomeButton(){
        danh_sach.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                Change("FXMLDocument.fxml");
            } catch (IOException ex) {
                Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void ThamGiaButton(){
        dang_ky_tham_gia.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String []arr = so_luong.getText().split("/");        
            if(n >= Integer.valueOf(arr[1])){
                alert.setContentText("Số lượng người đăng kí hội nghị này đã đủ");
                alert.show();
            }else{
                alert.setContentText("Bạn cần đăng nhập trước khi đăng kí");
                ButtonType buttonTypeOne = new ButtonType("Đăng nhập");
                ButtonType buttonTypeTwo = new ButtonType("Đăng kí");
                ButtonType buttonTypeThree = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    try {
                        Change("DangNhap.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (result.get() == buttonTypeTwo) {
                    try {
                        Change("Dangky.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    public void Change(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        if(string.equals("FXMLDocument.fxml")){
            scene2 = new Scene(View,790,650); 
        }
        else{
            scene2 = new Scene(View,600,400);
        }
        Stage s = JavaFXApplication5.getPrimaryStage();
        s.setScene(scene2);
    }
    @FXML
    private void DangNhapButton(){
        dang_nhap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
        dang_ky.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
    public void Init(int ma){
        Hoinghi h = new Hoinghi();
        Tochuc t = new Tochuc();
        Diadiem d = new Diadiem();
        List<Hoinghi> list1 =  h.excuteQuery("from Hoinghi  WHERE MaHoiNghi = "+String.valueOf(ma));
        List<Tochuc> list2 =  t.excuteQuery("from Tochuc where MaHoiNghi = "+list1.get(0).getMaHoiNghi());
        List<Diadiem> list3 =  d.excuteQuery("from Diadiem where MaDiaDiem = "+list2.get(0).getDiadiem().getMaDiaDiem());
        this.ma.setText("Mã: " + String.valueOf(list1.get(0).getMaHoiNghi()));
        this.ten.setText("Tên: " + list1.get(0).getTen());
        this.mo_ta.setText("Mô tả: "+list1.get(0).getMoTaChiTiet());
        this.dia_diem.setText("Địa chỉ: " + list3.get(0).getDiaChi());
        this.time.setText("Thời gian: " + String.valueOf(list2.get(0).getId().getThoiGianToChuc()));
        this.so_luong.setText("Số lượng tham dự: " + String.valueOf(list1.get(0).getNguoiThamDu())+ "/" + String.valueOf(list3.get(0).getSucChua()));
        if(list1.get(0).getAnh()!=null)
        {
            anh.setImage(new Image(getClass().getResourceAsStream("Image/"+list1.get(0).getAnh())));
        }else{
            anh.setImage(new Image(getClass().getResourceAsStream("Image/test.jpg")));                
        } 
        TableView(ma);
    }
    
    private final ObservableList<ThongKeLichSu> thong_ke = FXCollections.observableArrayList();
    @FXML
    private void TableView(int ma){
        int i = 0;
        Lichsudangky l = new Lichsudangky();
        List<Lichsudangky> list1 = l.excuteQuery("FROM Lichsudangky where maHoiNghi = " + ma);
        for(Lichsudangky o : list1){           
            thong_ke.add(new ThongKeLichSu(i+1,o.getId().getUserName(),String.valueOf(o.getNgayDangKy()),o.getTrangThai()));
            i++;
        }
        n = i;
        table_ma.setCellValueFactory(new PropertyValueFactory<>("table_ma"));
        table_ten.setCellValueFactory(new PropertyValueFactory<>("table_ten"));
        table_ngay.setCellValueFactory(new PropertyValueFactory<>("table_ngay"));
        table_dia_diem.setCellValueFactory(new PropertyValueFactory<>("table_dia_diem"));
        //add your data to the table here.
        table.setItems(thong_ke);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.HomeButton();
        this.ThamGiaButton();
        this.DangKyButton();
        this.DangNhapButton();  
    }
}
