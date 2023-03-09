package com.iug.realtimedatabase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.iug.realtimedatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference()

        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString()
            val id = binding.etID.text.toString()
            val age = binding.etAge.text.toString()

            if (isNotEmptyInputs(name, id, age)){
                // add your data
                val person= hashMapOf(
                    "name" to name,
                    "id" to id,
                    "age" to age
                )

                myRef.child("person").child("$count").setValue(person)
                count++

            }else{
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGet.setOnClickListener {
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                    binding.tvResult.text = value.toString()
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "failure", Toast.LENGTH_SHORT).show()

                }

            })
        }



    }



    private fun isNotEmptyInputs(name:String, number:String, address:String): Boolean =
        !(name.isNullOrEmpty() || number.isNullOrEmpty() || address.isNullOrEmpty())

}