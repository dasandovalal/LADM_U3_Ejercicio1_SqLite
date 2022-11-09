package mx.edu.ittepic.ladm_u3_ejercicio1_sqlite

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    val baseDatos = BaseDatos(this, "Ejemplo1",
    null,1)
    var IDs = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mostrarTodos()

        insertar.setOnClickListener {
            var persona = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("NOMBRE",nombre.text.toString())
            datos.put("DOMICILIO",domicilio.text.toString())
            datos.put("SUELDO",sueldo.text.toString().toFloat())

            var resultado = persona.insert("PERSONA","ID",datos)
            if (resultado == -1L){
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO GUARDAR").show()
            }else{
                Toast.makeText(this,"Se INSERTO CON EXITO", Toast.LENGTH_LONG).show()
                nombre.text.clear()
                domicilio.text.clear()
                sueldo.text.clear()
                mostrarTodos()
            }
        }
    }

    fun mostrarTodos() {

        var persona = baseDatos.readableDatabase
        val lista = ArrayList<String>()
        IDs.clear()
        var resultado = persona.query("PERSONA", arrayOf("*"), null,
            null, null, null, null)

        if (resultado.moveToFirst()) {

            do {
                val data = resultado.getString(1) + "\n" + resultado.getString(2) + "\n$ " +
                        resultado.getFloat(3)
                lista.add(data)
                IDs.add(resultado.getInt(0))
            } while (resultado.moveToNext())

        } else {
            lista.add("LA TABLA ESTA VACIA")
        }

        listaPersonas.adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, lista)

        listaPersonas.setOnItemClickListener{ adapterView, view, i, l ->
            val idSeleccionado = IDs.get(i)

            var nombre = lista.get(idSeleccionado-1)
            nombre = nombre.substring(0,nombre.indexOf("\n")).uppercase()

            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("QUE DESEAS HACER CON: ${nombre}?")
                .setPositiveButton("NADA"){d,i->}
                .setNegativeButton("ELIMINAR"){d,i->
                    eliminar(idSeleccionado)
                }
                .setNeutralButton("ACTUALIZAR"){d,i->
                    actualizar(idSeleccionado)
                }
                .show()
        }
    }

    private fun eliminar(idSeleccionado: Int) {
       val resultado =  baseDatos.writableDatabase.delete("PERSONA",
        "ID=?", arrayOf(idSeleccionado.toString()))

        if (resultado==0){
            AlertDialog.Builder(this)
                .setMessage("ERROR NO SE BORRO")
                .show()
        }else{
            Toast.makeText(this,"SE BORRO CON EXITO",Toast.LENGTH_LONG).show()
            mostrarTodos()
        }

    }

    private fun actualizar(idSeleccionado: Int){
        val otraVentana = Intent(this,MainActivity2::class.java)
        otraVentana.putExtra("idSeleccionado",idSeleccionado.toString())
        startActivity(otraVentana)
    }

    override fun onRestart() {
        super.onRestart()
        mostrarTodos()
    }
}