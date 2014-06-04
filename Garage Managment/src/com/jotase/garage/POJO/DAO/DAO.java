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

    public void update(T t) {
        connection.insert(t);
    }

    public T get(T t, String query) {
        setWhere(query);
        return (T) connection.get(t, query);
    }

    public void delete(T t) {
        connection.delete(t);
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
