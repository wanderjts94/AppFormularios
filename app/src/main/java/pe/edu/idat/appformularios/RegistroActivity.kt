package pe.edu.idat.appformularios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import pe.edu.idat.appformularios.comunes.AppMensaje
import pe.edu.idat.appformularios.comunes.TipoMensaje
import pe.edu.idat.appformularios.databinding.ActivityRegistroBinding
import java.util.ArrayList

class RegistroActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityRegistroBinding
    private var estadocivil=""
    private val listaPreferencias = ArrayList<String>()
    private val listapersonas= ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("MensajeInfo","App inicializar")
        binding.btnlistar.setOnClickListener(this)
        binding.btnregistrar.setOnClickListener(this)
        ArrayAdapter.createFromResource(
            this, R.array.estado_civil,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spestadocivil.adapter = adapter
        }
        binding.spestadocivil.onItemSelectedListener = this
        binding.cbdeportes.setOnClickListener(this)
        binding.cbmusica.setOnClickListener(this)
        binding.cbotros.setOnClickListener(this)
    }
    override fun onClick(vista: View) {
        if (vista is CheckBox) {
            agregarQuitarPreferenciasSeleccionada(vista)
        } else {
            when (vista.id) {
                R.id.btnregistrar -> registrarPersona()
                R.id.btnlistar -> startActivity(Intent(applicationContext, ListadoActivity::class.java).apply {
                    putExtra("listapersonas", listapersonas)
                })
            }
        }
    }

    private fun agregarQuitarPreferenciasSeleccionada(vista: CheckBox) {

        if(vista.isChecked) {
            when (vista.id) {
                R.id.cbdeportes -> listaPreferencias.add(vista.text.toString())
                R.id.cbmusica -> listaPreferencias.add(vista.text.toString())
                R.id.cbotros -> listaPreferencias.add(vista.text.toString())
            }
        }else{
                when(vista.id){
                    R.id.cbdeportes -> listaPreferencias.remove(vista.text.toString())
                    R.id.cbmusica -> listaPreferencias.remove(vista.text.toString())
                    R.id.cbotros -> listaPreferencias.remove(vista.text.toString())
                }
            }
        }


    fun registrarPersona(){
      if (validarformulario()){
          val infopersona= binding.etnombres.text.toString()+" "+
                  binding.etapellidos.text.toString()+ " " +
                  obtenerGeneroSeleccionado()+ " " +
                  /* listaPreferencias.toArray() + " " + */
                  obtenerpreferencia() + " " +
                  estadocivil + " " +
                  binding.swnotificacion.isChecked
          listapersonas.add(infopersona)
          AppMensaje.enviarmensaje(binding.root,getString(R.string.mensajeRegiscorrecto),TipoMensaje.SUCCESSFULL)
          stearControles()
      }
    }

    private fun obtenerpreferencia():String{
        var preferencia=""
        for(pref in listaPreferencias){
            preferencia+= "$pref -"
        }
        return preferencia

    }
    private fun stearControles() {
        listaPreferencias.clear()
        binding.etnombres.setText("")
        binding.etapellidos.setText("")
        binding.swnotificacion.isChecked = false
        binding.cbdeportes.isChecked=false
        binding.cbmusica.isChecked=false
        binding.cbotros.isChecked=false
        binding.rggenero.clearCheck()
        binding.spestadocivil.setSelection(0)
        binding.etnombres.isFocusableInTouchMode=true
        binding.etnombres.requestFocus()
    }

    fun validarPreferencias():Boolean{
        var repuesta=true
        if(binding.cbdeportes.isChecked || binding.cbmusica.isChecked || binding.cbotros.isChecked){
            repuesta=true
        }
           return repuesta
    }



    fun validadGenero():Boolean{
        var repuesta=true
        if (binding.rggenero.checkedRadioButtonId==-1){
            repuesta=false
        }
        return repuesta

    }
    fun validarnombreapp():Boolean{
        var respuesta=true
        if(binding.etnombres.text.toString().trim().isEmpty()){

            binding.etnombres.isFocusableInTouchMode = true
            binding.etnombres.requestFocus()
            respuesta=false
        }else if(binding.etapellidos.text.toString().trim().isEmpty()) {
            binding.etapellidos.isFocusableInTouchMode = true
            binding.etapellidos.requestFocus()
            respuesta = false
        }
        return respuesta
    }
    fun estadocivil():Boolean{
return estadocivil!="" }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        estadocivil = if (position > 0) {
            parent?.getItemAtPosition(position).toString()
        } else {
            ""
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun validarformulario(): Boolean {
        var respuesta = false
        if (!validarnombreapp()) {
            AppMensaje.enviarmensaje(binding.root, "Ingrese Nombre y Apellido", TipoMensaje.ERROR)
        }
        else if (!validadGenero()) {
            AppMensaje.enviarmensaje(binding.root, "Ingrese genero", TipoMensaje.ERROR)
        }
        else if (!validarPreferencias()) {
            AppMensaje.enviarmensaje(binding.root, "Ingrese preferencia", TipoMensaje.ERROR)
        }
        else if (!estadocivil()) {
            AppMensaje.enviarmensaje(binding.root, "Ingrese estado civil", TipoMensaje.ERROR)
        }
        else {
            respuesta = true
        }
        return respuesta
    }

    fun obtenerGeneroSeleccionado():String {
        var genero = ""
        when (binding.rggenero.checkedRadioButtonId) {
            R.id.rbmasculino -> {
                genero = binding.rbmasculino.text.toString()
            }

            R.id.rbfemenino -> {
                genero = binding.rbfemenino.text.toString()
            }
        }
        return genero
    }
}