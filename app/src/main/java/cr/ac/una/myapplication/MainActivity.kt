package cr.ac.una.myapplication

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class MainActivity : AppCompatActivity() {
    lateinit var jugar :Button
    lateinit var pos1: ImageButton
    lateinit var pos2: ImageButton
    lateinit var pos3: ImageButton
    lateinit var pos4: ImageButton
    lateinit var pos5: ImageButton
    lateinit var pos6: ImageButton
    var juegoService = JuegoService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jugar = findViewById(R.id.jugar)

        pos1 = findViewById(R.id.pos1)
        pos2 = findViewById(R.id.pos2)
        pos3 = findViewById(R.id.pos3)
        pos4 = findViewById(R.id.pos4)
        pos5 = findViewById(R.id.pos5)
        pos6 = findViewById(R.id.pos6)

        jugar.setOnClickListener() {
            enableDisableButton()
        }
        pos1.setOnClickListener(){
            seleccionafigura(it as ImageButton)
            muestraDialogo (juegoService.jugada(0,0))

        }
        pos2.setOnClickListener(){
            seleccionafigura(it as ImageButton)
            muestraDialogo (juegoService.jugada(0,0))

        }
        enableDisableButton()
    }

    private fun enableDisableButton(){
        pos1.isEnabled =  !pos1.isEnabled
        pos2.isEnabled =  !pos2.isEnabled
        pos3.isEnabled =  !pos3.isEnabled
        pos4.isEnabled =  !pos4.isEnabled
        pos5.isEnabled =  !pos5.isEnabled
        pos6.isEnabled =  !pos6.isEnabled
    }

    private fun seleccionafigura(imageButton: ImageButton){
        if (juegoService.figura == 'O')
            imageButton.setBackgroundResource(R.drawable.circulo)
        else
            imageButton.setBackgroundResource(R.drawable.cruz)
        imageButton.isEnabled=false
    }
    private fun muestraDialogo(mensaje: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Aviso")
            .setTitle(mensaje)
            .setPositiveButton("¡Entendido!") { dialog, which ->
                // Do something.
            }/*
            .setNegativeButton("Negative") { dialog, which ->
                // Do something else.
            }*/

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
