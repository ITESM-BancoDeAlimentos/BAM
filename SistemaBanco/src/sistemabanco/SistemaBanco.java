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
import java.util.ArrayList;
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
    public ArrayList <Ruta> consultaRutas(){
        int count;
        //se crea un arreglo para el return
        ArrayList <Ruta> rutas = new ArrayList <Ruta>();
        try {
            stmt.executeQuery("Select (*) FROM Rutas");
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Ruta r = new Ruta();
                r.setIDRuta(rs.getInt("IDRuta"));
                r.setNombre(rs.getString("Nombre"));
                rutas.add(r);
            }
            rs.close();
            return rutas;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return rutas;
    }
    
    //Podriamos modificar este metodo a que regrese un vector, 
    //ahorrandonos la primera query, despues lo veo
    public ArrayList <Visitas> consultaVisitas(Ruta ruta){
        int count;
        //se crea un arreglo con un solo dato para el return
        ArrayList <Visitas> visitas = new ArrayList <Visitas>();
        try {
            stmt.executeQuery("Select (*) FROM Visitas");
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Visitas v = new Visitas();
                v.setIDRuta(rs.getInt("IDRuta"));
                v.setIDSucursal(rs.getInt("IDSucursal"));
                //visitas[i].setDias(rs.getBoolean("Dias")); //Array? Boolean?
                //resto de los datos para cada ruta
                visitas.add(v);
            }
            rs.close();
            return visitas;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return visitas;
    }
    
    public ArrayList <HistorialVisitas> consultaHistorial(Ruta ruta){
        ArrayList <HistorialVisitas> hv = new ArrayList <HistorialVisitas>();
        try {
            
            stmt.executeQuery("Select (*) FROM HistorialVisitas");
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                HistorialVisitas hVisitas = new HistorialVisitas();
                hVisitas.setIDRuta(rs.getInt("IDRuta"));
                hVisitas.setIDSucursal(rs.getInt("IDSucursal"));
                hVisitas.setFecha(rs.getDate("Fecha"));
                hVisitas.setVehiculo(rs.getInt("Vehiculo"));
                hVisitas.setPiloto(rs.getInt("Piloto"));
                hVisitas.setCopiloto(rs.getInt("Copiloto"));
                hVisitas.setHoraSalida(rs.getDate("HoraSalida"));
                hVisitas.setHoraLlegada(rs.getDate("HoraLlegada"));
                hVisitas.setComentarios(rs.getString("Comentarios"));
                hv.add(hVisitas);
            }
            rs.close();
            return hv;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return hv;
    }
    //Impresion? Cual es la diferencia con Consulta Rutas?

}
