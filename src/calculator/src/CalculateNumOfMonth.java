package calculator.src;

public class CalculateNumOfMonth implements Calculator {
	
	private double totalAmt;
	private double interestRate;
	private double monthlyPayment;
	

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
			if (result < 12 || result > 72) {

				//monthTextField.setText("Result out of bound");
				
				return "Result out of bound";
				
			} else {
				int monthResult = (int) (Math.ceil(result));
				//monthTextField.setText(Integer.toString(monthResult));
				
				return Integer.toString(monthResult);
			}

		} else {

			double monthlyRate = (interestRate / 100) / 12;

			double logValue = 1 - ((totalAmt * monthlyRate) / monthlyPayment);
			double total = -1 * (Math.log(logValue) / Math.log(1 + monthlyRate));
			int monthResult = (int) (Math.ceil(total));

			if (monthResult < 12 || monthResult > 72) {
				
				//monthTextField.setText("Result out of bound");
				
				return "Result out of bound";
			} else {

				//monthTextField.setText(Integer.toString(monthResult));
				return Integer.toString(monthResult);
			}
		}
	}
	

}
