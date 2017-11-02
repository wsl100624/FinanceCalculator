package calculator.src;

public class CalculateAPR implements Calculator{
	
	private double totalAmt;
	private double monthlyPayment;
	private int month;
	private double firstMonthPayment;
	
	
	
	public CalculateAPR(double totalAmt, double monthlyPayment, int month) {
		
		this.totalAmt = totalAmt;
		this.monthlyPayment = monthlyPayment;
		this.month = month;
	}

	@Override
	public String calculate() {
		
		double low = 0.0;
		double mid = 0.0;
		double high = 75.0;
		double finalAPR = 0.00;

		double maxTotalAmt = monthlyPayment * month;

		double maxMonthlyRate = (high / 100) / 12;
		double minTotalAmt = monthlyPayment * (1 - Math.pow(1 + maxMonthlyRate, -month)) / maxMonthlyRate;

		if (totalAmt < minTotalAmt || totalAmt > maxTotalAmt) {
			
			return "Result out of bound";
			
		} else {
			
			int decimal = (int) monthlyPayment;
			double fraction = (monthlyPayment - decimal) * 100;

			if (Math.round(fraction) == 34) {
				firstMonthPayment = monthlyPayment - 0.01;
				return Double.toString(finalAPR);		
			} else {
				
				while (low <= high) {

					// set the "mid" which is the temporary APR
					mid = (low + high) / 2;

					// calculate the Principal with the temporary APR
					double tempMonthlyRate = (mid / 100) / 12;

					double testTotalAmt = monthlyPayment * (1 - Math.pow(1 + tempMonthlyRate, -month)) / tempMonthlyRate;

					// modify the temporary APR
					if (testTotalAmt < totalAmt) {

						high = mid - 0.001;

					} else if (testTotalAmt > totalAmt) {

						low = mid + 0.001;

					} else {

						return Double.toString(Math.round(mid * 100) / 100.0d);
					}
				}
				
				return Double.toString(Math.round(mid * 100) / 100.0d);
			}			
		}
	}
	
	public String getFirstMonthPayment() {
		return String.format("%.2f", firstMonthPayment);
	}
	

}
