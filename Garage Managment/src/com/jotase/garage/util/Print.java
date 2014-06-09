/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jotase.garage.util;

import com.jotase.garage.POJO.Invoice;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor.Work;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.connection.DatasourceConnectionProvider;

/**
 *
 * @author <@jota_Segovia>
 */
public class Print {

    private void print(File in, HashMap parametros, String file) {
        JasperReport reporte;
        JasperPrint reporte_view;

        try {
            reporte = (JasperReport) JRLoader.loadObject(in);
            //-----------------------------------

            com.jotase.garage.hibernate.Connection.getInstance().begin();
            Connection con = com.jotase.garage.hibernate.Connection.getInstance().session.connection();





            reporte_view = JasperFillManager.fillReport(reporte, parametros, con);
            JRExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, reporte_view);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("" + file));
            exporter.exportReport();
            JasperViewer.viewReport(reporte_view, false);
//            File path = new File("" + file);
//            try {
//                Desktop.getDesktop().open(path);
//            } catch (IOException ex) {
//            }

        } catch (JRException E) {
            E.printStackTrace();
            System.out.println(E.getMessage());
        }
    }

    public void printInvoice(Invoice invoice) {
        File url = new File("Invoices/invoice.jasper");
        HashMap parametros = new HashMap();
        parametros.put("ID", invoice.getId());
         String file = "Invoices/invoice " + invoice.getId()+".pdf";
        print(url, parametros, file);
    }
}
