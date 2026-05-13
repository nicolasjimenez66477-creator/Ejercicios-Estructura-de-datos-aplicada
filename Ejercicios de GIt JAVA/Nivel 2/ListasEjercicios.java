import java.util.*;

class Nodo {
    int valor;
    Nodo siguiente;

    Nodo(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}

public class ListasEjercicios {

    // Ejercicio 1a - Invertir iterativo
    public static Nodo invertirIterativo(Nodo cabeza) {
        Nodo anterior = null;
        Nodo actual = cabeza;
        while (actual != null) {
            Nodo siguiente = actual.siguiente;
            actual.siguiente = anterior;
            anterior = actual;
            actual = siguiente;
        }
        return anterior;
    }

    // Ejercicio 1b - Invertir recursivo
    public static Nodo invertirRecursivo(Nodo nodo) {
        if (nodo == null || nodo.siguiente == null) {
            return nodo;
        }
        Nodo nuevaCabeza = invertirRecursivo(nodo.siguiente);
        nodo.siguiente.siguiente = nodo;
        nodo.siguiente = null;
        return nuevaCabeza;
    }

    // Ejercicio 2a - n-ésimo desde el final (iterativo)
    public static int desdeElFinalIterativo(Nodo cabeza, int n) {
        Nodo adelantado = cabeza;
        Nodo actual = cabeza;
        for (int i = 0; i < n; i++) {
            adelantado = adelantado.siguiente;
        }
        while (adelantado != null) {
            adelantado = adelantado.siguiente;
            actual = actual.siguiente;
        }
        return actual.valor;
    }

    // Ejercicio 2b - n-ésimo desde el final (recursivo)
    public static int[] desdeElFinalRecursivo(Nodo nodo, int n) {
        if (nodo == null) {
            return new int[]{0, -1};
        }
        int[] resultado = desdeElFinalRecursivo(nodo.siguiente, n);
        resultado[0]++;
        if (resultado[0] == n) {
            resultado[1] = nodo.valor;
        }
        return resultado;
    }

    // Ejercicio 3 - Palíndromo
    private static Nodo frente;

    public static boolean esPalindromo(Nodo cabeza) {
        frente = cabeza;
        return esPalindromoRecursivo(cabeza);
    }

    private static boolean esPalindromoRecursivo(Nodo nodo) {
        if (nodo == null) {
            return true;
        }
        boolean resultado = esPalindromoRecursivo(nodo.siguiente);
        if (!resultado) {
            return false;
        }
        boolean coincide = (frente.valor == nodo.valor);
        frente = frente.siguiente;
        return coincide;
    }

    // Ejercicio 4 - Subconjuntos que suman objetivo (backtracking)
    public static void subconjuntosQueSuman(Nodo cabeza, int objetivo) {
        System.out.println("Subconjuntos que suman " + objetivo + ":");
        subconjuntosQueSumanRec(cabeza, objetivo, 0, "");
        System.out.println();
    }

    private static void subconjuntosQueSumanRec(Nodo nodo, int objetivo, int sumaActual, String camino) {
        if (sumaActual == objetivo) {
            System.out.println(camino.trim());
        }
        if (nodo == null || sumaActual > objetivo) {
            return;
        }
        subconjuntosQueSumanRec(nodo.siguiente, objetivo, sumaActual + nodo.valor, camino + nodo.valor + " ");
        subconjuntosQueSumanRec(nodo.siguiente, objetivo, sumaActual, camino);
    }

    // Ejercicio 5a - Eliminar duplicados iterativo
    public static void eliminarDuplicadosIterativo(Nodo cabeza) {
        Nodo actual = cabeza;
        while (actual != null) {
            Nodo comparador = actual;
            while (comparador.siguiente != null) {
                if (comparador.siguiente.valor == actual.valor) {
                    comparador.siguiente = comparador.siguiente.siguiente;
                } else {
                    comparador = comparador.siguiente;
                }
            }
            actual = actual.siguiente;
        }
    }

    // Ejercicio 5b - Eliminar duplicados recursivo
    public static Nodo eliminarDuplicadosRecursivo(Nodo nodo) {
        if (nodo == null || nodo.siguiente == null) {
            return nodo;
        }
        nodo.siguiente = eliminarDuplicadosRecursivo(nodo.siguiente);
        if (contiene(nodo.siguiente, nodo.valor)) {
            return nodo.siguiente;
        }
        return nodo;
    }

    private static boolean contiene(Nodo nodo, int valor) {
        if (nodo == null) return false;
        if (nodo.valor == valor) return true;
        return contiene(nodo.siguiente, valor);
    }

    // Ejercicio 6 - Laberinto
    static class Movimiento {
        int fila, columna;
        Movimiento siguiente;
        Movimiento(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }
    }

    public static boolean resolverLaberinto(int[][] laberinto, int filaIni, int colIni, int filaFin, int colFin) {
        Movimiento camino = null;
        boolean encontrado = resolverLaberintoRec(laberinto, filaIni, colIni, filaFin, colFin, camino);
        if (encontrado) {
            System.out.println("Camino encontrado:");
            imprimirCamino(camino);
        } else {
            System.out.println("No se encontró camino.");
        }
        return encontrado;
    }

    private static boolean resolverLaberintoRec(int[][] laberinto, int fila, int col, int filaFin, int colFin, Movimiento camino) {
        if (fila < 0 || fila >= laberinto.length || col < 0 || col >= laberinto[0].length || laberinto[fila][col] == 1) {
            return false;
        }
        if (laberinto[fila][col] == 2) {
            return false;
        }
        Movimiento paso = new Movimiento(fila, col);
        paso.siguiente = camino;
        camino = paso;

        if (fila == filaFin && col == colFin) {
            return true;
        }
        laberinto[fila][col] = 2;

        if (resolverLaberintoRec(laberinto, fila + 1, col, filaFin, colFin, camino)) return true;
        if (resolverLaberintoRec(laberinto, fila - 1, col, filaFin, colFin, camino)) return true;
        if (resolverLaberintoRec(laberinto, fila, col + 1, filaFin, colFin, camino)) return true;
        if (resolverLaberintoRec(laberinto, fila, col - 1, filaFin, colFin, camino)) return true;

        laberinto[fila][col] = 0;
        return false;
    }

    private static void imprimirCamino(Movimiento camino) {
        if (camino == null) return;
        imprimirCamino(camino.siguiente);
        System.out.println("(" + camino.fila + ", " + camino.columna + ")");
    }

    // Auxiliares
    public static void imprimirLista(Nodo cabeza) {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.print(actual.valor + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }

    public static Nodo crearLista(int... valores) {
        Nodo cabeza = null;
        Nodo ultimo = null;
        for (int v : valores) {
            Nodo nuevo = new Nodo(v);
            if (cabeza == null) {
                cabeza = nuevo;
                ultimo = cabeza;
            } else {
                ultimo.siguiente = nuevo;
                ultimo = nuevo;
            }
        }
        return cabeza;
    }

    // MAIN
    public static void main(String[] args) {

        System.out.println("=== EJERCICIO 1: Invertir lista ===");
        Nodo lista1 = crearLista(1, 2, 3, 4, 5);
        System.out.print("Original: ");
        imprimirLista(lista1);
        lista1 = invertirIterativo(lista1);
        System.out.print("Invertida (iterativa): ");
        imprimirLista(lista1);
        lista1 = invertirRecursivo(lista1);
        System.out.print("Invertida (recursiva): ");
        imprimirLista(lista1);
        System.out.println();

        System.out.println("=== EJERCICIO 2: n-ésimo desde el final ===");
        Nodo lista2 = crearLista(10, 20, 30, 40, 50);
        int n = 2;
        int resultadoIter = desdeElFinalIterativo(lista2, n);
        int resultadoRec = desdeElFinalRecursivo(lista2, n)[1];
        System.out.println("Lista: 10 20 30 40 50");
        System.out.println("El " + n + "° desde el final (iterativo): " + resultadoIter);
        System.out.println("El " + n + "° desde el final (recursivo): " + resultadoRec);
        System.out.println();

        System.out.println("=== EJERCICIO 3: Palíndromo ===");
        Nodo listaPal = crearLista(1, 2, 3, 2, 1);
        System.out.print("Lista: ");
        imprimirLista(listaPal);
        System.out.println("¿Es palíndromo? " + esPalindromo(listaPal));
        Nodo listaNoPal = crearLista(1, 2, 3, 4, 5);
        System.out.print("Lista: ");
        imprimirLista(listaNoPal);
        System.out.println("¿Es palíndromo? " + esPalindromo(listaNoPal));
        System.out.println();

        System.out.println("=== EJERCICIO 4: Subconjuntos que suman objetivo ===");
        Nodo listaSub = crearLista(3, 7, 1, 8, 4);
        int objetivo = 11;
        System.out.print("Lista: ");
        imprimirLista(listaSub);
        subconjuntosQueSuman(listaSub, objetivo);
        System.out.println();

        System.out.println("=== EJERCICIO 5: Eliminar duplicados ===");
        Nodo listaDup = crearLista(1, 2, 2, 3, 4, 4, 4, 5, 1);
        System.out.print("Original: ");
        imprimirLista(listaDup);
        eliminarDuplicadosIterativo(listaDup);
        System.out.print("Sin duplicados (iterativo): ");
        imprimirLista(listaDup);
        Nodo listaDup2 = crearLista(7, 7, 8, 9, 9, 10);
        System.out.print("Otra lista original: ");
        imprimirLista(listaDup2);
        listaDup2 = eliminarDuplicadosRecursivo(listaDup2);
        System.out.print("Sin duplicados (recursivo): ");
        imprimirLista(listaDup2);
        System.out.println();

        System.out.println("=== EJERCICIO 6: Laberinto ===");
        int[][] laberinto = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
        System.out.println("Laberinto (0 = camino, 1 = pared):");
        for (int[] fila : laberinto) {
            System.out.println(Arrays.toString(fila));
        }
        System.out.println("Entrada: (0,0)  Salida: (4,4)");
        resolverLaberinto(laberinto, 0, 0, 4, 4);
    }
}