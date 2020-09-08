package entity;
// Generated Jul 9, 2020 4:09:42 PM by Hibernate Tools 4.3.1

import database.NewHibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;




/**
 * Ad generated by hbm2java
 */
public class Ad  implements java.io.Serializable {


     private String userName;
     private String tenAd;
     private String pass;
     private String email;

    public Ad() {
    }

    public Ad(String userName, String tenAd, String pass, String email) {
       this.userName = userName;
       this.tenAd = tenAd;
       this.pass = pass;
       this.email = email;
    }
   
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getTenAd() {
        return this.tenAd;
    }
    
    public void setTenAd(String tenAd) {
        this.tenAd = tenAd;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public static List<Ad> excuteQuery(String string) {
                try{
            Session s = NewHibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Query q = s.createQuery(string);
            List<Ad> result = q.list();
            s.getTransaction().commit();
            return result;
        }catch(HibernateException e){
            System.out.println(e.getMessage());   
        }
        return null;
    }
    public void ThemAdmin(){
        Session s = NewHibernateUtil.getSessionFactory().openSession();   
        s.beginTransaction();
        s.persist(this);
        s.getTransaction().commit();
        s.close();
    }


}


