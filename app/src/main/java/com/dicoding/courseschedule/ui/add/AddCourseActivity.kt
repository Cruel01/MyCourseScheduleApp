package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private var selectedStartTime: String = ""
    private var selectedEndTime: String = ""
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.apply {
            title = getString(R.string.add_course)
            setDisplayHomeAsUpEnabled(true)
        }

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        val spinnerDay = findViewById<Spinner>(R.id.spinner_day)
        val dayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.day,
            android.R.layout.simple_spinner_item
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = dayAdapter

        val startTimeButton = findViewById<ImageButton>(R.id.ib_start_time)
        val endTimeButton = findViewById<ImageButton>(R.id.ib_end_time)

        startTimeButton.setOnClickListener {
            showTimePicker("startTimePicker")
        }

        endTimeButton.setOnClickListener {
            showTimePicker("endTimePicker")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<TextInputEditText>(R.id.ed_course_name).text.toString()
                val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer).text.toString()
                val note = findViewById<TextInputEditText>(R.id.ed_note).text.toString()

                if (courseName.isEmpty() || selectedStartTime.isEmpty() || selectedEndTime.isEmpty()) {
                    Toast.makeText(this, "Don't leave it empty!", Toast.LENGTH_SHORT).show()
                } else {
                    addCourseViewModel.insertCourse(courseName, day, selectedStartTime, selectedEndTime, lecturer, note)
                    finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTimePicker(tag: String) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)

        if (tag == "startTimePicker") {
            selectedStartTime = selectedTime
            findViewById<TextView>(R.id.tv_start_clock).text = selectedStartTime
        } else if (tag == "endTimePicker") {
            selectedEndTime = selectedTime
            findViewById<TextView>(R.id.tv_end_clock).text = selectedEndTime
        }
    }
}