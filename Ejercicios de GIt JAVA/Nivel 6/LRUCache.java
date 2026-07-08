import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de una caché LRU (Least Recently Used) con capacidad fija.
 * Las operaciones get y put son O(1) en promedio.
 * Utiliza HashMap para acceso rápido y lista doblemente enlazada para orden de uso.
 */
public class LRUCache<K, V> {

    // Nodo de la lista doblemente enlazada
    private static class Nodo<K, V> {
        K clave;
        V valor;
        Nodo<K, V> anterior;
        Nodo<K, V> siguiente;

        Nodo(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private final int capacidad;
    private final Map<K, Nodo<K, V>> mapa; // para acceso O(1) por clave
    private Nodo<K, V> cabeza; // más recientemente usado (MRU)
    private Nodo<K, V> cola;   // menos recientemente usado (LRU)

    /**
     * Constructor con capacidad máxima.
     * @param capacidad número máximo de elementos en la caché
     */
    public LRUCache(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
        this.capacidad = capacidad;
        this.mapa = new HashMap<>();
        this.cabeza = null;
        this.cola = null;
    }

    /**
     * Obtiene el valor asociado a una clave.
     * Si existe, mueve el elemento al frente (más reciente).
     * @param clave clave a buscar
     * @return valor asociado o null si no existe
     */
    public V get(K clave) {
        Nodo<K, V> nodo = mapa.get(clave);
        if (nodo == null) {
            return null;
        }
        // Mover al frente (más reciente)
        moverAlFrente(nodo);
        return nodo.valor;
    }

    /**
     * Inserta o actualiza un par clave-valor.
     * Si la clave ya existe, actualiza su valor y lo mueve al frente.
     * Si no existe, lo agrega; si la caché está llena, elimina el menos usado (cola).
     * @param clave clave
     * @param valor valor
     */
    public void put(K clave, V valor) {
        Nodo<K, V> nodo = mapa.get(clave);
        if (nodo != null) {
            // Actualizar valor y mover al frente
            nodo.valor = valor;
            moverAlFrente(nodo);
            return;
        }

        // Clave nueva: crear nodo
        nodo = new Nodo<>(clave, valor);
        mapa.put(clave, nodo);

        // Si la caché está llena, eliminar el último (menos usado)
        if (mapa.size() > capacidad) {
            eliminarLRU();
        }

        // Insertar al frente (más reciente)
        agregarAlFrente(nodo);
    }

    /**
     * Elimina el elemento menos recientemente usado (el de la cola).
     */
    private void eliminarLRU() {
        if (cola == null) return;

        // Eliminar de la lista
        Nodo<K, V> lru = cola;
        cola = lru.anterior;
        if (cola != null) {
            cola.siguiente = null;
        } else {
            cabeza = null; // la lista quedó vacía
        }
        // Eliminar del mapa
        mapa.remove(lru.clave);
    }

    /**
     * Mueve un nodo al frente (más reciente).
     */
    private void moverAlFrente(Nodo<K, V> nodo) {
        // Si ya está en cabeza, no hacer nada
        if (nodo == cabeza) return;

        // Desconectar nodo de su posición actual
        if (nodo.anterior != null) {
            nodo.anterior.siguiente = nodo.siguiente;
        }
        if (nodo.siguiente != null) {
            nodo.siguiente.anterior = nodo.anterior;
        }

        // Si el nodo era la cola, actualizar cola
        if (nodo == cola) {
            cola = nodo.anterior;
        }

        // Conectar nodo al frente
        nodo.anterior = null;
        nodo.siguiente = cabeza;
        if (cabeza != null) {
            cabeza.anterior = nodo;
        }
        cabeza = nodo;

        // Si la lista estaba vacía, cabeza y cola apuntan al mismo
        if (cola == null) {
            cola = cabeza;
        }
    }

    /**
     * Agrega un nuevo nodo al frente (cuando es un elemento nuevo).
     */
    private void agregarAlFrente(Nodo<K, V> nodo) {
        nodo.anterior = null;
        nodo.siguiente = cabeza;
        if (cabeza != null) {
            cabeza.anterior = nodo;
        }
        cabeza = nodo;
        if (cola == null) {
            cola = nodo;
        }
    }

    /**
     * Muestra el estado actual de la caché (para depuración).
     */
    public void mostrar() {
        System.out.print("Caché (orden MRU -> LRU): ");
        Nodo<K, V> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.clave + "=" + actual.valor);
            if (actual.siguiente != null) System.out.print(" -> ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    // Método principal para probar la caché LRU
    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN DE CACHÉ LRU ===\n");

        // Crear caché con capacidad 3
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Insertar elementos
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.mostrar(); // 3=C -> 2=B -> 1=A  (3 es el más reciente)

        // Acceder a la clave 1 (se mueve al frente)
        System.out.println("get(1) -> " + cache.get(1));
        cache.mostrar(); // 1=A -> 3=C -> 2=B

        // Insertar nuevo elemento, superando capacidad
        cache.put(4, "D"); // debe eliminar el menos usado (2=B)
        cache.mostrar(); // 4=D -> 1=A -> 3=C

        // Intentar obtener 2 (ya no existe)
        System.out.println("get(2) -> " + cache.get(2)); // null

        // Actualizar clave 3
        cache.put(3, "X");
        cache.mostrar(); // 3=X -> 4=D -> 1=A  (3 se mueve al frente)

        // Insertar otro
        cache.put(5, "E");
        cache.mostrar(); // 5=E -> 3=X -> 4=D  (se eliminó 1=A)

        // Verificar tamaño
        System.out.println("\nTamaño actual: " + cache.mapa.size() + " (capacidad: 3)");
    }
}