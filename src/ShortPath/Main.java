package ShortPath;

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

public class Main extends Application implements EventHandler<ActionEvent>{

    static int viewWidth = 1400;
    static int viewHeight = 1050;
    static int gridsize = 10000;
    static int numObstacles = 2000;
    static Color gridColor = Color.DARKBLUE;
    static Color fontColor = Color.WHITE;
    static String windowTitle = "Obstacle Ground";

    Button GeneratePolygons = new Button();
    Button ShortestPath = new Button();
    Canvas drawingarea = new Canvas();
    GraphicsContext gc = drawingarea.getGraphicsContext2D();
    ObstacleRange range;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(windowTitle);

        drawingarea.setHeight(viewHeight);
        drawingarea.setWidth(viewWidth);


        HBox HBoxPane = new HBox();
        BorderPane Border_Pane = new BorderPane();

        HBoxPane.setSpacing(10);
        HBoxPane.setPadding(new Insets(5,15,5,15));
        HBoxPane.getChildren().add(GeneratePolygons);
        HBoxPane.getChildren().add(ShortestPath);
        HBoxPane.setStyle("-fx-background-color: #336699;");
        GeneratePolygons.setText("Generate Obstacles");
        ShortestPath.setText("Get Shortest Path");

        GeneratePolygons.setOnAction(this);
        ShortestPath.setOnAction(this);

        Border_Pane.setTop(HBoxPane);
        Border_Pane.setCenter(drawingarea);

        gc.setStroke(fontColor);
        colorCanvas();


        Scene theScene = new Scene(Border_Pane,viewWidth,viewHeight);
        primaryStage.setScene(theScene);
        primaryStage.show();
    }


    public static void main(String[] args) {launch(args);}


    @Override
    public void handle(ActionEvent event) {
        //if button to generate obstacles is pressed
        if (event.getSource()==GeneratePolygons){
            clearCanvas();
            Double doubleXbound = gc.getCanvas().getWidth();
            Double doubleYbound = gc.getCanvas().getHeight();
            Double xratio=doubleXbound/gridsize;
            Double yratio=doubleYbound/gridsize;
            gc.setStroke(fontColor);

            //generate obstacle field
            gc.setFill(Color.RED);
            if(ObstacleRange.exists==false){
                range = new ObstacleRange(gridsize, gridsize, numObstacles);
            }
            else{
                range.reCreate();
            }


            // paint the obstacles on the view
            for(int i=0;i<gridsize;i++){

                    for(int j=0;j<gridsize;j++){
                        if(range.grid[i][j]==true){
                            gc.fillRect(i*(xratio),j*(yratio),2,2);
                        }
                    }
                }
            // set text at top of obstacle screen view
            gc.strokeText(range.toString(),5,15);
            }



        else if(event.getSource()==ShortestPath) {
            //TODO remove this
            System.out.println("Need to calculate shortest path");
        }

    }


    public void clearCanvas(){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        colorCanvas();
    }

    public void colorCanvas(){
        gc.setFill(gridColor);
        gc.fillRect(0,0,gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }


}
