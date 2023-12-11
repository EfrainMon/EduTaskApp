package com.bandup.edutask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Set;

public class BaseDatosHelper extends SQLiteOpenHelper {
    private Context context;

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
    public static final String COL_ALUMNO_ASIGNACION_NOMBRE_COMPLETO = "NombreAlumnoAsignacion";
    public static final String COL_ALUMNO_ASIGNACION_ALUMNO_ID = "Alumno_ID"; // Cambiado a Alumno_ID
    public static final String COL_ALUMNO_ASIGNACION_MATERIA_ID = "Materia_ID"; // Cambiado a Asignacion_ID
    public static final String COL_ALUMNO_ASIGNACION_ASIGNACION_ID = "Asignacion_ID"; // Cambiado a Asignacion_ID
    public static final String COL_ALUMNO_ASIGNACION_REALIZADA = "Realizada";

    // Columnas de la tabla Asignacion_Materia
    public static final String COL_ASIGNACION_MATERIA_ID = "ID";
    public static final String COL_ASIGNACION_MATERIA_MATERIA_ID = "Materia_ID"; // Cambiado a Materia_ID
    public static final String COL_ASIGNACION_MATERIA_ASIGNACION_ID = "Asignacion_ID"; // Cambiado a Asignacion_ID

    // Columnas de la tabla Alumno
    public static final String COL_ALUMNO_ID = "ID";
    public static final String COL_ALUMNO_NUM_CONTROL = "NumControl";
    public static final String COL_ALUMNO_NOMBRE_COMPLETO = "NombreCompleto";
    public static final String COL_ALUMNO_OCULTO = "Oculto";


    // Columnas de la tabla Asignacion
    public static final String COL_ASIGNACION_ID = "ID";
    public static final String COL_ASIGNACION_FECHA = "Fecha";
    public static final String COL_ASIGNACION_NOMBRE = "Nombre";
    public static final String COL_ASIGNACION_OCULTO = "Oculto";

    // Columnas de la tabla Materia
    public static final String COL_MATERIA_ID = "ID";
    public static final String COL_MATERIA_CLAVE = "Clave";
    public static final String COL_MATERIA_NOMBRE = "Nombre";
    public static final String COL_MATERIA_OCULTO = "Oculto";

    public BaseDatosHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;  // Agrega esta línea
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla Alumno
            String crearTablaAlumno = "CREATE TABLE IF NOT EXISTS " + TABLE_ALUMNO + " ("
                    + COL_ALUMNO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ALUMNO_NUM_CONTROL + " TEXT UNIQUE, "
                    + COL_ALUMNO_NOMBRE_COMPLETO + " TEXT, "  // Nueva columna para el nombre completo
                    + COL_ALUMNO_OCULTO + " INTEGER)";
            db.execSQL(crearTablaAlumno);


        // Crear la tabla Materia
            String crearTablaMateria = "CREATE TABLE IF NOT EXISTS " + TABLE_MATERIA + " ("
                    + COL_MATERIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_MATERIA_CLAVE + " TEXT UNIQUE, "
                    + COL_MATERIA_NOMBRE + " TEXT, "
                    + COL_MATERIA_OCULTO + " INTEGER)";
            db.execSQL(crearTablaMateria);

        // Crear la tabla Asignacion sin la columna Materia_ID y la restricción FOREIGN KEY
        String crearTablaAsignacion = "CREATE TABLE IF NOT EXISTS " + TABLE_ASIGNACION + " ("
                + COL_ASIGNACION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ASIGNACION_FECHA + " TEXT, "
                + COL_ASIGNACION_NOMBRE + " TEXT, "
                + COL_ASIGNACION_OCULTO + " INTEGER)";
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
                + COL_ALUMNO_ASIGNACION_NOMBRE_COMPLETO + " INTEGER, " // Cambiado a Alumno_ID
                + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + " INTEGER, " // Cambiado a Asignacion_ID
                + COL_ALUMNO_ASIGNACION_MATERIA_ID + " INTEGER, " // Cambiado a Materia_ID
                + COL_ALUMNO_ASIGNACION_REALIZADA + " INTEGER, " // Asignacion Realizada
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

        /*// Insertar datos en la tabla Alumno
        String insertarAlumno1 = "INSERT INTO " + TABLE_ALUMNO + " (" + COL_ALUMNO_ID + ", "
                + COL_ALUMNO_NUM_CONTROL + ", " + COL_ALUMNO_NOMBRE_COMPLETO + ", " + COL_ALUMNO_OCULTO + ") " +
                "VALUES (10, '20130810', 'Efrain Montalvo Sanchez', 0)";
        db.execSQL(insertarAlumno1);

        String insertarAlumno2 = "INSERT INTO " + TABLE_ALUMNO + " (" + COL_ALUMNO_ID + ", "
                + COL_ALUMNO_NUM_CONTROL + ", " + COL_ALUMNO_NOMBRE_COMPLETO + ", " + COL_ALUMNO_OCULTO + ") " +
                "VALUES ( '20130770', 'Tomás Alejandro Galván Gándara', 0)";
        db.execSQL(insertarAlumno2);*/
/*

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
                + COL_ASIGNACION_FECHA + ", " + COL_ASIGNACION_NOMBRE + ", "
                + COL_ASIGNACION_OCULTO + ") VALUES (10, '2013-12-06', 'Tarea 10', 0)";
        db.execSQL(insertarAsignacion1);

        String insertarAsignacion2 = "INSERT INTO " + TABLE_ASIGNACION + " (" + COL_ASIGNACION_ID + ", "
                + COL_ASIGNACION_FECHA + ", " + COL_ASIGNACION_NOMBRE + ", "
                + COL_ASIGNACION_OCULTO + ") VALUES (11, '2013-12-06', 'Tarea 11', 0)";
        db.execSQL(insertarAsignacion2);

        String insertarAsignacion3 = "INSERT INTO " + TABLE_ASIGNACION + " (" + COL_ASIGNACION_ID + ", "
                + COL_ASIGNACION_FECHA + ", " + COL_ASIGNACION_NOMBRE + ", "
                + COL_ASIGNACION_OCULTO + ") VALUES (12, '2013-12-09', 'Asignacion 3 Materias', 0)";
        db.execSQL(insertarAsignacion3);

        // Insertar datos en la tabla Alumno_Materia
        String insertarAlumnoMateria = "INSERT INTO " + TABLE_ALUMNO_MATERIA + " (" + COL_ALUMNO_MATERIA_ID + ", "
                + COL_ALUMNO_MATERIA_ALUMNO_ID + ", " + COL_ALUMNO_MATERIA_MATERIA_ID + ") VALUES (1, 10, 10)";
        db.execSQL(insertarAlumnoMateria);

        // Insertar datos en la tabla Alumno_Asignacion
        String insertarAlumnoAsignacion = "INSERT INTO " + TABLE_ALUMNO_ASIGNACION + " (" + COL_ALUMNO_ASIGNACION_ID + ", "
                + COL_ALUMNO_ASIGNACION_ALUMNO_ID + ", " + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + ", " + COL_ALUMNO_ASIGNACION_REALIZADA + ")" +
                " VALUES (1, 10, 10, 0)";
        db.execSQL(insertarAlumnoAsignacion);

        String insertarAlumnoAsignacion2 = "INSERT INTO " + TABLE_ALUMNO_ASIGNACION + " (" + COL_ALUMNO_ASIGNACION_ID + ", "
                + COL_ALUMNO_ASIGNACION_ALUMNO_ID + ", " + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + ", " + COL_ALUMNO_ASIGNACION_REALIZADA + ")" +
                " VALUES (2, 11, 11, 1)";
        db.execSQL(insertarAlumnoAsignacion2);

        // Insertar datos en la tabla Asignacion_Materia
        String insertarAsignacionMateria = "INSERT INTO " + TABLE_ASIGNACION_MATERIA + " (" + COL_ASIGNACION_MATERIA_ID + ", "
                + COL_ASIGNACION_MATERIA_MATERIA_ID + ", " + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") VALUES (1, 10, 10)";
        db.execSQL(insertarAsignacionMateria);

        String insertarAsignacionMateria2 = "INSERT INTO " + TABLE_ASIGNACION_MATERIA + " (" + COL_ASIGNACION_MATERIA_ID + ", "
                + COL_ASIGNACION_MATERIA_MATERIA_ID + ", " + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") VALUES (2, 11, 11)";
        db.execSQL(insertarAsignacionMateria2);

        String insertarAsignacionMateria3 = "INSERT INTO " + TABLE_ASIGNACION_MATERIA + " (" + COL_ASIGNACION_MATERIA_ID + ", "
                + COL_ASIGNACION_MATERIA_MATERIA_ID + ", " + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") VALUES (3, 10, 12)";
        db.execSQL(insertarAsignacionMateria3);

        String insertarAsignacionMateria4 = "INSERT INTO " + TABLE_ASIGNACION_MATERIA + " (" + COL_ASIGNACION_MATERIA_ID + ", "
                + COL_ASIGNACION_MATERIA_MATERIA_ID + ", " + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") VALUES (4, 11, 12)";
        db.execSQL(insertarAsignacionMateria4);

        String insertarAsignacionMateria5 = "INSERT INTO " + TABLE_ASIGNACION_MATERIA + " (" + COL_ASIGNACION_MATERIA_ID + ", "
                + COL_ASIGNACION_MATERIA_MATERIA_ID + ", " + COL_ASIGNACION_MATERIA_ASIGNACION_ID + ") VALUES (5, 12, 12)";
        db.execSQL(insertarAsignacionMateria5);
*/

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
    public boolean addAlumno(String numControl, String nombreCompleto, int oculto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ALUMNO_NUM_CONTROL, numControl);
        contentValues.put(COL_ALUMNO_NOMBRE_COMPLETO, nombreCompleto);
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
    public boolean addAsignacion(String fecha, String nombre, int oculto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ASIGNACION_FECHA, fecha);
        contentValues.put(COL_ASIGNACION_NOMBRE, nombre);
        contentValues.put(COL_ASIGNACION_OCULTO, oculto);

        long resultado = db.insert(TABLE_ASIGNACION, null, contentValues);

        return resultado != -1;
    }

    public Cursor getAsignaciones() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ASIGNACION + " WHERE " + COL_ASIGNACION_OCULTO + " = 0 ORDER BY " + COL_ASIGNACION_NOMBRE + " ASC";
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

    public Cursor getAsignacionesPorMateria(int materiaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_ASIGNACION + "." + COL_ASIGNACION_ID + " AS _id, " +
                TABLE_ASIGNACION + "." + COL_ASIGNACION_NOMBRE + ", " +
                TABLE_ASIGNACION + "." + COL_ASIGNACION_FECHA +
                " FROM " + TABLE_ASIGNACION +
                " INNER JOIN " + TABLE_ASIGNACION_MATERIA +
                " ON " + TABLE_ASIGNACION + "." + COL_ASIGNACION_ID + " = " +
                TABLE_ASIGNACION_MATERIA + "." + COL_ASIGNACION_MATERIA_ASIGNACION_ID +
                " WHERE " + TABLE_ASIGNACION_MATERIA + "." + COL_ASIGNACION_MATERIA_MATERIA_ID + " = ?" +
                " AND " + TABLE_ASIGNACION + "." + COL_ASIGNACION_OCULTO + " = 0";
        return db.rawQuery(query, new String[]{String.valueOf(materiaId)});
    }


    //ERNESTO CHECKBOX
    public boolean addAlumno_Materia(String alumnoID, String materiaID){
        //Aqui esta la insercion de codigo en caso de ser nuevo
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ALUMNO_MATERIA_ALUMNO_ID, alumnoID);
        contentValues.put(COL_ALUMNO_MATERIA_MATERIA_ID, materiaID);
        //contentValues.put("Materia_ID", materiaID);
        //Toast.makeText(MainActivity.getContext(), "Se ha ejecutadop el texto", Toast.LENGTH_SHORT);

        long resultado = db.insert(TABLE_ALUMNO_MATERIA, null, contentValues);
        return resultado !=-1;


    }

    //    public Cursor getAlumno_Materia(int alumnoID, int materiaID){
//        String query = "SELECT "+
//    }
    public boolean deleteAlumno_Materia(String alumnoID, String materiaID){

        //Primero se comprueba si existe la tupla

        //Aqui esta la insercion de codigo en caso de ser nuevo
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_ALUMNO_MATERIA_ALUMNO_ID + " = " +
                alumnoID+ " AND " + COL_ALUMNO_MATERIA_MATERIA_ID
                + " = " + materiaID;
        String[] whereArgs = new String[]{String.valueOf(alumnoID), String.valueOf(materiaID)};

        long resultado = db.delete(TABLE_ALUMNO_MATERIA, whereClause, whereArgs);
        return resultado != -1;



    }
    public boolean existeAlumno_Materia(String alumnoID, String materiaID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_ALUMNO_MATERIA_ID + " FROM "+
                TABLE_ALUMNO_MATERIA + " WHERE " + "'" +alumnoID +"'"+ " = " +
                COL_ALUMNO_MATERIA_ALUMNO_ID + " AND " + materiaID + " = " +
                COL_ALUMNO_MATERIA_MATERIA_ID;

        Cursor cursor =db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }
    public String getAlumnoClave(String nombre, String numControl ){ //YA FUNCIONA!
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_ALUMNO_ID + " FROM " + TABLE_ALUMNO +
                " WHERE " + COL_ALUMNO_NOMBRE_COMPLETO + " LIKE " + "'" + nombre + "'" +
                " AND " + COL_ALUMNO_NUM_CONTROL + " LIKE " + "'" +numControl + "'" +
                " AND " + COL_ALUMNO_OCULTO + " = " + 0;
        Cursor cursor = db.rawQuery(query, null);
        String idFinal="";
        if(cursor.moveToFirst()){
            idFinal = cursor.getString(0);
        }
        return idFinal;
    }
    public String getColMateriaId(String nombreMateria){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT " + COL_MATERIA_ID + " FROM " + TABLE_MATERIA +
                " WHERE " + COL_MATERIA_NOMBRE + " LIKE " + "'"+nombreMateria +"'"+
                " AND "  + COL_ALUMNO_OCULTO + " = " + 0;
        Cursor cursor = db.rawQuery(query, null);
        String idFinal="";
        if(cursor.moveToFirst()){
            idFinal = cursor.getString(0);
        }
        return idFinal;
    }
    //ERNESTO CHECKBOX

    //ERNESTO NUEVO
    public String getColAsignacionId(String nombreAsignacion){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT " + COL_ASIGNACION_ID + " FROM " + TABLE_ASIGNACION +
                " WHERE " + COL_ASIGNACION_NOMBRE + " LIKE " + "'"+nombreAsignacion +"'"+
                " AND "  + COL_ALUMNO_OCULTO + " = " + 0;
        Cursor cursor = db.rawQuery(query, null);
        String idFinal="";
        if(cursor.moveToFirst()){
            idFinal = cursor.getString(0);
        }
        return idFinal;
    }

    public boolean addAsignacion_Materia(String asignacionID, String materiaID){
        //Aqui esta la insercion de codigo en caso de ser nuevo
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ASIGNACION_MATERIA_ASIGNACION_ID, asignacionID);
        contentValues.put(COL_ASIGNACION_MATERIA_MATERIA_ID, materiaID);
        //contentValues.put("Materia_ID", materiaID);
        //Toast.makeText(MainActivity.getContext(), "Se ha ejecutadop el texto", Toast.LENGTH_SHORT);

        long resultado = db.insert(TABLE_ASIGNACION_MATERIA, null, contentValues);
        return resultado !=-1;
    }

    //ERNESTO NUEVO

    public void borrarBaseDatos() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Elimina la base de datos
        context.deleteDatabase(DB_NAME);

        // Crea la base de datos de nuevo
        onCreate(db);
    }

    public boolean actualizarAsignacion(int idAsignacion, String nuevoNombre, String nuevaFecha) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ASIGNACION_NOMBRE, nuevoNombre);
        values.put(COL_ASIGNACION_FECHA, nuevaFecha);

        int filasAfectadas = db.update(TABLE_ASIGNACION, values, COL_ASIGNACION_ID + " = ?", new String[]{String.valueOf(idAsignacion)});
        db.close();

        return filasAfectadas > 0;
    }

    // Método para obtener el ID de la asignación por nombre y fecha
    public int getIdAsignacionPorNombreYFecha(String nombreAsignacion, String fechaAsignacion) {
        SQLiteDatabase db = this.getReadableDatabase();
        int idAsignacion = -1; // Valor predeterminado si no se encuentra la asignación

        String[] columnas = {COL_ASIGNACION_ID}; // Ajusta el nombre de la columna de ID según tu base de datos
        String seleccion = COL_ASIGNACION_NOMBRE + " = ? AND " + COL_ASIGNACION_FECHA + " = ?";
        String[] argumentos = {nombreAsignacion, fechaAsignacion};

        Cursor cursor = db.query(TABLE_ASIGNACION, columnas, seleccion, argumentos, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            idAsignacion = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ASIGNACION_ID));
            cursor.close();
        }

        return idAsignacion;
    }

    public Cursor getMateriasOrdenAlfab() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_MATERIA_ID + " as _id, " + COL_MATERIA_CLAVE + ", " + COL_MATERIA_NOMBRE + ", " + COL_MATERIA_OCULTO +
                " FROM " + TABLE_MATERIA + " ORDER BY " + COL_MATERIA_NOMBRE + " ASC";
        return db.rawQuery(query, null);
    }

    public Cursor getAlumnosPorMateria(String nombreMateria) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_ALUMNO + ".* FROM " + TABLE_ALUMNO +
                " INNER JOIN " + TABLE_ALUMNO_MATERIA +
                " ON " + TABLE_ALUMNO + "." + COL_ALUMNO_ID + " = " +
                TABLE_ALUMNO_MATERIA + "." + COL_ALUMNO_MATERIA_ALUMNO_ID +
                " INNER JOIN " + TABLE_MATERIA +
                " ON " + TABLE_ALUMNO_MATERIA + "." + COL_ALUMNO_MATERIA_MATERIA_ID + " = " +
                TABLE_MATERIA + "." + COL_MATERIA_ID +
                " WHERE " + TABLE_MATERIA + "." + COL_MATERIA_NOMBRE + " = ?" +
                " AND " + TABLE_ALUMNO + "." + COL_ALUMNO_OCULTO + " = 0";

        return db.rawQuery(query, new String[]{nombreMateria});
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALUMNO + " WHERE " + COL_ALUMNO_OCULTO + " = 0";
        return db.rawQuery(query, null);
    }

    public Cursor getAsignados(int asignacionID, int materiaID, int realizada) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_ALUMNO_ASIGNACION + ".*, " +
                TABLE_ALUMNO + "." + COL_ALUMNO_NOMBRE_COMPLETO + " AS " + "_nom" +
                " FROM " + TABLE_ALUMNO_ASIGNACION +
                " LEFT JOIN " + TABLE_ALUMNO +
                " ON " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_ALUMNO_ID + " = " +
                TABLE_ALUMNO + "." + COL_ALUMNO_ID +
                " WHERE " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + " = ? " +
                " AND " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_MATERIA_ID + " = ? " +
                " AND " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_REALIZADA + " = ?";

        String[] selectionArgs = {String.valueOf(asignacionID), String.valueOf(materiaID), String.valueOf(realizada)};

        return db.rawQuery(query, selectionArgs);
    }

/*    public Cursor getAsignados(int asignacionID, int realizada) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_ALUMNO_ASIGNACION + ".*, " +
                TABLE_ALUMNO + "." + COL_ALUMNO_NOMBRE_COMPLETO + " AS " + "_nom" +
                " FROM " + TABLE_ALUMNO_ASIGNACION +
                " LEFT JOIN " + TABLE_ALUMNO +
                " ON " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_ALUMNO_ID + " = " +
                TABLE_ALUMNO + "." + COL_ALUMNO_ID +
                " WHERE " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_ASIGNACION_ID + " = ? " +
                " AND " + TABLE_ALUMNO_ASIGNACION + "." + COL_ALUMNO_ASIGNACION_REALIZADA + " = ?";

        String[] selectionArgs = {String.valueOf(asignacionID), String.valueOf(realizada)};

        return db.rawQuery(query, selectionArgs);
    }*/

    public boolean addAlumno_Asignacion(String alumnoID, String NombreAlumnoAsignacion, String asignacionID, String materiaID, int realizada) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ALUMNO_ASIGNACION_ALUMNO_ID, alumnoID);
        contentValues.put(COL_ALUMNO_ASIGNACION_NOMBRE_COMPLETO, NombreAlumnoAsignacion);
        contentValues.put(COL_ALUMNO_ASIGNACION_ASIGNACION_ID, asignacionID);
        contentValues.put(COL_ALUMNO_ASIGNACION_MATERIA_ID, materiaID);
        contentValues.put(COL_ALUMNO_ASIGNACION_REALIZADA, realizada);

        long resultado = db.insert(TABLE_ALUMNO_ASIGNACION, null, contentValues);

        return resultado != -1;
    }

        public void actualizarAsignacionId(int materiaId, int asignacionId, String alumnoId) {
            SQLiteDatabase db = this.getWritableDatabase();

            // Crea la consulta de actualización
            String updateQuery = "UPDATE Alumno_Asignacion SET asignacion_id = ? WHERE materia_id = ? AND alumno_id = ?";

            // Crea el array de valores para la consulta de actualización
            String[] updateValues = {String.valueOf(asignacionId), String.valueOf(materiaId), String.valueOf(alumnoId)};

            // Ejecuta la consulta de actualización
            db.execSQL(updateQuery, updateValues);

            // Cierra la conexión a la base de datos
            //db.close();
        }




}
