package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io .*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class Controller {

        @FXML
        private MenuItem Prima_btn;

        @FXML
        private MenuItem prima_task_btn;

        @FXML
        private MenuItem kruskal_task_btn;

        @FXML
        private MenuItem Kraskala_btn;

        @FXML
        private MenuItem dij_task_btn;

        @FXML
        private MenuItem Dijkstra_btn;

        @FXML
        private MenuItem floyd_task_btn;

        @FXML
        private MenuItem Floyd_btn;

        @FXML
        private MenuItem bell_task_btn;

        @FXML
        private MenuItem Ford_btn;

        @FXML
        private MenuItem falk_task_btn;

        @FXML
        private MenuItem Falkerson_btn;

        @FXML
        private MenuItem Exit_btn;

        @FXML
        private MenuItem Back_btn;

        @FXML
        private TextArea Ostov_in;

        @FXML
        private Button Input_btn;

        @FXML
        private Label Answer;

        @FXML
        private Button clear_btn;

        @FXML
        private Label inf;

        @FXML
        private Label task_text;

        @FXML
        private ImageView falk_image;

        private int whose_quid = 0;//0-Prima,1-Krusk,2-Dijkstra,3-Floyd,4-Ford-bell

        private String[] tasks = new String[]{"Тимур решил проложить веревочный телеграф, который связал бы все домики, в которых живут ребята из его команды. Всего домиков N. По карте ребята вычислили координаты каждого домика (Xi, Yi) в целых числах и выписали их на бумаге. За единицу измерения координат они взяли один метр. Однако возник вопрос: какие домики нужно соединять веревочным телеграфом, чтобы связь была между всеми домиками (возможно, через другие домики), а общая длина всех веревок была как можно меньше?\n" +
                "Требуется написать программу, которая по координатам домиков определяла бы, какова минимальная общая длина всех веревок, соединяющих все домики между собой (возможно, через другие домики).", "Андрей работает системным администратором и планирует создание новой сети в своей компании. Всего будет N хабов, они будут соединены друг с другом с помощью кабелей.\n" +
                "Поскольку каждый сотрудник компании должен иметь доступ ко всей сети, каждый хаб должен быть достижим от любого другого хаба – возможно, через несколько промежуточных хабов. Поскольку имеются кабели различных типов и короткие кабели дешевле, требуется сделать такой план сети (соединения хабов), чтобы максимальная длина одного кабеля была как можно меньшей. Есть еще одна проблема – не каждую пару хабов можно непосредственно соединять по причине проблем совместимости и геометрических ограничений здания. Андрей снабдит вас всей необходимой информацией о возможных соединениях хабов.\n" +
                "Необходимо помочь Андрею найти способ соединения хабов, который удовлетворит всем указанным выше условиям.", "Предположим, что вы хотите проехать из Новосибирска в Волгоград, используя магистральные шоссейные дороги, соединяющие различные города. \n" +
                "В построенном графе определить кратчайший путь между городами."};

        private void setVisible(boolean visible) {
            Ostov_in.setVisible(visible);
            Input_btn.setVisible(visible);
            clear_btn.setVisible(visible);
            inf.setVisible(!visible);
            falk_image.setVisible(false);
            task_text.setVisible(false);
        }

        private ArrayList<ArrayList<Double>> get_matrix() {
            String[] text = Ostov_in.getText().split("\n");
            int cnt_cities = Integer.parseInt(text[0].split(" ")[0]);
            int cnt_ways = Integer.parseInt(text[0].split(" ")[1]);

            ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
            for (int i = 0; i < cnt_cities; i++) {
                matrix.add(new ArrayList<>());
                for (int j = 0; j < cnt_cities; j++) {
                    matrix.get(i).add(0.0);
                }
            }

            for (int i = 1; i <= cnt_ways; i++) {
                String[] stroke = text[i].split(" ");
                int u = Integer.parseInt(stroke[0]) - 1;
                int v = Integer.parseInt(stroke[1]) - 1;
                int w = Integer.parseInt(stroke[2]);
                matrix.get(u).set(v, (double) w);
                matrix.get(v).set(u, (double) w);
            }

            return matrix;
        }

        private void clear() {
            Answer.setText("");
            Ostov_in.setText("");
        }

        private void task(int i) {
            task_text.setText(tasks[i]);
        }

        private void minway() {
            setVisible(false);
            inf.setVisible(false);
            task_text.setVisible(true);
            task(2);
        }

        public void initialize() {
            String main_txt = "Выполнил:Бегалко Илья\n\n\nКурс:II\n\n\nФакультет:ИТиКС\n\n\nГруппа:ФИТ-181";
            inf.setText(main_txt);
            setVisible(false);
            clear_btn.setOnMouseClicked(mouseEvent -> clear());
            falk_image.setVisible(false);

            Prima_btn.setOnAction(actionEvent -> {
                setVisible(true);
                whose_quid = 0;
                clear();
            });
            prima_task_btn.setOnAction(actionEvent -> {
                setVisible(false);
                inf.setVisible(false);
                task_text.setVisible(true);
                task(0);
            });

            Kraskala_btn.setOnAction(actionEvent -> {
                setVisible(true);
                whose_quid = 1;
                clear();
            });

            kruskal_task_btn.setOnAction(actionEvent -> {
                setVisible(false);
                inf.setVisible(false);
                task_text.setVisible(true);
                task(1);
            });

            Input_btn.setOnMouseClicked(mouseEvent -> {
                if (Ostov_in.getText() != null && whose_quid == 0) {
                    String[] text = Ostov_in.getText().split("\n");
                    ArrayList<Coords> matrix = new ArrayList<>();
                    int cnt = Integer.parseInt(text[0]);
                    for (int i = 1; i <= cnt; i++) {
                        String[] coords = text[i].split(" ");
                        matrix.add(new Coords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
                    }

                    Prima prima = new Prima(matrix);
                    Answer.setText("Ответ:" + prima.min_way());
                } else if (Ostov_in.getText() != null && whose_quid == 1) {
                    String[] text = Ostov_in.getText().split("\n");
                    int cnt_hubs = Integer.parseInt(text[0].split(" ")[0]);
                    int cnt = Integer.parseInt(text[0].split(" ")[1]);
                    ArrayList<Edge> edges = new ArrayList<>();

                    for (int i = 1; i <= cnt; i++) {
                        ArrayList<Integer> temp = new ArrayList<>();
                        for (String s : text[i].split(" ")) {
                            temp.add(Integer.parseInt(s));
                        }
                        edges.add(new Edge(temp.get(0), temp.get(1), temp.get(2)));
                    }
                    edges.sort(Edge::compareTo);
                    Kruskal kruskal = new Kruskal(edges, cnt_hubs);
                    ArrayList<Double> ans = kruskal.min_way();
                    StringBuilder answer = new StringBuilder("Ответ:\n" + ans.get(ans.size() - 1) + " " + ans.get(ans.size() - 2) + "\n");
                    for (int i = 0; i < ans.size() - 2; i += 2) {
                        answer.append(ans.get(i)).append(" ").append(ans.get(i + 1)).append("\n");
                    }
                    Answer.setText(String.valueOf(answer));
                } else if (Ostov_in.getText() != null && whose_quid == 2) {
                    ArrayList<ArrayList<Double>> matrix = get_matrix();

                    Dijkstra dijkstra = new Dijkstra(matrix, 0, matrix.size() - 1);
                    Answer.setText("Ответ:" + dijkstra.min_way());
                } else if (Ostov_in.getText() != null && whose_quid == 3) {
                    ArrayList<ArrayList<Double>> matrix = get_matrix();

                    Floyd floyd = new Floyd(matrix, 0, matrix.size() - 1);
                    Answer.setText("Ответ:" + floyd.min_way());
                } else if (Ostov_in.getText() != null && whose_quid == 4) {
                    ArrayList<ArrayList<Double>> matrix = get_matrix();

                    Ford_Bell fordBell = new Ford_Bell(matrix, 0, matrix.size() - 1);
                    Answer.setText("Ответ:" + fordBell.min_way());
                }
            });

            Dijkstra_btn.setOnAction(actionEvent -> {
                setVisible(true);
                whose_quid = 2;
                clear();
            });

            dij_task_btn.setOnAction(actionEvent -> minway());

            Floyd_btn.setOnAction(actionEvent -> {
                setVisible(true);
                whose_quid = 3;
                clear();
            });

            floyd_task_btn.setOnAction(actionEvent -> minway());

            Ford_btn.setOnAction(actionEvent -> {
                setVisible(true);
                whose_quid = 4;
                clear();
            });

            bell_task_btn.setOnAction(actionEvent -> minway());

            Falkerson_btn.setOnAction(actionEvent -> {
                setVisible(false);
                inf.setVisible(false);
                clear();

                ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
                ArrayList<ArrayList<Double>> matrix_a = new ArrayList<>();
                try {
                    File file = new File("falk.txt");
                    FileReader fr = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fr);
                    String line = reader.readLine();
                    ArrayList<Double> row_matr = new ArrayList<>();
                    Pattern pattern = Pattern.compile("(-*[\\d]+)\\((-*[\\d]+)\\)");
                    ArrayList<Double> row_a = new ArrayList<>();
                    while (line != null) {
                        row_matr.clear();
                        row_a.clear();
                        String[] row = line.split(" ");
                        for (String simb : row) {
                            if (simb.equals("-Inf")) {
                                row_matr.add(Double.NEGATIVE_INFINITY);
                                row_a.add(Double.POSITIVE_INFINITY);
                            } else {
                                Matcher matcher = pattern.matcher(simb);
                                if (matcher.matches()) {
                                    row_matr.add(Double.parseDouble(matcher.group(1)));
                                    row_a.add(Double.parseDouble(matcher.group(2)));
                                }
                            }
                        }
                        matrix.add((ArrayList<Double>) row_matr.clone());
                        matrix_a.add((ArrayList<Double>) row_a.clone());
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Falk falk = new Falk(matrix, matrix_a, 0, 6);
                double[] ans = falk.max_pot();
                Answer.setVisible(true);
                Answer.setText("Ответ:\nМаксимальный поток:\n" + ans[0] + "\nМинимальная стоимость:\n" + ans[1]);
                Class<?> clazz = this.getClass();

                InputStream input = clazz.getResourceAsStream("../sample/unknown.png");

                Image image = new Image(input);

                falk_image.setImage(image);
                falk_image.setVisible(true);
            });

            Back_btn.setOnAction(actionEvent -> {
                setVisible(false);
                clear();
            });

            Exit_btn.setOnAction(actionEvent -> System.exit(0));
        }
    }

