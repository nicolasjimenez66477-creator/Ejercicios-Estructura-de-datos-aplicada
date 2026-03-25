import java.util.Scanner;

public class Conversordeunidades {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== CONVERSOR DE UNIDADES ===\n");
        
        System.out.print("Elige una categoría (1-Temperatura, 2-Longitud, 3-Peso, 4-Tiempo): ");
        int categoria = scanner.nextInt();
        
        if (categoria == 1) {
            // Temperatura
            System.out.println("\n--- CONVERSOR DE TEMPERATURA ---");
            System.out.print("Ingresa el valor: ");
            double valor = scanner.nextDouble();
            System.out.print("Unidad de origen (C, F, K): ");
            String origen = scanner.next().toUpperCase();
            System.out.print("Unidad de destino (C, F, K): ");
            String destino = scanner.next().toUpperCase();
            
            double resultado = convertirTemperatura(valor, origen, destino);
            System.out.println(valor + "°" + origen + " = " + resultado + "°" + destino);
            
        } else if (categoria == 2) {
            // Longitud
            System.out.println("\n--- CONVERSOR DE LONGITUD ---");
            System.out.print("Ingresa el valor: ");
            double valor = scanner.nextDouble();
            System.out.print("Unidad de origen (m, km, millas): ");
            String origen = scanner.next().toLowerCase();
            System.out.print("Unidad de destino (m, km, millas): ");
            String destino = scanner.next().toLowerCase();
            
            double resultado = convertirLongitud(valor, origen, destino);
            System.out.println(valor + " " + origen + " = " + resultado + " " + destino);
            
        } else if (categoria == 3) {
            // Peso
            System.out.println("\n--- CONVERSOR DE PESO ---");
            System.out.print("Ingresa el valor: ");
            double valor = scanner.nextDouble();
            System.out.print("Unidad de origen (kg, lb, oz): ");
            String origen = scanner.next().toLowerCase();
            System.out.print("Unidad de destino (kg, lb, oz): ");
            String destino = scanner.next().toLowerCase();
            
            double resultado = convertirPeso(valor, origen, destino);
            System.out.println(valor + " " + origen + " = " + resultado + " " + destino);
            
        } else if (categoria == 4) {
            // Tiempo
            System.out.println("\n--- CONVERSOR DE TIEMPO ---");
            System.out.print("Ingresa el valor: ");
            int valor = scanner.nextInt();
            System.out.print("Unidad de origen (s, min, h): ");
            String origen = scanner.next().toLowerCase();
            System.out.print("Unidad de destino (s, min, h): ");
            String destino = scanner.next().toLowerCase();
            
            int resultado = convertirTiempo(valor, origen, destino);
            System.out.println(valor + " " + origen + " = " + resultado + " " + destino);
        }
        
        scanner.close();
    }
    
    // Funciones de Temperatura
    public static double convertirTemperatura(double valor, String origen, String destino) {
        double enCelsius = 0;
        
        // Convertir a Celsius
        if (origen.equals("C")) {
            enCelsius = valor;
        } else if (origen.equals("F")) {
            enCelsius = (valor - 32) * 5/9;
        } else if (origen.equals("K")) {
            enCelsius = valor - 273.15;
        }
        
        // Convertir desde Celsius a destino
        if (destino.equals("C")) {
            return enCelsius;
        } else if (destino.equals("F")) {
            return (enCelsius * 9/5) + 32;
        } else if (destino.equals("K")) {
            return enCelsius + 273.15;
        }
        
        return 0;
    }
    
    // Funciones de Longitud
    public static double convertirLongitud(double valor, String origen, String destino) {
        double enMetros = 0;
        
        // Convertir a metros
        if (origen.equals("m")) {
            enMetros = valor;
        } else if (origen.equals("km")) {
            enMetros = valor * 1000;
        } else if (origen.equals("millas")) {
            enMetros = valor * 1609.34;
        }
        
        // Convertir desde metros a destino
        if (destino.equals("m")) {
            return enMetros;
        } else if (destino.equals("km")) {
            return enMetros / 1000;
        } else if (destino.equals("millas")) {
            return enMetros / 1609.34;
        }
        
        return 0;
    }
    
    // Funciones de Peso
    public static double convertirPeso(double valor, String origen, String destino) {
        double enKilogramos = 0;
        
        // Convertir a kilogramos
        if (origen.equals("kg")) {
            enKilogramos = valor;
        } else if (origen.equals("lb")) {
            enKilogramos = valor / 2.20462;
        } else if (origen.equals("oz")) {
            enKilogramos = valor / 35.274;
        }
        
        // Convertir desde kilogramos a destino
        if (destino.equals("kg")) {
            return enKilogramos;
        } else if (destino.equals("lb")) {
            return enKilogramos * 2.20462;
        } else if (destino.equals("oz")) {
            return enKilogramos * 35.274;
        }
        
        return 0;
    }
    
    // Funciones de Tiempo
    public static int convertirTiempo(int valor, String origen, String destino) {
        int enSegundos = 0;
        
        // Convertir a segundos
        if (origen.equals("s")) {
            enSegundos = valor;
        } else if (origen.equals("min")) {
            enSegundos = valor * 60;
        } else if (origen.equals("h")) {
            enSegundos = valor * 3600;
        }
        
        // Convertir desde segundos a destino
        if (destino.equals("s")) {
            return enSegundos;
        } else if (destino.equals("min")) {
            return enSegundos / 60;
        } else if (destino.equals("h")) {
            return enSegundos / 3600;
        }
        
        return 0;
    }
}