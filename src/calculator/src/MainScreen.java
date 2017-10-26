package calculator.src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MainScreen extends JFrame {

	// - Private Properties

	private JPanel contentPane;

	private JButton calcBtn;
	private JButton saveBtn;
	private JButton clearBtn;
	
	// Test For GitHub Features.

	protected JTextField amtTextField; 			// >0
	protected JTextField monthTextField; 		// 12 month ~ 72 month
	protected JTextField aprTextField; 			// 0% ~ 75%
	protected JTextField paymentTextField; 		// >0

	// - Main

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// - Calculation Method

	public void calculatePayment(double totalAmt, int month, double interestRate) {

		// Test For GitHub Features.!!
		double monthlyPayment;

		if (interestRate == 0.0) {
			monthlyPayment = totalAmt / month;

		} else {

			double monthlyRate = (interestRate / 100) / 12;

			monthlyPayment = (totalAmt * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -month));
		}

		// show "0.34" when monthly payment come with "0.33"
		// show "0.04" when monthly payment come with "0.03"
		int decimal = (int) monthlyPayment;
		double fraction = (monthlyPayment - decimal) * 100;

		if (Math.round(fraction) == 33) {
			fraction = 0.34;
			monthlyPayment = decimal + fraction;
			paymentTextField.setText(Double.toString(monthlyPayment));

		} else if (Math.round(fraction) == 3) {
			fraction = 0.04;
			monthlyPayment = decimal + fraction;
			paymentTextField.setText(Double.toString(monthlyPayment));
		} else {
			// general result of monthly payment
			paymentTextField.setText(String.format("%.2f", monthlyPayment));
		}

	}

	public void calculateTotalAmount(double interestRate, int month, double monthlyPayment) {

		double totalAmt;

		if (interestRate == 0.0) {
			totalAmt = month * monthlyPayment;
		} else {
			double monthlyRate = (interestRate / 100) / 12;

			totalAmt = monthlyPayment * (1 - Math.pow(1 + monthlyRate, -month)) / monthlyRate;
		}

		amtTextField.setText(String.format("%.2f", totalAmt));
	}

	public void calculateNumOfMonth(double totalAmt, double interestRate, double monthlyPayment) {

		if (interestRate == 0.0) {
			double result;
			result = totalAmt / monthlyPayment;
			if (result < 12 || result > 72) {

				monthTextField.setText("Result out of bound");
				// JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry
				// - Number of Months out of bound");
			} else {
				int monthResult = (int) (Math.ceil(result));
				monthTextField.setText(Integer.toString(monthResult));
			}

		} else {

			double monthlyRate = (interestRate / 100) / 12;

			double logValue = 1 - ((totalAmt * monthlyRate) / monthlyPayment);
			double total = -1 * (Math.log(logValue) / Math.log(1 + monthlyRate));
			int monthResult = (int) (Math.ceil(total));

			if (monthResult < 12 || monthResult > 72) {
				monthTextField.setText("Result out of bound");
				// JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry
				// - Number of Months out of bound");
			} else {

				monthTextField.setText(Integer.toString(monthResult));
			}
		}

	}

	public void calculateAPR(double totalAmt, double monthlyPayment, int month) {

		double low = 0.0;
		double mid = 0.0;
		double high = 75.0;

		double maxTotalAmt = monthlyPayment * month;

		double maxMonthlyRate = (high / 100) / 12;
		double minTotalAmt = monthlyPayment * (1 - Math.pow(1 + maxMonthlyRate, -month)) / maxMonthlyRate;

		if (totalAmt < minTotalAmt || totalAmt > maxTotalAmt) {
			aprTextField.setText("Result out of bound");
			// JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry -
			// Calculated APR is out of bound");

		} else {

			while (low <= high) {

				// set the "mid" which is the temporary APR
				mid = (low + high) / 2;

				// Calculate the Principal with the temporary APR
				double tempMonthlyRate = (mid / 100) / 12;

				double testTotalAmt = monthlyPayment * (1 - Math.pow(1 + tempMonthlyRate, -month)) / tempMonthlyRate;

				// Modify the temporary APR
				if (testTotalAmt < totalAmt) {

					high = mid - 0.001;

				} else if (testTotalAmt > totalAmt) {

					low = mid + 0.001;

				} else {

					aprTextField.setText(Double.toString(Math.round(mid * 100) / 100.0d));
				}
			}

			aprTextField.setText(Double.toString(Math.round(mid * 100) / 100.0d));
		}

	}

	// - GUI Method

	// Setup window
	public MainScreen() {
		init();
	}

	// Initialize window
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 700);
		setTitle("Finance Calculator");
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Capital Amount: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_1 = new JLabel("Number of Months (12 - 72): ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_2 = new JLabel("APR (Up to 75%): ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_3 = new JLabel("Monthly Payment: ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);

		amtTextField = new JTextField();
		amtTextField.setColumns(10);

		monthTextField = new JTextField();
		monthTextField.setColumns(10);

		aprTextField = new JTextField();
		aprTextField.setColumns(10);

		paymentTextField = new JTextField();
		paymentTextField.setColumns(10);

		calcBtn = new JButton("Calculate");
		calcBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (aprTextField.getText().isEmpty() & amtTextField.getText() != null
							& monthTextField.getText() != null & paymentTextField.getText() != null) {

						// APR
						int numOfMonth = Integer.parseInt(monthTextField.getText());
						double monthlyPayment = Double.parseDouble(paymentTextField.getText());
						double totalAmt = Double.parseDouble(amtTextField.getText());

						if (numOfMonth < 12 || numOfMonth > 72 || monthlyPayment <= 0 || totalAmt <= 0) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							calculateAPR(totalAmt, monthlyPayment, numOfMonth);
						}

					} else if (monthTextField.getText().isEmpty() & amtTextField.getText() != null
							& aprTextField.getText() != null & paymentTextField.getText() != null) {

						// Number Of Month
						double totalAmt = Double.parseDouble(amtTextField.getText());
						double interestRate = Double.parseDouble(aprTextField.getText());
						double monthlyPayment = Double.parseDouble(paymentTextField.getText());

						if (totalAmt <= 0 || interestRate < 0 || interestRate > 75 || monthlyPayment <= 0) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							calculateNumOfMonth(totalAmt, interestRate, monthlyPayment);
						}

					} else if (amtTextField.getText().isEmpty() & aprTextField.getText() != null
							& monthTextField.getText() != null & paymentTextField.getText() != null) {

						// Capital Amount
						double interestRate = Double.parseDouble(aprTextField.getText());
						int numOfMonth = Integer.parseInt(monthTextField.getText());
						double monthlyPayment = Double.parseDouble(paymentTextField.getText());

						if (interestRate < 0 || interestRate > 75 || numOfMonth < 12 || numOfMonth > 72
								|| monthlyPayment <= 0) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							calculateTotalAmount(interestRate, numOfMonth, monthlyPayment);
						}

					} else {

						// Monthly Payment
						double totalAmt = Double.parseDouble(amtTextField.getText());
						int numOfMonth = Integer.parseInt(monthTextField.getText());
						double interestRate = Double.parseDouble(aprTextField.getText());

						if (totalAmt <= 0 || numOfMonth < 12 || numOfMonth > 72 || interestRate < 0
								|| interestRate > 75) {
							JOptionPane.showMessageDialog(MainScreen.this, "Invalid Entry");
						} else {
							calculatePayment(totalAmt, numOfMonth, interestRate);
						}

					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please enter at least 3 terms");
				}

			}
		});

		// setup JTable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 400, 410, 200);
		this.getContentPane().add(scrollPane);

		JTable table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "  Capital Amount", "Number of Months", "       APR  %", " Monthly Payment" }));

		// change column width
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 1) {
				column.setPreferredWidth(110); // second column is bigger
			} else if (i == 2) {
				column.setPreferredWidth(90);
			} else {
				column.setPreferredWidth(105);
			}
		}

		// center cell's text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 4; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		scrollPane.setViewportView(table);

		// setup Buttons
		calcBtn.setForeground(SystemColor.controlText);
		calcBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		calcBtn.setBackground(SystemColor.control);
		calcBtn.setBounds(200, 200, 100, 30);

		saveBtn = new JButton("Save To Grid");
		saveBtn.setForeground(SystemColor.controlText);
		saveBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		saveBtn.setBackground(SystemColor.control);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// save to grid
				if (amtTextField.getText().isEmpty() || aprTextField.getText().isEmpty()
						|| monthTextField.getText().isEmpty() || paymentTextField.getText().isEmpty()) {

					JOptionPane.showMessageDialog(MainScreen.this, "Nothing to save, please finish a calculation");

				} else {

					int numbOfColumn = table.getModel().getColumnCount();
					Object[] columnPosition = new Object[numbOfColumn];
					columnPosition[0] = "$ " + amtTextField.getText();
					columnPosition[1] = monthTextField.getText();
					columnPosition[2] = aprTextField.getText() + " %";
					columnPosition[3] = "$ " + paymentTextField.getText();
					((DefaultTableModel) table.getModel()).addRow(columnPosition);

					// clear textFields
					amtTextField.setText(null);
					monthTextField.setText(null);
					aprTextField.setText(null);
					paymentTextField.setText(null);
				}
			}
		});

		clearBtn = new JButton("Clear");
		clearBtn.setForeground(Color.BLACK);
		clearBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		clearBtn.setBackground(SystemColor.menu);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				amtTextField.setText(null);
				monthTextField.setText(null);
				aprTextField.setText(null);
				paymentTextField.setText(null);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(calcBtn,
										GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane
										.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup().addGap(33)
														.addGroup(gl_contentPane
																.createParallelGroup(Alignment.LEADING, false)
																.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE,
																		124, Short.MAX_VALUE)
																.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addContainerGap().addComponent(lblNewLabel_1,
																GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(paymentTextField).addComponent(aprTextField)
												.addComponent(monthTextField).addComponent(amtTextField,
														GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))))
						.addContainerGap(76, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addContainerGap(161, Short.MAX_VALUE)
										.addComponent(saveBtn).addGap(158))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(175)
								.addComponent(clearBtn, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(178, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(45)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						amtTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(37)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
						.addComponent(monthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(35)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2)
						.addComponent(aprTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(37)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_3)
						.addComponent(paymentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(34).addComponent(calcBtn, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(clearBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
				.addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(20)));
		contentPane.setLayout(gl_contentPane);
	}
}
