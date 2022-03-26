
package mx.edu.ittepic.ladm_u2_practica2_loteria_jorgezavalzaarroyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.ittepic.ladm_u2_practica2_loteria_jorgezavalzaarroyo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var count = 0
        binding.ivbaraja.setImageResource(R.drawable.loteria)

        var hilo = Baraja(binding.ivbaraja, this)

        binding.acciones.setOnClickListener {
            when (count) {
                // Iniciar el hilo del juego
                0 -> {
                    hilo.start()
                    count++
                    return@setOnClickListener
                }

                // Fase de victoria
                1 -> {
                    hilo.cantoLoteria()
                    binding.acciones.text = "Revisar"
                    count++
                    return@setOnClickListener
                }

                // Fase de revisión
                2 -> {
                    hilo.finVictoria()
                    binding.acciones.text = "Finalizar"
                    count++
                    return@setOnClickListener
                }

                // Dejar de revisar y Finalizar
                3 -> {
                    hilo.finalizar()
                    binding.acciones.text = "Reiniciar"
                    count++
                    return@setOnClickListener
                }

                // Reiniciar
                4 -> {
                    count = 1
                    hilo.reiniciar()
                    binding.acciones.text = "Loteria"
                }
            }
        }
    }
}