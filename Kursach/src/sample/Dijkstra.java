package sample;
import java.util.ArrayList;

class Dijkstra {
    private ArrayList<ArrayList<Double>> matrix;
    private int stok;
    private int istok;

    Dijkstra(ArrayList<ArrayList<Double>> matrix, int istok, int stok) {
        this.matrix = matrix;
        this.istok = istok;
        this.stok = stok;
        for (ArrayList<Double> arrayList : matrix) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (arrayList.get(j) == 0) {
                    arrayList.set(j, Double.POSITIVE_INFINITY);
                }
            }
        }

    }

    double min_way() {
        ArrayList<Integer> notused = new ArrayList<>();//Множество не использованных вершин
        for (int i = 1; i < matrix.size(); i++) {
            notused.add(i);
        }
        ArrayList<Double> stroke = matrix.get(istok);//берем пути для 1 вершины
        System.out.println(stroke);
        while (notused.size() != 0) {
            double min_weight = Double.POSITIVE_INFINITY;
            for (int i : notused) {
                if (min_weight > stroke.get(i)) {
                    min_weight = stroke.get(i);
                }
            }
            int index_min = stroke.indexOf(min_weight);
            notused.remove((Integer) index_min);
            System.out.println(notused);
            System.out.println(" " + stroke);
            if (index_min == stok) {
                break;
            } else {
                for (int i : notused) {
                    stroke.set(i, Math.min(stroke.get(i), matrix.get(index_min).get(i) + min_weight));
                }
            }
        }
        System.out.println(stroke);
        return stroke.get(stok);
    }

}
