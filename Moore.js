const arreglo_estados = [];

const salidas_moore = ["0", "1", "0", "0", "0", "0", "1", "0"];

let tipo_automata = "";

let cantidad_estados = "";
let contador_automatas = 1;

const transiciones = [
  ["B", "A"],
  ["A", "C"],
  ["C", "A"],
  ["E", "D"],
  ["D", "F"],
  ["F", "D"],
  ["E", "H"],
  ["D", "G"],
];

/*Función que me retorna un array de 0
Según el tamaño que le pasemos por parametro
**/

function obtener_array_inicial(tamaño) {
  let arreglo = [];
  for (let i = 0; i < tamaño; i++) {
    arreglo.push(0);
  }
  return arreglo;
}
/*Método que me retorna la matriz de
adyacencia según la lista de adyacencia
*@param{transiciones} lista de adyacencias
que me define el estado inical del automata
*/
function obtener_matriz(transiciones) {
  let matriz_estados = [];
  for (let contador in transiciones) {
    let matriz = obtener_array_inicial(transiciones.length);

    //console.log(arreglo_estados[contador]);
    for (let estados in transiciones[contador]) {
      let index = arreglo_estados.indexOf(transiciones[contador][estados]);
      matriz[index] = 1;
    }
    matriz_estados.push(matriz);
  }
  console.log(matriz_estados);
  return matriz_estados;
}

/*
*Algoritmo BFS para reconocer que estados
no son recorribles desde el punto inicial
*@param{grafo} recibe una matriz de 0 y 1 
*@param{root} recibe un entero que define el inicio
del recorrido, normalmente es 1
*/
let bfs = (grafo, inicio) => {
  let nodesLen = {};

  for (let i = 0; i < grafo.length; i++) {
    nodesLen[i] = Infinity;
  }
  nodesLen[inicio] = 0;

  let queue = [inicio];
  let reciente;

  while (queue.length != 0) {
    reciente = queue.shift();

    let conexion = grafo[reciente];
    let vecinoIdx = [];
    let idx = conexion.indexOf(1);

    while (idx != -1) {
      vecinoIdx.push(idx);
      idx = conexion.indexOf(1, idx + 1);
    }

    for (let j = 0; j < vecinoIdx.length; j++) {
      if (nodesLen[vecinoIdx[j]] == Infinity) {
        nodesLen[vecinoIdx[j]] = nodesLen[reciente] + 1;
        queue.push(vecinoIdx[j]);
      }
    }
  }

  return nodesLen;
};

/* Elimina de la lista de adyacencia
 * los estados que no son accesibles desde el origen
 *
 */
function eliminar_no_accesibles() {
  const estados = Object.values(bfs(obtener_matriz(transiciones), 1));
  const nuevo_estado = [];
  console.log(estados);
  for (let estado in estados) {
    if (estados[estado] == Infinity) {
      console.log(estado);
      arreglo_estados.splice(estados.indexOf(estados[estado]), 1);
      transiciones.splice(estados.indexOf(estados[estado]), 1);
      salidas_moore.splice(estados.indexOf(estados[estado]), 1);
    }
  }
}

/* Por el momento solo hace la primera
 * partición, verifica que sus salidas
 * sean iguales y las separa.
 */

function hacer_particion_moore() {
  let particiones = [];
  let particion = [];
  let visitados = [];
  //Primer partición
  for (let salidas in salidas_moore) {
    console.log(salidas_moore[salidas]);
    for (let j in salidas_moore) {
      console.log(salidas_moore[j]);
      if (salidas_moore[salidas] == salidas_moore[j]) {
        console.log("entra");
        if (visitados.includes(arreglo_estados[j]) == false) {
          particion.push(arreglo_estados[j]);
          visitados.push(arreglo_estados[j]);
        }
      }
    }
    particiones.push(particion);
    particion = [];
  }
  particiones.pop();

  // Particiones Pk+1
  for (let i in particiones) {
    console.log(particiones[i]);
    for (let j in particiones[i]) {
    }
  }

  return particiones;
}

//console.log(bfs(obtener_matriz(transiciones), 1));
//eliminar_no_accesibles();
console.log(hacer_particion_moore());

function enviar_datos() {
  console.log("perro");
  var valores = $.map($("#select option:selected"), function (o, i) {
    return $(o).text();
  });
  tipo_automata = valores.join(", ");
  if (tipo_automata == "Moore") {
    alert(tipo_automata);
    location.href = "Moore.html";
  }
}

function iniciar_datos() {
  cantidad_estados = parseInt(document.getElementById("input_estados").value);

  if (
    cantidad_estados == 0 ||
    parseInt(cantidad_estados) != document.getElementById("input_estados").value
  ) {
    alert("Error, debe digitar un valor númerico y mayor a cero.");
  } else {
    document.getElementById("input_entradas").style.display = "block";
  }
}

function registrar_estados() {
  if (cantidad_estados > 0) {
    while (true) {
      if (
        arreglo_estados.includes(
          document.getElementById("nombres_estados").value
        ) ||
        document.getElementById("nombres_estados").value == "" ||
        document.getElementById("nombres_estados").value == " "
      ) {
        alert("El nombre ya está incluido");
        document.getElementById("nombres_estados").value = "";
        document.getElementById("nombres_estados").placeholder =
          "Digite el nombre del estado" + (contador_automatas + 1);
        break;
      } else {
        arreglo_estados.push(document.getElementById("nombres_estados").value);
        document.getElementById("nombres_estados").value = "";
        document.getElementById("nombres_estados").placeholder =
          "Digite el nombre del estado" + (contador_automatas + 1);
        cantidad_estados--;
        break;
      }
    }
  } else {
    alert("No puede añadir más estados");
    console.log(arreglo_estados);
  }
}
