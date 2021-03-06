package com.tian.studyopengles

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import com.tian.studyopengles.lines.LineActivity
import com.tian.studyopengles.points.MultiplePoints
import com.tian.studyopengles.points.OnePoints
import com.tian.studyopengles.texture.TextureActivity
import com.tian.studyopengles.triangle.FragCoordActivity
import com.tian.studyopengles.triangle.FragCoordRender
import com.tian.studyopengles.triangle.MatrixActivity
import com.tian.studyopengles.triangle.TriangleActivity
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
            button("线段") {
                id = line_id
                setOnClickListener {
                    startDemoActivity(LineActivity::class.java)
                }
            }

            button("三角形") {
                id = triangle_id
                setOnClickListener {
                    startDemoActivity(TriangleActivity::class.java)
                }
            }

            button("三角形_fragCoord") {
                id = triangle_frag_coord_id
                setOnClickListener {
                    startDemoActivity(FragCoordActivity::class.java)
                }
            }

            button("三角形_matrix") {
                id = triangle_frag_coord_id
                setOnClickListener {
                    startDemoActivity(MatrixActivity::class.java)
                }
            }

            button("图片绘制") {
                id = texture_id
                setOnClickListener {
                    startDemoActivity(TextureActivity::class.java)
                }
            }
        }
    }

    companion object {
        val point_id = 1
        val multiple_point_id =2
        val line_id = 10
        val triangle_id = 20
        val triangle_frag_coord_id = 21
        val texture_id = 30
    }

    private fun startDemoActivity(cls: Class<*>) {
        val intent = Intent()
        intent.component = ComponentName(this, cls)
        intent.setPackage(this.packageName)
        this.startActivity(intent)
    }
}