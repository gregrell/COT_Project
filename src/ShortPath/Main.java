package ShortPath;

import VVComplex.VisibilityGraph;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application implements EventHandler<ActionEvent>{

    private static final int viewWidth = 1400;
    private static final int viewHeight = 1050;
    private static final int gridsize = 1000;
    private static final int numObstacles = 5;
    public static final int MaxObstacleSize=gridsize/numObstacles;
    private static final Color gridColor = Color.DARKBLUE;
    private static final Color fontColor = Color.WHITE;
    private static final String windowTitle = "Obstacle Ground";
    private static final String tempTxt ="GJK Testing";
    private static final String tempTxt2 ="VisibilityGraph";

    private final Button GeneratePolygons = new Button();
    private final Button ShortestPath = new Button();
    private final Button TempTest = new Button();
    private final Button TempTest2 = new Button();
    private final Canvas drawingarea = new Canvas();
    private final GraphicsContext gc = drawingarea.getGraphicsContext2D();
    private ObstacleRange range;
    private VisibilityGraph vg;


    @Override
    public void start(Stage primaryStage) throws Exception{
        @SuppressWarnings("UnusedAssignment") Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(windowTitle);

        drawingarea.setHeight(viewHeight);
        drawingarea.setWidth(viewWidth);


        HBox HBoxPane = new HBox();
        BorderPane Border_Pane = new BorderPane();

        HBoxPane.setSpacing(10);
        HBoxPane.setPadding(new Insets(5,15,5,15));
        HBoxPane.getChildren().add(GeneratePolygons);
        HBoxPane.getChildren().add(ShortestPath);
        HBoxPane.getChildren().add(TempTest);
        HBoxPane.getChildren().add(TempTest2);

        HBoxPane.setStyle("-fx-background-color: #336699;");
        GeneratePolygons.setText("Generate Obstacles");
        ShortestPath.setText("Get Shortest Path");
        TempTest.setText(tempTxt);
        TempTest2.setText(tempTxt2);

        GeneratePolygons.setOnAction(this);
        ShortestPath.setOnAction(this);
        TempTest.setOnAction(this);
        TempTest2.setOnAction(this);

        Border_Pane.setTop(HBoxPane);
        Border_Pane.setCenter(drawingarea);

        gc.setStroke(fontColor);
        colorCanvas();
        gc.setStroke(fontColor);

        //generate obstacle field
        gc.setFill(Color.RED);


        Scene theScene = new Scene(Border_Pane,viewWidth,viewHeight);
        primaryStage.setScene(theScene);
        primaryStage.show();
    }


    public static void main(String[] args) {launch(args);}


    @Override
    public void handle(ActionEvent event) {
        /*START OF OBSTACLE GENERATOR EVENTS HANDLER*/
        if (event.getSource()==GeneratePolygons) {
            clearCanvas();
            Double doubleXbound = gc.getCanvas().getWidth();
            Double doubleYbound = gc.getCanvas().getHeight();
            Double xratio = doubleXbound / gridsize;
            Double yratio = doubleYbound / gridsize;



            gc.setFill(Color.RED);

            if (!ObstacleRange.exists) {
                range = new ObstacleRange(gridsize, gridsize, numObstacles);
            } else {
                range.Create();
            }

            // set text at top of obstacle screen view
            gc.strokeText(range.toString(), 5, 15);


            // paint the obstacles on the view

            for (Obstacle O:range.getObstacles()) {
                if(O instanceof PointObstacle){
                    gc.fillRect(O.getRoot().x*(xratio),O.getRoot().y,5*(yratio),5);
                }
                if(O instanceof Polygon){
                    Polygon p = (Polygon)O;
                    double[] xpoints=p.getHullXpointsAsDouble();
                    double[] ypoints=p.getHullYpointsAsDouble();

                    for(int i=0;i<xpoints.length;i++){
                        xpoints[i]=xpoints[i]*xratio;
                        ypoints[i]=ypoints[i]*yratio;
                    }
                    gc.strokePolygon(xpoints,ypoints,p.getHull().size());
                }
            }

        }

/* END OF OBSTACLE GENERATOR EVENT HANDLER*/


        else if(event.getSource()==ShortestPath) {
            //TODO remove this
            System.out.println("Need to calculate shortest path");
        }





        else if(event.getSource()==TempTest) {
            clearCanvas();
            //TODO remove this
            Polygon poly = new Polygon(100,100,10);
            Polygon poly2 = new Polygon(100,100,10);
            poly2.setRoot(new Point(0,20));



            gc.strokePolygon(poly.getHullXpointsAsDouble(),poly.getHullYpointsAsDouble(),poly.getHull().size());
            gc.strokePolygon(poly2.getHullXpointsAsDouble(),poly2.getHullYpointsAsDouble(),poly2.getHull().size());

            GJK myGJK=new GJK();
            Point direction = new Point(1000,1000);

            Point support=myGJK.Support(poly,direction);
            Point support2=myGJK.Support(poly2, direction.inverse());


            gc.strokeLine(0,0,direction.getX(),direction.getY());
            gc.setFill(Color.RED);

            gc.fillRect(support.getX()-2,support.getY()-2,5,5);

            gc.setFill(Color.VIOLET);
            gc.fillRect(support2.getX()-2,support2.getY()-2,5,5);

            System.out.println(myGJK.GJKCollision(poly,poly2));


        }





        else if(event.getSource()==TempTest2) {
            //TODO remove this
            vg=new VisibilityGraph();
            vg.addObstacles(range.getObstacles());
            drawEdges(vg.getGraph().getEdges());

        }

    }


    private void clearCanvas(){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        colorCanvas();
    }

    private void colorCanvas(){
        gc.setFill(gridColor);
        gc.fillRect(0,0,gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void drawEdges(List<Edge> edge){
        gc.setStroke(Color.RED);
        for(Edge e:edge){
            gc.strokeLine(e.p1.x,e.p1.y,e.p2.x,e.p2.y);
        }
    }


}
