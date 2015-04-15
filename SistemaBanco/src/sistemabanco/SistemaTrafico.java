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
public class SistemaTrafico {

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
    public boolean borrarSucursalARuta(Ruta ruta, int IDSucursal){
        try{
            //No estoy seguro de si se puede mandar el bit string asi nada mas...
            String s = "DELETE FROM Visitas WHERE IDRuta = " + ruta.getIDRuta();
            stmt.executeUpdate(s);
        }
        catch(Exception e){
            System.out.println("Cannot update database "+ e);
        }
        return false;
    }
    public boolean editarDiasVisita(Ruta ruta, int IDSucursal, boolean dias[]){
        try{
            //No estoy seguro de si se puede mandar el bit string asi nada mas...
            String s = "UPDATE Visitas SET Dias = "+dias+ " WHERE IDRuta = "+ruta.getIDRuta()+" AND IDSucursal = "+IDSucursal;
            stmt.executeUpdate(s);
        }
        catch(Exception e){
            System.out.println("Cannot update database "+ e);
        }
        return false;
    }
    
    //Este metodo lo podriamos hacer para que busque la ruta por el nombre viejo y luego lo cambie por el nuevo.
    //Que opinan?
    public boolean modificaRuta(int IDRuta, String nombreNuevo){
        try {
           String s = "UPDATE Ruta SET Nombre = '" + nombreNuevo + "'WHERE IDRuta = " + IDRuta;
           stmt.executeUpdate(s);
           return true;
        } 
        catch (SQLException e) {
            System.out.println ("Cannot execute disposicion()" + e);
        }
        return false;
    }
    
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
            System.out.println("Cannot consultaRutas()"+e);
        }
        return rutas;
    }
    
 
    public ArrayList <Visitas> consultaVisitas(Ruta ruta){
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
            System.out.println("Cannot consultaVisitas()"+e);
        }
        return visitas;
    }
    
    //tal vez sea bueno crear un solo metodo de impresion, es decir hacerlo mas abstracto
    public boolean imprimirVisitas(ArrayList <Visitas> visitas){
        //how do u even print
        return false;
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
            System.out.println("Cannot consultaHistorial()"+e);
        }
        return hv;
    }

    
    public ArrayList <Empleados> consultaTransportistas(){
        ArrayList <Empleados> transportistas = new ArrayList <Empleados>();
        try {
            //Buscar por area o puesto???
            stmt.executeQuery("Select (*) FROM Empleados WHERE Area = Trafico");
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Empleados emp = new Empleados();
                emp.setIDEmpleado(rs.getInt("IDEmpleado"));
                emp.setNombre(rs.getString("Nombre"));
                emp.setDireccion(rs.getString("Direccion"));
                emp.setTelefono(rs.getString("Telefono"));
                emp.setFechaNacimiento(rs.getDate("FechaNacimiento"));
                emp.setSalario(rs.getDouble("Salario"));
                emp.setArea(rs.getString("Area"));
                emp.setPuesto(rs.getString("Puesto"));
                transportistas.add(emp);
            }
            rs.close();
            return transportistas;
        }
        catch(SQLException e){
            System.out.println("Cannot consultaTransportistas()"+e);
        }
        return transportistas;
    }
    
    public boolean crearEmpleado(Empleados emp){
        try{
            String s = "INSERT INTO Empleados (Nombre, Direccion, Telefono, Salario, Area, Puesto, FechaNacimiento, FechaContratacion)" 
                    + " VALUES ('"+emp.getNombre()+"', '"+emp.getDireccion()+"', '"+emp.getTelefono()
                    + "', "+ emp.getSalario()+", '"+emp.getArea()+"', '"+emp.getPuesto()+"', "
                    +emp.getFechaNacimiento()+", "+emp.getFechaContratacion()+")";
            stmt.executeUpdate(s);
            return true;
        }
        catch(Exception e){
            System.out.println ("Cannot update database" + e );
        }
        return false;
    }
    
    //emp debe de tener la informacion completa,
    //los campos no modificados y los campos ya modificados
    public boolean modificarEmpleado(Empleados emp){
        try{
            String s = "UPDATE Empleados SET Nombre = '"+emp.getNombre()+"', Direccion = '"+emp.getDireccion()+"', Telefono = '"
                    +emp.getTelefono()+", Salario = "+ emp.getSalario() + ", '"+emp.getArea()+"', '"+emp.getPuesto()+"', "
                    +emp.getFechaNacimiento()+", "+emp.getFechaContratacion()+" WHERE Area = Trfico AND IDEmpleado = "+emp.getIDEmpleado();
            stmt.executeUpdate(s);
            return true;
        }
        catch(Exception e){
            System.out.println ("Cannot update database" + e );
        }
        return false;
    }
    
    public ArrayList <ControlCombustible> reporteGasolina(Vehiculo v){
        ArrayList <ControlCombustible> cc = new ArrayList <ControlCombustible>();
        try {
            stmt.executeQuery("Select (*) FROM ControlCombustible WHERE IDVehiculo = "+ v.getIDVehiculo());
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                ControlCombustible act = new ControlCombustible();
                act.setIDVehiculo(v.getIDVehiculo());
                act.setKilometraje(rs.getInt("Kilometraje"));
                act.setCarga(rs.getInt("Carga"));
                act.setPrecioGas(rs.getInt("PrecioGas"));
                act.setNoTicket(rs.getInt("NoTicket"));
                act.setFecha(rs.getDate("Fecha"));
                act.setFormaPago(rs.getString("FormaPago"));
                act.setCombustible(rs.getString("Combustible"));
                cc.add(act);
            }
            rs.close();
            return cc;
        }
        catch(SQLException e){
            System.out.println("Cannot getAnuncios()"+e);
        }
        return cc;
    }

}
