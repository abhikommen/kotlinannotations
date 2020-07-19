package com.elyeproj.annotationexperiment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.elyeproj.annotation.ChangeMyName
import com.elyeproj.annotation.CheckCamelSource

@CheckCamelSource
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AnnotateTest"
    }

    @ReflectRuntime(5)
    val reflectTest: Int = 0


    @ChangeMyName("Senku")
    val name = "Abhishek"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "BEFORE $name")
        changeName(name)
        Log.d(TAG, "AFTER $name")


    }

    private fun bindReflectionValue(target: Any) {
        val declaredFields = target::class.java.declaredFields

        for (field in declaredFields) {
            for (annotation in field.annotations) {
                when(annotation) {
                    is ReflectRuntime -> {
                        field.isAccessible = true
                        field.set(target, annotation.value)
                    }
                }
            }
        }
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ReflectRuntime(val value: Int)
