import java.util.ArrayList;
import java.util.List;

public class UnionFind {



    public int countComponents(int n, int[][] edges) {
        int components = n;
        int[] rank = new int[n];
        int[] parent = new int[n];
        for(int i = 0 ; i<n ;i++){
            parent[i] = i;
            rank[i] = 1;
        }
        for(int i = 0 ; i< edges.length ;i++){
            components -= union(edges,edges[i][0],edges[i][1],parent,rank);
        }
        return components;
    }

    private int union(int[][] edges, int src, int dest, int[] parent,int[] rank) {
        int r1 = find(parent,src);
        int r2 = find(parent,dest);
        if(r1 == r2){
            return 0;
        }
        if(rank[r1] > rank[r2]){
            rank[r1]+=rank[r2];
            parent[r2] = r1;
        }else {
            rank[r2]+=rank[r1];
            parent[r1] = r2;
        }
        return 1;
    }

    private int find(int[] parent, int src) {
        if(src == parent[src]){
            return src;
        }
        return find(parent,parent[src]);
    }
}
