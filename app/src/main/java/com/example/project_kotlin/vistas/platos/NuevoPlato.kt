package com.example.project_kotlin.vistas.platos

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import com.example.project_kotlin.R
import java.io.ByteArrayOutputStream
import com.example.project_kotlin.dao.PlatoDao
import com.example.project_kotlin.utils.appConfig
import android.provider.MediaStore
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.CategoriaPlato
import com.example.project_kotlin.entidades.Plato

@Suppress("DEPRECATION")
class NuevoPlato : AppCompatActivity() {

    private lateinit var edtNombrePlato:EditText
    private lateinit var edtPrecioPlato:EditText
    private lateinit var btnImagen:Button
    private lateinit var spCategoria:Spinner
    private lateinit var btnAgregarplato:Button
    private lateinit var btnCancelar:Button
    private lateinit var platoDao:PlatoDao

    private var estadoCatFiltro : String = "Seleccionar Categoria"
    private val PICK_IMAGE_REQUEST = 1
    private var imageData: ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.platoregistrar)

        edtNombrePlato= findViewById(R.id.edtNombrePlato)
        edtPrecioPlato= findViewById(R.id.edtPrecioPlato)
        btnImagen= findViewById(R.id.btnSubirImagen)
        spCategoria=findViewById(R.id.spCategoria)
        btnAgregarplato=findViewById(R.id.btnAgregarPlato)
        btnCancelar=findViewById(R.id.btnCancelar)
        platoDao = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).platoDao()

        cargarCategoria()


        btnAgregarplato.setOnClickListener({Agregar()})

        btnImagen.setOnClickListener {
            // Lanzar la selección de imágenes desde la galería
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnCancelar.setOnClickListener({Cancelar()})


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Obtener la imagen seleccionada
            val imageUri = data.data
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            // Convertir la imagen en un ByteArray
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            imageData = outputStream.toByteArray()

            // Mostrar la imagen seleccionada en una ImageView (opcional)
            val imageView = findViewById<ImageView>(R.id.imagenCrear)
            imageView.setImageBitmap(bitmap)
        }
    }

    fun Agregar() {
        if (validarCampos()) {
         if (imageData != null ) {

                lifecycleScope.launch(Dispatchers.IO) {
                    val codigo = Plato.generarCodigo(platoDao.obtenerTodo())
                    val nombre = edtNombrePlato.text.toString()
                    val precio = edtPrecioPlato.text.toString().toDouble()
                    val codCatPlato = (spCategoria.selectedItemPosition).toString()
                    val cat = "C-00" + codCatPlato
                    System.out.println(cat)
                    val nombrecat = spCategoria.selectedItem.toString()

                    val bean = Plato(
                        id = codigo,
                        nombrePlato = nombre,
                        precioPlato = precio,
                        nombreImagen = imageData!!
                    )

                    bean.categoriaPlato = CategoriaPlato(cat, nombrecat)

                    platoDao.guardar(bean)
                    mostrarToast("Plato agregado correctamente")
                    Cancelar()
                }
            } else {
                mostrarToast("Seleccione una imagen primero")
            }
        }
    }

    private fun Cancelar() {
        val intent = Intent(this, DatosPlatos::class.java)
        startActivity(intent)
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarCategoria() {
        lifecycleScope.launch(Dispatchers.IO) {

            // Obtén la lista de categorías de platos desde la base de datos
            var data = ComandaDatabase.obtenerBaseDatos(appConfig.CONTEXT).categoriaPlatoDao().obtenerTodo()

            var nombreCate =   data.map {  it.categoria }

            //Opcion por defecto
            val opciones = mutableListOf<String>()
            opciones.add("Seleccionar Categoria")
            opciones.addAll(nombreCate)

            // Crea un ArrayAdapter con los nombres de las categorías de platos
            var adapter = ArrayAdapter(
                this@NuevoPlato,
                android.R.layout.simple_spinner_item,opciones

            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Asigna el adaptador al Spinner
            spCategoria.adapter = adapter
        }
    }

    fun validarCampos() : Boolean{
        val nombre = edtNombrePlato.text.toString()
        val precio = edtPrecioPlato.text.toString()
        val spcat = spCategoria.selectedItem
        val REGEX_NOMBRE = "^[A-Z][a-zA-Z\\s]*\$"
        val REGEX_PRECIO = "^[\\d]{1,3}(?:,[\\d]{3})*(?:\\.[\\d]{1,2})?\$"

        if (!REGEX_NOMBRE.toRegex().matches(nombre)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("Ingrese un nombre iniciado por Mayuscula la primera letra")
            return false
        }
        if (!REGEX_PRECIO.toRegex().matches(precio)) {
            // El campo nombre no cumple con el formato esperado
            mostrarToast("Ingrese un precio no maximo de 999.99")
            return false
        }
        if (spcat == estadoCatFiltro) {
            mostrarToast("Seleccione una categoría")
            return false
        }


        return true
    }
}
