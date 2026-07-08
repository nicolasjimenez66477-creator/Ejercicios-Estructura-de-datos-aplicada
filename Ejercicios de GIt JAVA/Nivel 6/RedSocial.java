import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

// Clase Publicacion
class Publicacion {
    private String contenido;
    private LocalDateTime fecha;
    private int meGusta;

    public Publicacion(String contenido) {
        this.contenido = contenido;
        this.fecha = LocalDateTime.now();
        this.meGusta = 0;
    }

    public String getContenido() { return contenido; }
    public LocalDateTime getFecha() { return fecha; }
    public int getMeGusta() { return meGusta; }
    public void darMeGusta() { meGusta++; }

    // Relevancia: mientras más reciente y más likes, mayor valor
    public double getRelevancia() {
        long horas = ChronoUnit.HOURS.between(fecha, LocalDateTime.now());
        return (meGusta + 1) / (horas + 1.0);
    }
}

// Clase Usuario
class Usuario {
    private int id;
    private String nombre;
    private Set<Integer> amigos;
    private List<Publicacion> publicaciones;

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.amigos = new HashSet<>();
        this.publicaciones = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Set<Integer> getAmigos() { return amigos; }
    public List<Publicacion> getPublicaciones() { return publicaciones; }

    public void agregarAmigo(int amigoId) {
        amigos.add(amigoId);
    }

    public void publicar(String contenido) {
        publicaciones.add(new Publicacion(contenido));
    }

    // Feed propio ordenado por relevancia
    public List<Publicacion> getFeedOrdenado() {
        List<Publicacion> feed = new ArrayList<>(publicaciones);
        feed.sort((p1, p2) -> Double.compare(p2.getRelevancia(), p1.getRelevancia()));
        return feed;
    }
}

// Clase principal que maneja la red social
class RedSocial {
    private Map<Integer, Usuario> usuarios;

    public RedSocial() {
        usuarios = new HashMap<>();
    }

    public void agregarUsuario(Usuario u) {
        usuarios.put(u.getId(), u);
    }

    public Usuario getUsuario(int id) {
        return usuarios.get(id);
    }

    public void hacerAmigos(int id1, int id2) {
        Usuario u1 = usuarios.get(id1);
        Usuario u2 = usuarios.get(id2);
        if (u1 != null && u2 != null) {
            u1.agregarAmigo(id2);
            u2.agregarAmigo(id1);
        }
    }

    // Sugerir amigos: amigos de amigos que no sean directos ni el propio usuario
    public List<Integer> sugerirAmigos(int id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) return Collections.emptyList();

        Set<Integer> sugerencias = new HashSet<>();
        Set<Integer> amigosDirectos = usuario.getAmigos();

        for (int amigoId : amigosDirectos) {
            Usuario amigo = usuarios.get(amigoId);
            if (amigo != null) {
                for (int amigoDeAmigo : amigo.getAmigos()) {
                    if (amigoDeAmigo != id && !amigosDirectos.contains(amigoDeAmigo)) {
                        sugerencias.add(amigoDeAmigo);
                    }
                }
            }
        }
        return new ArrayList<>(sugerencias);
    }

    // Camino más corto usando BFS (devuelve lista de IDs en orden)
    public List<Integer> caminoMasCorto(int origen, int destino) {
        if (!usuarios.containsKey(origen) || !usuarios.containsKey(destino)) {
            return Collections.emptyList();
        }
        if (origen == destino) {
            return Arrays.asList(origen);
        }

        Map<Integer, Integer> padre = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();

        cola.offer(origen);
        visitados.add(origen);
        padre.put(origen, null); // marcamos raíz

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            Usuario u = usuarios.get(actual);
            for (int vecino : u.getAmigos()) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padre.put(vecino, actual);
                    if (vecino == destino) {
                        // Reconstruir camino
                        List<Integer> camino = new LinkedList<>();
                        Integer paso = destino;
                        while (paso != null) {
                            camino.add(0, paso);
                            paso = padre.get(paso); // puede ser null para el origen
                        }
                        return camino;
                    }
                    cola.offer(vecino);
                }
            }
        }
        return Collections.emptyList(); // no hay camino
    }

    // Detección de comunidades: componentes conexos (BFS)
    public List<Set<Integer>> detectarComunidades() {
        Set<Integer> visitados = new HashSet<>();
        List<Set<Integer>> comunidades = new ArrayList<>();

        for (int id : usuarios.keySet()) {
            if (!visitados.contains(id)) {
                Set<Integer> comunidad = new HashSet<>();
                Queue<Integer> cola = new LinkedList<>();
                cola.offer(id);
                visitados.add(id);
                while (!cola.isEmpty()) {
                    int actual = cola.poll();
                    comunidad.add(actual);
                    Usuario u = usuarios.get(actual);
                    for (int vecino : u.getAmigos()) {
                        if (!visitados.contains(vecino)) {
                            visitados.add(vecino);
                            cola.offer(vecino);
                        }
                    }
                }
                comunidades.add(comunidad);
            }
        }
        return comunidades;
    }

    // Método principal para probar
    public static void main(String[] args) {
        RedSocial red = new RedSocial();

        // Crear usuarios
        Usuario u1 = new Usuario(1, "Ana");
        Usuario u2 = new Usuario(2, "Luis");
        Usuario u3 = new Usuario(3, "Carla");
        Usuario u4 = new Usuario(4, "David");
        Usuario u5 = new Usuario(5, "Elena");

        red.agregarUsuario(u1);
        red.agregarUsuario(u2);
        red.agregarUsuario(u3);
        red.agregarUsuario(u4);
        red.agregarUsuario(u5);

        // Crear amistades
        red.hacerAmigos(1, 2);
        red.hacerAmigos(1, 3);
        red.hacerAmigos(2, 4);
        red.hacerAmigos(3, 4);
        red.hacerAmigos(4, 5);

        // Publicaciones
        u1.publicar("Hola mundo");
        u1.publicar("Java es genial");
        u2.publicar("Programando en red social");
        u3.publicar("Me gusta el café");
        u4.publicar("Hoy es un buen día");
        u5.publicar("Aprendiendo algoritmos");

        // Dar algunos likes
        u1.getPublicaciones().get(0).darMeGusta();
        u1.getPublicaciones().get(0).darMeGusta();
        u1.getPublicaciones().get(1).darMeGusta();

        // Pruebas
        System.out.println("=== Sugerencias para Ana (id=1) ===");
        System.out.println(red.sugerirAmigos(1)); // debería mostrar [4,5] o similar

        System.out.println("\n=== Camino más corto de Ana (1) a Elena (5) ===");
        System.out.println(red.caminoMasCorto(1, 5)); // [1, 2, 4, 5] o [1,3,4,5]

        System.out.println("\n=== Feed de Ana ordenado por relevancia ===");
        for (Publicacion p : u1.getFeedOrdenado()) {
            System.out.println(" - " + p.getContenido() + " (likes: " + p.getMeGusta() + ", relevancia: " + p.getRelevancia() + ")");
        }

        System.out.println("\n=== Comunidades detectadas ===");
        List<Set<Integer>> comunidades = red.detectarComunidades();
        for (Set<Integer> com : comunidades) {
            System.out.println("  " + com);
        }
    }
}