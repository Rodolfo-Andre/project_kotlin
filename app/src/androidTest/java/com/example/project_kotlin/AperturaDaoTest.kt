package com.example.project_kotlin

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_kotlin.dao.AperturaDao
import com.example.project_kotlin.dao.CajaDao
import com.example.project_kotlin.dao.CategoriaPlatoDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Apertura
import com.example.project_kotlin.entidades.Caja
import com.example.project_kotlin.entidades.CategoriaPlato
import com.example.project_kotlin.entidades.Mesa
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AperturaDaoTest {
    private lateinit var aperturaDao: AperturaDao
    private lateinit var cajaDao: CajaDao
    private lateinit var categoriaPlatoDao: CategoriaPlatoDao
    private lateinit var db: ComandaDatabase

    @Before
    fun crearDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ComandaDatabase::class.java).build()
        aperturaDao = db.aperturaDao()
        categoriaPlatoDao = db.categoriaPlatoDao()
        cajaDao = db.cajaDao()
    }

    @After
    @Throws(IOException::class)
    fun cerrarDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun registrarCajaAperturaLeerCajaConApertura() {
        var caja = Caja()
        var cajaId = cajaDao.guardar(caja)

        var apertura = Apertura(cajaId = cajaId.toInt())
        var id = aperturaDao.guardar(apertura)

        var aperturaBD = aperturaDao.obtenerPorId(id)

        System.out.println(cajaDao.obtenerCajaConAperturas())
    }
}