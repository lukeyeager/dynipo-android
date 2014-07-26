package com.dynipo.andoid;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dynipo.andoid.ServerReaderDbHelper.ServerEntry;

public class AddServerActivity extends ActionBarActivity {

	private ServerReaderDbHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_server);
		
		dbHelper = new ServerReaderDbHelper(this.getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_server, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	
	/** Called when the user clicks the Add button */
	public void addServer(View view) {
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		TextView serverNameView = (TextView) findViewById(R.id.server_name);
		TextView serverPasswordView = (TextView) findViewById(R.id.server_password);
		
		String serverName = serverNameView.getText().toString();
		String serverPassword = (String) serverPasswordView.getText().toString();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ServerEntry.COLUMN_NAME_NAME, serverName);
		values.put(ServerEntry.COLUMN_NAME_PASSWORD, serverPassword);

		db.insert(
				ServerEntry.TABLE_NAME,
				null,
				values);
		
		this.finish();
	}
}
