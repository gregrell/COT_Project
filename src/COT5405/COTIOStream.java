package COT5405;

import ShortPath.Obstacle;
import ShortPath.Polygon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 11/13/16.
 */
public class COTIOStream {
    private String savename = "COT5405.dat";

    public COTIOStream(){


    }

    public void saveObstacles(List<Obstacle> O) {
        try {
            FileOutputStream fos = new FileOutputStream(savename, false);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for(Obstacle o:O){
                    oos.writeObject(o);
                    oos.flush();
                }
                System.out.println("Obstacles Saved");
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }











    public List<Obstacle> loadObstacles(){
        List<Obstacle> out = new ArrayList<Obstacle>();

        try {
            FileInputStream fis = new FileInputStream(savename);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);

                boolean eof=false;
                while(!eof){
                    try {
                        Obstacle tmp = (Obstacle) ois.readObject();
                        out.add(tmp);
                    } catch (ClassNotFoundException e) {
                        eof=true;
                        System.out.println("class not found exception");
                    }
                    catch (EOFException e){
                        eof=true;
                    }

                }
                System.out.println(out.size()+" Obstacles Loaded");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return out;

    }



}
