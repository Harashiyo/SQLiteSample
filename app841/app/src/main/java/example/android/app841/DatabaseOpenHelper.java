package example.android.app841;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shohei on 2015/12/03.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
private static final String DB_NAME="app841.db";
    private static final int DB_VERSION=1;
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DaoItem.create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //データベースの変更が生じた場合は，ここに処理を記述する。
    }
}
