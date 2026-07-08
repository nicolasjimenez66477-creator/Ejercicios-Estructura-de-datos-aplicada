import java.util.*;

class NodoHuffman implements Comparable<NodoHuffman> {
    char caracter;
    int frecuencia;
    NodoHuffman izquierdo, derecho;

    // Constructor para hoja
    public NodoHuffman(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
    }

    // Constructor para nodo interno
    public NodoHuffman(int frecuencia, NodoHuffman izquierdo, NodoHuffman derecho) {
        this.caracter = '\0'; // carácter nulo para internos
        this.frecuencia = frecuencia;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    @Override
    public int compareTo(NodoHuffman otro) {
        return this.frecuencia - otro.frecuencia;
    }

    // Verificar si es hoja
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
}

public class Huffman {

    // Construir el árbol de Huffman a partir de un texto
    public static NodoHuffman construirArbol(String texto) {
        // Contar frecuencias
        Map<Character, Integer> frecuencias = new HashMap<>();
        for (char c : texto.toCharArray()) {
            frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
        }

        // Cola de prioridad (min-heap)
        PriorityQueue<NodoHuffman> cola = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entrada : frecuencias.entrySet()) {
            cola.offer(new NodoHuffman(entrada.getKey(), entrada.getValue()));
        }

        // Construir árbol combinando los dos nodos de menor frecuencia
        while (cola.size() > 1) {
            NodoHuffman izquierdo = cola.poll();
            NodoHuffman derecho = cola.poll();
            NodoHuffman padre = new NodoHuffman(
                izquierdo.frecuencia + derecho.frecuencia,
                izquierdo,
                derecho
            );
            cola.offer(padre);
        }

        return cola.poll(); // raíz del árbol
    }

    // Generar códigos binarios mediante recorrido DFS
    public static Map<Character, String> generarCodigos(NodoHuffman raiz) {
        Map<Character, String> codigos = new HashMap<>();
        generarCodigosRec(raiz, "", codigos);
        return codigos;
    }

    private static void generarCodigosRec(NodoHuffman nodo, String codigo, Map<Character, String> codigos) {
        if (nodo == null) return;
        if (nodo.esHoja()) {
            codigos.put(nodo.caracter, codigo);
            return;
        }
        generarCodigosRec(nodo.izquierdo, codigo + "0", codigos);
        generarCodigosRec(nodo.derecho, codigo + "1", codigos);
    }

    // Comprimir texto: devuelve cadena binaria (String de '0' y '1')
    public static String comprimir(String texto, Map<Character, String> codigos) {
        StringBuilder comprimido = new StringBuilder();
        for (char c : texto.toCharArray()) {
            comprimido.append(codigos.get(c));
        }
        return comprimido.toString();
    }

    // Descomprimir: reconstruir texto a partir de cadena binaria y árbol
    public static String descomprimir(String binario, NodoHuffman raiz) {
        if (binario == null || binario.isEmpty()) return "";

        StringBuilder resultado = new StringBuilder();
        NodoHuffman actual = raiz;

        for (char bit : binario.toCharArray()) {
            if (bit == '0') {
                actual = actual.izquierdo;
            } else { // bit == '1'
                actual = actual.derecho;
            }

            if (actual.esHoja()) {
                resultado.append(actual.caracter);
                actual = raiz; // reiniciar para el siguiente carácter
            }
        }
        return resultado.toString();
    }

    // Calcular tasa de compresión (tamaño original vs comprimido)
    public static double tasaCompresion(String texto, String binario) {
        int bitsOriginales = texto.length() * 8; // 8 bits por carácter (asumiendo ASCII)
        int bitsComprimidos = binario.length();
        return (double) bitsComprimidos / bitsOriginales; // menor es mejor
    }

    // Método principal para probar
    public static void main(String[] args) {
        String texto = "Hola mundo, este es un ejemplo de compresion Huffman!";

        System.out.println("Texto original: " + texto);
        System.out.println("Longitud (caracteres): " + texto.length());

        // Construir árbol
        NodoHuffman raiz = construirArbol(texto);

        // Generar códigos
        Map<Character, String> codigos = generarCodigos(raiz);
        System.out.println("\nCódigos Huffman:");
        for (Map.Entry<Character, String> entry : codigos.entrySet()) {
            System.out.println("  '" + entry.getKey() + "' -> " + entry.getValue());
        }

        // Comprimir
        String binario = comprimir(texto, codigos);
        System.out.println("\nTexto comprimido (bits): " + binario);
        System.out.println("Longitud comprimida (bits): " + binario.length());

        // Descomprimir
        String descomprimido = descomprimir(binario, raiz);
        System.out.println("\nTexto descomprimido: " + descomprimido);

        // Verificar
        System.out.println("¿Coincide con original? " + texto.equals(descomprimido));

        // Tasa de compresión
        double tasa = tasaCompresion(texto, binario);
        System.out.printf("\nTasa de compresión: %.2f%% (%.2f veces más pequeño)\n",
                          tasa * 100, 1.0 / tasa);
        System.out.println("Bits originales: " + (texto.length() * 8));
        System.out.println("Bits comprimidos: " + binario.length());
    }
}
