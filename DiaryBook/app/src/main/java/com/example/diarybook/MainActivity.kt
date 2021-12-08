package com.example.diarybook

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var diarys: ArrayList<DiaryItem>
    private lateinit var sqlUtils: SqlUtils
    private lateinit var account: String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        sqlUtils = SqlUtils(this, "Diary")

        account = sharedPreferences.getString("account", "")!!

        diarys = sqlUtils.query_author(account!!)

        diary_list.adapter = DiaryListAdapter(this, R.layout.diary_list_item, diarys)

        // 长按菜单
        diary_list.setOnCreateContextMenuListener { menu, v, menuInfo ->
            menu.setHeaderTitle("选择操作")
            menu.add(0,0,0,"编辑")
            menu.add(0,1,1,"删除")
        }

        // 点击转入展示日记活动
        diary_list.setOnItemClickListener { parent, view, position, id ->
            val selected_diary = diarys[position]
            val id = selected_diary.id
            val intent = Intent(this, ShowContentActivty::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        write.setOnClickListener {
            val intent = Intent(this, WriteDiaryActivity::class.java)
            intent.putExtra("account", account)
            startActivity(intent)
        }

        info.setOnClickListener {
            val intent = Intent(this, UpdateAccountActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val info: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        when (item.itemId) {
            0 -> {
                val diary = diarys[position]
                val intent = Intent(this, EditDiaryActivity::class.java)

                intent.putExtra("id", diary.id)

                startActivity(intent)
            }
            1 -> {
                sqlUtils.delete(diarys[position].id)
                diarys.removeAt(position)
                val diary_adapter = diary_list.adapter as DiaryListAdapter
                diary_adapter.notifyDataSetChanged()
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        account = sharedPreferences.getString("account", "")!!
        diarys = sqlUtils.query_author(account)
        diary_list.adapter = DiaryListAdapter(this, R.layout.diary_list_item, diarys)
        super.onResume()
    }

    fun add_test_data() {
        val sqlUtils = SqlUtils(this, "Diary")

        sqlUtils.insert("Test 1", "admin", "2021/11/2", "This is Test 1", "", "")
        sqlUtils.insert("Test 2", "admin", "2021/11/2", "This is Test 2", "", "")
        sqlUtils.insert("Test 3", "admin", "2021/11/2", "This is Test 3", "", "")
        sqlUtils.insert("Test 4", "admin", "2021/11/2", "This is Test 4", "", "")
        sqlUtils.insert("Test 5", "admin", "2021/11/2", "This is Test 5", "", "")
        sqlUtils.insert("Test 6", "admin", "2021/11/2", "This is Test 6", "", "")
        sqlUtils.insert("Test 7", "admin", "2021/11/2", "This is Test 7", "", "")
        sqlUtils.insert("Test 8", "admin", "2021/11/2", "This is Test 8", "", "")
        sqlUtils.insert("Test 9", "admin", "2021/11/2", "This is Test 9", "", "")
        sqlUtils.insert("Test 10", "admin", "2021/11/2", "This is Test 10", "", "")

        sqlUtils.insert("Test 11", "xvzh", "2021/11/2", "This is Test 1", "", "")
        sqlUtils.insert("Test 12", "xvzh", "2021/11/2", "This is Test 2", "", "")
        sqlUtils.insert("Test 13", "xvzh", "2021/11/2", "This is Test 3", "", "")
        sqlUtils.insert("Test 14", "xvzh", "2021/11/2", "This is Test 4", "", "")
        sqlUtils.insert("Test 15", "xvzh", "2021/11/2", "This is Test 5", "", "")
        sqlUtils.insert("Test 16", "xvzh", "2021/11/2", "This is Test 6", "", "")
        sqlUtils.insert("Test 17", "xvzh", "2021/11/2", "This is Test 7", "", "")
        sqlUtils.insert("Test 18", "xvzh", "2021/11/2", "This is Test 8", "", "")
        sqlUtils.insert("Test 19", "xvzh", "2021/11/2", "This is Test 9", "", "")
        sqlUtils.insert("Test 20", "xvzh", "2021/11/2", "This is Test 10", "", "")

        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }
}