package mx.edu.ittepic.ladm_u3_ejercicio1_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        /*
        Se ejecuta cuando se  EJECUTA x PRIMERA VEZ" la app
        dentro del celular del cleinte y construye la base de datos y las tablas
         */

        db.execSQL("CREATE TABLE PERSONA(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOMBRE VARCHAR(400),DOMICILIO VARCHAR(300), SUELDO FLOAT)")

    /* db.execSQL("CREATE TABLE PERSOMA()")
        db.execSQL("INSERT INTO PERSONA VALUES()")
        db.rawQuery()

        db.insert()
        db.query()
        db.update()
        db.delete()*/

    }

    override fun onUpgrade(p0: SQLiteDatabase, anterior: Int, nueva: Int) {
        /*
        Se ejecuta SI y SOLO SI hay un cambio de versión
        la version utiliza los números naturales (1...)
        y se spera que a un cambio de versión uses un número mayor
        que el actual
         */

    }
}