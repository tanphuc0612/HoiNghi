/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import entity.Ad;
import entity.Diadiem;
import entity.Hoinghi;
import entity.Lichsudangky;
import entity.LichsudangkyId;
import entity.ThongKeLichSu;
import entity.Tochuc;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class UserHoiNghiChiTietController implements Initializable {

    @FXML
    private Label ma;
    
    @FXML
    private Label ten;
    
    @FXML
    private Label mo_ta;
    
    @FXML
    private Label dia_diem;
    
    @FXML
    private Label time; 
    
    @FXML
    private Label so_luong;
    
    @FXML
    private ImageView anh;
    
    @FXML
    private Button danh_sach;
    
    @FXML
    private Button dang_ky_tham_gia;
    
    @FXML
    private Label user;

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
    private boolean admin = false; 
    /**
     * Initializes the controller class.
     */
    private int n = 0;
    private void HomeButton(){
        danh_sach.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(admin){
                    try {
                        Change("AdminHome.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(UserHoiNghiChiTietController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }else{
                    try {
                        Change("UserHome.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(UserHoiNghiChiTietController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
           }
        });
    }
    private void ThamGiaButton(){
        dang_ky_tham_gia.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                LichsudangkyId l = new LichsudangkyId();
                Lichsudangky lich = new Lichsudangky();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                String []arr = so_luong.getText().split("/");
                List<Lichsudangky> list =  lich.excuteQuery("FROM Lichsudangky where MaHoiNghi = "+ ma.getText()+" and username = '"+user.getText()+"'");
                if(!list.isEmpty()){
                    alert.setContentText("Bạn đã đăng ký hội nghị này trước đây");   
                    alert.show();
                }
                else if(n >= Integer.valueOf(arr[1])){
                    alert.setContentText("Số lượng người đăng kí hội nghị này đã đủ");
                    alert.show();
                }
                else{
                    lich.setNgayDangKy(Date.valueOf(java.time.LocalDate.now()));
                    lich.setTrangThai("Chờ duyệt");
                    l.setMaHoiNghi(Integer.parseInt(ma.getText()));
                    l.setUserName(user.getText());
                    lich.setId(l);
                    lich.DangKiThamGia();
                    alert.setContentText("đăng ký hội nghị này thành công");   
                    alert.show();
                    TableView(Integer.valueOf(ma.getText()));
                }
           }
        });
    }
    public void Change(String string) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent View = loader.load();
        Scene scene2;
        scene2 = new Scene(View,790,650); 
        Stage s = JavaFXApplication5.getPrimaryStage();
        if(!admin){
            UserHomeController controll = loader.getController();
            controll.Init(user.getText());
        }
        s.setScene(scene2);
    }
    public void Init(int ma, String username){
        Hoinghi h = new Hoinghi();
        Tochuc t = new Tochuc();
        Diadiem d = new Diadiem();
        List<Hoinghi> list1 =  h.excuteQuery("from Hoinghi  WHERE MaHoiNghi = "+String.valueOf(ma));
        List<Tochuc> list2 =  t.excuteQuery("from Tochuc where MaHoiNghi = "+list1.get(0).getMaHoiNghi());
        List<Diadiem> list3 =  d.excuteQuery("from Diadiem where MaDiaDiem = "+list2.get(0).getDiadiem().getMaDiaDiem());
        this.ma.setText(String.valueOf(list1.get(0).getMaHoiNghi()));
        this.ten.setText(list1.get(0).getTen());
        this.mo_ta.setText(list1.get(0).getMoTaChiTiet());
        this.dia_diem.setText(list3.get(0).getDiaChi());
        this.time.setText(String.valueOf(list2.get(0).getId().getThoiGianToChuc()));
        this.so_luong.setText(String.valueOf(list1.get(0).getNguoiThamDu())+ "/" + String.valueOf(list3.get(0).getSucChua()));
        if(list1.get(0).getAnh() != null)
        {
            if(list1.get(0).getAnh().isEmpty()){
                anh.setImage(new Image(getClass().getResourceAsStream("Image/test.jpg")));                    
            }else{
                anh.setImage(new Image(getClass().getResourceAsStream("Image/"+list1.get(0).getAnh())));
            }
        }else{
            anh.setImage(new Image(getClass().getResourceAsStream("Image/test.jpg")));                
        } 
        TableView(ma);
        user.setText(username);
        List<Ad> list = Ad.excuteQuery("From Ad where userName = '"+username+"'");
        if(!list.isEmpty()){
            dang_ky_tham_gia.setVisible(false);
            admin = true;
        }
    }
    
    @FXML
    public void TableView(int ma){
        int i = 0;
        ObservableList<ThongKeLichSu> thong_ke = FXCollections.observableArrayList();
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
    }     
    
}
