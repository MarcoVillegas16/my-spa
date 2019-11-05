/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let url;


let variables = [
    document.getElementById('nombre'),
    document.getElementById('apellidoPaterno'),
    document.getElementById('apellidoMaterno'),
    document.getElementById('genero'),
    document.getElementById('telefono'),
    document.getElementById('rfc'),
    document.getElementById('nombreUsuario'),
    document.getElementById('correo'),
    document.getElementById('contrasenia'),
    document.getElementById('domicilio')

];

let generarUrl = () => {
    url = 'http://localhost:8084/MySpa/api/cliente?';
    variables.forEach(currentValue => {
        url += `${currentValue.id}=${currentValue.value}&`;// http://localhost:8084/MySpa/api/cliente?nombre=Juan&...
    });
    url = url.slice(0, -1);//Quitamos el ultimo & del enlace  
    return url;
};

document.getElementById("btn-registrar-cliente").addEventListener('click', () => {
    let enlace = generarUrl();

    hacerPeticion(enlace, 'POST');
});

let hacerPeticion = (enlace, metodo) => {

    const http = new XMLHttpRequest();
    var resultado;

    http.open(metodo, enlace);
    http.onreadystatechange = function () {

        if (this.readyState === 4 && this.status === 200) {
            //console.log(this.responseText);
            resultado = JSON.parse(this.responseText);
            console.log(resultado);
        }
    };
    http.send();
};






















