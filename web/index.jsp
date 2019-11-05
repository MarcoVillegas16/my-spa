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
            <p>telefono</p>
            <input type="text" name="telefono" id="telefono">
            <p>rfc</p>
            <input type="text" name="rfc" id="rfc">
            <p>nombreUsuario</p>
            <input type="text" name="nombreUsuario" id="nombreUsuario">
            <p>correo</p>
            <input type="text" name="correo" id="correo">
            <p>contrasenia</p>
            <input type="text" name="contrasenia" id="contrasenia">
            <p>domicilio</p>
            <input type="text" name="domicilio" id="domicilio">
            <input type="button" id="btn-registrar-cliente">
        </form>
        <script type="text/javascript" src="enviar.js"></script>
    </body>
</html>
