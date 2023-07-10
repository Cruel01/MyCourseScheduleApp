package com.dicoding.courseschedule.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course

class DetailActivity : AppCompatActivity() {

    companion object {
        const val COURSE_ID = "course_Id"
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val courseId = intent.getIntExtra(COURSE_ID, 0)
        val factory = DetailViewModelFactory.createFactory(this, courseId)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

        viewModel.course.observe(this, { course ->
            showCourseDetail(course)
        })

    }

    private fun showCourseDetail(course: Course?) {
        course?.apply {
            findViewById<TextView>(R.id.tv_course_name).text = courseName
            findViewById<TextView>(R.id.tv_time).text = getTimeString(startTime, endTime)
            findViewById<TextView>(R.id.tv_lecturer).text = lecturer
            findViewById<TextView>(R.id.tv_note).text = note
        }
    }

    private fun getTimeString(startTime: String, endTime: String): String {
        return "($startTime - $endTime)"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.delete_alert))
                    setNegativeButton(getString(R.string.no), null)
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        viewModel.delete()
                        finish()
                    }
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}