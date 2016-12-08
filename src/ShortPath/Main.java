package ShortPath;

import COT5405.COTIOStream;
import VVComplex.VisibilityGraph;
import Voronoi.*;
import Voronoi3.Voronoi3;
import Voronoi3.*;
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

import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements EventHandler<ActionEvent>{

    private static final int viewWidth = 1400;
    private static final int viewHeight = 1050;
    private static final int gridsize = 1000;
    private static final int numObstacles = 10;
    public static final int MaxObstacleSize=gridsize/10;
    private static final Color gridColor = Color.DARKBLUE;
    private static final Color fontColor = Color.WHITE;
    private static final String windowTitle = "Obstacle Ground";
    private static final String tempTxt ="GJK Testing";
    private static final String tempTxt2 ="VisibilityGraph";
    private static final String saveButtonText ="Save Obstacles";
    private static final String loadButtonText ="Load Obstacles";


    private final Button GeneratePolygons = new Button();
    private final Button ShortestPath = new Button();
    private final Button GJK = new Button();
    private final Button TempTest2 = new Button();
    private final Button SaveButton = new Button();
    private final Button LoadButton = new Button();
    private final Canvas drawingarea = new Canvas();
    private final GraphicsContext gc = drawingarea.getGraphicsContext2D();
    private ObstacleRange range;
    private VisibilityGraph vg;
    private List<Obstacle> O;



    private Double doubleXbound;
    private Double doubleYbound;
    private Double xratio;
    private Double yratio;


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
        HBoxPane.getChildren().add(GJK);
        HBoxPane.getChildren().add(TempTest2);
        HBoxPane.getChildren().add(SaveButton);
        HBoxPane.getChildren().add(LoadButton);

        HBoxPane.setStyle("-fx-background-color: #336699;");
        GeneratePolygons.setText("Generate Obstacles");
        ShortestPath.setText("Get Shortest Path");
        GJK.setText(tempTxt);
        TempTest2.setText(tempTxt2);
        SaveButton.setText(saveButtonText);
        LoadButton.setText(loadButtonText);

        GeneratePolygons.setOnAction(this);
        ShortestPath.setOnAction(this);
        GJK.setOnAction(this);
        TempTest2.setOnAction(this);
        SaveButton.setOnAction(this);
        LoadButton.setOnAction(this);

        Border_Pane.setTop(HBoxPane);
        Border_Pane.setCenter(drawingarea);

        gc.setStroke(fontColor);
        colorCanvas();
        gc.setStroke(fontColor);

        //generate obstacle field
        gc.setFill(Color.RED);


        doubleXbound = gc.getCanvas().getWidth();
        doubleYbound = gc.getCanvas().getHeight();
        xratio = doubleXbound / gridsize;
        yratio = doubleYbound / gridsize;

        if (!ObstacleRange.exists) {
            range = new ObstacleRange(gridsize, gridsize, numObstacles);
        } else {
            range.Create();
        }



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
            gc.setStroke(Color.WHITE);
            gc.setFill(Color.WHITE);

            if (!ObstacleRange.exists) {
                range = new ObstacleRange(gridsize, gridsize, numObstacles);
            } else {
                range.Create();
            }


            // set text at top of obstacle screen view
            gc.strokeText(range.toString(), 5, 15);


            // paint the obstacles on the view
/*************************************************************************************************************
            for (Obstacle O:range.getObstacles()) {
                if(O instanceof PointObstacle){
                    //gc.fillRect(O.getRoot().x*(xratio),O.getRoot().y,4*(yratio),4);
                    //System.out.println(O.toString());
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
**************************************************************************************************************/

            gc.setFill(Color.RED);
            gc.setStroke(Color.RED);


            ///Generate Voronoi diagram object
            //System.out.println("Range obstacles "+range.getObstacles());
            //VoronoiDiagram Vor = new VoronoiDiagram(range.getObstacles(),gc);
            //Voronoi2 Vor2 = new Voronoi2(range.getObstacles(),gc);

            Voronoi3 Vor3 = new Voronoi3(range.getObstacles());
            System.out.println(Vor3.getDG().getTriangles().size());


            gc.setFill(Color.WHITE);
            gc.setStroke(Color.WHITE);

            for(Point p:Vor3.getPoints()){// Draw the point obstacles
                gc.fillRect(p.getX(),p.getY(),3,3);
                gc.strokeText(p.toString(),p.getX(),p.getY());
            }


            for(Edge e:Vor3.getDG().getEdges()){ //Draw all the edges of the Delaunay triangles
                //System.out.println("Edges Drawn "+seg.toString());

                //Edge e=seg.toEdge();
                gc.setStroke(Color.RED);


                gc.strokeLine(e.p1.x,e.p1.y,e.p2.x,e.p2.y);
            }

            //Draw the Voronoi vertices
            for(Triangle trg:Vor3.getDG().getTriangles()){

            }


        }

/* END OF OBSTACLE GENERATOR EVENT HANDLER*/


        else if(event.getSource()==ShortestPath) {
            //TODO remove this
            System.out.println("Need to calculate shortest path");
        }





        else if(event.getSource()== GJK) {
            clearCanvas();
            //TODO remove this
            Polygon poly = new Polygon(0,20,100,100,10);
            Polygon poly2 = new Polygon(0,20,100,100,10);

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
            gc.setStroke(Color.WHITE);

            drawEdges(vg.getGraph().getEdges());
            List<Edge> radialEdges = vg.VisibleVertices(new Point(320,370),range.getObstacles());
            List<Double> angles = new ArrayList<Double>();
            angles=vg.getAngles();

            gc.setFill(Color.CYAN);

           /* int i=0;
            for(Point pt:vg.getV()){
                gc.fillText(angles.get(i).toString(),pt.getX()*xratio,pt.getY()*yratio);
                i++;
            }*/
            gc.setStroke(Color.CRIMSON);
            drawEdges(radialEdges);

            gc.setFill(Color.VIOLET);
            //gc.fillRect(-463*xratio,92*yratio,5,5);


        }

        else if(event.getSource()==SaveButton) {
            COTIOStream fileHandler = new COTIOStream();
            List<Obstacle> obslist = range.getObstacles();
            fileHandler.saveObstacles(obslist);
            //TODO remove this
        }

        else if(event.getSource()==LoadButton) {
            //TODO remove this
            COTIOStream fileHandler = new COTIOStream();
            List<Obstacle> inObstacles = fileHandler.loadObstacles();
            range.setObstacles(inObstacles);

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
        for(Edge e:edge){
            gc.strokeLine(e.p1.x * (xratio),e.p1.y * (yratio),e.p2.x *(xratio),e.p2.y *(yratio));
        }
    }


}
