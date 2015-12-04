package edu.MD.visualization;

import java.util.List;

import edu.MD.number.MDVector;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class ParticleAnimationCanvas extends Canvas {
	final PhongMaterial greyMaterial = new PhongMaterial();

	
	private ParticleAnimationCanvas(int i, int j) {
		super(i,j);
	}
	private final ParticleAnimationCanvas INSTANCE = new ParticleAnimationCanvas(350,250);
	private Group g;
	
	public void setGroup(List<MDVector> positions){
		greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);
		g = new Group();
		for (int i=0; i<positions.size(); i++){
			Sphere particle = new Sphere(3);
			particle.setTranslateX(positions.get(i).getCartesianComponent()[0].toDouble());
			particle.setTranslateX(positions.get(i).getCartesianComponent()[1].toDouble());
			particle.setTranslateX(positions.get(i).getCartesianComponent()[2].toDouble());
			g.getChildren().add(particle);
		}
	}

}
