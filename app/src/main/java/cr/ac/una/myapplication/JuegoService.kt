package cr.ac.una.myapplication

class JuegoService  {
    val matriz = Array(3) { CharArray(3) }
    var figura: Char = 'X'

    /**
     * @return X gana, O gana, Empate, Sigue X, Sigue O
     */
    fun jugada(x:Int, y:Int): String{
        matriz[x][y]= figura
        if (figura == 'X') figura = 'O' else figura = 'X'

        val estadoJuego = obtenerEstadoJuego()
        if (estadoJuego != "") {
            return estadoJuego // Si hay un ganador, devuelve el mensaje correspondiente.
        }

        return "Turno de $figura"
    }

    fun inicializar(){
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                matriz[i][j] = ' '
            }
        }
    }

    fun obtenerEstadoJuego():String {

        for (i in 0 until 3) {
            if (matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2] && matriz[i][0] != ' ') {
                return "${matriz[i][0]} gana"
            }
            if (matriz[0][i] == matriz[1][i] && matriz[1][i] == matriz[2][i] && matriz[0][i] != ' ') {
                return "${matriz[0][i]} gana"
            }
        }


        if (matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2] && matriz[0][0] != ' ') {
            return "${matriz[0][0]} gana"
        }
        if (matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0] && matriz[0][2] != ' ') {
            return "${matriz[0][2]} gana"
        }

        // Verificar empate
        if (esEmpate()) {
            return "Empate"
        }

        // El juego sigue
        return "Sigue ${figura}"
    }

    private fun esEmpate(): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (matriz[i][j] == ' ') {
                    return false
                }
            }
        }
        return true
    }

    fun hayGanador(): Boolean {
        val estadoJuego = obtenerEstadoJuego()
        return estadoJuego.startsWith("¡Ganador:")
    }

    fun obtenerLineaGanadora(): List<Pair<Int, Int>>? {
        val estadoJuego = obtenerEstadoJuego()
        if (hayGanador()) {
            val partes = estadoJuego.split(" ")
            val fila = partes[2].toInt()
            val columna = partes[3].toInt()

            // Devolver las posiciones de la línea ganadora
            return when (partes[1]) {
                "fila" -> listOf(Pair(fila, 0), Pair(fila, 1), Pair(fila, 2))
                "columna" -> listOf(Pair(0, columna), Pair(1, columna), Pair(2, columna))
                "diagonal" -> listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2))
                "antidiagonal" -> listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
                else -> null
            }
        }
        return null
    }

}
