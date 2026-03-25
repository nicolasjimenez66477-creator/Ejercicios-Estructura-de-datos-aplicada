import java.util.Arrays;
import java.util.Scanner;

public class Operacionesconlistas {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== OPERACIONES CON LISTAS ===\n");
        
        System.out.print("¿Cuántos números quieres ingresar? ");
        int cantidad = scanner.nextInt();
        
        int[] numeros = new int[cantidad];
        
        System.out.println("Ingresa los números:");
        for (int i = 0; i < cantidad; i++) {
            System.out.print("Número " + (i + 1) + ": ");
            numeros[i] = scanner.nextInt();
        }
        
        System.out.println("\nRESULTADOS:");
        System.out.println("Lista ingresada: " + Arrays.toString(numeros));
        System.out.println("----------------------------------------");
        
        // Encontrar mayor y menor
        System.out.println("Mayor elemento: " + encontrarMayor(numeros));
        System.out.println("Menor elemento: " + encontrarMenor(numeros));
        
        // Calcular promedio
        System.out.println("Promedio: " + calcularPromedio(numeros));
        
        // Eliminar duplicados
        int[] sinDuplicados = eliminarDuplicados(numeros);
        System.out.println("Sin duplicados: " + Arrays.toString(sinDuplicados));
        
        // Ordenar lista
        int[] ordenados = ordenarBubbleSort(numeros.clone());
        System.out.println("Lista ordenada: " + Arrays.toString(ordenados));
        
        scanner.close();
    }
    
    public static int encontrarMayor(int[] lista) {
        int mayor = lista[0];
        
        for (int i = 1; i < lista.length; i++) {
            if (lista[i] > mayor) {
                mayor = lista[i];
            }
        }
        
        return mayor;
    }
    
    public static int encontrarMenor(int[] lista) {
        int menor = lista[0];
        
        for (int i = 1; i < lista.length; i++) {
            if (lista[i] < menor) {
                menor = lista[i];
            }
        }
        
        return menor;
    }
    
    public static double calcularPromedio(int[] lista) {
        int suma = 0;
        
        for (int i = 0; i < lista.length; i++) {
            suma = suma + lista[i];
        }
        
        return (double) suma / lista.length;
    }
    
    public static int[] eliminarDuplicados(int[] lista) {
        int cantidadUnicos = 0;
        
        for (int i = 0; i < lista.length; i++) {
            boolean duplicado = false;
            
            for (int j = 0; j < i; j++) {
                if (lista[i] == lista[j]) {
                    duplicado = true;
                    break;
                }
            }
            
            if (!duplicado) {
                cantidadUnicos++;
            }
        }
        
        int[] unicos = new int[cantidadUnicos];
        int indice = 0;
        
        for (int i = 0; i < lista.length; i++) {
            boolean duplicado = false;
            
            for (int j = 0; j < i; j++) {
                if (lista[i] == lista[j]) {
                    duplicado = true;
                    break;
                }
            }
            
            if (!duplicado) {
                unicos[indice] = lista[i];
                indice++;
            }
        }
        
        return unicos;
    }
    
    public static int[] ordenarBubbleSort(int[] lista) {
        int n = lista.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista[j] > lista[j + 1]) {
                    int temp = lista[j];
                    lista[j] = lista[j + 1];
                    lista[j + 1] = temp;
                }
            }
        }
        
        return lista;
    }

}