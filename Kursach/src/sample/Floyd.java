package sample;
import java.util.ArrayList;

class Floyd {
    private ArrayList<ArrayList<Double>> matrix;
    private int istok;
    private int stok;

    Floyd(ArrayList<ArrayList<Double>> matrix, int istok, int stok) {
        this.istok = istok;
        this.stok = stok;
        this.matrix = matrix;

        for (ArrayList<Double> arrayList : matrix) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (arrayList.get(j) == 0) {
                    arrayList.set(j, Double.POSITIVE_INFINITY);
                }
            }
        }
    }

    double min_way() {
        ArrayList<ArrayList<Double>> temp = matrix;
        for (int k = 0; k < matrix.size(); k++) {
            for (int i = 0; i < matrix.size(); i++) {
                ArrayList<Double> stroke = matrix.get(i);
                for (int j = 0; j < stroke.size(); j++) {
                    double min = Math.min(stroke.get(j), stroke.get(k) + matrix.get(k).get(j));
                    temp.get(i).set(j, min);
                }
            }
            matrix = temp;
        }
        return matrix.get(istok).get(stok);
    }
}
