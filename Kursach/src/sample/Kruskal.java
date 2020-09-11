package sample;

import java.util.ArrayList;

class Kruskal {
    private ArrayList<Edge> edges;
    private int count_node;

    Kruskal(ArrayList<Edge> edges, int node) {
        this.edges = edges;
        count_node = node;
    }

    ArrayList<Double> min_way() {
        double max_w = 0;
        double count = 0;
        ArrayList<Double> mass = new ArrayList<>();
        Components components = new Components(count_node);
        for (Edge edge : edges) {
            if (!components.comp(edge)) {
                components.unite(edge);
                mass.add((double) edge.getU());
                mass.add((double) edge.getV());
                count++;
                if (edge.getW() > max_w) {
                    max_w = edge.getW();
                }
            }
        }
        mass.add(max_w);
        mass.add(count);
        return mass;
    }

}

class Edge {
    private int v;
    private int u;
    private double w;

    Edge(int u, int v, double w) {
        this.v = v;
        this.u = u;
        this.w = w;
    }

    double getW() {
        return w;
    }

    int getV() {
        return v;
    }

    int getU() {
        return u;
    }

    int compareTo(Edge edge) {
        if (w != edge.w) return w < edge.w ? -1 : 1;
        return 0;
    }

}

class Components {
    private ArrayList<Integer> array_comp = new ArrayList<>();

    Components(int size) {
        for (int i = 0; i < size; i++) {
            array_comp.add(i);
        }
    }

    boolean comp(Edge edge) {
        return (array_comp.get(edge.getV() - 1).equals(array_comp.get(edge.getU() - 1)));
    }

    void unite(Edge edge) {
        if (array_comp.get(edge.getV() - 1) < array_comp.get(edge.getU() - 1)) {
            array_comp.set(edge.getU() - 1, array_comp.get(edge.getV() - 1));
        } else {
            array_comp.set(edge.getV() - 1, array_comp.get(edge.getU() - 1));
        }
    }
}
