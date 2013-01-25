/**
 * Main Activity for the calculator
 *
 * @version        0.1 18 de Enero del 2013
 * @author         Edilberto Ruvalcaba Galindo
 */
package com.eddie.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private CalculatorBrain calculator;
	private EditText resultView;
	private boolean userIsTypying;
	private boolean alreadyAFloatNumber;

	@Override
	/**
	 * Initializes the variables involved with the MainActivity
	 * @param savedInstanceState	An app instance state
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the result view from the layout
		resultView = (EditText) findViewById(R.id.result);
		calculator = new CalculatorBrain();
		userIsTypying = false;
		alreadyAFloatNumber = false;
	}
	
	@Override
	/**
	 * Saves the current state in the app
	 * @param	savedInstanceState	the app instance where the state is going to be saved
	 */
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		byte [] serializeCalculator = CalculatorBrain.serializeObject(calculator);
		savedInstanceState.putByteArray("calculator", serializeCalculator);
		savedInstanceState.putBoolean("userIsTyping", userIsTypying);
		savedInstanceState.putBoolean("alreadyAFloatNumber", alreadyAFloatNumber);
	}
	
	/**
	 * Restores the saved state in the app
	 * @param	savedInstanceState		the app instance previously saved
	 */
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		calculator = (CalculatorBrain) CalculatorBrain.deserializeObject(savedInstanceState.getByteArray("calculator"));
		userIsTypying = savedInstanceState.getBoolean("userIsTypying");
		alreadyAFloatNumber = savedInstanceState.getBoolean("alreadyAFloatNumber");
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
//	
	
	/**
	 * Appends a new digit to the current number
	 * @param view	listener for the current action
	 */
	public void appendDigit(View view) {
		Button buttonPressed = (Button) view;
		if (userIsTypying) {
			resultView.setText(resultView.getText().toString() + buttonPressed.getText().toString());
		} else {
			resultView.setText(buttonPressed.getText().toString());
		}
		userIsTypying = true;
	}
	
	/**
	 * Defines the operation to be executed
	 * @param view	listener for the current action
	 */
	public void defineOperation(View view) {
		// Save the current number
		calculator.addOperand(getNumberInDisplay());
		Button buttonPressed = (Button) view;
		calculator.addOperation(buttonPressed.getText().toString());
		userIsTypying = false;
	}
	
	/**
	 * Calls action to execute the operations called by the user
	 * @param view	listener for the current action
	 */
	public void performOperation(View view) {
		if (userIsTypying) {
			calculator.addOperand(getNumberInDisplay());
		}
		double result = calculator.performOperation();
		resultView.setText(result + "");
		userIsTypying = false;	
	}

	/**
	 * Appends a dot to the current number
	 * @param view	listener for the current action
	 */
	public void appendDot(View view) {
		if (!alreadyAFloatNumber) {
			if (userIsTypying) {
				resultView.setText(resultView.getText().toString() + ".");
			} else {
				resultView.setText("0.");
				userIsTypying = true;
			}
			alreadyAFloatNumber = true;	
		}
	}
	
	/**
	 * Clear the text in the result display
	 * @param view	listener for the current action
	 */
	public void clearDisplay(View view) {
		resultView.setText("0");
		userIsTypying = false;
		alreadyAFloatNumber = false;
	}
	
	/**
	 * Gets the current number in the display
	 * @return	the current number in the display
	 */
	private double getNumberInDisplay() {
		return Double.parseDouble(resultView.getText().toString());
	}

	
	
}
