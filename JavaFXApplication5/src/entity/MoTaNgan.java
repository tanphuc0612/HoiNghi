/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hp
 */
public class MoTaNgan{
        private  SimpleIntegerProperty  table_ma;
        private  SimpleStringProperty  table_ten;
        private  SimpleStringProperty  table_MoTa;
        
        public MoTaNgan(Integer table_ma, String table_ten, String table_MoTa) {
        this.table_ma = new SimpleIntegerProperty(table_ma);
        this.table_ten = new SimpleStringProperty(table_ten);
        this.table_MoTa = new SimpleStringProperty(table_MoTa);
        }
        
    public int getTable_ma() {
        return table_ma.get();
    }

    public void setTable_ma(int table_ma) {
        this.table_ma = new SimpleIntegerProperty(table_ma);
    }

    public String getTable_ten() {
        return table_ten.get();
    }

    public void setTable_ten(String table_ten) {
        this.table_ten = new SimpleStringProperty(table_ten);
    }

    public String getTable_MoTa() {
        return table_MoTa.get();
    }

    public void setTable_MoTa(String table_MoTa) {
        this.table_MoTa = new SimpleStringProperty(table_MoTa);
    }
 }