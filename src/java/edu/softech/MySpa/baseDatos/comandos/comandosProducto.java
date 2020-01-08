// Comandos CRUD para la tabla Producto
package edu.softech.MySpa.baseDatos.comandos;

import edu.softech.MySpa.baseDatos.conexionBasesDatos;
import edu.softech.MySpa.modelo.Producto;
import edu.softech.MySpa.modelo.Producto_Sucursal;
import edu.softech.MySpa.modelo.Sucursal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Víctor A. Santillán Mtz.
 */
public class comandosProducto {

    // Objetos para ejecutar instrucciones SQL
    PreparedStatement ps;

    // Objeto para almacenar consultas SQL
    ResultSet rs;

    // Objeto para ejecutar procedimientos almacenados
    CallableStatement cs;

    // Objeto para la conexion a base de datos
    conexionBasesDatos conn = new conexionBasesDatos();

    // String que almacena las instruciones SQL/procedimientos almacenados
    String query;

    /**
     * Inserta una nuevo producto en la base de datos.
     * @param producto_sucursal con los atributos nombre, marca y precioUso (Producto),
     * idSucursal (Sucursal) y stock(Producto_Sucursal).
     * @return 
     * @throws java.sql.SQLException si ocurrio un error al insertar el producto
     * @throws Exception
     */
    public Producto_Sucursal registarProducto(Producto_Sucursal producto_sucursal) throws SQLException, Exception {

        // Query a ejecutar
        query = "CALL registrarProducto(?, ?, ?, ?, ?, ?, ?)";

        // Conexion a base de datos
        conn.Conectar();

        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada al query
        cs.setString("var_nombre", producto_sucursal.getProducto().getNombre());
        cs.setString("var_marca", producto_sucursal.getProducto().getMarca());
        cs.setFloat("var_precioUso", producto_sucursal.getProducto().getPrecioUso());
        cs.setInt("var_idSucursal", producto_sucursal.getSucursal().getIdSucursal());
        cs.setInt("var_stock", producto_sucursal.getStock());

        // Establecemos los parametros de salida
        cs.registerOutParameter("var_idProducto", Types.INTEGER);
        cs.registerOutParameter("var_estatus", Types.INTEGER);

        // Ejecutamos el query
        cs.execute();

        // Obtenemos los parametros de salida y los empatamos al objeto Producto
        int var_idProducto = cs.getInt("var_idProducto");
        int var_estatus = cs.getInt("var_estatus");
        
        producto_sucursal.getProducto().setIdProducto(var_idProducto);
        producto_sucursal.getProducto().setEstatus(var_estatus);

        // Finalizamos la conexion
        conn.Desconectar();

        // Retornamos el objeto
        return producto_sucursal;

    }

    /**
     * Actualiza un producto
     * @param producto_sucursal
     * @return
     * @throws Exception
     */
    public boolean modificarProducto(Producto_Sucursal producto_sucursal) throws Exception {

        // Query a ejecutar
        query = "CALL modificarProducto(?, ?, ?, ?, ?, ?, ?)";

        // Conexion a base de datos
        conn.Conectar();
        
        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada al query
        cs.setInt("var_idProducto", producto_sucursal.getProducto().getIdProducto());
        cs.setString("var_nombre", producto_sucursal.getProducto().getNombre());
        cs.setString("var_marca", producto_sucursal.getProducto().getMarca());
        cs.setFloat("var_precioUso", producto_sucursal.getProducto().getPrecioUso());
        cs.setInt("var_idSucursal", producto_sucursal.getSucursal().getIdSucursal());
        cs.setInt("var_stock", producto_sucursal.getStock());
        
        // Colocamos el parametro de salida que nos indicara
        // sí la actualización fue exitosa.
        cs.registerOutParameter("respuesta", Types.BOOLEAN);

        // Ejecutamos el query.
        cs.execute();
        
        // Obtenemos la respuesta y la retornamos
        boolean resultado = cs.getBoolean("respuesta");
        conn.Desconectar();
        return resultado;

    }

    /**
     * Altera el estatus de un producto; lo cambia a 2 (inactivo).
     * @param producto_sucursal
     * @return
     * @throws Exception
     */
    public Producto_Sucursal borrarProducto(Producto_Sucursal producto_sucursal) throws Exception {

         // Query a ejecutar
         query = "CALL borrarProducto(?, ?, ?, ?, ?, ?, ?, ?)";

        // Conexion a base de datos
        conn.Conectar();

        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada en el query
        cs.setInt("var_idProducto", producto_sucursal.getProducto().getIdProducto());

        // Colocamos los parametros de salida en el query
        cs.registerOutParameter("var_nombre", Types.VARCHAR);
        cs.registerOutParameter("var_marca", Types.VARCHAR);
        cs.registerOutParameter("var_estatus", Types.INTEGER);
        cs.registerOutParameter("var_precioUso", Types.FLOAT);
        cs.registerOutParameter("var_idSucursal", Types.INTEGER);
        cs.registerOutParameter("var_stock", Types.INTEGER);
        cs.registerOutParameter("respuesta", Types.BOOLEAN);

        // Ejecutamos el query
        cs.execute();
        
        // ¿Logro hacer la actualización?
        if(cs.getBoolean("respuesta")){
            // Obtenemos los parametros de salida...
            String var_nombre = cs.getString("var_nombre");
            String var_marca = cs.getString("var_marca");
            int var_estatus = cs.getInt("var_estatus");
            float var_precioUso = cs.getFloat("var_precioUso");
            int var_idSucursal = cs.getInt("var_idSucursal");
            int var_stock = cs.getInt("var_stock");
            // ... y los empatamos a producto y sucursal de producto_sucursal
            producto_sucursal.getProducto().setNombre(var_nombre);
            producto_sucursal.getProducto().setMarca(var_marca);
            producto_sucursal.getProducto().setEstatus(var_estatus);
            producto_sucursal.getProducto().setPrecioUso(var_precioUso);
            producto_sucursal.getSucursal().setIdSucursal(var_idSucursal);
            producto_sucursal.setStock(var_stock);
        } else {
            return null;
        }

        // Finalizamos la conexion
        conn.Desconectar();

        // Retornamos el objeto Producto
        return producto_sucursal;
    }

    /**
     * Consulta que devuelve un unico producto
     * @param producto_sucursal
     * @return
     * @throws SQLException
     */
    public Producto_Sucursal buscarProducto(Producto_Sucursal producto_sucursal) throws SQLException, Exception {
        
        // Query a ejecutar
        query = "SELECT * FROM v_productosucursal WHERE idProducto = ?";
        
        // Conexion a base de datos
        conn.Conectar();
        
        // Preparamos el query
        ps = conn.getConexión().prepareCall(query);
        ps.setString(1, Integer.toString(producto_sucursal.getProducto().getIdProducto()));

        // Ejecutamos el query
        rs = ps.executeQuery();
        
        // Obtenemos los campos y los asignamos al producto
        if(rs.next()){
            producto_sucursal.getProducto().setNombre(rs.getString("nombre"));
            producto_sucursal.getProducto().setMarca(rs.getString("marca"));
            producto_sucursal.getProducto().setEstatus(rs.getInt("estatus"));
            producto_sucursal.getProducto().setPrecioUso(rs.getFloat("precioUso"));
            producto_sucursal.getSucursal().setIdSucursal(rs.getInt("idSucursal"));
            producto_sucursal.setStock(rs.getInt("stock"));
        }
        
        // Retornamos el producto con sus atributos.
        return producto_sucursal;

    }
    
    /**
     * Devuleve una lista de productos.
     * @return
     * @throws Exception 
     */
    public Queue buscarProductos() throws Exception {

        // Lista de productos
        Queue<Producto_Sucursal> productos = new LinkedList<>();

        // Hacemos una consulta de la vista de los productos
        query = "SELECT * FROM v_productosucursal";

        // Nos conectamos a la base de datos, preparamos y ejecutamos el query
        conn.Conectar();
        ps = conn.getConexión().prepareCall(query);
        rs = ps.executeQuery();

        
        // Almacenamos todos los productos en una lista, mientras exista un siguiente registro.
        // Auxiliar para los registros devueltos.
        Producto_Sucursal aux;

        while (rs.next()) {
            // Crea un objeto Sucursal el cual contendra idSucursal
            Sucursal sucursal = new Sucursal();
            sucursal.setIdSucursal(rs.getInt("idSucursal"));
            // Crea el objeto Producto_Sucursal a listar.
            aux = new Producto_Sucursal(
                    sucursal,
                    new Producto (  rs.getInt("idProducto"),
                                    rs.getString("nombre"),
                                    rs.getString("marca"),
                                    rs.getInt("estatus"),
                                    rs.getFloat("precioUso")),
                    rs.getInt("stock")
            );
            // Se almacena en la lista
            productos.add(aux);
        }

        // Finalmente, nos desconectamos de la base de datos y devolvemos la lista
        conn.Desconectar();
        return productos;
    }

    
}
