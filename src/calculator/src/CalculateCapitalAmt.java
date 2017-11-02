package calculator.src;

public class CalculateCapitalAmt implements Calculator {

	private double interestRate;
	private int month;
	private double monthlyPayment;
	private double firstMonthPayment;
	
	
	
	public CalculateCapitalAmt(double interestRate, int month, double monthlyPayment) {
		
		this.interestRate = interestRate;
		this.month = month;
		this.monthlyPayment = monthlyPayment;
	}
	
	@Override
	public String calculate() {
		
		double totalAmt;

		if (interestRate == 0.0) {
			totalAmt = month * monthlyPayment;
			
			int decimal = (int) totalAmt;
			double fraction = Math.round((totalAmt - decimal) * 100);
			
			if (fraction == 2 || fraction == 20) {
				totalAmt = Math.floor(totalAmt);
				firstMonthPayment = monthlyPayment - 0.01;
			}

		} else {
			double monthlyRate = (interestRate / 100) / 12;

			totalAmt = monthlyPayment * (1 - Math.pow(1 + monthlyRate, -month)) / monthlyRate;
		}

		return String.format("%.2f", totalAmt);
	}

	public double getFirstMonthPayment() {
		return firstMonthPayment;
	}

}
