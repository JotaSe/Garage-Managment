/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.controller;

import java.util.List;

/**
 *
 * @author <@jota_Segovia>
 */
public interface ControllerInterface <T>{
    
    /**
     *
     * @param b
     */
    public void enable(boolean b);
    public void edit();
    public void save(T t);
    public void delete(T t);
    public List<T> getList(String query);
    public void set(T t);
    public T get();
    public void clean();
    public void search();
    public void setEditFirst();
    
    
}
