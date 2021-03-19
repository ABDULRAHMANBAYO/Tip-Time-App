package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    // declares a top-level variable in the class for the binding object.
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initializes the binding object which you'll use to access Views in the activity_main.xml layout.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
        binding.costOfServiceEditText.setOnKeyListener { v, keyCode, _ ->
            handleKeyEvent( v, keyCode)
        }
    }

   private fun calculateTip() {

        // Get the cost of service
        val costOfServiceText = binding.costOfServiceEditText.text.toString()
        val cost=costOfServiceText.toDoubleOrNull()
        //   Get tip percentage
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            binding.optionTwentyPercent.id -> 0.20
            binding.optionEighteenPercent.id -> 0.18
            else -> 0.15
        }
        //Calculate tip
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
            var tip = tipPercentage * cost

        //Check if round off switch is toggled

        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        } //Format tip
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
    //Assign tip to tipResult text view
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}