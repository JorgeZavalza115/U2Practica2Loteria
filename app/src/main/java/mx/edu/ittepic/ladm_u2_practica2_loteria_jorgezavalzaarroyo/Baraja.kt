package mx.edu.ittepic.ladm_u2_practica2_loteria_jorgezavalzaarroyo

import android.app.AlertDialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.ImageView
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Baraja( ivb:ImageView, act:MainActivity ) : Thread() {
    val act = act
    val ivb = ivb
    private var loteria = false
    private var restantes = false
    private var victory = false
    private var reiniciar = false
    var mazo = ArrayList<Int>()
    var mazoAudio = ArrayList<Int>()
    var mediaPlayer : MediaPlayer? = null
    var corrutina : Job? = null
    var rand = 0

    val barajaFija = arrayOf (R.drawable.carta1, R.drawable.carta2, R.drawable.carta3, R.drawable.carta4, R.drawable.carta5, R.drawable.carta6, R.drawable.carta7, R.drawable.carta8, R.drawable.carta9, R.drawable.carta10,
        R.drawable.carta11, R.drawable.carta12, R.drawable.carta13, R.drawable.carta14, R.drawable.carta15, R.drawable.carta16, R.drawable.carta17, R.drawable.carta18, R.drawable.carta19, R.drawable.carta20,
        R.drawable.carta21, R.drawable.carta22, R.drawable.carta23, R.drawable.carta24, R.drawable.carta25, R.drawable.carta26, R.drawable.carta27, R.drawable.carta28, R.drawable.carta29, R.drawable.carta30,
        R.drawable.carta31, R.drawable.carta32, R.drawable.carta33, R.drawable.carta34, R.drawable.carta35, R.drawable.carta36, R.drawable.carta37, R.drawable.carta38, R.drawable.carta39, R.drawable.carta40,
        R.drawable.carta41, R.drawable.carta42, R.drawable.carta43, R.drawable.carta44, R.drawable.carta45, R.drawable.carta46, R.drawable.carta47, R.drawable.carta48, R.drawable.carta49, R.drawable.carta50,
        R.drawable.carta51, R.drawable.carta52, R.drawable.carta53, R.drawable.carta54)

    val barajaAudio = arrayOf(R.raw.a1gallo, R.raw.a2diablito, R.raw.a3dama, R.raw.a4catrin, R.raw.a5paraguas, R.raw.a6sirena, R.raw.a7escalera, R.raw.a8botella, R.raw.a9barril, R.raw.a10arbol,
        R.raw.a11melon, R.raw.a12valiente, R.raw.a13gorrito, R.raw.a14muerte, R.raw.a15pera, R.raw.a16bandera, R.raw.a17bandolon, R.raw.a18violoncello, R.raw.a19garza,  R.raw.a20pajaro,
        R.raw.a21mano, R.raw.a22bota, R.raw.a23luna, R.raw.a24cotorro, R.raw.a25borracho, R.raw.a26negrito, R.raw.a27corazon, R.raw.a28sandia, R.raw.a29tambor, R.raw.a30camaron,
        R.raw.a31jaras, R.raw.a32musico, R.raw.a33arana, R.raw.a34soldado, R.raw.a35estrella, R.raw.a36cazo, R.raw.a37mundo, R.raw.a38apache, R.raw.a39nopal, R.raw.a40alacran,
        R.raw.a41rosa, R.raw.a42calavera, R.raw.a43campana, R.raw.a44cantarito, R.raw.a45venado, R.raw.a46sol, R.raw.a47corona, R.raw.a48chalupa, R.raw.a49pino, R.raw.a50pescado,
        R.raw.a51palma, R.raw.a52maceta, R.raw.a53arpa, R.raw.a54rana)

    override fun run() {
        super.run()

        while ( true ) {
            reiniciar = false
            // Fase de preparación
            for (x in barajaFija) {
                mazo.add(x)
            }
            for (x in barajaAudio) {
                mazoAudio.add(x)
            }

            var tope = 54

            // Fase de juego
            while (!loteria) {
                if ( mazo.size == 0 ) {
                    loteria = true
                    break
                }
                rand = Random.nextInt(tope)
                act.runOnUiThread {
                    ivb.setImageResource(mazo[rand])
                }
                corrutina = GlobalScope.launch {
                    playAudio(rand)
                }
                sleep(3000)
                mazo.removeAt(rand)
                mazoAudio.removeAt(rand)
                tope--
            }

            // Fase de victoria
            act.runOnUiThread {
                ivb.setImageResource(R.drawable.winner)
                try {
                    mediaPlayer = MediaPlayer.create(act, R.raw.victoria)
                    mediaPlayer?.start()
                } catch ( e:Exception) {
                    AlertDialog.Builder(act)
                        .setMessage( e.message )
                        .show()
                }
            }
            while ( !victory ) {  }

            // Fase de revisión
            while ( !restantes ) {
                if ( mazo.size == 0 ) {
                    restantes = true
                    break
                }
                rand = Random.nextInt( tope )
                act.runOnUiThread {
                    ivb.setImageResource(mazo[rand])
                }
                corrutina = GlobalScope.launch {
                    playAudio(rand)
                }
                sleep(3000)
                mazo.removeAt(rand)
                mazoAudio.removeAt(rand)
                tope--
            }

            // Fase de finalización
            act.runOnUiThread {
                ivb.setImageResource(R.drawable.rollback)
            }
            while ( !reiniciar ) {  }
        }
    }

    fun cantoLoteria() {
        loteria = true
    }

    fun finVictoria() {
        victory = true
    }

    fun finalizar() {
        restantes = true
    }

    fun reiniciar() {
        loteria = false
        restantes = false
        victory = false
        reiniciar = true
    }

    fun playAudio( ind:Int ) {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer = MediaPlayer.create(act, mazoAudio[ind])
            mediaPlayer?.start()
        } catch ( e:Exception) {
            AlertDialog.Builder(act)
                .setMessage( e.message )
                .show()
        }
    }
}