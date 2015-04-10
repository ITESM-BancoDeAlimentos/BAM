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
            String s = "INSERT INTO Rutas (Nombre)" + " VALUES ('"+ruta.getNombre()+"')"; //faltan los parametros
            stmt.executeUpdate(s);
            return true;
        }
        catch(Exception e){
            System.out.println ("Cannot update database" + e );
        }
        return false;
    }
    
    public boolean agregarSucursalARuta(Ruta ruta, int IDSucursal, boolean dias[]){
        try{
            //No estoy seguro de si se puede mandar el bit string asi nada mas...
            String s = "INSERT INTO Visitas (IDRuta, IDSucursal, Dias)" + " VALUES ("+ruta.getIDRuta()+", "+IDSucursal +", "+dias+")";
            stmt.executeUpdate(s);
        }
        catch(Exception e){
            System.out.println("Cannot update database "+ e);
        }
        return false;
    }
    
    //Ruta contiene el nuevo valor y el ID viejo
    //Este metodo lo podriamos hacer para que busque la ruta por el nombre viejo y luego lo cambie por el nuevo.
    //Que opinan?
    public boolean modificaRuta(Ruta ruta){
        try {
           String s = "UPDATE Ruta SET Nombre = '" + ruta.getNombre() + "'WHERE IDRuta = " + ruta.getIDRuta();
           stmt.executeUpdate(s);
           return true;
        } 
        catch (SQLException e) {
            System.out.println ("Cannot execute disposicion()" + e);
        }
        return false;
    }
    
    //Podriamos modificar este metodo a que regrese un vector, 
    //ahorrandonos la primera query, despues lo veo
    public Ruta[] consultaRutas(){
        int count;
        //se crea un arreglo para el return
        Ruta [] r = new Ruta[1];
        try {
            stmt.executeQuery("SELECT COUNT(*) as cant FROM Rutas");
            ResultSet rs = stmt.getResultSet();
            rs.next();
            count=rs.getInt("cant");
            rs.close();
            stmt.executeQuery("Select (*) FROM Rutas");
            rs = stmt.getResultSet();
            rs.next();
            Ruta [] rutas = new Ruta[count];
            for(int i=0; i<count; i++){
                rutas[i].setIDRuta(rs.getInt("IDRuta"));
                rutas[i].setNombre(rs.getString("Nombre"));
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
    
    //Podriamos modificar este metodo a que regrese un vector, 
    //ahorrandonos la primera query, despues lo veo
    public Visitas[] consultaVisitas(Ruta ruta){
        int count;
        //se crea un arreglo con un solo dato para el return
        Visitas [] v = new Visitas[1];
        try {
            stmt.executeQuery("SELECT COUNT(*) as cant FROM Visitas");
            ResultSet rs = stmt.getResultSet();
            rs.next();
            count=rs.getInt("cant");
            rs.close();
            stmt.executeQuery("Select (*) FROM Visitas");
            rs = stmt.getResultSet();
            rs.next();
            Visitas [] visitas = new Visitas[count];
            for(int i=0; i<count; i++){
                visitas[i].setIDRuta(rs.getInt("IDRuta"));
                visitas[i].setIDSucursal(rs.getInt("IDSucursal"));
                //visitas[i].setDias(rs.getBoolean("Dias")); //Array? Boolean?
                //resto de los datos para cada ruta
                rs.next();
            }
            rs.close();
            return visitas;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return v;
    }
    
    public HistorialVisitas[] consultaHistorial(Ruta ruta){
        int count;
        //se crea un arreglo con un solo dato para el return
        HistorialVisitas [] hv = new HistorialVisitas[1];
        try {
            stmt.executeQuery("SELECT COUNT(*) as cant FROM HistorialVisitas");
            ResultSet rs = stmt.getResultSet();
            rs.next();
            count=rs.getInt("cant");
            rs.close();
            stmt.executeQuery("Select (*) FROM HistorialVisitas");
            rs = stmt.getResultSet();
            rs.next();
            HistorialVisitas [] hVisitas = new HistorialVisitas[count];
            for(int i=0; i<count; i++){
                hVisitas[i].setIDRuta(rs.getInt("IDRuta"));
                hVisitas[i].setIDSucursal(rs.getInt("IDSucursal"));
                hVisitas[i].setFecha(rs.getDate("Fecha"));
                hVisitas[i].setVehiculo(rs.getInt("Vehiculo"));
                hVisitas[i].setPiloto(rs.getInt("Piloto"));
                hVisitas[i].setCopiloto(rs.getInt("Copiloto"));
                hVisitas[i].setHoraSalida(rs.getDate("HoraSalida"));
                hVisitas[i].setHoraLlegada(rs.getDate("HoraLlegada"));
                hVisitas[i].setComentarios(rs.getString("Comentarios"));
                //visitas[i].setDias(rs.getBoolean("Dias")); //Array? Boolean?
                //resto de los datos para cada ruta
                rs.next();
            }
            rs.close();
            return visitas;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return v;
    }
    //Impresion? Cual es la diferencia con Consulta Rutas?

}
