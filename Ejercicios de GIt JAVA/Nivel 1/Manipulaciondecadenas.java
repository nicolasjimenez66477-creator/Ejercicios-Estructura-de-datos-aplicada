import java.util.Scanner;

public class Manipulaciondecadenas {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== MANIPULACION DE CADENAS ===\n");
        
        System.out.print("Ingresa una frase o palabra: ");
        String texto = scanner.nextLine();
        
        System.out.println("\nRESULTADOS:");
        System.out.println("Texto original: \"" + texto + "\"");
        System.out.println("----------------------------------------");
        
        // Contar vocales y consonantes
        int[] resultado = contarVocalesConsonantes(texto);
        System.out.println("Vocales: " + resultado[0]);
        System.out.println("Consonantes: " + resultado[1]);
        
        // Invertir cadena
        System.out.println("Texto invertido: " + invertirCadena(texto));
        
        // Verificar si es palíndromo
        System.out.println("Es palíndromo: " + (esPalindromo(texto) ? "SI" : "NO"));
        
        // Contar palabras
        System.out.println("Número de palabras: " + contarPalabras(texto));
        
        scanner.close();
    }
    
    public static int[] contarVocalesConsonantes(String texto) {
        int vocales = 0;
        int consonantes = 0;
        
        texto = texto.toLowerCase();
        
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            
            if (c >= 'a' && c <= 'z') {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vocales++;
                } else {
                    consonantes++;
                }
            }
        }
        
        int[] resultado = {vocales, consonantes};
        return resultado;
    }
    
    public static String invertirCadena(String texto) {
        String invertido = "";
        
        for (int i = texto.length() - 1; i >= 0; i--) {
            invertido = invertido + texto.charAt(i);
        }
        
        return invertido;
    }
    
    public static boolean esPalindromo(String texto) {
        String textoLimpio = texto.toLowerCase().replace(" ", "");
        
        int inicio = 0;
        int fin = textoLimpio.length() - 1;
        
        while (inicio < fin) {
            if (textoLimpio.charAt(inicio) != textoLimpio.charAt(fin)) {
                return false;
            }
            inicio++;
            fin--;
        }
        
        return true;
    }
    
    public static int contarPalabras(String texto) {
        int contador = 0;
        boolean esPalabra = false;
        
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            
            if (c == ' ') {
                esPalabra = false;
            } else {
                if (!esPalabra) {
                    contador++;
                    esPalabra = true;
                }
            }
        }
        
        return contador;
    }
}