package edu.MD.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class NISTSaturationPropertiesReader {
	private String filename;
	private final String fileNamePreFix = "/rsc/";
	private final String fileNameSurffix = "_saturation_properties.txt";
	private Map<String, TreeMap<Double, Double>> saturationDensityVSTemperature = new HashMap<>();

	public NISTSaturationPropertiesReader(String name) throws FileNotFoundException, IOException {
		filename = fileNamePreFix + name + fileNameSurffix;
		TreeMap<Double, Double> vaporDensity = new TreeMap<>();
		TreeMap<Double, Double> liquidDensity = new TreeMap<>();
		saturationDensityVSTemperature.put("vapor", vaporDensity);
		saturationDensityVSTemperature.put("liquid", liquidDensity);
		readFile();
	}

	private void readFile() throws FileNotFoundException, IOException {
		URL url = this.getClass().getResource(filename);
		try (BufferedReader reader = new BufferedReader(new FileReader(url.getFile()))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] dataInStr = line.split("\\s+");
				double temperature = Double.parseDouble(dataInStr[0]); // in
																		// Unit
																		// of K
				double liquidMolarDensity = Double.parseDouble(dataInStr[2]); // in
																				// Unit
																				// of
																				// mol/m^3
				double vaporMolarDensity = Double.parseDouble(dataInStr[14]); // in
																				// Unit
																				// of
																				// mol/m^3
				saturationDensityVSTemperature.get("liquid").put(temperature, liquidMolarDensity);
				saturationDensityVSTemperature.get("vapor").put(temperature, vaporMolarDensity);
			}

		}
	}

	public Map<String, TreeMap<Double, Double>> getSaturationDensityVSTemperature() {
		return saturationDensityVSTemperature;
	}

}
