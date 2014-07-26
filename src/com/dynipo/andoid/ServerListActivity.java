package com.dynipo.andoid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dynipo.andoid.ServerListAdapter.ServerListItem;
import com.dynipo.andoid.ServerReaderDbHelper.ServerEntry;

public class ServerListActivity extends ActionBarActivity {
	
	public static int SERVER_LIST_VIEW_ID = 1;
	
	private ServerReaderDbHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_list);
		
		 dbHelper = new ServerReaderDbHelper(this.getApplicationContext());
	}
	
	@Override
    // The activity is about to become visible.
    protected void onStart() {
		loadServerList();
        super.onStart();
    }

	// Loads servers from DB and populates the list
	private void loadServerList() {
		ListView serverListView = (ListView) findViewById(R.id.server_list);
		serverListView.removeAllViewsInLayout();
		/*
		serverListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		serverListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				
				// Define 'where' part of query.
				String selection = ServerEntry.COLUMN_NAME_NAME + " = ?";
				// Specify arguments in placeholder order.
				String[] selectionArgs = { ((TextView)((LinearLayout)view).findViewById(R.id.server_list_item_name)).getText().toString() };
				// Issue SQL statement.
				db.delete(ServerEntry.TABLE_NAME, selection, selectionArgs);
				
				return false;
			}
		});
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				ServerEntry._ID,
				ServerEntry.COLUMN_NAME_NAME,
				ServerEntry.COLUMN_NAME_PASSWORD
				};
		
		// How you want the results sorted in the resulting Cursor
		String sortOrder = ServerEntry.COLUMN_NAME_NAME;

		Cursor cursor = db.query(
				ServerEntry.TABLE_NAME,  // The table to query
				projection,		// The columns to return
				null,			// The columns for the WHERE clause
				null,			// The values for the WHERE clause
				null,			// don't group the rows
				null,			// don't filter by row groups
				sortOrder		// The sort order
				);
		
		ServerListItem servers[] = new ServerListItem[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext())
		{
			String serverName = cursor.getString(
					cursor.getColumnIndexOrThrow(ServerEntry.COLUMN_NAME_NAME)
					);
			String serverPassword = cursor.getString(
					cursor.getColumnIndexOrThrow(ServerEntry.COLUMN_NAME_PASSWORD)
					);
			servers[i] = new ServerListItem(serverName, serverPassword, null);
			i++;
		}
		
		ServerListAdapter adapter = new ServerListAdapter(this, servers);
		serverListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			Intent intent = new Intent(this, AddServerActivity.class);
		    startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
