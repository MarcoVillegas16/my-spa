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
        <meta name="viewport" id="viewport" content="width=device-width, initial-scale=1, 
              maximum-scale=1">
        <title>JSP Page</title>
    </head>
    <body>


        <form method="post" action="api/cliente">
            <p>Nombre</p>
            <input type="text" name="nombre" id="nombre">
            <p>apellidoPaterno</p>
            <input type="text" name="apellidoPaterno" id="apellidoPaterno">
            <p>apellidoMaterno</p>
            <input type="text" name="apellidoMaterno" id="apellidoMaterno">
            <p>genero</p>
            <input type="text" name="genero" id="genero">
            <p>telefonoCelular</p>
            <input type="text" name="telefonoCelular" id="telefonoCelular">
            <p>telefonoCasa</p>
            <input type="text" name="telefonoCasa" id="telefonoCasa">
            <p>rfc</p>
            <input type="text" name="rfc" id="rfc">
            <p>nombreUsuario</p>
            <input type="text" name="nombreUsuario" id="nombreUsuario">
            <p>contrasenia</p>
            <input type="text" name="contrasenia" id="contrasenia">
            <p>calle</p>
            <input type="text" name="calle" id="calle">
            <p>numeroCasa</p>
            <input type="text" name="numeroCasa" id="numeroCasa">
            <p>colonia</p>
            <input type="text" name="colonia" id="colonia">
            <input type="button" id="btn-registrar-cliente">
        </form>
        <script type="text/javascript" src="enviar.js"></script>
    </body>
</html>
