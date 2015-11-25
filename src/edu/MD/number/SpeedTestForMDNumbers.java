package edu.MD.number;

import java.lang.reflect.InvocationTargetException;

import globalSettingUtility.NumberFactorySetting;

public class SpeedTestForMDNumbers {
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		NumberFactorySetting.set("JavaBigDecimalFactory", 64);
		// NumberFactorySetting.set("JavaDefaultNumberFactory");
		int testTime = 10000;

		NumberFactory numFactory = NumberFactory.getInstance();
		double x[] = new double[testTime];
		double[] result = new double[testTime];
		MDNumber[] xDN = new MDNumber[testTime];
		MDNumber[] resultDN = new MDNumber[testTime];

		// Add
		setTestNum(testTime, x, result);
		long startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			result[i] = result[i] + x[i];
		}
		long endTime = System.nanoTime();
		long duration1 = endTime - startTime;

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);
		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			resultDN[i] = resultDN[i].plus(xDN[i]);
		}
		endTime = System.nanoTime();
		long duration2 = endTime - startTime;

		System.out.println(String.format("The " + testTime + " add oprations take %.3f times more time for MDNumber.",
				(duration2 - duration1) / ((double) duration1)));

		// multiplication

		setTestNum(testTime, x, result);
		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			result[i] = result[i] * x[i];
		}
		endTime = System.nanoTime();
		duration1 = endTime - startTime;

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);
		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			resultDN[i] = resultDN[i].times(xDN[i]);
		}
		endTime = System.nanoTime();
		duration2 = endTime - startTime;

		System.out.println(String.format("The " + testTime + " times oprations take %.3f times more time for MDNumber.",
				(duration2 - duration1) / ((double) duration1)));

		// power

		setTestNum(testTime, x, result);
		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			result[i] = Math.pow(result[i], x[i]);
		}
		endTime = System.nanoTime();
		duration1 = endTime - startTime;

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);
		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			resultDN[i] = resultDN[i].pow(xDN[i]);
		}
		endTime = System.nanoTime();
		duration2 = endTime - startTime;

		System.out.println(String.format("The " + testTime + " pow oprations take %.3f times more time for MDNumber.",
				(duration2 - duration1) / ((double) duration1)));

		// power and divide

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);
		MDNumber one = numFactory.valueOf(1);

		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			resultDN[i] = one.divide(resultDN[i]);
		}
		endTime = System.nanoTime();
		duration1 = endTime - startTime;

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);
		startTime = System.nanoTime();

		for (int i = 0; i < testTime; i++) {
			resultDN[i] = resultDN[i].pow(-1);
		}
		endTime = System.nanoTime();
		duration2 = endTime - startTime;

		System.out.println(String.format(
				"The " + testTime + " pow operation take %.3f times more time than divide oprations for MDNumber.",
				(duration2 - duration1) / ((double) duration1)));

		// power and sqrt

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);

		startTime = System.nanoTime();
		for (int i = 0; i < testTime; i++) {
			resultDN[i] = resultDN[i].sqrt();
		}
		endTime = System.nanoTime();
		duration1 = endTime - startTime;

		setTestMDNum(testTime, numFactory, x, result, xDN, resultDN);
		startTime = System.nanoTime();

		for (int i = 0; i < testTime; i++) {
			resultDN[i] = resultDN[i].pow(0.5);
		}
		endTime = System.nanoTime();
		duration2 = endTime - startTime;

		System.out.println(String.format(
				"The " + testTime + " pow operation take %.3f times more time than sqrt oprations for MDNumber.",
				(duration2 - duration1) / ((double) duration1)));

	}

	private static void setTestMDNum(int testTime, NumberFactory numFactory, double[] x, double[] result,
			MDNumber[] xDN, MDNumber[] resultDN) {
		for (int i = 0; i < testTime; i++) {
			x[i] = Math.random() * 100;
			result[i] = Math.random() * 100;
			xDN[i] = numFactory.valueOf(x[i]);
			resultDN[i] = numFactory.valueOf(result[i]);
		}
	}

	private static void setTestNum(int testTime, double[] x, double[] result) {
		for (int i = 0; i < testTime; i++) {
			x[i] = Math.random() * 100;
			result[i] = Math.random() * 100;
		}
	}

}
