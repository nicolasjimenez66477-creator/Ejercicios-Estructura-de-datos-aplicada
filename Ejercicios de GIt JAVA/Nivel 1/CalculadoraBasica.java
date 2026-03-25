import java.util.Scanner;

public class CalculadoraBasica {
    
    // Funciones básicas
    public static double sumar(double a, double b) {
        return a + b;
    }
    
    public static double restar(double a, double b) {
        return a - b;
    }
    
    public static double multiplicar(double a, double b) {
        return a * b;
    }
    
    public static String dividir(double a, double b) {
        if (b == 0) {
            return "Error: No se puede dividir entre cero";
        }
        return String.valueOf(a / b);
    }
    
    public static double potencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }
    
    public static String raizCuadrada(double numero) {
        if (numero < 0) {
            return "Error: No se puede calcular raíz cuadrada de número negativo";
        }
        return String.valueOf(Math.sqrt(numero));
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        double num1, num2;
        
        System.out.println("=== CALCULADORA BASICA ===\n");
        
        do {
            System.out.println("\n1. Sumar");
            System.out.println("2. Restar");
            System.out.println("3. Multiplicar");
            System.out.println("4. Dividir");
            System.out.println("5. Potencia");
            System.out.println("6. Raiz cuadrada");
            System.out.println("7. Salir");
            System.out.print("Elige una opcion: ");
            opcion = scanner.nextInt();
            
            switch(opcion) {
                case 1:
                    System.out.print("Ingresa primer numero: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Ingresa segundo numero: ");
                    num2 = scanner.nextDouble();
                    System.out.println("Resultado: " + sumar(num1, num2));
                    break;
                    
                case 2:
                    System.out.print("Ingresa primer numero: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Ingresa segundo numero: ");
                    num2 = scanner.nextDouble();
                    System.out.println("Resultado: " + restar(num1, num2));
                    break;
                    
                case 3:
                    System.out.print("Ingresa primer numero: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Ingresa segundo numero: ");
                    num2 = scanner.nextDouble();
                    System.out.println("Resultado: " + multiplicar(num1, num2));
                    break;
                    
                case 4:
                    System.out.print("Ingresa primer numero: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Ingresa segundo numero: ");
                    num2 = scanner.nextDouble();
                    System.out.println("Resultado: " + dividir(num1, num2));
                    break;
                    
                case 5:
                    System.out.print("Ingresa base: ");
                    num1 = scanner.nextDouble();
                    System.out.print("Ingresa exponente: ");
                    num2 = scanner.nextDouble();
                    System.out.println("Resultado: " + potencia(num1, num2));
                    break;
                    
                case 6:
                    System.out.print("Ingresa numero: ");
                    num1 = scanner.nextDouble();
                    System.out.println("Resultado: " + raizCuadrada(num1));
                    break;
                    
                case 7:
                    System.out.println("Adios!");
                    break;
                    
                default:
                    System.out.println("Opcion no valida");
            }
            
        } while(opcion != 7);
        
        scanner.close();
    }
}