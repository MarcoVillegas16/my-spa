<%-- 
    Document   : index
    Created on : 25-oct-2019, 8:32:28
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, 
              maximum-scale=1">
        <title>JSP Page</title>
    </head>
    <body>


        <form method="get" action="api/cliente">
            <p>Nombre</p>
            <input type="text" name="nombre">
            <p>apellidoPaterno</p>
            <input type="text" name="apellidoPaterno">
            <p>apellidoMaterno</p>
            <input type="text" name="apellidoMaterno">
            <p>genero</p>
            <input type="text" name="genero">
            <p>telefonoCelular</p>
            <input type="text" name="telefonoCelular">
            <p>telefonoCasa</p>
            <input type="text" name="telefonoCasa">
            <p>rfc</p>
            <input type="text" name="rfc">
            <p>nombreUsuario</p>
            <input type="text" name="nombreUsuario">
            <p>contrasenia</p>
            <input type="text" name="contrasenia">
            <p>puesto</p>
            <input type="text" name="puesto">
            <p>estatusUsuario</p>
            <input type="text" name="estatusUsuario">
            <p>calle</p>
            <input type="text" name="calle">
            <p>numeroCasa</p>
            <input type="text" name="numeroCasa">
            <p>colonia</p>
            <input type="text" name="colonia">
            <input type="submit">
        </form>
    </body>
</html>
