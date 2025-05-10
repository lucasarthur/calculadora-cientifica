package com.arthur.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var MR = 0.0
        var resultado = 0.0
        var operacao = 0
        var primeiroNumero = true
        var IsResult = false
        var historico = ""
        val display = findViewById<TextView>(R.id.display)
        val displayHist = findViewById<TextView>(R.id.displayHist)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btn0 = findViewById<Button>(R.id.btn0)
        val btnMultiplicar = findViewById<Button>(R.id.btnMultiplicar)
        val btnMais = findViewById<Button>(R.id.btnMais)
        val btnMenos = findViewById<Button>(R.id.btnMenos)
        val btnDividir = findViewById<Button>(R.id.btnDividir)
        val btnPonto = findViewById<Button>(R.id.btnPonto)
        val btnIgual = findViewById<Button>(R.id.btnIgual)
        val btnMRC = findViewById<Button>(R.id.btnMRC)
        val btnMMAIS = findViewById<Button>(R.id.btnMMais)
        val btnMMenos = findViewById<Button>(R.id.btnMMenos)
        val btnCE = findViewById<Button>(R.id.btnCE)

        fun atualizarDisplay(valor: String) {
            if (IsResult || display.text.toString() == "0") {
                display.text = valor
                IsResult = false
            } else {
                display.text = display.text.toString().plus(valor)
            }
        }

        btn0.setOnClickListener { atualizarDisplay("0") }
        btn1.setOnClickListener { atualizarDisplay("1") }
        btn2.setOnClickListener { atualizarDisplay("2") }
        btn3.setOnClickListener { atualizarDisplay("3") }
        btn4.setOnClickListener { atualizarDisplay("4") }
        btn5.setOnClickListener { atualizarDisplay("5") }
        btn6.setOnClickListener { atualizarDisplay("6") }
        btn7.setOnClickListener { atualizarDisplay("7") }
        btn8.setOnClickListener { atualizarDisplay("8") }
        btn9.setOnClickListener { atualizarDisplay("9") }

        btnCE.setOnClickListener {
            display.text = "0"
            displayHist.text = ""
            resultado = 0.0
            operacao = 0
            primeiroNumero = true
            IsResult = false
            historico = ""
        }

        btnPonto.setOnClickListener {
            if (!display.text.toString().contains(".")) {
                display.text = display.text.toString().plus(".")
            }
        }

        btnMRC.setOnClickListener {
            display.text = MR.toString()
            IsResult = true
        }

        btnMMAIS.setOnClickListener {
            MR += display.text.toString().toDouble()
            IsResult = true
        }

        btnMMenos.setOnClickListener {
            MR -= display.text.toString().toDouble()
            IsResult = true
        }

        fun executarOperacao(novoNumero: Double): Double {
            return when (operacao) {
                1 -> resultado + novoNumero
                2 -> resultado - novoNumero
                3 -> resultado * novoNumero
                4 -> if (novoNumero != 0.0) resultado / novoNumero else {
                    0.0
                }
                else -> novoNumero
            }
        }

        fun lidarComOperador(novaOperacao: Int) {
            val numeroAtual = display.text.toString().toDoubleOrNull() ?: 0.0
            if (IsResult) {
                historico = numeroAtual.toString()
                primeiroNumero = false
                IsResult = false
            } else if (!primeiroNumero) {
                resultado = executarOperacao(numeroAtual)
                historico += when (operacao) {
                    1 -> " + "
                    2 -> " - "
                    3 -> " * "
                    4 -> " / "
                    else -> ""
                } + numeroAtual.toString()
                displayHist.text = historico
            } else {
                resultado = numeroAtual
                primeiroNumero = false
                historico += numeroAtual.toString()
                displayHist.text = historico
            }
            operacao = novaOperacao
            IsResult = true
            display.text = "0"
        }

        btnMais.setOnClickListener { lidarComOperador(1) }
        btnMenos.setOnClickListener { lidarComOperador(2) }
        btnMultiplicar.setOnClickListener { lidarComOperador(3) }
        btnDividir.setOnClickListener { lidarComOperador(4) }

        btnIgual.setOnClickListener {
            val numeroAtual = display.text.toString().toDoubleOrNull() ?: 0.0
            resultado = executarOperacao(numeroAtual)
            historico += when (operacao) {
                1 -> " + "
                2 -> " - "
                3 -> " * "
                4 -> " / "
                else -> ""
            } + numeroAtual.toString()
            displayHist.text = historico
            display.text = resultado.toString()
            operacao = 0
            primeiroNumero = true
            IsResult = true
            historico = resultado.toString()
        }
    }
}