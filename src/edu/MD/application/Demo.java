package edu.MD.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Demo extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("MD Simulation Visulization");
		FlowPane rootNode = new FlowPane(20,20);
		rootNode.setAlignment(Pos.CENTER);
		Scene primaryScene = new Scene(rootNode, 800, 600);
		primaryStage.setScene(primaryScene);
		
		
	}

}
