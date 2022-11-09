package mx.edu.ittepic.ladm_u3_ejercicio1_sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

   val baseDatos = BaseDatos(this, "Ejemplo1",null,1)
   var idSeleccionado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        idSeleccionado = intent.extras!!.getString("idSeleccionado")!!

        val cursor = baseDatos.readableDatabase.query("PERSONA", arrayOf("*"),"ID=?",
            arrayOf(idSeleccionado),null,null,null)

        if (cursor.moveToFirst()){
            act_nombre.setText(cursor.getString(1))
            act_domicilio.setText(cursor.getString(2))
            act_sueldo.setText(cursor.getFloat(3).toString())
        }else{
            act_nombre.setText("NO HAY DATOS")
            act_domicilio.setText("NO HAY DOMICILIO")
            act_sueldo.setText("NO HAY SUELDO")
            actualizar.isEnabled = false
            act_nombre.isEnabled = false
            act_domicilio.isEnabled = false
            act_sueldo.isEnabled = false
        }

        actualizar.setOnClickListener {
            actualizar()
        }

        regresar.setOnClickListener {
            finish()
        }
    }

    fun actualizar(){
        val datos = ContentValues()

        datos.put("NOMBRE",act_nombre.text.toString())
        datos.put("DOMICILIO",act_domicilio.text.toString())
        datos.put("SUELDO",act_sueldo.text.toString().toFloat())
        val res = baseDatos.writableDatabase.update("PERSONA",datos, "ID=?",
        arrayOf(idSeleccionado))
        if (res==0){
            AlertDialog.Builder(this)
                .setMessage("NO SE PUDO ACTUALIZAR")
                .show()
        }else{
            Toast.makeText(this,"EXITO!",Toast.LENGTH_LONG).show()
            actualizar.isEnabled = false
            actualizar.setText("QUEDO ACTUALIZADO")
            act_nombre.isEnabled = false
            act_domicilio.isEnabled = false
            act_sueldo.isEnabled = false
        }
    }
}