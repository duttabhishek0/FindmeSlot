package com.abhishek.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.myapplication.adapter.SingleItemAdapter
import com.abhishek.myapplication.databinding.ActivityMainBinding
import com.abhishek.myapplication.model.SingleItemModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var centerList: MutableList<SingleItemModel>
    private lateinit var singleItemAdapter: SingleItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        centerList = mutableListOf()
        binding.centerListRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        singleItemAdapter = SingleItemAdapter(centerList)
        binding.centerListRecyclerView.adapter = singleItemAdapter
        binding.centerListRecyclerView.setHasFixedSize(true)

        binding.btnSearch.setOnClickListener {
            closeKeyboard()
            val pinCode = binding.etPinCode.text.toString()
            if (pinCode.length != 6) {
                binding.etPinCode.error = "Please enter a valid pin-code"
            } else {
                showDatePickerDialog(pinCode)
            }
        }
    }

    private fun showDatePickerDialog(pinCode: String) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val dateStr = """${dayOfMonth}-${monthOfYear + 1}-${year}"""
            getAppointment(pinCode, dateStr)
        }, year, month, day)
        dpd.show()
    }

    private fun getAppointment(pinCode: String, date: String) {
        val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=$pinCode&date=$date"
        val queue = Volley.newRequestQueue(this@MainActivity)

        // Show loading indicator
        binding.progressBar.visibility = View.VISIBLE

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            binding.progressBar.visibility = View.GONE
            Log.e("request", "Success response is $response")
            try {
                val centerArray = response.getJSONArray("centers")
                if (centerArray.length() == 0) {
                    Toast.makeText(this, "No centers available", Toast.LENGTH_SHORT).show()
                }
                centerList.clear()
                for (i in 0 until centerArray.length()) {
                    val centerObj = centerArray.getJSONObject(i)
                    val centerName = centerObj.getString("name")
                    val centerAddress = centerObj.getString("address")
                    val centerFromTime = centerObj.getString("from")
                    val centerToTime = centerObj.getString("to")
                    val feeType = centerObj.getString("fee_type")

                    val sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0)
                    val ageLimit = sessionObj.getInt("min_age_limit")
                    val vaccineName = sessionObj.getString("vaccine")
                    val availableCapacity = sessionObj.getInt("available_capacity")

                    val center = SingleItemModel(
                        centerName,
                        centerAddress,
                        centerFromTime,
                        centerToTime,
                        feeType,
                        ageLimit,
                        vaccineName,
                        availableCapacity
                    )
                    centerList.add(center)
                }
                singleItemAdapter.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show()
            }
        }, { error ->
            binding.progressBar.visibility = View.GONE
            Log.e("Error", "Response is $error")
            Toast.makeText(this@MainActivity, "Failed to get response", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}