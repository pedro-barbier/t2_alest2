import java.io.*;

public class Mapa {
    public int height; // Quantidade de linhas do mapa
    public int width; // Quantidade de colunas do mapa
    public String[] lines; // O mapa convertido em uma lista de strings de suas linhas (utilizado para coordenadas)
    public int[] pos_nums = new int[10]; // Coordenadas dos portos no mapa
    Graph grafo; // Grafo do mapa indicando pontos navegaveis

    public Mapa(File arquivo) {
        LerArquivo(arquivo);
        initializeGraph();
    }

    public void LerArquivo(File arquivo) {
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader in = new BufferedReader(fr);

            String line = in.readLine();
            String[] cord_xy = line.split(" ");
            height = Integer.parseInt(cord_xy[0]);
            width = Integer.parseInt(cord_xy[1]);

            lines = new String[height];
            for (int i = 0; i < height; i++) {
                line = in.readLine();

                lines[i] = line;
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro na leitura do arquivo");
        }
    }

    public void initializeGraph(){
        grafo = new Graph(height * width);

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                char c = lines[i].charAt(j); // Caractere atual

                // Ignora posições inacessiveis
                if (c == '*') continue;
                // Salva coordenadas de cada porto
                if (Character.isDigit(c)) {
                    pos_nums[c - 48] = i*width + j;
                }
                // Conectar vertice atual ao vertice a sua direita
                if (j+1 < width && lines[i].charAt(j+1) != '*') {
                    grafo.addEdge(i*width + j, (i)*width + j+1);
                }
                // Conectar vertice atual ao vertice abaixo
                if (i+1 < height && lines[i+1].charAt(j) != '*') {
                    grafo.addEdge(i*width + j, (i+1)*width + j);
                }

            }

        }

    }
}
