/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventanas.controller.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class DBConnection {

    private Connection con = null;

    public Connection getCon() {
        return con;
    }
    private Statement st = null;
    private String nomb_db;
    private String user;
    private String ruta;
    private String clave;
    private static DBConnection _instance;
    private Database database;

    public DBConnection() {

        String[] databasedata;
        database = new Database();
        try {
            databasedata = database.getDataBaseData();
            db(databasedata[0], databasedata[1], databasedata[2], databasedata[3]);
        } catch (FileNotFoundException ex) {
           
        } catch (IOException ex) {
           
        }


    }

    public void db(String host, String nomb_db, String ruta, String clave) {
        this.user = host;
        this.nomb_db = nomb_db;
        this.ruta = ruta;
        this.clave = clave;


    }

    public static synchronized DBConnection getInstance() {
        if (_instance == null) {
            _instance = new DBConnection();
        }
        return _instance;
    }

    public void openConnection() {
        if (con != null) {
            closeConnection();
        }
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://" + this.user + "/" + nomb_db + "",
                    ruta, clave);
            st = (Statement) con.createStatement();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
        }
    }

    public void closeConnection() {
        try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    public Statement getSt() {
        return st;
    }

    public ResultSet query(String query) throws SQLException {
        Statement stt = (Statement) con.createStatement();
        ResultSet res = stt.executeQuery(query);
        return res;
    }

    public int update(String insertQuery) throws SQLException {
        Statement stt = (Statement) con.createStatement();
        int result = stt.executeUpdate(insertQuery);
        return result;
    }

    public int insert(String insertQuery) throws SQLException {
        Statement stt = (Statement) con.createStatement();
        int result = stt.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
        ResultSet keys = stt.getGeneratedKeys();
        if (keys.next()) {
            //keys.next();
            result = keys.getInt(1);
        }

        return result;
    }

    public void delete(String insertQuery) throws SQLException {
        Statement stt = (Statement) con.createStatement();
        stt.execute(insertQuery);

    }
}