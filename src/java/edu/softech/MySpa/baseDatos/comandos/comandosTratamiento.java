// Comandos CRUD para la tabla Tratamiento
package edu.softech.MySpa.baseDatos.comandos;

import edu.softech.MySpa.baseDatos.conexionBasesDatos;
import edu.softech.MySpa.modelo.Tratamiento;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Víctor A. Santillán Mtz.
 */
public class comandosTratamiento {

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
     * @param tratamiento
     * @return
     * @throws Exception
     */
    public Tratamiento registrarTratamiento(Tratamiento tratamiento) throws Exception {

        /*
        Se crea un registro en la tabla Tratamiento.
        Se llama a un procedimiento almacenado para el registro.
        Se devuleve el objeto Tratamiento con lo atributos
        @idTratamiento y @estatus actualizados
         */
        // Llama al procedimiento almacenado para crear un nuevo registro Tratamiento
        /*
            1. IN nombre
            2. IN descripcion
            3. IN costo
            4. OUT idTratamiento
            5. OUT estatus
         */
        query = "CALL registrarTratamiento(?, ?, ?, ?, ?)";

        // Conectamos a la base de datos
        conn.Conectar();

        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada al query
        cs.setString("var_nombre", tratamiento.getNombre());
        cs.setString("var_descripcion", tratamiento.getDescripcion());
        cs.setFloat("var_costo", tratamiento.getCosto());

        // Establecemos los parametros de salida
        cs.registerOutParameter("var_idTratamiento", Types.INTEGER);
        cs.registerOutParameter("var_estatus", Types.INTEGER);

        // Ejecutamos el query
        cs.execute();

        // Obtenemos los parametros de salida y los empatamos
        int var_idTratamiento = cs.getInt("var_idTratamiento");
        int var_estatus = cs.getInt("var_estatus");
        tratamiento.setIdTratamiento(var_idTratamiento);
        tratamiento.setEstatus(var_estatus);

        // Finalizamos la conexion
        conn.Desconectar();

        // Retornamos el objeto
        return tratamiento;
    }

    /**
     *
     * @param tratamiento
     * @return
     * @throws Exception
     */
    public boolean modificarTratamiento(Tratamiento tratamiento) throws Exception {

        // Llama al procedimiento almacenado para modificar un tratamiento
        /*
            1. IN   idTratamiento   INT
            2. IN   nombre          VARCHAR
            3. IN   descripcion     VARCHAR
            4. IN   costo           FLOAT
            5. OUT  respuesta       BOOLEAN
         */
        query = "CALL modificarTratamiento(?, ?, ?, ?, ?)";

        // Conectamo a la base de datos
        conn.Conectar();
        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos los parametros de entrada al query
        cs.setInt("var_idTratamiento", tratamiento.getIdTratamiento());
        cs.setString("var_nombre", tratamiento.getNombre());
        cs.setString("var_descripcion", tratamiento.getDescripcion());
        cs.setFloat("var_costo", tratamiento.getCosto());
        
        // Preparamos el parametro de salida al query.
        // Indicara sí se realizo la actualización con exito.
        cs.registerOutParameter("respuesta", Types.BOOLEAN);

        // Ejecutamos el query
        cs.execute();
        
        // Obtenemos la respueta
        boolean respuesta = cs.getBoolean("respuesta");
        
        // Finalizamos la conexion
        conn.Desconectar();
        
        // Devolvemos la respuesta
        return respuesta;
    }

    /**
     *
     * @param tratamiento
     * @return
     * @throws Exception
     */
    public Tratamiento borrarTratamiento(Tratamiento tratamiento) throws Exception {

        // Llama al procedimiento almacenado para deshabilitar un tratamiento
        /*
            1. IN idTratamiento INT
            2. OUT nombre       VARCHAR
            3. OUT descripcion  VARCHAR
            4. OUT costo        FLOAT
            5. OUT estatus      INT
         */
        query = "CALL borrarTratamiento(?, ?, ?, ?, ?, ?) ";

        // Conectamo a la base de datos
        conn.Conectar();
        // Preparamos el query
        cs = conn.getConexión().prepareCall(query);

        // Colocamos el parametro de entrada en el query
        cs.setInt("var_idTratamiento", tratamiento.getIdTratamiento());

        // Colocamos los parametros de salida en el query
        cs.registerOutParameter("var_nombre", Types.VARCHAR);
        cs.registerOutParameter("var_descripcion", Types.VARCHAR);
        cs.registerOutParameter("var_costo", Types.FLOAT);
        cs.registerOutParameter("var_estatus", Types.INTEGER);
        cs.registerOutParameter("respuesta", Types.BOOLEAN);

        // Ejecutamos el query
        cs.execute();
        
        //
        boolean respuesta = cs.getBoolean("respuesta");
        
        if (respuesta) {
            // Obtenemos los parametros de salida y los empatamos al tratamiento
            tratamiento.setNombre(cs.getString("var_nombre"));
            tratamiento.setDescripcion(cs.getString("var_descripcion"));
            tratamiento.setCosto(cs.getFloat("var_costo"));
            tratamiento.setEstatus(cs.getInt("var_estatus"));
        } else {
            // Finalizamos la conexion
            conn.Desconectar();
            // Retornamos 'null' porque no se pudo hacer la eliminacion.
            return null;
        }
        // Finalizamos la conexion
        conn.Desconectar();

        // Retornamos el objeto Tratamiento
        return tratamiento;

    }

    /**
     *
     * @param tratamiento
     * @return
     * @throws Exception
     */
    public Tratamiento buscarTratamiento(Tratamiento tratamiento) throws Exception {

        // Hacemos una consulta filtrada de la vista de los tratamientos
        query = "SELECT * FROM v_tratamiento WHERE idTratamiento = ?";

        // Conectamo a la base de datos
        conn.Conectar();
        // Preparamos el query
        ps = conn.getConexión().prepareCall(query);

        // Colocamos el parametro necesario para el query
        ps.setInt(1, tratamiento.getIdTratamiento());

        // Ejecutamos el query
        rs = ps.executeQuery();

        // Obtenemos los campos y los asignamos al objeto
        if (rs.next()) {
            tratamiento.setNombre(rs.getString("nombre"));
            tratamiento.setDescripcion(rs.getString("descripcion"));
            tratamiento.setCosto(rs.getFloat("costo"));
            tratamiento.setEstatus(rs.getInt("estatus"));
        } else {
            // Se desconecta de la base de datos
            conn.Desconectar();
            return null;
        }
        
        // Se desconecta de la base de datos
            conn.Desconectar();

        // Retronamos el objeto con sus atributos
        return tratamiento;
    }

    /**
     *
     * @return @throws Exception
     */
    public Queue buscarTratamientos() throws Exception {

        /*
        Usaremos una lista enlazada para devolver todos los tratamientos
        encontrados en la base de datos
         */
        // Lista de tratamientos
        Queue<Tratamiento> tratamientos = new LinkedList<>();

        // Hacemos una cosnulta a la vista de tratamientos
        query = "SELECT * FROM v_tratamiento";

        // Nos conectamos a la base de datos, preparamos y ejecutamos el query
        conn.Conectar();
        ps = conn.getConexión().prepareCall(query);
        rs = ps.executeQuery();

        /*
        @rs almacenara todos los registros que encuentre la base de datos.
        A continuación pasaremos por un ciclo en donde, mientras haya un
        registro en @rs, se creara un objeto Tratamiento y lo almacenara
        en @tratamientos
         */
        // auxiliar para los registros devueltos
        Tratamiento aux;

        while (rs.next()) {
            aux = new Tratamiento(
                    rs.getInt("idTratamiento"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getFloat("costo"),
                    rs.getInt("estatus"));
            tratamientos.add(aux);
        }

        // Finalmente, nos desconectamos de la base de datos y devolvemos la lista
        conn.Desconectar();
        return tratamientos;
    }

}
