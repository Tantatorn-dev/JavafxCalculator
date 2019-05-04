package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.beans.EventHandler;
import java.util.ArrayList;

public class Main extends Application {

    Display display = new Display();

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10,10,10,10));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        ArrayList<Button> buttons = new ArrayList<>();

        buttons.add(new Button("1"));
        buttons.add(new Button("2"));
        buttons.add(new Button("3"));
        buttons.add(new Button("4"));
        buttons.add(new Button("5"));
        buttons.add(new Button("6"));
        buttons.add(new Button("7"));
        buttons.add(new Button("8"));
        buttons.add(new Button("9"));
        buttons.add(new Button("+"));
        buttons.add(new Button("-"));
        buttons.add(new Button("*"));
        buttons.add(new Button("/"));
        buttons.add(new Button("."));
        buttons.add(new Button("0"));
        buttons.add(new Button("clear"));
        buttons.add(new Button("="));

        for(int i=0;i<9;i++){
            final int m=i;

            gridPane.add(buttons.get(i),i%3,i/3);
            buttons.get(i).setOnAction((event)-> {
                display.setNumInput(Integer.toString(m+1));
            });
        }

        for(int i=0;i<4;i++){
            final int m = 9+i;
            gridPane.add(buttons.get(m),3,i);
            buttons.get(m).setOnAction(
                    (event)->{
                        display.setNumInput(buttons.get(m).getText());
                    }
            );
        }

        for(int i=0;i<3;i++){
            final int m = i+13;
            gridPane.add(buttons.get(m),i,3);
        }

        buttons.get(14).setOnAction((event)->{
            display.setNumInput("0");
        });

        buttons.get(15).setOnAction((event)->{
            display.clearInput();
        });

        buttons.get(16).setOnAction((event -> {
            display.compute();
        }));

        buttons.forEach((Button i) ->{
            i.setMinWidth(150);
            i.setMinHeight(60);
        });

        buttons.get(16).setMaxWidth(640);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(0,0,0,0));
        vBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.getChildren().addAll(display,gridPane,buttons.get(16));

        borderPane.setCenter(vBox);

        primaryStage.setTitle("JavaFX Calculator");
        primaryStage.setScene(new Scene(borderPane, 650, 500));
        primaryStage.show();
    }

    class Display extends StackPane {
        private Text display=new Text();
        private StringBuilder text;

        public Display(){
            text=new StringBuilder("");
            display.setText(text.toString());
            display.setFont(new Font(48));
            getChildren().add(display);
        }


        void setNumInput(String input){
            text.append(input);
            display.setText(text.toString());
        }

        void clearInput(){
            text = new StringBuilder("");
            display.setText(text.toString());
        }

        void compute() {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = null;
            try {
                result = engine.eval(text.toString());
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            text=new StringBuilder(result.toString());
            display.setText(text.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
