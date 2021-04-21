import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {

    //не посещенный узел с известным минимальным растоянием(поиск)
    //d - минимальное расстояние до узла
    //u - посещен ли узел
    private Node min_existing_path_node(int[][] d, boolean[][] u) {
        Node node = new Node(-1, -1);
        //дабы зайти в условие
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                if (!u[i][j] && ((node.getX() == -1 && node.getY() == -1) || d[i][j] < d[node.getX()][node.getY()])) {
                    node.setX(i);
                    node.setY(j);
                }
            }
        }
        return node;
    }

    public List<Node> shortest_path(int[][] map, Node start, Node end) {
        //для построения пути(восстановления)
        Node[][] p = new Node[map.length][map[0].length];
        //для заполнения посещенных нод
        boolean[][] u = new boolean[map.length][map[0].length];
        //хранит текущее минимальное расстояние до узла(от стартового)
        int[][] d = new int[map.length][map[0].length];

        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                d[i][j] = Integer.MAX_VALUE;
            }
        }

        d[start.getX()][start.getY()] = 0;
        boolean is_end = false;

        for (int i = 0; i < map.length && !is_end; i++) { //
            for (int j = 0; j < map[0].length; j++) { // за i*j шагов (гарантированно будет найден короткий путь
                Node v = min_existing_path_node(d, u);

                if (d[v.getX()][v.getY()] == Integer.MAX_VALUE) {
                    is_end = true;
                    break;
                }
                u[v.getX()][v.getY()] = true;

                for (int dir = 0; dir < 4; dir++) { //dir(0-up, 1-down, 2-left, 3-right)
                    if ((dir == 0 && v.getX() == 0) ||
                            (dir == 1 && v.getX() == map.length - 1) ||
                            (dir == 2 && v.getY() == 0) ||
                            (dir == 3 && v.getY() == map[0].length - 1)) {
                        continue;
                    }

                    Node nodeTo = new Node(v.getX() + (dir == 0 ? -1 : (dir == 1 ? 1 : 0)),
                            v.getY() + (dir == 2 ? -1 : (dir == 3 ? 1 : 0)));
                    //разница высот + минимальное расстояние до узла V
                    //проверяем, выгоднее ли идти до соседа через точку V или другой путь
                    int len = d[v.getX()][v.getY()] + Math.abs(map[v.getX()][v.getY()] - map[nodeTo.getX()][nodeTo.getY()]);

                    if (d[nodeTo.getX()][nodeTo.getY()] > d[v.getX()][v.getY()] + len) {
                        d[nodeTo.getX()][nodeTo.getY()] = d[v.getX()][v.getY()] + len;
                        p[nodeTo.getX()][nodeTo.getY()] = v;
                    }
                }
            }
        }
        List<Node> path = new ArrayList<>();
        //наш искомый путь
        for(Node v = end;!v.equals(start);v = p[v.getX()][v.getY()]){
            path.add(v);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }
}
