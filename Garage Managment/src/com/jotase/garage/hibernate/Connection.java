/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.hibernate;

import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

/**
 *
 * @author <@jota_Segovia>
 */
public class Connection {

    private Session session;
    private Transaction transaccion;
    private SessionFactory sessionFactory;
    private static Connection _instance;

    /**
     * Singleton constructor
     */
    private Connection() {
        try {
            System.out.println("connecting");
            sessionFactory =new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //transaccion.rollback();
        }
    }

    /**
     * @Method
     */
    private void begin() {
        session = sessionFactory.openSession();
        transaccion = session.beginTransaction();
    }

    private void end() {

        session.close();
    }

    public void insert(Object object) {
        begin();
        try {
            session.saveOrUpdate(object);
            transaccion.commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Error :"+e.getMessage());
            System.out.println(e.getMessage());
            transaccion.rollback();
        } finally {
            JOptionPane.showMessageDialog(null, "Transaction complete");
            end();
        }


    }

    public void delete(Object object) {
        begin();
        try {
            session.delete(object);
            transaccion.commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Error :"+e.getMessage());
            transaccion.rollback();
        } finally {
            JOptionPane.showMessageDialog(null, "Transaction complete");
            end();
        }
    }

    public List getList(String _query) {
        begin();
        List<Object> list = null;
        try {
            Query query = session.createQuery(_query);
            list = query.list();
            transaccion.commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Error :"+e.getMessage());
            transaccion.rollback();
        } finally {
            JOptionPane.showMessageDialog(null, "Transaction complete");
            end();
        }
        return list;

    }

    public Object get(Object object, String _query) {
        begin();
        try {
            object = session.get(Object.class, _query);
            transaccion.commit();
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Error :"+e.getMessage());
            transaccion.rollback();
        } finally {
            JOptionPane.showMessageDialog(null, "Transaction complete");
            end();
        }

        return object;
    }

    public static synchronized Connection getInstance() {
        if (_instance == null) {
            _instance = new Connection();
        }
        return _instance;
    }
}
