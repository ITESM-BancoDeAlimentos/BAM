/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabanco;

/**
 *
 * @author Enrique
 */
import java.sql.*;
import java.io.*;
public class SistemaBanco {

    Connection conn;
    Statement stmt;
    
    public static void main(String[] args) {
        try{
            String userName="root";
            String password="password";
            String url = "jdbc:mysql://localhost/SEBB";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection (url, userName, password);
            stmt = conn.createStatement();
        }
        catch(Exception e){
            System.out.println("Cannot connect to database server");
        }
    }
    
    public boolean crearRuta(Ruta ruta, int IDVehiculo){ //requiere de todos los datos en los parametros
        try{
            String s = "INSERT INTO Rutas (Nombre, Lugares, IDVehiculo)" + " VALUES ("+ruta.getNombre()+" , '"+ ruta.getLugares() + "' , " + IDVehiculo+")"; //faltan los parametros
            System.out.println(s); 
            stmt.executeUpdate(s);
            return true;
        }
        catch(Exception e){
            System.out.println ("Cannot update database" + e );
        }
        return false;
    }
    
    /*
    Requiere de todos los datos en los parametros
    Asume que los datos que no se quieren modificar son mandados al metodo
    con los valores originales
    */
    public boolean modificaRuta(Ruta ruta, int IDVehiculo){
        try {
           String s = "UPDATE Ruta SET Nombre, Lugares, IDVehiculo = '" + ruta.getNombre() + ", " + ruta.getLugares() + ", " +  
                    IDVehiculo + "'WHERE IDSuscripcion = " + ruta.getIDRuta();
           stmt.executeUpdate(s);
           return true;
        } 
        catch (SQLException e) {
            System.out.println ("Cannot execute disposicion()" + e);
        }
        return false;
    }
    
    //Este metodo es solo para las creadas por el usuario?
    //Tenemos que revisar eso...
    public Ruta[] consultaRutas(){
        int count;
        Ruta [] r = new Ruta[1];
        try {
            stmt.executeQuery("SELECT COUNT(*) as cant FROM Rutas");
            ResultSet rs = stmt.getResultSet();
            rs.next();
            count=rs.getInt("cant");
            rs.close();
            stmt.executeQuery("Select (*) FROM Anuncios");
            rs = stmt.getResultSet();
            rs.next();
            Ruta [] rutas = new Ruta[count];
            for(int i=0; i<count; i++){
                //is this correct?
                rutas[i].setNombre(rs.getString("Nombre"));
                rutas[i].setLugares(rs.getString("Lugares"));
                //resto de los datos para cada ruta
                rs.next();
            }
            rs.close();
            return rutas;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return r;
    }
    
    //Impresion? Cual es la diferencia con Consulta Rutas?

}
