package edu.MD.utilityBD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.MD.numberBD.MDNumber;
import edu.MD.numberBD.NumberFactory;

public class NISTSaturationPropertiesReader {
	private final NumberFactory numberFactory = NumberFactory.getInstance();
	private String filename;
	private final String fileNameSurffix = "_saturation_properties.txt";
	private Map<String, TreeMap<Double, MDNumber>> saturationDensityVSTemperature = new HashMap<>();

	public NISTSaturationPropertiesReader(String name) throws FileNotFoundException, IOException {
		filename = name + fileNameSurffix;
		TreeMap<Double, MDNumber> vaporDensity = new TreeMap<>();
		TreeMap<Double, MDNumber> liquidDensity = new TreeMap<>();
		saturationDensityVSTemperature.put("vapor", vaporDensity);
		saturationDensityVSTemperature.put("liquid", liquidDensity);
		readFile();
	}

	private void readFile() throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
				saturationDensityVSTemperature.get("liquid").put(temperature,
						numberFactory.valueOf(liquidMolarDensity));
				saturationDensityVSTemperature.get("vapor").put(temperature, numberFactory.valueOf(vaporMolarDensity));
			}

		}
	}

	public Map<String, TreeMap<Double, MDNumber>> getSaturationDensityVSTemperature() {
		return saturationDensityVSTemperature;
	}

}
