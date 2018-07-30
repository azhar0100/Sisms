package com.example.azhar.sisms

import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telephony.SmsManager
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.EditText

class InboxActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    val TAG = "InboxActivity"
    lateinit var mRecyclerView: RecyclerView
    var mAdapter: InboxAdapter = InboxAdapter(null)
    var thread_id = -1
    var address : String? = null

    internal val SMS_SUMMARY_PROJECTION = arrayOf(Telephony.Sms.Inbox._ID,
            Telephony.Sms.Inbox.ADDRESS,
            Telephony.Sms.Inbox.BODY,
            Telephony.Sms.Inbox.READ,
            Telephony.Sms.TYPE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        mRecyclerView = findViewById<RecyclerView>(R.id.inboxRecycler)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        try {
            thread_id = intent.extras!!.getInt("thread_id")
            address = intent.extras!!.getString("address")
            Log.i(TAG, "I am here bitch with $this.thread_id")
        } catch (e: NullPointerException) {
            this.thread_id = -1
        }

        supportLoaderManager.initLoader(0,null,this as LoaderManager.LoaderCallbacks<Cursor>)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val baseUri = Telephony.Sms.CONTENT_URI

        Log.e(TAG,"I swear i am using $thread_id , lay off")

        return CursorLoader(this, baseUri,
                SMS_SUMMARY_PROJECTION, Telephony.Sms.THREAD_ID + " = ?", arrayOf(Integer.toString(thread_id)),
                Telephony.Sms.DATE)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data)
        Log.e(TAG, "Data is swapped and it has count ${data.count}")
        mRecyclerView.scrollToPosition(mAdapter.itemCount - 1)

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null)
    }

    fun onSendButton(button: View){
        var text = findViewById<EditText>(R.id.editText).text.toString()
        findViewById<EditText>(R.id.editText).text = SpannableStringBuilder("")
        var manager = SmsManager.getDefault()
        Log.e(TAG,"Yeah I did get the manager boy")
        manager.sendTextMessage(address,null,text,null,null)
    }


}
