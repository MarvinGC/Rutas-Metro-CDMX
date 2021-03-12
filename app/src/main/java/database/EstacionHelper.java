package database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EstacionHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE = "metro.db";
    private static final int VERSION = 1;
    public final static String RUTA_BASE = "/data/data/com.devmarvin.rutasdelmetro/databases/";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public EstacionHelper(Context context) {
        super(context, NOMBRE_BASE, null, VERSION);
        this.myContext = context;

    }

    //Crear Base de Datos en el sistema
    public void createDatabase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if(dbExist)
        {
            Log.v("Base de Datos:", "Base de Datos ya existe");
            String myPath = RUTA_BASE + NOMBRE_BASE;
            File dbfile = new File(myPath);
            dbfile.delete();
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }

        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }

    }
    //Check database already exist or not
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = RUTA_BASE + NOMBRE_BASE;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }
    //Copia la base de datos de assets a una nueva base de datos en el sistema android
    private void copyDataBase() throws IOException
    {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(NOMBRE_BASE);

        // Path to the just created empty db
        String outFileName = RUTA_BASE + NOMBRE_BASE;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    //Eliminar base de datos
    public void db_delete()
    {
        File file = new File(RUTA_BASE + NOMBRE_BASE);
        if(file.exists())
        {
            file.delete();
            Log.i("Base de Datos:","Base de datos eliminada");
        }
    }
    //abre la base de datos
    public void openDatabase() throws SQLException
    {
        String ruta = RUTA_BASE + NOMBRE_BASE;
        myDataBase = SQLiteDatabase.openDatabase(ruta, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase() throws SQLException
    {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            Log.v("Base de Datos:", "Base de datos actualizada");
            db_delete();
        }
    }
}
