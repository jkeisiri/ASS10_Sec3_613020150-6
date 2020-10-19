package com.example.lab10sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_delete.*
import kotlinx.android.synthetic.main.activity_edit_delete.edt_age
import kotlinx.android.synthetic.main.activity_edit_delete.edt_id
import kotlinx.android.synthetic.main.activity_edit_delete.edt_name
import kotlinx.android.synthetic.main.insert_layout.*
import androidx.appcompat.app.AlertDialog

class EditDeleteActivity : AppCompatActivity() {
    private lateinit var dbHandler: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete)

        dbHandler = DatabaseHelper.getInstance(baseContext)

        val mId =intent.getStringExtra("mId")
        val mName =intent.getStringExtra("mName")
        val mAge =intent.getStringExtra("mAge")

        edt_id.setText(mId)
        edt_id.isEnabled =false
        edt_name.setText(mName)
        edt_age.setText(mAge)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    fun updateStudent(v:View){
        var id = edt_id.text.toString()
        var name = edt_name.text.toString()
        var age = edt_age.text.toString().toInt()
        var result = dbHandler.updateStudent(Student(id=id,name=name,age=age))
        if(result > -1) {
            Toast.makeText(
                applicationContext,
                "The student is update successfully",
                Toast.LENGTH_LONG
            ).show()

        }else{
            Toast.makeText(applicationContext,"Insert Filure", Toast.LENGTH_LONG).show()
        }
        finish()
    }
    fun  deleteStudent(v: View){
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("Warning Message")
            setMessage("Do you want to delete student ?")
            setNegativeButton("Yes"){
                dialog, which->
                val result = dbHandler?.deleteStudent(edt_id.text.toString())
                if (result!!>-1){
                    Toast.makeText(applicationContext, "Deleted succesfully",Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(applicationContext,"Delete Failue", Toast.LENGTH_LONG).show()

                }
                finish()
            }
            setPositiveButton("No"){dialog, which->dialog.cancel()}
            show()
        }
    }
}


