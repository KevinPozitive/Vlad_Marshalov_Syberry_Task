import java.io.*;
import java.util.List;

public class Rover {
    public static void calculateRoverPath(int[][] map) throws FileNotFoundException {
        Service service = new Service();
        List<Node> path = service.shortest_path(map,new Node(0,0),new Node(map.length - 1, map[0].length - 1));
        int fuel = 0;
        File file = new File("path-plan.txt");
        PrintWriter pw = new PrintWriter(file);
        for(int j = 0; j<path.size();j++){
            pw.println("["+path.get(j).getX()+","+path.get(j).getY()+"]");
        }
        for(int i = 0; i<path.size()-1;i++){
            fuel += Math.abs(map[path.get(i).getX()][path.get(i).getY()] - map[path.get(i+1).getX()][path.get(i+1).getY()]);
        }
        int steps = path.size()-1;
        pw.println("Steps: " + steps);
        fuel += path.size()-1;
        pw.println("Fuel: "+ fuel);

        pw.close();
    }

}
