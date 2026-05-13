import java.util.*;

class Item {
    String nombre;
    String tipo;
    int valor;
    int peso;
    int rareza;

    Item(String nombre, String tipo, int valor, int peso, int rareza) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.peso = peso;
        this.rareza = rareza;
    }

    String etiquetaRareza() {
        switch (rareza) {
            case 1: return "⚪ Común";
            case 2: return "🟢 Poco común";
            case 3: return "🔵 Raro";
            case 4: return "🟣 Épico";
            case 5: return "🟡 Legendario";
            default: return "???";
        }
    }

    public String toString() {
        return etiquetaRareza() + " | " + nombre + " (" + tipo + ") - "
            + valor + " oro, " + peso + " kg";
    }
}

class Inventario {
    private Item[] items;
    private int cantidad;

    Inventario(int capacidad) {
        items = new Item[capacidad];
        cantidad = 0;
    }

    void agregar(Item item) {
        if (cantidad < items.length) items[cantidad++] = item;
    }

    void mostrar() {
        System.out.println("\n=== Inventario (" + cantidad + " objetos) ===");
        for (int i = 0; i < cantidad; i++)
            System.out.println("  " + (i + 1) + ". " + items[i].toString());
    }

    void ordenarPorValor() {
        for (int i = 0; i < cantidad - 1; i++) {
            boolean intercambio = false;
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (items[j].valor > items[j + 1].valor) {
                    Item temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                    intercambio = true;
                }
            }
            if (!intercambio) break;
        }
        System.out.println("\n✔ Inventario ordenado por valor (menor a mayor).");
    }

    void ordenarPorRareza() {
        for (int i = 0; i < cantidad - 1; i++) {
            int indiceMax = i;
            for (int j = i + 1; j < cantidad; j++)
                if (items[j].rareza > items[indiceMax].rareza) indiceMax = j;
            Item temp = items[i];
            items[i] = items[indiceMax];
            items[indiceMax] = temp;
        }
        System.out.println("\n✔ Inventario ordenado por rareza (legendario primero).");
    }

    void ordenarPorPeso() {
        for (int i = 1; i < cantidad; i++) {
            Item clave = items[i];
            int j = i - 1;
            while (j >= 0 && items[j].peso > clave.peso) {
                items[j + 1] = items[j];
                j--;
            }
            items[j + 1] = clave;
        }
        System.out.println("\n✔ Inventario ordenado por peso (más liviano primero).");
    }

    void ordenarPorNombre() {
        for (int i = 0; i < cantidad - 1; i++) {
            boolean intercambio = false;
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (items[j].nombre.compareTo(items[j + 1].nombre) > 0) {
                    Item temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                    intercambio = true;
                }
            }
            if (!intercambio) break;
        }
        System.out.println("\n✔ Inventario ordenado por nombre (alfabético).");
    }

    Item buscarPorValor(int valorBuscado) {
        int inicio = 0, fin = cantidad - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (items[medio].valor == valorBuscado) return items[medio];
            if (items[medio].valor < valorBuscado) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }
}

class RegistroCombate {
    String enemigo;
    int dañoTotal;
    int ronda;

    RegistroCombate(String enemigo, int dañoTotal, int ronda) {
        this.enemigo = enemigo;
        this.dañoTotal = dañoTotal;
        this.ronda = ronda;
    }

    public String toString() {
        return "Ronda " + ronda + ": vs " + enemigo + " (" + dañoTotal + " daño)";
    }
}

class HistorialCombates {
    static void ordenarPorDaño(RegistroCombate[] registros, int inicio, int fin) {
        if (inicio >= fin) return;
        int medio = (inicio + fin) / 2;
        ordenarPorDaño(registros, inicio, medio);
        ordenarPorDaño(registros, medio + 1, fin);
        merge(registros, inicio, medio, fin);
    }

    private static void merge(RegistroCombate[] arr, int inicio, int medio, int fin) {
        RegistroCombate[] temp = new RegistroCombate[fin - inicio + 1];
        int i = inicio, j = medio + 1, k = 0;
        while (i <= medio && j <= fin) {
            if (arr[i].dañoTotal >= arr[j].dañoTotal) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }
        while (i <= medio) temp[k++] = arr[i++];
        while (j <= fin) temp[k++] = arr[j++];
        for (int m = 0; m < temp.length; m++) arr[inicio + m] = temp[m];
    }
}

class HeroeRanking {
    String nombre;
    int nivel;
    int victorias;
    int dañoTotal;

    HeroeRanking(String nombre, int nivel, int victorias, int dañoTotal) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.victorias = victorias;
        this.dañoTotal = dañoTotal;
    }

    public String toString() {
        return nombre + " (Nv." + nivel + " | " + victorias + " victorias | "
            + dañoTotal + " daño total)";
    }
}

class Ranking {
    static void ordenarPorVictorias(HeroeRanking[] heroes, int inicio, int fin) {
        if (inicio >= fin) return;
        int indicePivote = particionar(heroes, inicio, fin);
        ordenarPorVictorias(heroes, inicio, indicePivote - 1);
        ordenarPorVictorias(heroes, indicePivote + 1, fin);
    }

    private static int particionar(HeroeRanking[] arr, int inicio, int fin) {
        int pivote = arr[fin].victorias;
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (arr[j].victorias >= pivote) {
                i++;
                HeroeRanking temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        HeroeRanking temp = arr[i + 1];
        arr[i + 1] = arr[fin];
        arr[fin] = temp;
        return i + 1;
    }

    static void mostrar(HeroeRanking[] heroes, int cantidad) {
        System.out.println("\n🏆 === RANKING DE HÉROES ===");
        for (int i = 0; i < cantidad; i++) {
            String medalla;
            switch (i) {
                case 0: medalla = "🥇"; break;
                case 1: medalla = "🥈"; break;
                case 2: medalla = "🥉"; break;
                default: medalla = "  " + (i + 1) + "."; break;
            }
            System.out.println(medalla + " " + heroes[i].toString());
        }
    }
}

public class Ordenamientos {
    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN DE ORDENAMIENTOS EN RPG ===\n");

        Inventario inv = new Inventario(20);
        inv.agregar(new Item("Espada de fuego", "arma", 250, 8, 4));
        inv.agregar(new Item("Poción menor", "pocion", 15, 1, 1));
        inv.agregar(new Item("Escudo de roble", "armadura", 80, 12, 2));
        inv.agregar(new Item("Hierba curativa", "material", 5, 1, 1));
        inv.agregar(new Item("Anillo del dragón", "accesorio", 1000, 1, 5));
        inv.agregar(new Item("Cota de malla", "armadura", 150, 15, 3));

        inv.mostrar();
        inv.ordenarPorValor();
        inv.mostrar();
        inv.ordenarPorRareza();
        inv.mostrar();
        inv.ordenarPorPeso();
        inv.mostrar();
        inv.ordenarPorNombre();
        inv.mostrar();

        System.out.println("\n--- BÚSQUEDA BINARIA ---");
        inv.ordenarPorValor();
        int precio = 150;
        Item encontrado = inv.buscarPorValor(precio);
        if (encontrado != null)
            System.out.println("🔍 Ítem con valor " + precio + ": " + encontrado.nombre);
        else
            System.out.println("🔍 No hay ítem con valor " + precio);

        System.out.println("\n--- HISTORIAL DE COMBATES (MERGE SORT) ---");
        RegistroCombate[] combates = {
            new RegistroCombate("Goblin", 25, 1),
            new RegistroCombate("Dragón", 150, 3),
            new RegistroCombate("Orco", 60, 2),
            new RegistroCombate("Slime", 10, 4)
        };
        System.out.println("Antes:");
        for (RegistroCombate r : combates) System.out.println("  " + r);
        HistorialCombates.ordenarPorDaño(combates, 0, combates.length - 1);
        System.out.println("Después (mayor daño primero):");
        for (RegistroCombate r : combates) System.out.println("  " + r);

        System.out.println("\n--- RANKING DE HÉROES (QUICK SORT) ---");
        HeroeRanking[] heroes = {
            new HeroeRanking("Aldric", 15, 42, 3200),
            new HeroeRanking("Luna", 18, 67, 5100),
            new HeroeRanking("Thorin", 12, 28, 1800),
            new HeroeRanking("Elara", 20, 89, 7500),
            new HeroeRanking("Kael", 16, 55, 4300)
        };
        Ranking.ordenarPorVictorias(heroes, 0, heroes.length - 1);
        Ranking.mostrar(heroes, heroes.length);
    }
}