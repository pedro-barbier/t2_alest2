import java.io.*;

public class Main {
    public static void main(String[] args) {
        File arquivo = new File("src/casosDeTeste");
        File[] x = arquivo.listFiles();
        System.out.print("Casos a serem testados: ");
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i].getName() + (i == x.length -1 ? ".\n" : ", "));
        }

        Mapa atual = new Mapa(x[0]);

        char[] map2 = new char[atual.height * atual.width];
        for (int i = 0; i < atual.height; i++) {
            for (int j = 0; j < atual.width; j++) {
                map2[i*atual.width + j] = atual.lines[i].charAt(j);
            }
        }

        boolean porto_pulado = false;
        int combustivel_a_gastar = 0;
        BreadthFirstSearch bfs = new BreadthFirstSearch(atual.grafo, atual.pos_nums[1]);
        for (int i = 2; i < atual.pos_nums.length+1; i++) {
            int j = i == 10 ? 1 : i;
            if (!bfs.hasPath(atual.pos_nums[j])) {
                System.out.println("Impossível chegar em " + j);
                porto_pulado = true;
                continue;
            };
            Iterable<Integer> p = bfs.pathTo(atual.pos_nums[j]);

            if (i!=10) bfs = new BreadthFirstSearch(atual.grafo, atual.pos_nums[i]);

            System.out.print("Caminho de " + (i==10 ? (porto_pulado ? 8 : 9) : (porto_pulado ? (j-2) : (j-1))) + " para " + j + ": ");
            porto_pulado = false;

            for (Integer p1 : p) {
                System.out.print(p1 + " ");

                if (!Character.isDigit(map2[p1])) map2[p1] = 'X';

                combustivel_a_gastar++;
            }
            System.out.println();
            combustivel_a_gastar -= 2;
        }

        for (int i = 0; i < atual.height; i++) {
            for (int j = 0; j < atual.width; j++) {
                System.out.print(map2[i*atual.width + j]);
            }
            System.out.println();
        }

        System.out.println("Combustivel a gastar para explorar todos portos possíveis, e retornar ao porto original: " + combustivel_a_gastar);

    }
}