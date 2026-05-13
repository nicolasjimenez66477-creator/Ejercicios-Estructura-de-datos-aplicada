
class Pila<T> {
    private Object[] datos;
    private int tope;

    public Pila(int capacidad) {
        datos = new Object[capacidad];
        tope = -1;
    }

    public void push(T valor) {
        if (tope == datos.length - 1) throw new RuntimeException("Pila llena");
        datos[++tope] = valor;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (tope == -1) throw new RuntimeException("Pila vacia");
        return (T) datos[tope--];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (tope == -1) throw new RuntimeException("Pila vacia");
        return (T) datos[tope];
    }

    public boolean isEmpty() { return tope == -1; }
    public int size() { return tope + 1; }
}

class Personaje {
    String nombre;
    int vida, ataque, defensa;

    Personaje(String nombre, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    String estado() {
        return nombre + " [HP: " + vida + " | ATK: " + ataque + " | DEF: " + defensa + "]";
    }
}

class Accion {
    String descripcion;
    int cambioVida, cambioAtaque, cambioDefensa;

    Accion(String descripcion, int cambioVida, int cambioAtaque, int cambioDefensa) {
        this.descripcion = descripcion;
        this.cambioVida = cambioVida;
        this.cambioAtaque = cambioAtaque;
        this.cambioDefensa = cambioDefensa;
    }
}

class SistemaAcciones {
    private Pila<Accion> historial;
    private Pila<Accion> rehecho;
    private Personaje personaje;

    SistemaAcciones(Personaje personaje) {
        this.personaje = personaje;
        historial = new Pila<>(100);
        rehecho = new Pila<>(100);
    }

    void ejecutar(Accion accion) {
        personaje.vida += accion.cambioVida;
        personaje.ataque += accion.cambioAtaque;
        personaje.defensa += accion.cambioDefensa;
        historial.push(accion);
        rehecho = new Pila<>(100);
        System.out.println("✔ " + accion.descripcion);
        System.out.println("  " + personaje.estado());
    }

    void deshacer() {
        if (historial.isEmpty()) {
            System.out.println("⚠ No hay acciones para deshacer.");
            return;
        }
        Accion accion = historial.pop();
        personaje.vida -= accion.cambioVida;
        personaje.ataque -= accion.cambioAtaque;
        personaje.defensa -= accion.cambioDefensa;
        rehecho.push(accion);
        System.out.println("↩ Deshecho: " + accion.descripcion);
        System.out.println("  " + personaje.estado());
    }

    void rehacer() {
        if (rehecho.isEmpty()) {
            System.out.println("⚠ No hay acciones para rehacer.");
            return;
        }
        Accion accion = rehecho.pop();
        personaje.vida += accion.cambioVida;
        personaje.ataque += accion.cambioAtaque;
        personaje.defensa += accion.cambioDefensa;
        historial.push(accion);
        System.out.println("↪ Rehecho: " + accion.descripcion);
        System.out.println("  " + personaje.estado());
    }

    void verHistorialAcciones() {
        if (historial.isEmpty()) {
            System.out.println("Historial vacio.");
            return;
        }
        Pila<Accion> temp = new Pila<>(100);
        System.out.println("=== Historial de acciones (mas reciente a mas antigua) ===");
        while (!historial.isEmpty()) {
            Accion a = historial.pop();
            System.out.println("  - " + a.descripcion);
            temp.push(a);
        }
        while (!temp.isEmpty()) historial.push(temp.pop());
    }

    void deshacerTodo() {
        if (historial.isEmpty()) {
            System.out.println("⚠ No hay acciones para deshacer.");
            return;
        }
        while (!historial.isEmpty()) {
            Accion a = historial.pop();
            personaje.vida -= a.cambioVida;
            personaje.ataque -= a.cambioAtaque;
            personaje.defensa -= a.cambioDefensa;
            rehecho.push(a);
        }
        System.out.println("↩ Se deshicieron todas las acciones.");
        System.out.println("  " + personaje.estado());
    }
}

class Hechizo {
    String nombre, elemento;
    int dano;

    Hechizo(String nombre, int dano, String elemento) {
        this.nombre = nombre;
        this.dano = dano;
        this.elemento = elemento;
    }

    public String toString() {
        return nombre + " (" + elemento + ", " + dano + " dmg)";
    }
}

class LibroHechizos {
    private Pila<Hechizo> historial;

    LibroHechizos() { historial = new Pila<>(50); }

    void lanzar(Hechizo hechizo) {
        historial.push(hechizo);
        System.out.println("🔥 Lanzaste: " + hechizo);
    }

    void verUltimo() {
        if (historial.isEmpty()) System.out.println("No has lanzado ningun hechizo.");
        else System.out.println("Ultimo hechizo: " + historial.peek());
    }

    void verHistorial() {
        if (historial.isEmpty()) {
            System.out.println("Historial vacio.");
            return;
        }
        System.out.println("=== Historial de hechizos (mas reciente primero) ===");
        Pila<Hechizo> temp = new Pila<>(50);
        while (!historial.isEmpty()) {
            Hechizo h = historial.pop();
            System.out.println("  - " + h);
            temp.push(h);
        }
        while (!temp.isEmpty()) historial.push(temp.pop());
    }

    int contarPorElemento(String elemento) {
        return contarRecursivo(elemento);
    }

    private int contarRecursivo(String elemento) {
        if (historial.isEmpty()) return 0;
        Hechizo h = historial.pop();
        int cuenta = contarRecursivo(elemento);
        if (h.elemento.equals(elemento)) cuenta++;
        historial.push(h);
        return cuenta;
    }

    Hechizo hechizoMasPoderoso() {
        if (historial.isEmpty()) return null;
        Pila<Hechizo> temp = new Pila<>(50);
        Hechizo max = null;
        while (!historial.isEmpty()) {
            Hechizo h = historial.pop();
            if (max == null || h.dano > max.dano) max = h;
            temp.push(h);
        }
        while (!temp.isEmpty()) historial.push(temp.pop());
        return max;
    }
}

class CalculadoraDano {
    static int evaluar(String formula) {
        Pila<Integer> pila = new Pila<>(100);
        String[] tokens = formula.split(" ");
        for (String token : tokens) {
            if (token.matches("-?\\d+")) pila.push(Integer.parseInt(token));
            else {
                int b = pila.pop();
                int a = pila.pop();
                switch (token) {
                    case "+": pila.push(a + b); break;
                    case "-": pila.push(a - b); break;
                    case "*": pila.push(a * b); break;
                    case "/": pila.push(a / b); break;
                }
            }
        }
        return pila.pop();
    }
}

class ValidadorHechizo {
    static boolean formulaValida(String encantamiento) {
        Pila<Character> pila = new Pila<>(encantamiento.length());
        for (char c : encantamiento.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') pila.push(c);
            else if (c == ')' || c == ']' || c == '}') {
                if (pila.isEmpty()) return false;
                char abierto = pila.pop();
                if (c == ')' && abierto != '(') return false;
                if (c == ']' && abierto != '[') return false;
                if (c == '}' && abierto != '{') return false;
            }
        }
        return pila.isEmpty();
    }
}

class Recompensas {
    static void insertarAlFondo(Pila<String> pila, String valor) {
        if (pila.isEmpty()) pila.push(valor);
        else {
            String temp = pila.pop();
            insertarAlFondo(pila, valor);
            pila.push(temp);
        }
    }

    static void invertir(Pila<String> pila) {
        if (!pila.isEmpty()) {
            String temp = pila.pop();
            invertir(pila);
            insertarAlFondo(pila, temp);
        }
    }
}

class Mochila {
    private Pila<String> items;

    Mochila() { items = new Pila<>(20); }

    void guardar(String item) {
        items.push(item);
        System.out.println("Guardaste: " + item);
    }

    String sacar() {
        if (items.isEmpty()) {
            System.out.println("Mochila vacia.");
            return null;
        }
        String item = items.pop();
        System.out.println("Sacaste: " + item);
        return item;
    }

    void verContenido() {
        if (items.isEmpty()) {
            System.out.println("Mochila vacia.");
            return;
        }
        Pila<String> temp = new Pila<>(20);
        System.out.println("Contenido de la mochila (tope primero):");
        while (!items.isEmpty()) {
            String item = items.pop();
            System.out.println("  - " + item);
            temp.push(item);
        }
        while (!temp.isEmpty()) items.push(temp.pop());
    }
}

public class Pilas {
    public static void main(String[] args) {
        Personaje heroe = new Personaje("Aldric", 100, 15, 10);
        SistemaAcciones sistema = new SistemaAcciones(heroe);

        System.out.println("=== ESTADO INICIAL ===");
        System.out.println(heroe.estado());
        System.out.println();

        sistema.ejecutar(new Accion("Beber pocion de fuerza", 0, 5, 0));
        sistema.ejecutar(new Accion("Equipar escudo de hierro", 0, 0, 8));
        sistema.ejecutar(new Accion("Recibir golpe de goblin", -20, 0, 0));

        System.out.println("\n=== DESHACIENDO ===");
        sistema.deshacer();
        sistema.deshacer();

        System.out.println("\n=== REHACIENDO ===");
        sistema.rehacer();

        System.out.println("\n=== HISTORIAL DE ACCIONES ===");
        sistema.verHistorialAcciones();

        System.out.println("\n=== DESHACER TODO ===");
        sistema.deshacerTodo();

        System.out.println("\n=== LIBRO DE HECHIZOS ===");
        LibroHechizos libro = new LibroHechizos();
        libro.lanzar(new Hechizo("Bola de fuego", 25, "fuego"));
        libro.lanzar(new Hechizo("Rayo gelido", 18, "hielo"));
        libro.lanzar(new Hechizo("Llamarada", 30, "fuego"));
        libro.lanzar(new Hechizo("Tormenta electrica", 35, "rayo"));
        libro.verUltimo();
        libro.verHistorial();
        System.out.println("Hechizos de fuego: " + libro.contarPorElemento("fuego"));
        System.out.println("Hechizo mas poderoso: " + libro.hechizoMasPoderoso());

        System.out.println("\n=== CALCULADORA DE DANO (POSTFIJA) ===");
        int dano = CalculadoraDano.evaluar("15 5 + 2 * 8 -");
        System.out.println("Formula: 15 5 + 2 * 8 -  → Dano: " + dano);

        System.out.println("\n=== VALIDADOR DE HECHIZOS ===");
        System.out.println("{fuego[rayo(explosion)]} → " + (ValidadorHechizo.formulaValida("{fuego[rayo(explosion)]}") ? "Valido" : "Invalido"));
        System.out.println("{fuego[rayo(explosion]} → " + (ValidadorHechizo.formulaValida("{fuego[rayo(explosion]}") ? "Valido" : "Invalido"));

        System.out.println("\n=== RECOMPENSAS (INVERTIR PILA) ===");
        Pila<String> cofre = new Pila<>(10);
        cofre.push("Espada oxidada");
        cofre.push("Pocion menor");
        cofre.push("Anillo de poder");
        cofre.push("Armadura de dragon");
        System.out.println("Tope antes: " + cofre.peek());
        Recompensas.invertir(cofre);
        System.out.println("Tope despues: " + cofre.peek());

        System.out.println("\n=== MOCHILA ===");
        Mochila mochila = new Mochila();
        mochila.guardar("Pocion de vida");
        mochila.guardar("Mapa del tesoro");
        mochila.guardar("Llave dorada");
        mochila.verContenido();
        mochila.sacar();
        mochila.verContenido();
    }
}