package com.example.tiptime

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener{calculateTip()}
        binding.editTxtCostOfService.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    @SuppressLint("StringFormatInvalid", "SetTextI18n")
    fun calculateTip(){
        val stringInTextField = binding.editTxtCostOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if(cost == null){
            binding.txtTipResult.text = "Please enter the cost of service"
            return
        }

        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.radioBtn20Percent -> 0.20
            R.id.radioBtn18Percent -> 0.18
            else -> {0.15}
        }

        var tip = tipPercentage * cost

        if (binding.switchRoundUp.isChecked){
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip: Double){
        val formattedTip =  NumberFormat.getCurrencyInstance().format(tip)
        binding.txtTipResult.text = getString(R.string.tip_amount, formattedTip)
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