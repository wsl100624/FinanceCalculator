package calculator.src;

public class CalculateCapitalAmt implements Calculator {

	private double interestRate;
	private int month;
	private double monthlyPayment;
	
	
	
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
		} else {
			double monthlyRate = (interestRate / 100) / 12;

			totalAmt = monthlyPayment * (1 - Math.pow(1 + monthlyRate, -month)) / monthlyRate;
		}

		//amtTextField.setText(String.format("%.2f", totalAmt));
		return String.format("%.2f", totalAmt);
	}

}
