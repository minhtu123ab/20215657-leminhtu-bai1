package com.example.bai1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var input: EditText
    lateinit var output: TextView
    lateinit var inputSpinner: Spinner
    lateinit var outputSpinner: Spinner
    lateinit var convert: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)
        inputSpinner = findViewById(R.id.spinner)
        output = findViewById(R.id.text_result)
        outputSpinner = findViewById(R.id.spinner2)
        convert = findViewById(R.id.materialButtonC)

        // Thiết lập Adapter cho các Spinner
        val inArrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.countries,
            android.R.layout.simple_spinner_item
        )
        inArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputSpinner.adapter = inArrayAdapter

        val outArrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.countries,
            android.R.layout.simple_spinner_item
        )
        outArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        outputSpinner.adapter = outArrayAdapter

        // Tỷ giá hối đoái so với USD
        val exchangeRates = mapOf(
            0 to 25330.0,   // VietNam
            1 to 7.12,      // China
            2 to 0.92,      // Europe
            3 to 153.0,     // Japan
            4 to 0.77,      // United Kingdom
            5 to 1.0        // United States
        )

        var rate1 = 1.0
        var rate2 = 1.0

        inputSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                rate1 = exchangeRates[position] ?: 1.0
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        outputSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                rate2 = exchangeRates[position] ?: 1.0
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        convert.setOnClickListener {
            val value = input.text.toString().toDoubleOrNull() ?: 0.0
            val result = convertFun(value, rate1, rate2)
            Log.d("MainActivity", "Value: $value, Rate1: $rate1, Rate2: $rate2, Result: $result")
            output.text = result.toString()
        }
    }
}

fun convertFun(input: Double, rate1: Double, rate2: Double): Double {
    return input * rate2 / rate1
}
