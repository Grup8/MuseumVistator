package net.infobosccoma.museu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "PersonDB";
	// Books table name
	private static final String TABLE_PERSONS = "persons";

	// Books Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NOM = "nom";
	private static final String KEY_COGNOM = "cognom";

	private static final String[] COLUMNS = { KEY_ID, KEY_NOM, KEY_COGNOM };

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PERSON_TABLE = "CREATE TABLE persons ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "nom TEXT, "
				+ "cognom TEXT )";

		
		db.execSQL(CREATE_PERSON_TABLE);
	}

	public void addPerson(Person person) {
		Log.d("addPerson", person.toString());
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOM, person.getNom()); // get title
		values.put(KEY_COGNOM, person.getCognom()); // get author

		
		db.insert(TABLE_PERSONS, null, values);

		
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}