package com.jotase.garage.util;

/*@Autor Daniel Andres Soto Mestre
 *@Nombre del programa: Garchivo
 *@Fecha: 25/01/11
 *@Funcion: Genera o lee archivos .txt
 *@Seccion: C-511
 *@Nombre de la materia: Procesamiento de Datos
 */
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Archivo {

    private int BUFFER = 10485760;

    public String getDir(String a) {
        String dir = null;// = "C:\\Users\\Jeanmarie Gonzalez\\Documents\\NetBeansProjects\\Innova\\_DESCARGASWEB_MVMTOS__H00570810320130115121323.txt";
        JFileChooser op = new JFileChooser();
        op.setAcceptAllFileFilterUsed(false);
        op.setDialogTitle(a);
        op.setFileSelectionMode(JFileChooser.FILES_ONLY);
        op.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Archivo SQL (*.sql)", "sql");
        op.setFileFilter(filter1);
        int returnVal = op.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            StringBuffer fileBuffer;
            if (filter1.accept(op.getSelectedFile())) {
                File f = op.getCurrentDirectory();

                dir = op.getSelectedFile().getAbsolutePath();

            }
        }
        System.out.println(dir);
        return dir;
    }

    public String getDir(String a, String filter, String type) {
        String dir = null;// = "C:\\Users\\Jeanmarie Gonzalez\\Documents\\NetBeansProjects\\Innova\\_DESCARGASWEB_MVMTOS__H00570810320130115121323.txt";
        JFileChooser op = new JFileChooser();
        op.setAcceptAllFileFilterUsed(false);
        op.setDialogTitle(a);
        op.setFileSelectionMode(JFileChooser.FILES_ONLY);
        op.setCurrentDirectory(new File("."));
        //FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Archivo SQL (*.sql)", "sql");
        FileNameExtensionFilter filter1 = null;
        if (filter.isEmpty()) {
            filter1 = new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        } else {
            filter1 = new FileNameExtensionFilter(filter, type);
        }
        op.setFileFilter(filter1);
        op.setFileFilter(new FileNameExtensionFilter("JPG image (*.jpg) ", "jpg"));
        int returnVal = op.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            StringBuffer fileBuffer;
            if (filter1.accept(op.getSelectedFile())) {
                File f = op.getCurrentDirectory();

                dir = op.getSelectedFile().getAbsolutePath();

            }
        }
        System.out.println(dir);
        return dir;
    }

    public String setDir(String a, String filter, String type) {
        String dir = null;// = "C:\\Users\\Jeanmarie Gonzalez\\Documents\\NetBeansProjects\\Innova\\_DESCARGASWEB_MVMTOS__H00570810320130115121323.txt";
        JFileChooser op = new JFileChooser();
        op.setAcceptAllFileFilterUsed(false);
        op.setDialogTitle(a);
        op.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // op.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter1 = null;
        if (filter.isEmpty()) {
            filter1 = new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        } else {
            filter1 = new FileNameExtensionFilter(filter, type);
        }

        op.setFileFilter(filter1);
        int returnVal = op.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            StringBuffer fileBuffer;
            // File f = op.getCurrentDirectory();
            dir = op.getSelectedFile().getAbsolutePath();
            if (!dir.endsWith(".csv")) {
                dir = dir + ".csv";
            }
            System.out.println(dir);
            // JOptionPane.showMessageDialog(null, "La base de dato fue importada con exito!");
        }

        return dir;
    }

    public String setDir(String a) {
        String dir = null;// = "C:\\Users\\Jeanmarie Gonzalez\\Documents\\NetBeansProjects\\Innova\\_DESCARGASWEB_MVMTOS__H00570810320130115121323.txt";
        JFileChooser op = new JFileChooser();
        op.setAcceptAllFileFilterUsed(false);
        op.setDialogTitle(a);
        op.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // op.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Archivo SQL (*.sql)", "sql");
        op.setFileFilter(filter1);
        int returnVal = op.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            StringBuffer fileBuffer;
            // File f = op.getCurrentDirectory();
            dir = op.getSelectedFile().getAbsolutePath();
            if (!dir.endsWith(".sql")) {
                dir = dir + ".sql";
            }
            System.out.println(dir);
            // JOptionPane.showMessageDialog(null, "La base de dato fue importada con exito!");
        }

        return dir;
    }

    public String Leer_Linea(String archivo, int linea) throws FileNotFoundException, IOException {
        String salida = "";
        BufferedReader entrada = new BufferedReader(new FileReader(archivo));
        for (int i = 0; i < linea; i++) {
            salida = entrada.readLine();
        }
        return salida;
    }

    public PrintWriter creacion(String nombre) throws IOException {
        File f = new File(nombre + ".txt");
        FileWriter fw = new FileWriter(f);
        PrintWriter salida = new PrintWriter(fw);
        return salida;
    }

    public PrintWriter creacion(String nombre, String extension) throws IOException {
        File f = new File(nombre + extension);
        FileWriter fw = new FileWriter(f);
        BufferedWriter br = new BufferedWriter(fw);
        PrintWriter salida = new PrintWriter(br);
        return salida;
    }

    public PrintWriter Creacion(File archivo) throws IOException {
        FileWriter fw = new FileWriter(archivo);

        BufferedWriter br = new BufferedWriter(fw);


        PrintWriter salida = new PrintWriter(br);
        return salida;
    }

    public BufferedReader lectura(String nombre) throws FileNotFoundException {
        File f = new File(nombre + ".txt");
        FileReader fr = new FileReader(f);
        BufferedReader entrada = new BufferedReader(fr);
        return entrada;
    }

    public BufferedReader lectura(String nombre, String extension) throws FileNotFoundException {
        File f = new File(nombre + extension);
        FileReader fr = new FileReader(f);
        BufferedReader entrada = new BufferedReader(fr);
        return entrada;
    }

    public JTextArea Lectura(File archivo) {
        JTextArea areaTexto = new JTextArea();
        String linea;
        try {
            FileReader fr = new FileReader(archivo);
            try (BufferedReader entrada = new BufferedReader(fr)) {

                while ((linea = entrada.readLine()) != null) {
                    areaTexto.append(linea + "\n");
                }
            }
        } catch (FileNotFoundException ex) {
            //DRE.error("Archivo no encontrado");
        } catch (IOException e) {
        }
        return areaTexto;

    }
    /*   
     /**Metodo para leer las lineas de un archivo de texto y guardarlas en un
     arreglo de String. Argumento: El archivo a leer. Devuelve: El arreglo de
     String*
     public static String[] LeerLineas(File archivo)
     { 
     String linea[] = new String[1];
     int lineas=0;
     try {
     FileReader fr = new FileReader(archivo);
     BufferedReader entrada = new BufferedReader(fr);
                
     while (entrada.readLine() != null)
     lineas++;

     linea = new String[lineas];
                
     fr = new FileReader(archivo);
     entrada = new BufferedReader(fr);
                
     for(int i = 0; i< lineas; i++)
     linea[i] = entrada.readLine();

     entrada.close ();
     }
     catch (FileNotFoundException ex)
     {
     DRE.error("Archivo no encontrado");
     }
     catch  (IOException e ) {}
     return linea;
    
     }*/

    /**
     * Metodo para eliminar la extension del nombre de un archivo. Devuelve el
     * nombre sin la extension. *
     */
    public String quitar_ext(File file) {
        int pos = file.getName().lastIndexOf(".");
        String nombre = file.getName().substring(0, pos);
        return nombre;
    }

    /**
     * Metodo para buscar un archivo. Devuelve: Los archivos que contienen o no,
     * los archivos buscados. Argumentos: Un String con el nombre del archivo a
     * buscar. El directorio padre o raiz (donde empezara la busqueda)*
     */
    /*   public static File[] buscarArchivo(String nombre_archivo, File padre)
     {
     Arbol C = new Arbol();
     File archivos[];
     return  archivos = C.buscarArchivo(nombre_archivo, padre);
     }*/
    /**
     * Metodo para buscar un directorio. Devuelve: Los archivos que contienen o
     * no, los directorios buscados. Argumentos: Un String con el nombre del
     * directorio a buscar. El directorio padre o raiz (donde empezara la
     * busqueda)*
     */
    /*public static File[] buscarDirectorio(String nombre_directorio, File padre)
     {
     Arbol C = new Arbol();
     File archivos[];
     return  archivos = C.buscarDirectorio(nombre_directorio, padre);
     }*/
    /**
     * Metodo para buscar un archivo o directorio (en orden especifico).
     * Devuelve: el archivo que contiene o no, los directorios buscados.
     * Argumentos: un String con los nombres de los directorios a buscar*
     */
    public File buscar(String files[]) {
        int cantidad = files.length, contador = 0;
        String dirs[];
        File C = new File("C:\\");
        File sub = C;
        dirs = C.list();

        do {
            encontrado = false;
            //Busqueda del directorio o archivo
            for (int i = 0; i < dirs.length; i++) {
                if (files[contador].equalsIgnoreCase(dirs[i])) {
                    encontrado = true;
                    sub = new File(sub.getAbsolutePath() + "\\" + files[contador]);
                    contador++;
                    dirs = sub.list();
                    break;
                }
            }
            //si no se encontro
            if (encontrado == false) {
                int num_sub = 0;
                //se recorren todos los sub directorios
                inicio:
                for (int i = 0; i < dirs.length; i++) {
                    //subdirectorio actual
                    sub = new File(sub.getAbsolutePath() + "\\" + dirs[i]);

                    String lista_actual[] = sub.list();
                    for (int j = 0; j < lista_actual.length; j++) {
                        if (files[contador].equalsIgnoreCase(lista_actual[j])) {
                            encontrado = true;
                            sub = new File(sub.getAbsolutePath() + "\\" + files[contador]);
                            contador++;
                            dirs = sub.list();
                            break;
                        }
                    }
                    if (encontrado == true) {
                        break;
                    }
                    /**
                     * si no encuentra nada en el sub directorio y ya lo
                     * recorrio todo*
                     */
                    if (encontrado == false && i == dirs.length - 1) {
                        sub = new File(sub.getAbsolutePath() + "\\" + dirs[num_sub]);
                        dirs = sub.list();
                        num_sub++;
                        continue inicio;
                    }
                }
            }

            if (files.length == contador) {
                break;
            }

        } while (encontrado == true);

        return C;
    }
    //variable para verificar si se encontraron los directorios buscados
    boolean encontrado = false;

    /**
     * Metodo para copiar un archivo. Argumentos: El archivo a copiar. La
     * ubicacion donde se quiere copiar. El nombre que tendra el archivo
     * copiado*
     */
    public void copiarFile(File archivo, File nueva_ubicacion, String nombre_archivo) {
        FileChannel in;
        FileChannel out;
        if (archivo.isFile() == true && nueva_ubicacion.isDirectory() == true) {
            try {
                FileInputStream r = new FileInputStream(archivo);
                nueva_ubicacion = new File(nueva_ubicacion.getAbsolutePath() + "\\" + nombre_archivo);
                FileOutputStream w = new FileOutputStream(nueva_ubicacion);

                in = r.getChannel();
                out = w.getChannel();
                in.transferTo(0, archivo.length(), out);
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            }
        } else {
            System.out.println("Archivo o directorio invalido!");
        }
    }

    /**
     * Metodo para copiar un archivo. Argumentos: El archivo a copiar. La
     * ubicacion donde se quiere copiar. El nombre que tendra el archivo
     * copiado*
     */
    public File copiarDevolverFile(File archivo, File nueva_ubicacion, String nombre_archivo) {
        FileChannel in;
        FileChannel out;
        if (archivo.isFile() == true && nueva_ubicacion.isDirectory() == true) {
            try {
                FileInputStream r = new FileInputStream(archivo);
                nueva_ubicacion = new File(nueva_ubicacion.getAbsolutePath() + "\\" + nombre_archivo);
                FileOutputStream w = new FileOutputStream(nueva_ubicacion);

                in = r.getChannel();
                out = w.getChannel();
                in.transferTo(0, archivo.length(), out);

                in.close();
                out.close();
                r.close();
                w.close();
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            }
        } else {
            System.out.println("Archivo o directorio invalido!");
        }
        return nueva_ubicacion;
    }

    /**
     * Metodo para copiar varios archivos. Argumentos: Los archivos a copiar (en
     * un arreglo bidimensional). La ubicacion donde se quieren copiar.*
     */
    public void copiarFiles(File archivos[], File nueva_ubicacion) {
        FileChannel in;
        FileChannel out;
        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].isFile() == true && nueva_ubicacion.isDirectory() == true) {
                try {
                    FileInputStream r = new FileInputStream(archivos[i]);
                    File nueva_ubicacion2 = new File(nueva_ubicacion.getAbsolutePath() + "\\" + archivos[i].getName());
                    FileOutputStream w = new FileOutputStream(nueva_ubicacion2);

                    in = r.getChannel();
                    out = w.getChannel();
                    in.transferTo(0, archivos[i].length(), out);

                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }
            } else {
                if (!archivos[i].isFile()) {
                    archivos[i].mkdirs();
                } else {
                    System.out.println("Archivos o directorio invalidos!");
                }
                File archivos2[] = archivos[i].listFiles();
                if (archivos2 != null) {
                    copiarFiles(archivos2, archivos[i]);
                }
            }
        }
    }

    public void write(String L17, String L18, String L19, String L20, String L21, String L22, String L23, String L24, String L25,
            String L26, String L27) throws IOException {
        BufferedReader reader = null;
        try {
            String todo = "";
            File f = new File("config.txt");
            FileReader fr = new FileReader(f);
            reader = new BufferedReader(new FileReader("config.txt"));
            for (String next, line = reader.readLine(); line != null; /*line = next*/) {
                // next = reader.readLine();
                todo = todo + line + "\n";
                line = reader.readLine();

            }
            System.out.print(todo);

            String texto = todo.replace("null", "")
                    .replace("*BancoDeposito = " + Leer_Linea("config.txt", 17).substring(17), "*BancoDeposito = " + L17)
                    .replace("*BancoNC = " + Leer_Linea("config.txt", 18).substring(11), "*BancoNC = " + L18)
                    .replace("*BancoNB = " + Leer_Linea("config.txt", 19).substring(11), "*BancoNB = " + L19)
                    .replace("*xCobNC = " + Leer_Linea("config.txt", 20).substring(10), "*xCobNC = " + L20)
                    .replace("*xPagNC = " + Leer_Linea("config.txt", 21).substring(10), "*xPagNC = " + L21)
                    .replace("*xCobNB = " + Leer_Linea("config.txt", 22).substring(10), "*xCobNB = " + L22)
                    .replace("*xPagNB = " + Leer_Linea("config.txt", 23).substring(10), "*xPagNB = " + L23)
                    .replace("*InvCarg = " + Leer_Linea("config.txt", 24).substring(11), "*InvCarg = " + L24)
                    .replace("*InvDesc = " + Leer_Linea("config.txt", 25).substring(11), "*InvDesc = " + L25)
                    .replace("*InvTrans = " + Leer_Linea("config.txt", 26).substring(12), "*InvTrans = " + L26)
                    .replace("*decMonto = " + Leer_Linea("config.txt", 27).substring(12), "*InvTrans = " + L27);
            System.out.printf("esto: \n" + texto);
            overWrite(texto);

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException logOrIgnore) {
                }
            }
        }

    }

    public void overWrite(String texto) throws FileNotFoundException {
        int BUFFER = 10485760;
        BufferedReader br = new BufferedReader(new StringReader(texto));
        BufferedWriter bufferedWriter = null;
        String ln = System.getProperty("line.separator");
        try {
            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter("config.txt"));
            PrintWriter pw = new PrintWriter(bufferedWriter);


            Scanner scanner = new Scanner(texto);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                pw.write(line + "\r\n");


            }


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void EcribirFichero(File Ffichero, String SCadena) {
        try {
            //Si no Existe el fichero lo crea  
            if (!Ffichero.exists()) {
                Ffichero.createNewFile();
            }
            /*Abre un Flujo de escritura,sobre el fichero con codificacion utf-8.  
             *Además  en el pedazo de sentencia "FileOutputStream(Ffichero,true)", 
             *true es por si existe el fichero seguir añadiendo texto y no borrar lo que tenia*/
            BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero, true), "utf-8"));
            /*Escribe en el fichero la cadena que recibe la función.  
             *el string "\r\n" significa salto de linea*/
            Fescribe.write(SCadena + "\r\n");
            //Cierra el flujo de escritura  
            Fescribe.close();
        } catch (Exception ex) {
            //Captura un posible error le imprime en pantalla   
            System.out.println(ex.getMessage() + "ola k ase");
        }
    }

    public void BorrarFichero(File Ffichero) {
        try {
            /*Si existe el fichero*/
            if (Ffichero.exists()) {
                /*Borra el fichero*/
                Ffichero.delete();
                System.out.println("Fichero Borrado con Exito");
            }
        } catch (Exception ex) {
            /*Captura un posible error y le imprime en pantalla*/
            System.out.println(ex.getMessage());
        }
    }

    public void ModificarFichero(File FficheroAntiguo, String Satigualinea, String Snuevalinea) {
        /*Obtengo un numero aleatorio*/
        Random numaleatorio = new Random(3816L);
        /*Creo un nombre para el nuevo fichero apartir del 
         *numero aleatorio*/
        File f = new File("config.txt");
        String SnombFichNuev = "/auxiliar" + String.valueOf(Math.abs(numaleatorio.nextInt()) + ".txt");
        /*Crea un objeto File para el fichero nuevo*/
        File FficheroNuevo = new File(SnombFichNuev);
        System.out.println(FficheroNuevo.toString());
        try {
            /*Si existe el fichero inical*/
            if (f.exists()) {
                /*Abro un flujo de lectura*/
                BufferedReader Flee = new BufferedReader(new FileReader(f));
                String Slinea;
                /*Recorro el fichero de texto linea a linea*/
                while ((Slinea = Flee.readLine()) != null) {
                    /*Si la lia obtenida es igual al la bucada 
                     *para modificar*/
                    if (Slinea.toUpperCase().trim().equals(Satigualinea.toUpperCase().trim())) {
                        /*Escribo la nueva linea en vez de la que tenia*/
                        EcribirFichero(FficheroNuevo, Snuevalinea);
                    } else {
                        /*Escribo la linea antigua*/
                        EcribirFichero(FficheroNuevo, Slinea);
                    }
                }
                /*Obtengo el nombre del fichero inicial*/
                String SnomAntiguo = f.getName();
                /*Borro el fichero inicial*/
                BorrarFichero(f);
                /*renombro el nuevo fichero con el nombre del  
                 *fichero inicial*/
                FficheroNuevo.renameTo(FficheroAntiguo);
                /*Cierro el flujo de lectura*/
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /*Captura un posible error y le imprime en pantalla*/
            System.out.println(ex.getMessage() + "csm");
        }
    }

    /**
     * Metodo para copiar un directorio. Argumentos: El directorio a copiar. La
     * ubicacion donde se quiere copiar. El nombre que tendra el nuevo
     * directorio*
     */
    public void copiarDir(File directorio, File nueva_ubicacion, String nombre_directorio) {
        if (directorio.isDirectory() == true && nueva_ubicacion.isDirectory() == true) {
            File sub_files[] = directorio.listFiles();
            nueva_ubicacion = new File(nueva_ubicacion.getAbsolutePath() + "\\" + nombre_directorio);
            nueva_ubicacion.mkdirs();

            copiarFiles(sub_files, nueva_ubicacion);
        } else {
            System.out.println("Archivo o directorio invalido!");
        }
    }
    /**
     * METODO QUE CREA LAS CARPETAS Y ARCHIVOS NECESARIOS PARA EL FUNCIONAMIENTO
     * DE UN PROGRAMA*
     */
    /*    public static void archivosImportantes(String nombre_programa)
     {

     File ProgramFiles = new File("C:\\Program Files");
     File ArchivosDePrograma = new File("C:\\Archivos de programa");

     if(ProgramFiles.exists())
     directorio_principal(ProgramFiles, nombre_programa);
     else
     if(ArchivosDePrograma.exists())
     directorio_principal(ArchivosDePrograma, nombre_programa);
     else
     {
     ArchivosDePrograma.mkdirs();
     archivosImportantes(nombre_programa);
     }

     }*/
    /**
     * METODO PARA LA CREACION DEL DIRECTORIO PRINCIPAL DE UN PROGRAMA*
     */
    /*    private static void directorio_principal(File file, String nombre_programa)
     {
     File DIRP = new File(file.getAbsolutePath()+"\\"+nombre_programa);DRE.msj(DIRP.getAbsolutePath()+"\\MySQL", nombre_programa);
     //creacion del directorio del programa (en caso que no exista)
     if(!DIRP.exists()) DIRP.mkdirs();

     crearDirMySQL(DIRP);
     }*/
    /**
     * Crea en el directorio del programa un directorio que contendra el archivo
     * ejecutable de mySQL. NOTA: El programa debe ser ejecutado desde el mismo
     * directorio donde se encuentra el archivo ejecutable de mySQL.*
     */
    /*    private static void crearDirMySQL(File DIRP)
     {
     File mysqlExe = new File(DIRP.getAbsolutePath()+"\\MySQL");
     File copia;
     File copia2 = new File("");
     //si el archivo que contiene MySQL ya existe
     if(mysqlExe.exists());
     //si el archivo de mySQL NO existe
     else
     {
     mysqlExe = new File("");
     mysqlExe = new File(mysqlExe.getAbsolutePath()+"\\MySQL");
     //si la primera ejecucion del programa es en el directorio incorrecto
     if(!mysqlExe.exists())
     {
     DRE.msjA("Para el correcto funcionamiento del"
     + " programa, su primera ejecucion debe ser realizada\nen la"
     + " carpeta que contiene el archivo ejecutable (.jar). "
     + "Especificamente en la que se encontraba el\n programa "
     + "inicialmente", "Advertencia");
     System.exit(0);
     }
     //copiar el archivo ejecutable de mySQL
     else
     {
     copia = mysqlExe;
     copia2 = new File(DIRP.getAbsolutePath());
     Archivo.copiarDir(copia, copia2, "MySQL installers");
     }
     }
     }*/
}