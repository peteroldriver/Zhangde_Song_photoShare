package com.example.zhangde_song_photoshare

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.zhangde_song_photoshare.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var imageId = R.mipmap.dog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        try {
            applicationContext.openFileInput("text").bufferedReader().useLines { lines ->
                if (lines != null) {
                    binding.editText.text.clear()
                    lines.forEach { line ->
                        binding.editText.text.append(line)
                    }
                }
            }

            applicationContext.openFileInput("imageId").bufferedReader().useLines { lines ->
                if (lines != null) {
                    lines.forEach { line ->
                        binding.imageView.setImageResource(line.toInt())
                    }
                }
            }
        }
        catch (e : Exception){
            e.printStackTrace()
        }

        var imageList = intArrayOf(R.mipmap.cat, R.mipmap.animal, R.mipmap.dog, R.mipmap.dog2, R.mipmap.cat2)
        binding.button.setOnClickListener {
            imageId = imageList.random();
            binding.imageView.setImageResource(imageId)
        }
    }

    private fun saveInfo(){
        applicationContext.openFileOutput("text", Context.MODE_PRIVATE).use {
            it.write(binding.editText.text.toString().toByteArray())
        }

        applicationContext.openFileOutput("imageId", Context.MODE_PRIVATE).use {
            it.write(imageId.toString().toByteArray())
        }
    }

    override fun onStop() {
        super.onStop()
        saveInfo()
        Log.d("OnStop()", "-------------------------")
    }
    override fun onDestroy() {
        super.onDestroy()
        saveInfo()
        Log.d("OnDestory()", "--------------------")
    }
}