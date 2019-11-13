package sample.firestoredemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import java.util.HashMap

class MainActivity : AppCompatActivity() {
    internal lateinit var etFirstName: EditText
    internal lateinit var etMobileNo: EditText
    internal lateinit var btnSend: Button
    internal lateinit var btnGet: Button
    internal lateinit var tvUserDetails: TextView
    private val TAG = "data"
    private val mdoc = FirebaseFirestore.getInstance().document("student/studentData")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etFirstName = findViewById(R.id.et_name)
        etMobileNo = findViewById(R.id.et_mobileno)
        btnSend = findViewById(R.id.btn_send)
        btnGet = findViewById(R.id.btn_get)
        tvUserDetails = findViewById(R.id.tv_user_details)

        btnSend.setOnClickListener {
            val user = HashMap<String, Any>()
            user["name"] = etFirstName.text.toString()
            user["mobileNo"] = etMobileNo.text.toString()
            mdoc.set(user).addOnSuccessListener { Toast.makeText(this@MainActivity, "Student data saved successfully", Toast.LENGTH_SHORT).show() }.addOnFailureListener { Toast.makeText(this@MainActivity, "student error data came.", Toast.LENGTH_SHORT).show() }
        }

        btnGet.setOnClickListener {
            mdoc.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    tvUserDetails.text = " Name - " + documentSnapshot.getString("name") + "\n Mobile No - " + documentSnapshot.getString("mobileNo")
                }
            }
        }
    }
}
