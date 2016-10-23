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

    Button GeneratePolygons = new Button();
    Button ShortestPath = new Button();
    Canvas drawingarea = new Canvas();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Obstacle Ground Modified from mac");
        drawingarea.setHeight(1100);
        drawingarea.setWidth(1400);


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

        GraphicsContext gc = drawingarea.getGraphicsContext2D();
        gc.setFill(Color.AQUA);
        gc.fillRect(0,0,gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        Scene theScene = new Scene(Border_Pane,1500,1100);
        primaryStage.setScene(theScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void handle(ActionEvent event) {
        if (event.getSource()==GeneratePolygons){
            //TODO remove this
            System.out.println("Need to generate polygons");
        }
        else if(event.getSource()==ShortestPath) {
            //TODO remove this
            System.out.println("Need to calculate shortest path");
        }

    }
}
