/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let url = 'http://localhost:8084/MySpa/api/cliente?';


let variables = [
    document.getElementById('nombre'),
    document.getElementById('apellidoPaterno'),
    document.getElementById('apellidoMaterno'),
    document.getElementById('genero'),
    document.getElementById('telefonoCelular'),
    document.getElementById('telefonoCasa'),
    document.getElementById('rfc'),
    document.getElementById('nombreUsuario'),
    document.getElementById('contrasenia'),
    document.getElementById('calle'),
    document.getElementById('numeroCasa'),
    document.getElementById('colonia')

];

let generarUrl = () => {
    variables.forEach(currentValue => {
        url += `${currentValue.id}=${currentValue.value}&`;// http://localhost:8084/MySpa/api/cliente?nombre=Juan&...
    });
    url = url.slice(0, -1);//Quitamos el ultimo & del enlace

    return url;
};

document.getElementById("btn-registrar-cliente").addEventListener('click', () => {
    let enlace = generarUrl();

    hacerPeticionAjax(enlace);
});

let hacerPeticionAjax = enlace => {
    const http = new XMLHttpRequest();

    http.open("GET", enlace);
    http.onreadystatechange = function () {

        if (this.readyState === 4 && this.status === 200) {
            var resultado = JSON.parse(this.responseText);
            console.log(resultado);
        }
    };
    http.send();
};

