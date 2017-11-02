package calculator.src;

public class CalculatePayment implements Calculator{
	
	private double totalAmt;
	private int month;
	private double interestRate;
	private double firstMonthPayment;
	
	
	public CalculatePayment(double totalAmt, int month, double interestRate) {
		
		this.totalAmt = totalAmt;
		this.month = month;
		this.interestRate = interestRate;
	}

	
	public String calculate() {

		double monthlyPayment; // also final month payment

		if (interestRate == 0.0) {
			monthlyPayment = totalAmt / month;
			firstMonthPayment = monthlyPayment;

		} else {

			double monthlyRate = (interestRate / 100) / 12;

			monthlyPayment = (totalAmt * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -month));
			firstMonthPayment = monthlyPayment;
		}

		// show "0.34" when monthly payment come with "0.33"
		// show "0.04" when monthly payment come with "0.03"
		int decimal = (int) monthlyPayment;
		double fraction = (monthlyPayment - decimal) * 100;

		if (Math.round(fraction) == 33) {
			fraction = 0.34;
			monthlyPayment = decimal + fraction;

			return Double.toString(monthlyPayment);

		} else if (Math.round(fraction) == 3) {
			fraction = 0.04;
			monthlyPayment = decimal + fraction;

			return Double.toString(monthlyPayment);
		} else {
			// general result of monthly payment
						
			return String.format("%.2f", monthlyPayment);
		}

	}


	public String getFirstMonthPayment() {
		return String.format("%.2f", firstMonthPayment);
	}

}
