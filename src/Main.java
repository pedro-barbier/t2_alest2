import java.io.*;

public class Main {
    public static void main(String[] args) {
        File arquivo = new File("src/casosDeTeste");
        File[] arquivos = arquivo.listFiles();
        assert arquivos != null;

        int contador = 0;
        for (File file : arquivos) {
            System.out.println("Caminhos do mapa "+contador);
            Mapa atual = new Mapa(file);

            char[] map2 = new char[atual.height * atual.width];
            for (int i = 0; i < atual.height; i++) {
                for (int j = 0; j < atual.width; j++) {
                    map2[i * atual.width + j] = atual.lines[i].charAt(j);
                }
            }

            int ultimo_porto = 1;
            int combustivel_a_gastar = 0;
            BreadthFirstSearch bfs = new BreadthFirstSearch(atual.grafo, atual.pos_nums[1]);
            for (int i = 2; i < atual.pos_nums.length + 1; i++) {
                int j = i == 10 ? 1 : i;

                if (!bfs.hasPath(atual.pos_nums[j])) {
                    System.out.println("Impossível chegar em " + j);
                    continue;
                }
                Iterable<Integer> p = bfs.pathTo(atual.pos_nums[j]);

                if (i != 10) bfs = new BreadthFirstSearch(atual.grafo, atual.pos_nums[i]);

                System.out.print("Caminho de " + ultimo_porto + " para " + j + ": ");
                ultimo_porto = i;

                for (Integer p1 : p) {
                    System.out.print(p1 + " ");

                    if (!Character.isDigit(map2[p1])) map2[p1] = 'X';

                    combustivel_a_gastar++;
                }
                System.out.println();
                combustivel_a_gastar--;
            }

            System.out.println("Combustivel a gastar para explorar todos portos possíveis, e retornar ao porto original: " + combustivel_a_gastar);
            System.out.println();

            Out out = new Out("mapas_caminho/mapa" + contador + ".txt");

            out.println("Custo mínimo para a viagem: " + combustivel_a_gastar);
            for (int i = 0; i < atual.height; i++) {
                for (int j = 0; j < atual.width; j++) {
                    out.print(map2[i * atual.width + j]);
                }
                out.println();
            }
            out.close();

            contador++;
        }

    }
}