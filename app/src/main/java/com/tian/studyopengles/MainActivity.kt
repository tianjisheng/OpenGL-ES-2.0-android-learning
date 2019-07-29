package com.tian.studyopengles

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import com.tian.studyopengles.lines.LineActivity
import com.tian.studyopengles.points.MultiplePoints
import com.tian.studyopengles.points.OnePoints
import org.jetbrains.anko.button
import org.jetbrains.anko.verticalLayout

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            button("点") {
                id = point_id
                setOnClickListener {
                    startDemoActivity(OnePoints::class.java)
                }
            }
            button("多个点") {
                id = multiple_point_id
                setOnClickListener {
                    startDemoActivity(MultiplePoints::class.java)
                }
            }
            button("线") {
                id = line_id
                setOnClickListener {
                    startDemoActivity(LineActivity::class.java)
                }
            }
        }
    }

    companion object {
        val point_id = 1
        val multiple_point_id =2
        val line_id = 10
        val line_loop_id = 11
    }

    private fun startDemoActivity(cls: Class<*>) {
        val intent = Intent()
        intent.component = ComponentName(this, cls)
        intent.setPackage(this.packageName)
        this.startActivity(intent)
    }
}