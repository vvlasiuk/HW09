package net.ukr.vlsv.hw03_chat.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.ukr.vlsv.hw03_chat.R
import net.ukr.vlsv.hw03_chat.data.CountUserMessages
import net.ukr.vlsv.hw03_chat.data.NameUsers
import net.ukr.vlsv.hw03_chat.entities.MessageTable

class RecyclerAdapter (private var mDataset: MutableList<MessageTable>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    val VIEW_TYPE_HEADER         = 0
    val VIEW_TYPE_USER_1_MESSAGE = 1
    val VIEW_TYPE_USER_2_MESSAGE = -1
    val HEADER_POSITION          = 0

    var NAME_USERS = NameUsers(null, null)
    var COUNT_USER_MESSAGE = CountUserMessages(0, 0)

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mTextView: TextView

        init {
            mTextView = v.findViewById(R.id.text1_tw) as TextView
        }

        fun onBind(message: MessageTable) {
            mTextView.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResource = getLayout(viewType)
        val view: View = LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
        return ViewHolder(view)
    }

    private fun getLayout(viewType: Int): Int {
        when (viewType) {
            VIEW_TYPE_HEADER -> return R.layout.activity_count_message
            VIEW_TYPE_USER_1_MESSAGE -> return R.layout.activity_user1_item
            VIEW_TYPE_USER_2_MESSAGE -> return R.layout.activity_user2_item
        }
        return R.layout.activity_user1_item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mDataset[position])
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == HEADER_POSITION) return 0

        if (mDataset[position].userId == "1") {return 1}

        return -1
    }

    fun addMessage(message: MessageTable) {
        mDataset.add(message)
        notifyItemInserted(mDataset.size - 1)

        if (message.userId == "1") COUNT_USER_MESSAGE.CountUser_1++
        if (message.userId == "2") COUNT_USER_MESSAGE.CountUser_2++

        setCountUser()
    }

    fun setCountUser() {
        mDataset.set(0, MessageTable(0, "0", "${NAME_USERS.NameUser_1}: ${COUNT_USER_MESSAGE.CountUser_1}       ${NAME_USERS.NameUser_2}: ${COUNT_USER_MESSAGE.CountUser_2}"))
        notifyItemChanged(0)
    }
}