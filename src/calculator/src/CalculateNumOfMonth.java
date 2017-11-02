package calculator.src;

public class CalculateNumOfMonth implements Calculator {
	
	private double totalAmt;
	private double interestRate;
	private double monthlyPayment;
	private double firstMonthPayment;
	

	public CalculateNumOfMonth(double totalAmt, double interestRate, double monthlyPayment) {
		
		this.totalAmt = totalAmt;
		this.interestRate = interestRate;
		this.monthlyPayment = monthlyPayment;
	}
	
	@Override
	public String calculate() {	
		
		if (interestRate == 0.0) {
			double result;
			result = totalAmt / monthlyPayment;
			
			int monthResult = (int) (Math.ceil(result));
				
			return Integer.toString(monthResult);

		} else {

			double monthlyRate = (interestRate / 100) / 12;

			double logValue = 1 - ((totalAmt * monthlyRate) / monthlyPayment);
			double total = -1 * (Math.log(logValue) / Math.log(1 + monthlyRate));
			int monthResult = (int) (Math.ceil(total));


			return Integer.toString(monthResult);
			
		}
	}

	public String getFirstMonthPayment() {
		
		firstMonthPayment = monthlyPayment;
		
		int decimal = (int) monthlyPayment;
		double fraction = (monthlyPayment - decimal) * 100;

		if (Math.round(fraction) == 33) {
			
			firstMonthPayment = monthlyPayment;

		} else if (Math.round(fraction) == 34) {
			firstMonthPayment = monthlyPayment - 0.01;
		} 
		
		return String.format("%.2f", firstMonthPayment);
	}
	

}
