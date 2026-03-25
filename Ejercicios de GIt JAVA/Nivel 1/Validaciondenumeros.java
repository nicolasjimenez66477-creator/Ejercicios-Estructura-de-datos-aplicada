import java.util.Scanner;

public class Validaciondenumeros {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== VALIDADOR DE NUMEROS ===\n");
        
        System.out.print("Ingresa un numero: ");
        int numero = scanner.nextInt();
        
        System.out.println("\nRESULTADOS:");
        System.out.println("Numero: " + numero);
        System.out.println("- Par o Impar: " + esParOImpar(numero));
        System.out.println("- Es primo: " + (esPrimo(numero) ? "SI" : "NO"));
        System.out.println("- Es perfecto: " + (esPerfecto(numero) ? "SI" : "NO"));
        System.out.println("- Es palindromo: " + (esPalindromo(numero) ? "SI" : "NO"));
        
        scanner.close();
    }
    
    public static String esParOImpar(int numero) {
        if (numero % 2 == 0) {
            return "Par";
        } else {
            return "Impar";
        }
    }
    
    public static boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        
        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean esPerfecto(int numero) {
        int sumaDivisores = 0;
        
        for (int i = 1; i < numero; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
            }
        }
        
        return sumaDivisores == numero;
    }
    
    public static boolean esPalindromo(int numero) {
        int original = numero;
        int invertido = 0;
        
        while (numero > 0) {
            int digito = numero % 10;
            invertido = invertido * 10 + digito;
            numero = numero / 10;
        }
        
        return original == invertido;
    }
}