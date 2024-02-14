package com.mathapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    private lateinit var commandET: EditText
    private lateinit var resultTV: TextView

    private lateinit var simplifyBT: Button
    private lateinit var factorBT: Button
    private lateinit var deriveBT: Button
    private lateinit var integrateBT: Button
    private lateinit var find0BT: Button
    private lateinit var findTangentBT: Button
    private lateinit var areaUnderCurveBT: Button
    private lateinit var cosineBT: Button
    private lateinit var sineBT: Button
    private lateinit var tangentBT: Button
    private lateinit var inverseCosineBT: Button
    private lateinit var inverseSineBT: Button
    private lateinit var inverseTangentBT: Button
    private lateinit var absoluteValueBT: Button
    private lateinit var logarithmBT: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
    }

    private fun initView() {
        this.commandET = findViewById(R.id.command_et)
        this.resultTV = findViewById(R.id.result_tv)

        this.simplifyBT = findViewById(R.id.simplify_bt)
        this.simplifyBT.setOnClickListener { this.processOperation("simplify") }

        this.factorBT = findViewById(R.id.factor_bt)
        this.factorBT.setOnClickListener { this.processOperation("factor") }

        this.deriveBT = findViewById(R.id.derive_bt)
        this.deriveBT.setOnClickListener { this.processOperation("derive") }

        this.integrateBT = findViewById(R.id.integrate_bt)
        this.integrateBT.setOnClickListener { this.processOperation("integrate") }

        this.find0BT = findViewById(R.id.find_0_bt)
        this.find0BT.setOnClickListener { this.processOperation("zeroes") }

        this.findTangentBT = findViewById(R.id.find_tangent_bt)
        this.findTangentBT.setOnClickListener { this.processOperation("tangent") }

        this.areaUnderCurveBT = findViewById(R.id.area_under_curve_bt)
        this.areaUnderCurveBT.setOnClickListener { this.processOperation("area") }

        this.cosineBT = findViewById(R.id.cosine_bt)
        this.cosineBT.setOnClickListener { this.processOperation("cos") }

        this.sineBT = findViewById(R.id.sine_bt)
        this.sineBT.setOnClickListener { this.processOperation("sin") }

        this.tangentBT = findViewById(R.id.tangent_bt)
        this.tangentBT.setOnClickListener { this.processOperation("tan") }

        this.inverseCosineBT = findViewById(R.id.inverse_cosine_bt)
        this.inverseCosineBT.setOnClickListener { this.processOperation("arccos") }

        this.inverseSineBT = findViewById(R.id.inverse_sine_bt)
        this.inverseSineBT.setOnClickListener { this.processOperation("arcsin") }

        this.inverseTangentBT = findViewById(R.id.inverse_tangent_bt)
        this.inverseTangentBT.setOnClickListener { this.processOperation("arctan") }

        this.absoluteValueBT = findViewById(R.id.absolute_value_bt)
        this.absoluteValueBT.setOnClickListener { this.processOperation("abs") }

        this.logarithmBT = findViewById(R.id.logarithm_bt)
        this.logarithmBT.setOnClickListener { this.processOperation("log") }
    }

    private fun processOperation (operation: String) {
        val expression = this.commandET.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newton.vercel.app/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val newton = retrofit.create(Newton::class.java)

        val operation_url = URLEncoder.encode(operation, "UTF-8")
        val call = newton.calculate(operation_url, expression)

        call.enqueue(object : Callback<NewtonDO> {
            override fun onResponse(call: Call<NewtonDO>, response: Response<NewtonDO>) {
                val body = response.body()
                if (body != null)
                    this@MainActivity.resultTV.text = body.result
                else
                    Toast.makeText(this@MainActivity, "Incorrect expression!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<NewtonDO>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Cannot connect to NewtonAPI", Toast.LENGTH_SHORT).show()
            }
        })
    }

}