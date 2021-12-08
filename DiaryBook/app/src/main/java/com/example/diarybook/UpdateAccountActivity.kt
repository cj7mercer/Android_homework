package com.example.diarybook

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_account.*

class UpdateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)
        val sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        val old_account = sharedPreferences.getString("account", "")!!
        update_old_account.setText(old_account)

        update_account_submit.setOnClickListener {
            val editor = sharedPreferences.edit()
            val new_account = update_account_account.text.toString()
            val new_password = update_account_password.text.toString()
            val old_password = sharedPreferences.getString("password", "")!!
            val accounts = sharedPreferences.getString("accounts", "")!!.split("#") as ArrayList
            val pwds = sharedPreferences.getString("passwords", "")!!.split("#") as ArrayList

            if (new_account in accounts) {
                Toast.makeText(this, "用户名已被使用，换一个试试", Toast.LENGTH_SHORT).show()
            }
            else if (new_account == "") {
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show()
            }
            else {
                accounts.remove(old_account)
                accounts.add(new_account)
                pwds.remove(old_password)
                pwds.add(new_password)

                val accounts = accounts.joinToString("#")
                val pwds = pwds.joinToString("#")

                editor.putString("accounts", accounts)
                editor.putString("passwords", pwds)
                editor.putString("account", new_account)

                editor.apply()

                val sqlUtils = SqlUtils(this, "Diary")
                sqlUtils.update_author(old_account, new_account)

                finish()
            }
        }

    }
}