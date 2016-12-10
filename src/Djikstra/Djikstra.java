package Djikstra;

import ShortPath.Edge;
import ShortPath.VectorAlgebra;
import Voronoi3.Vertex;

import java.util.*;

/**
 * Created by gregrell on 12/10/16.
 */
public class Djikstra {
    Hashtable<String,Float> dist = new Hashtable<String, Float>();
    Hashtable<String,Vertex> prev = new Hashtable<String,Vertex>();
    Hashtable<String,Edge> prevE = new Hashtable<String,Edge>();
    List<Vertex> Q = new ArrayList<Vertex>();
    Vertex source;

    public Djikstra(List<Vertex> V, List<Edge> E, Vertex source){
        this.source=source;
        for(Vertex v:V){
            dist.put(v.toString(),Float.POSITIVE_INFINITY);
            prev.put(v.toString(),v);//can't set null so set to itself
            Q.add(v);
        }

        dist.put(source.toString(),(float)0);
        compute();

        //System.out.println(pathTo(V.get(50)));

    }


    private void compute(){
        while(Q.size()!=0){
            float minDist=Float.POSITIVE_INFINITY;
            Vertex minElement=Q.get(0);
            for(Vertex v:Q){
                if(dist.get(v.toString())<minDist){//find the minimum vertex from the Q
                    minDist=dist.get(v.toString());
                    minElement=v;
                }
            }
            Q.remove(minElement); //remove it from the Q

            List<Vertex> neighbors = new ArrayList<Vertex>();
            List<Edge> neighborEdges = new ArrayList<Edge>();

            for(Edge e:minElement.getEdges()){ //extract each vertex neighboring Q, can only be found using the edges in minElement and looking at their pointer to the next vertex
                if(Q.contains(e.getV1())){ //only one of these IF statements is guaranteed to trigger as the source point for one of the endpoints was just previously removed from Q
                    neighbors.add(e.getV1());
                    neighborEdges.add(e);
                }
                if(Q.contains(e.getV2())){
                    neighbors.add(e.getV2());
                    neighborEdges.add(e);
                }
            }

            for(Vertex v:neighbors){
                float alt = dist.get(minElement.toString()) + (float)VectorAlgebra.distance2pts(minElement.getP(),v.getP());
                if(alt<dist.get(v.toString())){
                    dist.put(v.toString(),alt);
                    prev.put(v.toString(),minElement);
                    prevE.put(v.toString(),neighborEdges.get(neighbors.indexOf(v)));
                }
            }

        }
    }

    public List<Edge> pathTo(Vertex v){
        Vertex node=v;
        List<Edge> path = new ArrayList<>();

        while(node!=source && !(prev.get(node.toString())==node)){
            path.add(prevE.get(node.toString()));
            node=prev.get(node.toString());
        }
        return path;
    }
}
