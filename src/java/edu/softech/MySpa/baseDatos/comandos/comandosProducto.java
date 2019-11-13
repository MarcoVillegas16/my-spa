// Comandos CRUD para la tabla Producto
package edu.softech.MySpa.baseDatos.comandos;

import edu.softech.MySpa.baseDatos.conexionBasesDatos;
import edu.softech.MySpa.modelo.Producto;
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

    // Objetos para ejcutar instrucciones SQL
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
     *
     * @param producto
     * @return
     * @throws Exception
     */
    public Producto registarProducto(Producto producto) throws Exception {

        /*
        Se crea un registro en la tabla Producto.
        Se llama a un procedimiento almacenado para el registro.
        Se devuleve el objeto Producto con lo atributos
        @idProducto y @estatus actualizados
         */
        // Llama al procedimiento almacenado para registrar un nuevo producto
        /*
            1. IN Nombre
            2. IN Marca
            3. IN PrecioUso
            4. OUT idProducto
            5. OUT estatus
         */
        query = "CALL registrarProducto(?, ?, ?, ?, ?)";

        // Conectamos a la base de datos
        conn.Conectar();

        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada al query
        cs.setString(1, producto.getNombre());
        cs.setString(2, producto.getMarca());
        cs.setString(3, Float.toString(producto.getPrecioUso()));

        // Establecemos los parametros de salida
        cs.registerOutParameter("var_idProducto", Types.INTEGER);
        cs.registerOutParameter("var_estatus", Types.INTEGER);

        // Ejecutamos el query
        cs.execute();

        // Obtenemos los parametros de salida y los empatamos al objeto Producto
        int var_idProducto = cs.getInt("var_idProducto");
        int var_estatus = cs.getInt("var_estatus");
        producto.setIdProducto(var_idProducto);
        producto.setEstatus(var_estatus);

        // Finalizamos la conexion
        conn.Desconectar();

        // Retornamos el objeto
        return producto;

    }

    /**
     *
     * @param producto
     * @return
     * @throws Exception
     */
    public boolean modificarProducto(Producto producto) throws Exception {

        // Llama al procedimiento almacenado para actualizar un producto

        /*
            1. Nombre
            2. Marca
            3. Estatus
            4. PrecioUso
         */
        query = "CALL modificarProducto(?, ?, ?, ?, ?)";

        // Conectamo a la base de datos
        conn.Conectar();
        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada al query
        cs.setString(1, Integer.toString(producto.getIdProducto()));
        cs.setString(2, producto.getNombre());
        cs.setString(3, producto.getMarca());
        cs.setString(4, Integer.toString(producto.getEstatus()));
        cs.setString(5, Float.toString(producto.getPrecioUso()));

        // Ejecutamos el query, finalizamos la conexion y devolvemos el resultado
        // de la evaluación
        conn.Desconectar();
        return cs.execute();

    }

    /**
     *
     * @param producto
     * @return
     * @throws Exception
     */
    public Producto borrarProducto(Producto producto) throws Exception {

        // Llama al procedimiento almacenado para borrar un producto
        //1. idProducto
        query = "CALL borrarProducto(?, ?, ?, ?, ?)";

        // Conectamo a la base de datos
        conn.Conectar();

        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada en el query
        cs.setInt(1, producto.getIdProducto());

        // Colocamos los parametros de salida en el query
        cs.registerOutParameter("var_nombre", Types.VARCHAR);
        cs.registerOutParameter("var_marca", Types.VARCHAR);
        cs.registerOutParameter("var_estatus", Types.INTEGER);
        cs.registerOutParameter("var_precioUso", Types.FLOAT);

        // Ejecutamos el query
        cs.execute();

        // Obtenemos los parametros de salida y los empatamos al objeto Producto
        producto.setNombre(cs.getString("var_nombre"));
        producto.setMarca(cs.getString("var_marca"));
        producto.setEstatus(cs.getInt("var_estatus"));
        producto.setPrecioUso(cs.getFloat("var_precioUso"));
        

        // Finalizamos la conexion
        conn.Desconectar();

        // Retornamos el objeto Producto
        return producto;
    }

    /**
     *
     * @param producto
     * @return
     * @throws SQLException
     */
    public Producto buscarProducto(Producto producto) throws SQLException, Exception {
        
        // Hacemos una consulta filtrada de la vista de los productos
        query = "SELECT * FROM v_productos WHERE ID_Producto = ?";
        
        // Conectamo a la base de datos
        conn.Conectar();
        
        // Preparamos el query
        ps = conn.getConexión().prepareCall(query);
        ps.setString(1, Integer.toString(producto.getIdProducto()));

        // Ejecutamos el query
        rs = ps.executeQuery();
        
        // Obtenemos los campos y los asignamos al producto
        rs.next();
        producto.setNombre(rs.getString("Nombre"));
        producto.setMarca(rs.getString("Marca"));
        producto.setEstatus(rs.getInt("Estatus"));
        producto.setPrecioUso(rs.getFloat("PrecioUso"));
        
        // Retornamos el producto con sus atributos.
        return producto;

    }
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public Queue buscarProductos() throws Exception {

        /*
        Usaremos una lista enlazada para devolver todos los productos
        encontrados en la base de datos
         */
        
        // Lista de productos
        Queue<Producto> productos = new LinkedList<>();

        // Hacemos una consulta de la vista de los productos
        query = "SELECT * FROM v_producto";

        // Nos conectamos a la base de datos, preparamos y ejecutamos el query
        conn.Conectar();
        ps = conn.getConexión().prepareCall(query);
        rs = ps.executeQuery();

        /*
        @rs almacenara todos los registros que encuentre la base de datos.
        A continuación pasaremos por un ciclo en donde, mientras haya un
        registro en @rs, se creara un objeto Producto y lo almacenara
        en @productos
         */
        // auxiliar para los registros devueltos
        Producto aux;

        while (rs.next()) {
            aux = new Producto(rs.getInt("ID_Producto"), rs.getString("Nombre"),
                    rs.getString("Marca"), rs.getInt("Estatus"), rs.getInt("PrecioUso"));

            productos.add(aux);
        }

        // Finalmente, nos desconectamos de la base de datos y devolvemos la lista
        conn.Desconectar();

        return productos;
    }

    
}
