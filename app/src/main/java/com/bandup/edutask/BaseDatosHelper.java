package com.bandup.edutask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatosHelper extends SQLiteOpenHelper {

    public static final String TAG = "BaseDatosHelper";
    public static final String DB_NAME = "EduTaskDB";
    public static final int DB_VERSION = 1;

    // Nombres de las tablas
    public static final String TABLE_MATERIA = "Materia";
    public static final String TABLE_ALUMNO = "Alumno";
    public static final String TABLE_ASIGNACION = "Asignacion";
    public static final String TABLE_ALUMNO_MATERIA = "Alumno_Materia";
    public static final String TABLE_ALUMNO_ASIGNACION = "Alumno_Asignacion";
    public static final String TABLE_ASIGNACION_MATERIA = "Asignacion_Materia";

    // Columnas de la tabla Alumno_Materia
    public static final String COL_ALUMNO_MATERIA_ID = "ID";
    public static final String COL_ALUMNO_MATERIA_ALUMNO_ID = "Alumno_ID"; // Cambiado a Alumno_ID
    public static final String COL_ALUMNO_MATERIA_MATERIA_ID = "Materia_ID"; // Cambiado a Materia_ID

    // Columnas de la tabla Alumno_Asignacion
    public static final String COL_ALUMNO_ASIGNACION_ID = "ID";
    public static final String COL_ALUMNO_ASIGNACION_ALUMNO_ID = "Alumno_ID"; // Cambiado a Alumno_ID
    public static final String COL_ALUMNO_ASIGNACION_ASIGNACION_ID = "Asignacion_ID"; // Cambiado a Asignacion_ID

    // Columnas de la tabla Asignacion_Materia
    public static final String COL_ASIGNACION_MATERIA_ID = "ID";
    public static final String COL_ASIGNACION_MATERIA_MATERIA_ID = "Materia_ID"; // Cambiado a Materia_ID
    public static final String COL_ASIGNACION_MATERIA_ASIGNACION_ID = "Asignacion_ID"; // Cambiado a Asignacion_ID

    // Columnas de la tabla Alumno
    public static final String COL_ALUMNO_ID = "ID";
    public static final String COL_ALUMNO_NUM_CONTROL = "NumControl";
    public static final String COL_ALUMNO_NOMBRE = "Nombre";
    public static final String COL_ALUMNO_A_PATERNO = "aPaterno";
    public static final String COL_ALUMNO_A_MATERNO = "aMaterno";
    public static final String COL_ALUMNO_OCULTO = "Oculto";

    // Columnas de la tabla Asignacion
    public static final String COL_ASIGNACION_ID = "ID";
    public static final String COL_ASIGNACION_FECHA = "Fecha";
    public static final String COL_ASIGNACION_NOMBRE = "Nombre";
    public static final String COL_ASIGNACION_REALIZADA = "Realizada";
    public static final String COL_ASIGNACION_OCULTO = "Oculto";

    // Columnas de la tabla Materia
    public static final String COL_MATERIA_ID = "ID";
    public static final String COL_MATERIA_CLAVE = "Clave";
    public static final String COL_MATERIA_NOMBRE = "Nombre";
    public static final String COL_MATERIA_OCULTO = "Oculto";

    public BaseDatosHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            // Crear la tabla Alumno
            String crearTablaAlumno = "CREATE TABLE IF NOT EXISTS " + TABLE_ALUMNO + " ("
                    + COL_ALUMNO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ALUMNO_NUM_CONTROL + " TEXT UNIQUE, "
                    + COL_ALUMNO_NOMBRE + " TEXT, "
                    + COL_ALUMNO_A_PATERNO + " TEXT, "
                    + COL_ALUMNO_A_MATERNO + " TEXT, "
                    + COL_ALUMNO_OCULTO + " INTEGER)";
            db.execSQL(crearTablaAlumno);

            // Crear la tabla Materia
            String crearTablaMateria = "CREATE TABLE IF NOT EXISTS " + TABLE_MATERIA + " ("
                    + COL_MATERIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_MATERIA_CLAVE + " TEXT UNIQUE, "
                    + COL_MATERIA_NOMBRE + " TEXT, "
                    + COL_MATERIA_OCULTO + " INTEGER)";
            db.execSQL(crearTablaMateria);

            // Crear la tabla Asignacion
            String crearTablaAsignacion = "CREATE TABLE IF NOT EXISTS " + TABLE_ASIGNACION + " ("
                    + COL_ASIGNACION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ASIGNACION_FECHA + " TEXT, "
                    + COL_ASIGNACION_NOMBRE + " TEXT, "
                    + COL_ASIGNACION_REALIZADA + " INTEGER, "
                    + COL_ASIGNACION_OCULTO + " INTEGER, "
                    + "Materia_ID INTEGER, "
                    + "FOREIGN KEY(Materia_ID) REFERENCES " + TABLE_MATERIA + "(" + COL_MATERIA_ID + "))";
            db.execSQL(crearTablaAsignacion);

    // Crear la tabla Alumno_Materia
            String crearTablaAlumnoMateria = "CREATE TABLE IF NOT EXISTS " + TABLE_ALUMNO_MATERIA + " ("
                    + COL_ALUMNO_MATERIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ALUMNO_MATERIA_ALUMNO_ID + " INTEGER, " // Cambiado a Alumno_ID
                    + COL_ALUMNO_MATERIA_MATERIA_ID + " INTEGER, " // Cambiado a Materia_ID
                    + "FOREIGN KEY(" + COL_ALUMNO_MATERIA_ALUMNO_ID + ") REFERENCES " + TABLE_ALUMNO + "(" + COL_ALUMNO_ID + "), "
                    + "FOREIGN KEY(" + COL_ALUMNO_MATERIA_MATERIA_ID + ") REFERENCES " + TABLE_MATERIA + "(" + COL_MATERIA_ID + "))";
            db.execSQL(crearTablaAlumnoMateria);

    // Crear la tabla Alumno_Asignacion
            String crearTablaAlumnoAsignacion = "CREATE TABLE IF NOT EXISTS " + TABLE_ALUMNO_ASIGNACION + " ("
                    + COL_ALUMNO_ASIGNACION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ALUMNO_ASIGNACION_ALUMNO_ID + " INTEGER, " // Cambiado a Alumno_ID
                    + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + " INTEGER, " // Cambiado a Asignacion_ID
                    + "FOREIGN KEY(" + COL_ALUMNO_ASIGNACION_ALUMNO_ID + ") REFERENCES " + TABLE_ALUMNO + "(" + COL_ALUMNO_ID + "), "
                    + "FOREIGN KEY(" + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + ") REFERENCES " + TABLE_ASIGNACION + "(" + COL_ASIGNACION_ID + "))";
            db.execSQL(crearTablaAlumnoAsignacion);

    // Crear la tabla Asignacion_Materia
            String crearTablaAsignacionMateria = "CREATE TABLE IF NOT EXISTS " + TABLE_ASIGNACION_MATERIA + " ("
                    + COL_ASIGNACION_MATERIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ASIGNACION_MATERIA_MATERIA_ID + " INTEGER, " // Cambiado a Materia_ID
                    + COL_ASIGNACION_MATERIA_ASIGNACION_ID + " INTEGER, " // Cambiado a Asignacion_ID
                    + "FOREIGN KEY(" + COL_ASIGNACION_MATERIA_MATERIA_ID + ") REFERENCES " + TABLE_MATERIA + "(" + COL_MATERIA_ID + "), "
                    + "FOREIGN KEY(" + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") REFERENCES " + TABLE_ASIGNACION + "(" + COL_ASIGNACION_ID + "))";
            db.execSQL(crearTablaAsignacionMateria);

        // Insertar datos en la tabla Alumno
        String insertarAlumno1 = "INSERT INTO " + TABLE_ALUMNO + " (" + COL_ALUMNO_ID + ", "
                + COL_ALUMNO_NUM_CONTROL + ", " + COL_ALUMNO_NOMBRE + ", " + COL_ALUMNO_A_PATERNO + ", "
                + COL_ALUMNO_A_MATERNO + ", " + COL_ALUMNO_OCULTO + ") VALUES (10, '20130810', 'Efrain', 'Montalvo', 'Sanchez', 0)";
        db.execSQL(insertarAlumno1);

        String insertarAlumno2 = "INSERT INTO " + TABLE_ALUMNO + " (" + COL_ALUMNO_ID + ", "
                + COL_ALUMNO_NUM_CONTROL + ", " + COL_ALUMNO_NOMBRE + ", " + COL_ALUMNO_A_PATERNO + ", "
                + COL_ALUMNO_A_MATERNO + ", " + COL_ALUMNO_OCULTO + ") VALUES (11, '20130770', 'Tomas Alejandro', 'Galvan', 'Gandara', 0)";
        db.execSQL(insertarAlumno2);

        // Insertar datos en la tabla Materia
        String insertarMateria1 = "INSERT INTO " + TABLE_MATERIA + " (" + COL_MATERIA_ID + ", "
                + COL_MATERIA_CLAVE + ", " + COL_MATERIA_NOMBRE + ", " + COL_MATERIA_OCULTO + ") VALUES (10, 'A11A', 'Android', 0)";
        db.execSQL(insertarMateria1);

        String insertarMateria2 = "INSERT INTO " + TABLE_MATERIA + " (" + COL_MATERIA_ID + ", "
                + COL_MATERIA_CLAVE + ", " + COL_MATERIA_NOMBRE + ", " + COL_MATERIA_OCULTO + ") VALUES (11, 'B11B', 'Automatas', 0)";
        db.execSQL(insertarMateria2);

        String insertarMateria3 = "INSERT INTO " + TABLE_MATERIA + " (" + COL_MATERIA_ID + ", "
                + COL_MATERIA_CLAVE + ", " + COL_MATERIA_NOMBRE + ", " + COL_MATERIA_OCULTO + ") VALUES (12, 'C12C', 'Conmutacion', 0)";
        db.execSQL(insertarMateria3);

        String insertarMateria4 = "INSERT INTO " + TABLE_MATERIA + " (" + COL_MATERIA_ID + ", "
                + COL_MATERIA_CLAVE + ", " + COL_MATERIA_NOMBRE + ", " + COL_MATERIA_OCULTO + ") VALUES (13, 'D13D', 'Gestion de Proyectos de Software', 1)";
        db.execSQL(insertarMateria4);

        // Insertar datos en la tabla Asignacion
        String insertarAsignacion1 = "INSERT INTO " + TABLE_ASIGNACION + " (" + COL_ASIGNACION_ID + ", "
                + COL_ASIGNACION_FECHA + ", " + COL_ASIGNACION_NOMBRE + ", " + COL_ASIGNACION_REALIZADA + ", "
                + COL_ASIGNACION_OCULTO + ", Materia_ID) VALUES (10, '2013-12-06', 'Tarea 10', 0, 0, 10)";
        db.execSQL(insertarAsignacion1);

        // Insertar datos en la tabla Alumno_Materia
        String insertarAlumnoMateria = "INSERT INTO " + TABLE_ALUMNO_MATERIA + " (" + COL_ALUMNO_MATERIA_ID + ", "
                + COL_ALUMNO_MATERIA_ALUMNO_ID + ", " + COL_ALUMNO_MATERIA_MATERIA_ID + ") VALUES (1, 10, 10)";
        db.execSQL(insertarAlumnoMateria);

        // Insertar datos en la tabla Alumno_Asignacion
        String insertarAlumnoAsignacion = "INSERT INTO " + TABLE_ALUMNO_ASIGNACION + " (" + COL_ALUMNO_ASIGNACION_ID + ", "
                + COL_ALUMNO_ASIGNACION_ALUMNO_ID + ", " + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + ") VALUES (1, 10, 10)";
        db.execSQL(insertarAlumnoAsignacion);

        // Insertar datos en la tabla Asignacion_Materia
        String insertarAsignacionMateria = "INSERT INTO " + TABLE_ASIGNACION_MATERIA + " (" + COL_ASIGNACION_MATERIA_ID + ", "
                + COL_ASIGNACION_MATERIA_MATERIA_ID + ", " + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") VALUES (1, 10, 10)";
        db.execSQL(insertarAsignacionMateria);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes implementar la lógica de actualización de la base de datos si es necesario
        // Por ahora, simplemente borramos todas las tablas y las volvemos a crear
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUMNO_MATERIA);
        // ... (borrar las otras tablas)
        onCreate(db);
    }

    // Puedes agregar métodos para insertar, actualizar y consultar datos en cada tabla según tus necesidades

    // Ejemplo de método para agregar datos a la tabla Alumno
    // Métodos para operaciones en la tabla Alumno
    public boolean addAlumno(String numControl, String nombre, String aPaterno, String aMaterno, int oculto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ALUMNO_NUM_CONTROL, numControl);
        contentValues.put(COL_ALUMNO_NOMBRE, nombre);
        contentValues.put(COL_ALUMNO_A_PATERNO, aPaterno);
        contentValues.put(COL_ALUMNO_A_MATERNO, aMaterno);
        contentValues.put(COL_ALUMNO_OCULTO, oculto);

        long resultado = db.insert(TABLE_ALUMNO, null, contentValues);

        return resultado != -1;
    }

    public Cursor getAlumnos() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALUMNO + " WHERE " + COL_ALUMNO_OCULTO + " = 0";
        return db.rawQuery(query, null);
    }

    public void deleteAlumno(String numControl) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Actualiza el valor de Oculto a 1 en lugar de eliminar la fila
        ContentValues values = new ContentValues();
        values.put(COL_ALUMNO_OCULTO, 1);

        db.update(TABLE_ALUMNO, values, COL_ALUMNO_NUM_CONTROL + " = ?", new String[]{numControl});

        // Puedes añadir logs o mensajes aquí para indicar que el alumno fue "eliminado"
        Log.d(TAG, "deleteAlumno: Alumno con número de control " + numControl + " oculto (Oculto = 1)");
    }

    // Métodos para operaciones en la tabla Asignacion
    public boolean addAsignacion(String fecha, String nombre, int realizada, int oculto, int materiaID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ASIGNACION_FECHA, fecha);
        contentValues.put(COL_ASIGNACION_NOMBRE, nombre);
        contentValues.put(COL_ASIGNACION_REALIZADA, realizada);
        contentValues.put(COL_ASIGNACION_OCULTO, oculto);
        contentValues.put("Materia_ID", materiaID);

        long resultado = db.insert(TABLE_ASIGNACION, null, contentValues);

        return resultado != -1;
    }

    public Cursor getAsignaciones() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ASIGNACION + " WHERE " + COL_ASIGNACION_OCULTO + " = 0";
        return db.rawQuery(query, null);
    }

    public void deleteAsignacion(int asignacionID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Actualiza el valor de Oculto a 1 en lugar de eliminar la fila
        ContentValues values = new ContentValues();
        values.put(COL_ASIGNACION_OCULTO, 1);

        db.update(TABLE_ASIGNACION, values, COL_ASIGNACION_ID + " = ?", new String[]{String.valueOf(asignacionID)});

        // Puedes añadir logs o mensajes aquí para indicar que la asignación fue "eliminada"
        Log.d(TAG, "deleteAsignacion: Asignación con ID " + asignacionID + " oculta (Oculto = 1)");
    }

    // Métodos para operaciones en la tabla Materia
    public boolean addMateria(String clave, String nombre, int oculto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MATERIA_CLAVE, clave);
        contentValues.put(COL_MATERIA_NOMBRE, nombre);
        contentValues.put(COL_MATERIA_OCULTO, oculto);

        long resultado = db.insert(TABLE_MATERIA, null, contentValues);

        return resultado != -1;
    }

    public void deleteMateria(String nombre, String clave) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Actualiza el valor de Oculto a 1 en lugar de eliminar la fila
        ContentValues values = new ContentValues();
        values.put(COL_MATERIA_OCULTO, 1);

        // Corregir el orden de la condición para que sea clave = ? AND nombre = ?
        db.update(TABLE_MATERIA, values, COL_MATERIA_CLAVE + " = ? AND " + COL_MATERIA_NOMBRE + " = ?", new String[]{clave, nombre});

        // Puedes añadir logs o mensajes aquí para indicar que la materia fue "eliminada"
        Log.d(TAG, "deleteMateria: Materia con nombre " + nombre + " y clave " + clave + " oculta (Oculto = 1)");
    }


    public Cursor getMaterias() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_MATERIA_ID + " AS _id, " + COL_MATERIA_NOMBRE + " FROM " + TABLE_MATERIA + " WHERE " + COL_MATERIA_OCULTO + " = 0";

        return db.rawQuery(query, null);
    }

    // Dentro de la clase BaseDatosHelper

    // Método para obtener el nombre de la columna Materia_Nombre
    public static String getMateriaNombreColumn() {
        return COL_MATERIA_NOMBRE;
    }

    public Cursor getAsignacionesParaDia(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ASIGNACION +
                " WHERE " + COL_ASIGNACION_FECHA + " = ? AND " +
                COL_ASIGNACION_OCULTO + " = 0 AND " +
                COL_ASIGNACION_FECHA + " <= ?";
        return db.rawQuery(query, new String[]{fecha, fecha});
    }
}