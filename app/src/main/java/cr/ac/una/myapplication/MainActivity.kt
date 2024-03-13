package cr.ac.una.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    lateinit var jugar: Button
    lateinit var pos1: ImageButton
    lateinit var pos2: ImageButton
    lateinit var pos3: ImageButton
    lateinit var pos4: ImageButton
    lateinit var pos5: ImageButton
    lateinit var pos6: ImageButton
    lateinit var pos7: ImageButton
    lateinit var pos8: ImageButton
    lateinit var pos9: ImageButton
    var juegoService = JuegoService()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jugar = findViewById(R.id.jugar)
        jugar.isEnabled = true

        pos1 = findViewById(R.id.pos1)
        pos2 = findViewById(R.id.pos2)
        pos3 = findViewById(R.id.pos3)
        pos4 = findViewById(R.id.pos4)
        pos5 = findViewById(R.id.pos5)
        pos6 = findViewById(R.id.pos6)
        pos7 = findViewById(R.id.pos7)
        pos8 = findViewById(R.id.pos8)
        pos9 = findViewById(R.id.pos9)

        jugar.setOnClickListener() {
            enableDisableButton()
            juegoService.inicializar()
        }

        pos1.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(0, 0)
            val estadoJuego = juegoService.obtenerEstadoJuego()


            muestraDialogo(resultado)

        }

        pos2.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(0, 1)
            val estadoJuego = juegoService.obtenerEstadoJuego()

            muestraDialogo(resultado)

        }

        pos3.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(0, 2)

            muestraDialogo(resultado)

        }

        pos4.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(1, 0)

            muestraDialogo(resultado)

        }

        pos5.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(1, 1)

            muestraDialogo(resultado)

        }

        pos6.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(1, 2)

            muestraDialogo(resultado)

        }

        pos7.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(2, 0)

            muestraDialogo(resultado)

        }

        pos8.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(2, 1)

            muestraDialogo(resultado)

        }

        pos9.setOnClickListener() {
            seleccionaFigura(it as ImageButton)
            val resultado = juegoService.jugada(2, 2)
            val estadoJuego = juegoService.obtenerEstadoJuego()
            if (estadoJuego.equals("Empate") or estadoJuego.equals("gana")) {
                verificarGanador(resultado)
            } else {
                muestraDialogo(resultado)
            }
        }

        enableDisableButton()
    }

    private fun enableDisableButton() {
        pos1.isEnabled = !pos1.isEnabled
        pos2.isEnabled = !pos2.isEnabled
        pos3.isEnabled = !pos3.isEnabled
        pos4.isEnabled = !pos4.isEnabled
        pos5.isEnabled = !pos5.isEnabled
        pos6.isEnabled = !pos6.isEnabled
        pos7.isEnabled = !pos7.isEnabled
        pos8.isEnabled = !pos8.isEnabled
        pos9.isEnabled = !pos9.isEnabled
    }

    private fun seleccionaFigura(imageButton: ImageButton) {
        if (juegoService.figura == 'O')
            imageButton.setBackgroundResource(R.drawable.circulo)
        else
            imageButton.setBackgroundResource(R.drawable.cruz)
        imageButton.isEnabled = false
    }

    private fun muestraDialogo(mensaje: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Aviso")
            .setTitle(mensaje)
            .setPositiveButton("¡Entendido!") { dialog, which ->
                val resultado = juegoService.obtenerEstadoJuego()

                if (resultado.startsWith("¡Ganador:") || resultado == "Empate") {
                    val lineaGanadora = juegoService.obtenerLineaGanadora()

                    val felicitaciones = if (resultado.startsWith("¡Ganador:")) {
                        "$resultado\n¡Felicidades, ganador!"
                    } else {
                        resultado
                    }

                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)

                    builder.setMessage(felicitaciones)
                        .setTitle("¡Fin del juego!")
                        .setPositiveButton("Reiniciar") { dialog, which ->
                            enableDisableButton()
                            reiniciarJuego()
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    // Resaltar la línea ganadora
                    if (lineaGanadora != null) {
                        resaltarLineaGanadora(lineaGanadora)
                    }
                }
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun verificarGanador(resultado: String) {
        if (juegoService.hayGanador()) {
            val lineaGanadora = juegoService.obtenerLineaGanadora()
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val mensaje = "$resultado\n¡Felicidades, ganador!"

            builder.setMessage(mensaje)
                .setTitle("¡Fin del juego!")
                .setPositiveButton("Reiniciar") { dialog, which ->
                    enableDisableButton()
                    reiniciarJuego()
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            // Resaltar la línea ganadora
            if (lineaGanadora != null) {
                resaltarLineaGanadora(lineaGanadora)
            }
        }
    }

    private fun resaltarLineaGanadora(posiciones: List<Pair<Int, Int>>) {
        val ganador = juegoService.figura

        for ((fila, columna) in posiciones) {
            when (fila * 3 + columna) {
                0 -> pos1.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                1 -> pos2.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                2 -> pos3.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                3 -> pos4.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                4 -> pos5.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                5 -> pos6.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                6 -> pos7.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                7 -> pos8.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
                8 -> pos9.setImageResource(if (ganador == 'O') R.drawable.circulo_rojo else R.drawable.cruz_roja)
            }
        }

        // Desactivar botones después de resaltar la línea ganadora
        enableDisableButton()
    }

    private fun reiniciarJuego() {
        juegoService.inicializar()

        pos1.isEnabled = false
        pos2.isEnabled = false
        pos3.isEnabled = false
        pos4.isEnabled = false
        pos5.isEnabled = false
        pos6.isEnabled = false
        pos7.isEnabled = false
        pos8.isEnabled = false
        pos9.isEnabled = false

        pos1.setBackgroundResource(R.drawable.limpio)
        pos2.setBackgroundResource(R.drawable.limpio)
        pos3.setBackgroundResource(R.drawable.limpio)
        pos4.setBackgroundResource(R.drawable.limpio)
        pos5.setBackgroundResource(R.drawable.limpio)
        pos6.setBackgroundResource(R.drawable.limpio)
        pos7.setBackgroundResource(R.drawable.limpio)
        pos8.setBackgroundResource(R.drawable.limpio)
        pos9.setBackgroundResource(R.drawable.limpio)

        enableDisableButton()
    }
}

