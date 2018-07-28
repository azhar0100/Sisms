package com.example.azhar.sisms;

import android.database.Cursor;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import static android.support.constraint.Constraints.TAG;

public class InboxAdapter extends CursorAdapter<TextViewHolder> {

    private static String TAG = "InboxAdapter";
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        //return 0;
        getItem(position);
        Log.e(TAG,Integer.toString(mCursor.getCount()));
        return mCursor.getInt(mCursor.getColumnIndexOrThrow(Telephony.Sms.TYPE)) == Telephony.Sms.MESSAGE_TYPE_SENT ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(TextViewHolder viewHolder, final int position) {
//        String result = (String) listOfContacts.keySet().toArray()[position];
//        String result2 = (String) listOfContacts.values().toArray()[position];
        // Get element from your dataset at this position and replace the contents of the view
        // with that
        //viewHolder.getHeadView().setText(result1.get(position));
        getItem(position);
        Log.d(TAG + "position", Integer.toString(position));
        viewHolder.getTextView().setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Telephony.Sms.BODY)));
        /*if(mCursor != null) {
            mCursor.moveToPosition(position);
            Log.e(TAG, mCursor.getString(2));
            viewHolder.getHeadView().setText(mCursor.getString(1));
            viewHolder.getTextView().setText(mCursor.getString(2));
        }else{
            viewHolder.getTextView().setText("null");
        }*/
    }

    public TextViewHolder onCreateViewHolder(ViewGroup view, int type){
        View v;
        if(type==1) {
            v = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.sentview, view, false);
            return new SentViewHolder(v);
        }
        if (type==0) {
            v = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.inboxview, view, false);
            return new InboxViewHolder(v);
        }
        Log.e(TAG,"Why the fuck did it get here?");
        return null;
    }

    public InboxAdapter(Cursor c){
        super(c);
    }
}

abstract class TextViewHolder extends RecyclerView.ViewHolder{
    protected TextView textView;
    public TextViewHolder(View v){
        super(v);
    }

    public TextView getTextView() {
        return textView;
    }
}


class InboxViewHolder extends TextViewHolder{

    public InboxViewHolder(View v) {
        super(v);
        textView = (TextView) v.findViewById(R.id.cute_textview);
    }
}

class SentViewHolder extends  TextViewHolder{
    public SentViewHolder(View v) {
        super(v);
        textView = (TextView) v.findViewById(R.id.cute_textview_sent);
    }
}
