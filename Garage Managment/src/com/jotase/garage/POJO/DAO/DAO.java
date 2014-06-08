/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.POJO.DAO;

import com.jotase.garage.hibernate.Connection;
import java.util.List;

/**
 *
 * @author javierjose
 */
public class DAO<T> {

    Connection connection = Connection.getInstance();
    
    public boolean insert(T t){
        return connection.insert(t);
    }
    public boolean update(T t) {
        return connection.update(t);
    }

    public T get(T t, String query) {
        setWhere(query);
        return (T) connection.get(t, query);
    }

    public boolean delete(T t) {
        return connection.delete(t);
    }

    public List getList(String query) {
        setWhere(query);
        return connection.getList(query);
    }

    private String setWhere(String query) {
        query = (query != null) ? (" where " + query) : "";
        return query;
    }
}
