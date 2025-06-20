import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstSearch{
    private int [] edgeTo;
    private int [] distTo;
    private boolean [] marked;
    private int s;

    public BreadthFirstSearch(Graph g, int ref) {
        this.s=ref;
        edgeTo=new int [g.V()];
        distTo=new int [g.V()];
        marked=new boolean [g.V()];
        edgeTo[ref] = ref;           // marca o pai do nó de origem como ele próprio
        distTo[ref] = 0;             //
        marked[ref] = true;          //
        bfs(g,s);
    }

    private void bfs(Graph g, int ref) {
        Queue<Integer> q = new ArrayDeque<Integer>();
        q.add(ref);
        distTo[ref]=0;
        marked[ref]=true;
        while(!q.isEmpty()){
            int aux = q.remove();

            if(aux!=edgeTo[aux])
                distTo[aux]=distTo[edgeTo[aux]]+1;

            for(int adj: g.adj(aux))
                if(!marked[adj]){
                    marked[adj]=true;
                    edgeTo[adj]=aux;
                    q.add(adj);
                }
        }
    }

    public boolean hasPath(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!marked[v]) return null;

        Bag b = new Bag();
        b.add(v);
        while(v!=edgeTo[v]){
            v=edgeTo[v];
            b.add(v);
        }
        return b;

    }

}