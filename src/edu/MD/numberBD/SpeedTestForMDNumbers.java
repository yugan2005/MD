package edu.MD.numberBD;

public class SpeedTestForMDNumbers {
	public static void main(String[] args) {
//		NumberFactory.setFactorySetting("JavaBigDecimalFactory", 32);
		 NumberFactory.setFactorySetting("JavaDefaultNumberFactory");

		NumberFactory numFactory = NumberFactory.getInstance();
		double x = 0.17;
		double result = 63.8;
		MDNumber xDN = numFactory.valueOf(x);
		MDNumber resultDN = numFactory.valueOf(result);

		// Add and minus

		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				result = result + x;
				result = result - x;
			}
		}
		long endTime = System.nanoTime();
		long duration1 = endTime - startTime;

		startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				resultDN = resultDN.add(xDN);
				resultDN = resultDN.minus(xDN);
			}
		}
		endTime = System.nanoTime();
		long duration2 = endTime - startTime;

		System.out.println(String.format("The 1e6 add and minus oprations take %.3f percent more time.",
				100 * (duration2 - duration1) / ((double) duration1)));

		// multiplication and division

		result = 6.7;
		resultDN = numFactory.valueOf(6.7);
		startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				result = result * x;
				result = result / x;
			}
		}
		endTime = System.nanoTime();
		duration1 = endTime - startTime;

		startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				resultDN = resultDN.times(xDN);
				resultDN = resultDN.divide(xDN);
			}
		}
		endTime = System.nanoTime();
		duration2 = endTime - startTime;

		System.out.println(String.format("The 1e6 times and divide oprations take %.3f percent more time.",
				100 * (duration2 - duration1) / ((double) duration1)));

//		// power and root
//
//		result = 6.7;
//		resultDN = numFactory.valueOf(6.7);
//		startTime = System.nanoTime();
//		double x2 = 1.0 / x;
//		for (int i = 0; i < 1000; i++) {
//			for (int j = 0; j < 1000; j++) {
//				result = Math.pow(result, x);
//				result = Math.pow(result, x2);
//			}
//		}
//		endTime = System.nanoTime();
//		duration1 = endTime - startTime;
//		MDNumber xDN2 = xDN.pow(-1);
//		startTime = System.nanoTime();
//		for (int i = 0; i < 100; i++) {
//			for (int j = 0; j < 1000; j++) {
//				resultDN = resultDN.pow(xDN);
//				resultDN = resultDN.pow(xDN2);
//			}
//		}
//		endTime = System.nanoTime();
//		duration2 = endTime - startTime;
//
//		System.out.println(String.format("The 1e5 pow and root oprations take %.3f percent more time.",
//				100 * (duration2 - duration1) / ((double) duration1)));

		// power and divide

		resultDN = numFactory.valueOf(6.7);
		MDNumber one = numFactory.valueOf(1);

		startTime = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 1000; j++) {
				resultDN = one.divide(resultDN);
				resultDN = one.divide(resultDN);
			}
		}
		endTime = System.nanoTime();
		duration1 = endTime - startTime;
		
		resultDN = numFactory.valueOf(6.7);
		startTime = System.nanoTime();
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				resultDN = resultDN.pow(-1);
				resultDN = resultDN.pow(-1);
			}
		}
		endTime = System.nanoTime();
		duration2 = endTime - startTime;

		System.out.println(String.format("The 1e6 pow VS. divide oprations take %.3f percent more time.",
				100 * (duration2 - duration1) / ((double) duration1)));

	}

}
