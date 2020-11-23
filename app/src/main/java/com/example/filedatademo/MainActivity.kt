package com.example.filedatademo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FileWriterButton.setOnClickListener {
            val s = BookName.text.toString()
            if (s != "") {
                fileSave(s)
                Toast.makeText(this, "数据存储成功", Toast.LENGTH_LONG).show()
                BookName.text.clear()
            } else {
                Toast.makeText(this, "请先填写数据", Toast.LENGTH_LONG).show()
            }
        }
        FileReaderButton.setOnClickListener {
            OutText.text = fileOpen()
        }
        SPWriterButton.setOnClickListener {
            val bookName = BookName.text.toString()
            val bookPrice = BookPrice.text.toString()
            if (bookName != "" && bookPrice != "") {
                spSave(bookName, bookPrice)
                Toast.makeText(this, "数据存储成功", Toast.LENGTH_LONG).show()
                BookName.text.clear()
                BookPrice.text.clear()
            } else {
                Toast.makeText(this, "请先填写数据", Toast.LENGTH_LONG).show()
            }
        }
        SPReaderButton.setOnClickListener {
            val sp = getSharedPreferences("sp_data", MODE_PRIVATE)
            val stringBuilder = StringBuilder()
            stringBuilder.append(sp.getString("name", "")).append("\n")
                .append(sp.getString("price", "")).append("元")
            OutText.text = stringBuilder.toString()
        }
    }

    private fun fileSave(string: String) {
        try {
            //确定文件名和写入模式
            val output = openFileOutput("data", MODE_PRIVATE)
            //转换为IO写入流
            val writer = BufferedWriter(OutputStreamWriter(output))
            //use，Kotlin提供的内置扩展函数，他会保证在{}执行完后，自动将外层流关闭
            writer.use {
                it.write(string)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun fileOpen(): String {
        val stringBuilder = StringBuilder()
        try {
            val input = openFileInput("data")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use { reader.forEachLine { stringBuilder.append(it) } }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    private fun spSave(bookName: String, bookPrice: String) {
        getSharedPreferences("sp_data", MODE_PRIVATE).edit {
            putString("name", bookName)
            putString("price", bookPrice)
        }
    }
}