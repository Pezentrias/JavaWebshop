/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdbeans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.Product;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class Listazo {
List<Product> cdk;

    public Listazo() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        cdk= session.createQuery("FROM Product").list();
        session.close();
    }

    public List<Product> getCdk() {
        return cdk;
    }

    public void setCdk(List<Product> cdk) {
        this.cdk = cdk;
    }
    
}
