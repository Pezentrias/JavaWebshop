/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.Client;
import pojos.Order;
import pojos.Orderitem;
import pojos.Product;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class Kosar {

   private List<Orderitem> kosarList;
   private List<Client> ugyfelek;
   private Client ugyfel;
   private int kivUgyfelId;
   private String uzenet;
   
   
    public Kosar() {
        kosarList=new  ArrayList<>();
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        ugyfelek=session.createQuery("From Client").list();
        session.close();
        
    }
public void megrendel (){
    Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
    
    Client c = (Client) session.get(Client.class, kivUgyfelId);
    //session.createQuery(FROM Tag WHERE id=0)
    
    Order o =new Order(c,false,new Date(), new HashSet<>());
    for (Orderitem oi : kosarList) {
        oi.setOrder(o);   
    }
    session.beginTransaction();
    session.save(o);
    session.getTransaction().commit();
    
    session.beginTransaction();
    for (Orderitem oi : kosarList) {
        session.save(oi);   
    }
    session.getTransaction().commit();
    session.close();
    kosarList=new ArrayList<>();
    uzenet="MEGRENDELÉSÉT MENTETTÜK, KÜLDÜNK E-MAILT ÖNNEK!";
    
    
}
    
    public void hozzaad(Product cd){
        boolean ujElem=true;
        for (Orderitem o : kosarList) {
            if(o.getProduct().equals(cd)){
                mennyNovel(o);
                ujElem=false;
                break;
              }
            }
               if(ujElem) {
              kosarList.add(new Orderitem(null, cd, 1, cd.getPrice()));  }
         
      
    }
    
    public void torol(Orderitem oi){
        kosarList.remove(oi);
    }
    public int getKosarDarabszam(){
        int szamlalo=0;
         for (Orderitem oi : kosarList) {
            szamlalo+=oi.getQty();
            
        } return szamlalo;
    }
    
    public void mennyNovel (Orderitem oi){
        oi.setQty(oi.getQty()+1);
        oi.setPrice(oi.getQty()*oi.getProduct().getPrice());
    }
    
     public void mennyCsokkent (Orderitem oi){
        if(oi.getQty()>1){
             oi.setQty(oi.getQty()-1);
            oi.setPrice(oi.getQty()*oi.getProduct().getPrice());
           
        }
        else{
            torol(oi);
        }
    }
    
    public double getKosarErtek(){
        double osszeg=0;
        for (Orderitem orderitem : kosarList) {
           osszeg+= orderitem.getPrice();
        }
     
        return osszeg;
    }

    public int getKivUgyfelId() {
        return kivUgyfelId;
    }

    public void setKivUgyfelId(int kivUgyfelId) {
        this.kivUgyfelId = kivUgyfelId;
    }

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }
    
    
    
    
    public List<Orderitem> getKosarList() {
        return kosarList;
    }

    public void setKosarList(List<Orderitem> kosarList) {
        this.kosarList = kosarList;
    }

    public List<Client> getUgyfelek() {
        return ugyfelek;
    }

    public void setUgyfelek(List<Client> ugyfelek) {
        this.ugyfelek = ugyfelek;
    }

    public Client getUgyfel() {
        return ugyfel;
    }

    public void setUgyfel(Client ugyfel) {
        this.ugyfel = ugyfel;
    }
    
    
    
}
