
package com.example.zhoupeng.e2voicedroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessagesListAdapter extends BaseAdapter {
    private Context context;
    private int count;
    private static LayoutInflater layoutInflater;

    public MessagesListAdapter(Context context) {
        this.context = context;
        count = Conversation.getCount();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        TextMessage item = Conversation.getMessage(position);

        if (convertView == null) {
            if ("sys".equals(item.getFrom())) {
                convertView = layoutInflater.inflate(R.layout.message_system, null);
                holder = new Holder();
                holder.data = (TextView) convertView.findViewById(R.id.editTextUserDetailInput_sys);
            }
            else if ("tx".equals(item.getFrom())) {
                convertView = layoutInflater.inflate(R.layout.message_sent, null);
                holder = new Holder();
                holder.data = (TextView) convertView.findViewById(R.id.editTextUserDetailInput_tx);
            } else {
                convertView = layoutInflater.inflate(R.layout.message_response, null);
                holder = new Holder();
                holder.data = (TextView) convertView.findViewById(R.id.editTextUserDetailInput_rx);
            }
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.data.setText(item.getMessage());
        return convertView;
    }

    // Helper class to recycle View's
    static class Holder {
        TextView data;
    }

    // Add new items
    public void refreshList(TextMessage message) {
        Conversation.add(message);
        notifyDataSetChanged();
    }

}
