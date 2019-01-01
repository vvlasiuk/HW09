package net.ukr.vlsv.hw03_chat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.ukr.vlsv.hw03_chat.viewmodels.MessageViewModel


import net.ukr.vlsv.hw03_chat.R
import net.ukr.vlsv.hw03_chat.adapter.SpacesItemDecoration
import net.ukr.vlsv.hw03_chat.viewmodels.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var messageViewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageViewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(MessageViewModel::class.java)

        chat_rv.setHasFixedSize(true)                               // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        chat_rv.layoutManager = LinearLayoutManager(this)           // используем linear layout manager
        chat_rv.addItemDecoration(SpacesItemDecoration(15))
        chat_rv.adapter = messageViewModel.chatAdapter

        ok_btn.setOnClickListener() {
            messageViewModel.onOK_ButtonClicked(text_tw.text, radioButton1.isChecked)
        }
    }
}
