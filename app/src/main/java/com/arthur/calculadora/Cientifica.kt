package com.arthur.calculadora

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.*
import java.text.DecimalFormat
import kotlin.math.asinh

class Cientifica : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var displayHist: TextView
    private var currentInput = "0"
    private var nextInput = ""
    private var currentOperator: String? = null
    private var memoryValue: Double = 0.0
    private var SecondFunction = false
    private val decimalFormato = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cientifica)

        display = findViewById(R.id.display_cientifica)
        displayHist = findViewById(R.id.displayHist_cientifica)

        findViewById<Button>(R.id.btn0_cientifica).setOnClickListener { appendNumber("0") }
        findViewById<Button>(R.id.btn1_cientifica).setOnClickListener { appendNumber("1") }
        findViewById<Button>(R.id.btn2_cientifica).setOnClickListener { appendNumber("2") }
        findViewById<Button>(R.id.btn3_cientifica).setOnClickListener { appendNumber("3") }
        findViewById<Button>(R.id.btn4_cientifica).setOnClickListener { appendNumber("4") }
        findViewById<Button>(R.id.btn5_cientifica).setOnClickListener { appendNumber("5") }
        findViewById<Button>(R.id.btn6_cientifica).setOnClickListener { appendNumber("6") }
        findViewById<Button>(R.id.btn7_cientifica).setOnClickListener { appendNumber("7") }
        findViewById<Button>(R.id.btn8_cientifica).setOnClickListener { appendNumber("8") }
        findViewById<Button>(R.id.btn9_cientifica).setOnClickListener { appendNumber("9") }

        findViewById<Button>(R.id.btn_decimal_cientifica).setOnClickListener { addDecimal() }

        findViewById<Button>(R.id.btn_div_cientifica).setOnClickListener { performOperation("÷") }
        findViewById<Button>(R.id.btn_mais_menos).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btn_igual_cientifica).setOnClickListener { calculate() }

        findViewById<Button>(R.id.btnCE_cientifica).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btn_apagar).setOnClickListener { clearAll() }

        findViewById<Button>(R.id.btn_mc).setOnClickListener { memoryClear() }
        findViewById<Button>(R.id.btn_mr).setOnClickListener { memoryRecall() }
        findViewById<Button>(R.id.btn_m_plus).setOnClickListener { memoryAdd() }
        findViewById<Button>(R.id.btn_m_minus).setOnClickListener { memorySubtract() }
        findViewById<Button>(R.id.btn_ms).setOnClickListener { memoryStore() }

        findViewById<Button>(R.id.btn_2nd).setOnClickListener { toggleSecondFunction() }
        findViewById<Button>(R.id.btn_pi).setOnClickListener { setConstant(PI) }
        findViewById<Button>(R.id.btn_e).setOnClickListener { setConstant(E) }
        findViewById<Button>(R.id.btn_x2).setOnClickListener { performUnaryOperation { it * it } }
        findViewById<Button>(R.id.btn_raiz).setOnClickListener { performUnaryOperation { sqrt(it) } }
        findViewById<Button>(R.id.btn_1x).setOnClickListener { performUnaryOperation { 1 / it } }
        findViewById<Button>(R.id.btn_xy).setOnClickListener { performOperation("^") }
        findViewById<Button>(R.id.btn_10x).setOnClickListener { performUnaryOperation { pow(10.0, it) } }
        findViewById<Button>(R.id.btn_log).setOnClickListener { performUnaryOperation { log10(it) } }
        findViewById<Button>(R.id.btn_ln).setOnClickListener { performUnaryOperation { log(it) } }
        findViewById<Button>(R.id.btn_mod).setOnClickListener { performOperation("mod") }
        findViewById<Button>(R.id.btn_n_factorial).setOnClickListener { performUnaryOperation { factorial(it) } }
        findViewById<Button>(R.id.btn_abs).setOnClickListener { performUnaryOperation { abs(it) } }
        findViewById<Button>(R.id.btn_parenteses_abre).setOnClickListener { appendNumber("(") }
        findViewById<Button>(R.id.btn_parenteses_fecha).setOnClickListener { appendNumber(")") }


        findViewById<Button>(R.id.btn_sin).setOnClickListener { performTrigonometricOperation("sin") }
        findViewById<Button>(R.id.btn_m_cos).setOnClickListener { performTrigonometricOperation("cos") }
        findViewById<Button>(R.id.btn_tan).setOnClickListener { performTrigonometricOperation("tan") }
        findViewById<Button>(R.id.btn_m_hyp).setOnClickListener {

            if (SecondFunction) {
                performTrigonometricOperation("asinh")
            } else {
                performTrigonometricOperation("sinh")
            }
        }
        findViewById<Button>(R.id.btn_m_sec).setOnClickListener {
            if (SecondFunction) {
                performTrigonometricOperation("asec")
            } else {
                performTrigonometricOperation("sec")
            }
        }
        findViewById<Button>(R.id.btn_csc).setOnClickListener {
            if (SecondFunction) {
                performTrigonometricOperation("acsc")
            } else {
                performTrigonometricOperation("csc")
            }
        }
    }

    private fun appendNumber(number: String) {
        if (currentInput == "0"|| currentInput == "-Infinito" || currentInput == "Não é um numero") {
            currentInput = number
        } else {
            currentInput += number
        }
        display.text = currentInput
    }

    private fun addDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            display.text = currentInput
        }
    }

    private fun toggleSign() {
        Log.d("toggleSign", "currentInput before: $currentInput")
        if (currentInput != "0" && currentInput != "") {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1)
            } else {
                currentInput = "-$currentInput"
            }
            display.text = currentInput
        }
        Log.d("toggleSign", "currentInput after: $currentInput")
    }

    private fun clearEntry() {
        currentInput = "0"
        display.text = currentInput
    }

    private fun clearAll() {
        currentInput = "0"
        nextInput = ""
        currentOperator = null
        display.text = currentInput
        displayHist.text = ""
    }

    private fun setConstant(constant: Double) {
        currentInput = constant.toString()
        display.text = currentInput
    }

    private fun factorial(n: Double): Double {
        if (n == 0.0) return 1.0
        if (n < 0 || n % 1 != 0.0) return Double.NaN
        var result = 1.0
        for (i in 1..n.toInt()) {
            result *= i
        }
        return result
    }

    private fun performOperation(operator: String) {
        if (currentInput != "0" && currentInput != "") {
            if (nextInput != "") {
                calculate()
            }
            nextInput = currentInput
            currentInput = "0"
            currentOperator = operator
            displayHist.text = "$nextInput $operator "
            display.text = currentInput
        }
    }

    private fun calculate() {
        if (nextInput != "" && currentOperator != null) {
            val num1 = nextInput.toDoubleOrNull()
            val num2 = currentInput.toDoubleOrNull()
            if (num1 != null && num2 != null) {
                var result = 0.0
                try {
                    when (currentOperator) {
                        "+" -> result = num1 + num2
                        "-" -> result = num1 - num2
                        "÷" -> {
                            if (num2 == 0.0) {
                                currentInput = "Não pode dividir por 0"
                                display.text = currentInput
                                displayHist.text = "Não pode dividir por 0"
                                return
                            }
                            result = num1 / num2
                        }
                        "*" -> result = num1 * num2
                        "^" -> result = pow(num1, num2)
                        "mod" -> result = num1 % num2
                    }
                    val formattedResult = decimalFormato.format(result)
                    currentInput = formattedResult
                    display.text = currentInput
                    displayHist.text = "$nextInput $currentOperator $formattedResult"
                    nextInput = ""
                    currentOperator = null
                } catch (e: Exception) {
                    currentInput = "Erro"
                    display.text = currentInput
                    displayHist.text = "Erro"
                    nextInput = ""
                    currentOperator = null
                }
            } else {
                currentInput = "Invalida"
                display.text = currentInput
                displayHist.text = "Invalida"
                nextInput = ""
                currentOperator = null
            }
        }
    }

    private fun performUnaryOperation(operation: (Double) -> Double) {
        if (currentInput != "0" && currentInput != "") {
            val num = currentInput.toDoubleOrNull()
            if (num != null) {
                try {
                    val result = operation(num)
                    currentInput = decimalFormato.format(result)
                    display.text = currentInput
                } catch (e: Exception) {
                    currentInput = "Erro"
                    display.text = currentInput
                }
            } else {
                currentInput = "Invalido"
                display.text = currentInput
            }
        }
    }

    private fun memoryClear() {
        memoryValue = 0.0
    }

    private fun memoryRecall() {
        currentInput = decimalFormato.format(memoryValue)
        display.text = currentInput
    }

    private fun memoryStore() {
        val num = currentInput.toDoubleOrNull()
        if (num != null) {
            memoryValue = num
        }
    }

    private fun memoryAdd() {
        val num = currentInput.toDoubleOrNull()
        if (num != null) {
            memoryValue += num
        }
    }

    private fun memorySubtract() {
        val num = currentInput.toDoubleOrNull()
        if (num != null) {
            memoryValue -= num
        }
    }

    private fun toggleSecondFunction() {
        SecondFunction = !SecondFunction

        val btnSin = findViewById<Button>(R.id.btn_sin)
        val btnCos = findViewById<Button>(R.id.btn_m_cos)
        val btnTan = findViewById<Button>(R.id.btn_tan)
        val btnSinh = findViewById<Button>(R.id.btn_m_hyp)
        val btnSec = findViewById<Button>(R.id.btn_m_sec)
        val btnCsc = findViewById<Button>(R.id.btn_csc)

        if (SecondFunction) {
            btnSin.text = "asin"
            btnCos.text = "acos"
            btnTan.text = "atan"
            btnSinh.text = "asinh"
            btnSec.text = "asec"
            btnCsc.text = "acsc"
        } else {
            btnSin.text = "sin"
            btnCos.text = "cos"
            btnTan.text = "tan"
            btnSinh.text = "sinh"
            btnSec.text = "sec"
            btnCsc.text = "csc"
        }
    }

    private fun performTrigonometricOperation(operation: String) {
        if (currentInput != "0" && currentInput != "") {
            val num = currentInput.toDoubleOrNull()
            if (num != null) {
                try {
                    val result = when (operation) {
                        "sin" -> sin(num)
                        "cos" -> cos(num)
                        "tan" -> tan(num)
                        "sinh" -> sinh(num)
                        "asinh" -> asinh(num)
                        "sec" -> 1 / cos(num)
                        "asec" -> acos(1 / num)
                        "csc" -> 1 / sin(num)
                        "acsc" -> asin(1 / num)
                        else -> num
                    }
                    currentInput = decimalFormato.format(result)
                    display.text = currentInput
                } catch (e: Exception) {
                    currentInput = "Erro"
                    display.text = currentInput
                }
            } else {
                currentInput = "Invalido"
                display.text = currentInput
            }
        }
    }
}

