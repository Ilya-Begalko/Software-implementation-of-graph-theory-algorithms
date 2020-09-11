package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Falk {
    private ArrayList<Node> matrix_c;
    private ArrayList<ArrayList<Double>> matrix_f;
    private ArrayList<ArrayList<Double>> matrix_a;
    private int istok;
    private int stok;

    Falk(ArrayList<ArrayList<Double>> matrix_c, ArrayList<ArrayList<Double>> matrix_a, int istok, int stok) {
        this.matrix_c = new ArrayList<>();
        this.matrix_f = new ArrayList<>();
        for (int i = 0; i < matrix_c.size(); i++) {
            matrix_f.add(new ArrayList<>());
            for (Double elem : matrix_c.get(i)) {
                matrix_f.get(i).add(elem);
            }
        }
        this.matrix_a = matrix_a;
        for (int i = 0; i < matrix_c.size(); i++) {
            for (int j = 0; j < matrix_c.get(i).size(); j++) {
                if (j < i) {
                    if (matrix_c.get(i).get(j) != Double.NEGATIVE_INFINITY) {
                        matrix_c.get(i).set(j, 0.0);
                    }
                }
            }
        }

        for (ArrayList<Double> node : matrix_c) {
            this.matrix_c.add(new Node(node));
        }

        this.istok = istok;
        this.stok = stok;
    }

    private ArrayList<Integer> findCycle(ArrayList<ArrayList<Double>> ostat) {
        ArrayList<Double> d = new ArrayList<>();
        ArrayList<Integer> p = new ArrayList<>();
        for (int i = 0; i < ostat.size(); i++) {
            d.add(1.0);
            p.add(-1);
        }
        int x = -1;
        for (int k = 1; k < ostat.size(); k++) {
            x = -1;
            for (int i = 0; i < ostat.size(); i++) {
                ArrayList<Double> stroke = ostat.get(i);
                for (int j = 0; j < stroke.size(); j++) {
                    if (d.get(j) + ostat.get(j).get(i) < d.get(i)) {
                        d.set(i, Math.max(Double.NEGATIVE_INFINITY, d.get(j) + ostat.get(j).get(i)));
                        p.set(i, j);
                        x = i;
                    }
                }
            }
        }
        if (x == -1) {
            return null;
        } else {
            int y = x;
            for (int i = 0; i < ostat.size(); i++) {
                y = p.get(y);
            }
            ArrayList<Integer> path = new ArrayList<>();
            for (int curr = y; ; curr = p.get(curr)) {
                path.add(curr);
                if (curr == y && path.size() > 1) break;
            }
            Collections.reverse(path);
            return path;
        }
    }

    private void change(int i, int j, double f) {
        Node edge1 = matrix_c.get(i);
        Node edge2 = matrix_c.get(j);
        if (i < j) {
            edge1.setWeight(edge1.getWeight(j) - f, j);
            edge2.setWeight((edge2.getWeight(i) + f), i);
        } else {
            edge1.setWeight((edge1.getWeight(j) + f), j);
            edge2.setWeight((edge2.getWeight(i) - f), i);
        }
    }

    private ArrayList<ArrayList<Double>> getOstat() {
        ArrayList<ArrayList<Double>> ostat = new ArrayList<>();
        for (int i = 0; i < matrix_c.size(); i++) {
            ostat.add(new ArrayList<>());
            for (int j = 0; j < matrix_c.size(); j++) {
                ostat.get(i).add(matrix_a.get(i).get(j) * (matrix_c.get(i).getWeight(j) - matrix_f.get(i).get(j)));
            }
        }
        return ostat;
    }

    double[] max_pot() {
        double min_cost = 0;
        ArrayList<Integer> way = new ArrayList<>();
        boolean cont = true;
        double max_i = 0;
        while (cont) {
            way.add(istok);
            matrix_c.get(istok).setMark(new Double[]{Double.POSITIVE_INFINITY, -1.0});
            int it = istok;
            int previous = istok;
            double min = Double.POSITIVE_INFINITY;
            while (it != stok) {
                double[] max = matrix_c.get(it).max(matrix_c);
                if (max[0] > 0) {
                    matrix_c.get((int) max[1]).setMark(new Double[]{max[0], (double) it});
                    previous = it;
                    it = (int) max[1];
                    way.add((int) max[1]);
                    if (min > max[0]) {
                        min = max[0];
                    }
                    if (matrix_c.get(it).getWeight(stok) == 1) {
                        it = stok;
                    }
                } else if (it == istok) {
                    cont = false;
                    break;
                } else {
                    matrix_c.get(it).setMark(new Double[]{-1.0, -1.0});
                    way.remove(way.size() - 1);
                    it = previous;
                    if (way.size() > 1) previous = way.get(way.size() - 2);
                    else previous = way.get(way.size() - 1);
                }
            }
            if (cont) {
                for (int i = 0; i < way.size() - 1; i++) {
                    int v1 = way.get(i);
                    int v2 = way.get(i + 1);
                    change(v1, v2, min);
                }
                max_i += min;

                way.clear();

                for (Node node : matrix_c) {
                    node.setMark(new Double[]{0.0, 0.0});
                }
            }
        }

        for (int i = 0; i < matrix_c.size(); i++) {
            for (int j = 0; j < matrix_c.size(); j++) {
                if (j > i) matrix_c.get(i).setWeight(matrix_c.get(j).getWeight(i), j);
                else {
                    if (matrix_c.get(i).getWeight(j) != Double.NEGATIVE_INFINITY) {
                        matrix_c.get(i).setWeight(-matrix_c.get(i).getWeight(j), j);
                    }
                }
            }
        }

        while (true) {
            ArrayList<ArrayList<Double>> ostat = getOstat();
            ArrayList<Integer> path = findCycle(ostat);
            if (path != null) {
                int current;
                int next;
                Double min = Double.POSITIVE_INFINITY;
                for (int i = 0; i < path.size() - 2; i++) {
                    current = path.get(i);
                    next = path.get(i + 1);
                    Double c_ostat = ostat.get(current).get(next) / matrix_a.get(current).get(next);
                    c_ostat = Math.abs(c_ostat);
                    if (min > c_ostat) {
                        min = c_ostat;
                    }
                }
                for (int i = 0; i < path.size() - 2; i++) {
                    current = path.get(i);
                    next = path.get(i + 1);
                    if (current < next) {
                        matrix_c.get(current).addWeight(min, next);
                        matrix_c.get(next).addWeight(-min, current);
                    } else {
                        matrix_c.get(current).addWeight(-min, next);
                        matrix_c.get(next).addWeight(min, current);
                    }
                }
            } else break;
        }

        for (int i = 0; i < matrix_c.size(); i++) {
            for (int j = 0; j < matrix_c.size(); j++) {
                if (matrix_c.get(i).getWeight(j) > 0) {
                    min_cost += matrix_c.get(i).getWeight(j) * matrix_a.get(i).get(j);
                }
            }
        }

        return new double[]{max_i, min_cost};
    }
}

class Node {
    ArrayList<Double> out;
    private Double[] mark;

    Node(ArrayList<Double> out) {
        this.mark = new Double[]{0.0, 0.0};
        this.out = out;
    }

    void setMark(Double[] mark) {
        this.mark = mark;
    }

    private boolean notMarked() {
        return Arrays.equals(mark, new Double[]{0.0, 0.0});
    }

    void setWeight(Double d, int i) {
        out.set(i, d);
    }

    void addWeight(Double d, int i) {
        out.set(i, out.get(i) + d);
    }

    double getWeight(int i) {
        return out.get(i);
    }

    double[] max(ArrayList<Node> nodes) {
        double max = 0;
        int iter = 0;
        int index_max = 0;
        for (Double weight : out) {
            if (nodes.get(iter).notMarked()) {
                if (max < weight) {
                    index_max = iter;
                    max = weight;
                }
            }
            iter++;
        }

        return new double[]{max, (double) index_max};
    }

}
