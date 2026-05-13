
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
        if (tope == -1) throw new RuntimeException("Pila vacía");
        return (T) datos[tope--];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (tope == -1) throw new RuntimeException("Pila vacía");
        return (T) datos[tope];
    }

    public boolean isEmpty() { return tope == -1; }
    public int size() { return tope + 1; }
}

class ColaConDosPilas<T> {
    private Pila<T> entrada;
    private Pila<T> salida;

    public ColaConDosPilas(int capacidad) {
        entrada = new Pila<>(capacidad);
        salida = new Pila<>(capacidad);
    }

    public void enqueue(T elemento) {
        entrada.push(elemento);
    }

    public T dequeue() {
        if (salida.isEmpty()) {
            while (!entrada.isEmpty()) {
                salida.push(entrada.pop());
            }
        }
        if (salida.isEmpty()) throw new RuntimeException("Cola vacía");
        return salida.pop();
    }

    public T peek() {
        if (salida.isEmpty()) {
            while (!entrada.isEmpty()) {
                salida.push(entrada.pop());
            }
        }
        if (salida.isEmpty()) throw new RuntimeException("Cola vacía");
        return salida.peek();
    }

    public boolean isEmpty() {
        return entrada.isEmpty() && salida.isEmpty();
    }

    public int size() {
        return entrada.size() + salida.size();
    }
}

class Combatiente {
    String nombre;
    int vida;
    int ataque;
    int defensa;
    boolean esHeroe;

    Combatiente(String nombre, int vida, int ataque, int defensa, boolean esHeroe) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.esHeroe = esHeroe;
    }

    boolean estaVivo() { return vida > 0; }

    String estado() {
        String tipo = esHeroe ? "🛡" : "👹";
        return tipo + " " + nombre + " [HP: " + vida + "]";
    }
}

class SistemaTurnos {
    private ColaConDosPilas<Combatiente> cola;

    public SistemaTurnos(int max) {
        cola = new ColaConDosPilas<>(max);
    }

    void agregarCombatiente(Combatiente c) {
        cola.enqueue(c);
        System.out.println("➕ " + c.nombre + " se une al combate.");
    }

    void mostrarOrden() {
        System.out.println("\n=== Orden de turnos (cola con dos pilas) ===");
        ColaConDosPilas<Combatiente> temp = new ColaConDosPilas<>(cola.size() + 10);
        int pos = 1;
        while (!cola.isEmpty()) {
            Combatiente c = cola.dequeue();
            System.out.println("  " + pos + ". " + c.estado());
            temp.enqueue(c);
            pos++;
        }
        while (!temp.isEmpty()) {
            cola.enqueue(temp.dequeue());
        }
    }

    void ejecutarRonda() {
        System.out.println("\n⚔ === NUEVA RONDA === ⚔");
        int total = cola.size();
        for (int i = 0; i < total; i++) {
            Combatiente actual = cola.dequeue();
            if (!actual.estaVivo()) {
                System.out.println("  💀 " + actual.nombre + " ya está derrotado.");
                continue;
            }
            Combatiente objetivo = buscarObjetivo(actual);
            if (objetivo != null) {
                int danio = Math.max(1, actual.ataque - objetivo.defensa);
                objetivo.vida -= danio;
                System.out.println("  ⚔ " + actual.nombre + " ataca a " + objetivo.nombre + " por " + danio + " de daño.");
                if (!objetivo.estaVivo()) {
                    System.out.println("  💀 " + objetivo.nombre + " ha muerto.");
                }
            } else {
                System.out.println("  😵 " + actual.nombre + " no encuentra enemigo.");
            }
            if (actual.estaVivo()) {
                cola.enqueue(actual);
            }
        }
    }

    private Combatiente buscarObjetivo(Combatiente atacante) {
        ColaConDosPilas<Combatiente> temp = new ColaConDosPilas<>(cola.size() + 10);
        Combatiente encontrado = null;
        while (!cola.isEmpty()) {
            Combatiente c = cola.dequeue();
            if (encontrado == null && c.estaVivo() && c.esHeroe != atacante.esHeroe) {
                encontrado = c;
            }
            temp.enqueue(c);
        }
        while (!temp.isEmpty()) {
            cola.enqueue(temp.dequeue());
        }
        return encontrado;
    }

    boolean combateTerminado() {
        ColaConDosPilas<Combatiente> temp = new ColaConDosPilas<>(cola.size() + 10);
        boolean hayHeroes = false;
        boolean hayEnemigos = false;
        while (!cola.isEmpty()) {
            Combatiente c = cola.dequeue();
            if (c.estaVivo()) {
                if (c.esHeroe) hayHeroes = true;
                else hayEnemigos = true;
            }
            temp.enqueue(c);
        }
        while (!temp.isEmpty()) {
            cola.enqueue(temp.dequeue());
        }
        return !hayHeroes || !hayEnemigos;
    }
}

public class Colas {
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 4: Cola con dos pilas en sistema de turnos ===\n");

        SistemaTurnos combate = new SistemaTurnos(10);

        combate.agregarCombatiente(new Combatiente("Aldric", 90, 18, 7, true));
        combate.agregarCombatiente(new Combatiente("Luna", 65, 22, 4, true));
        combate.agregarCombatiente(new Combatiente("Goblin", 35, 10, 2, false));
        combate.agregarCombatiente(new Combatiente("Orco", 70, 16, 5, false));

        combate.mostrarOrden();

        int ronda = 1;
        while (!combate.combateTerminado()) {
            System.out.println("\n--- Ronda " + ronda + " ---");
            combate.ejecutarRonda();
            ronda++;
            if (ronda > 20) break;
        }

        System.out.println("\n🏆 COMBATE FINALIZADO 🏆");
    }
}