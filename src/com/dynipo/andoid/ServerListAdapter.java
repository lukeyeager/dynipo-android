package com.dynipo.andoid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServerListAdapter extends BaseAdapter {
	
	static public class ServerListItem {
		public String name;
		public String password;
		public String ipAddress;
		
		public ServerListItem(String n, String p, String a) {
			name = n;
			password = p;
			ipAddress = a;
		}
	}
	
	Context context;
    ServerListItem[] data;
    private static LayoutInflater inflater = null;
    
    public ServerListAdapter(Context context, ServerListItem[] data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.server_list_item, null);
        TextView nameView = (TextView) vi.findViewById(R.id.server_list_item_name);
        if (data[position].name.isEmpty())
        	nameView.setText("<server-name>");
        else
        	nameView.setText(data[position].name);
        TextView passwordView = (TextView) vi.findViewById(R.id.server_list_item_password);
        passwordView.setText(data[position].password);
        TextView ipAddressView = (TextView) vi.findViewById(R.id.server_list_item_ipaddress);
        if (data[position].name.isEmpty())
        	ipAddressView.setText("<none>");
        else
        	ipAddressView.setText(data[position].ipAddress);
        return vi;
	}

}
