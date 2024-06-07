package com.example.sqllite

import android.app.DownloadManager.Query
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sqllite.ui.theme.SQLLiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome: EditText = findViewById(R.id.edtNome)
        val edtEndereco: EditText = findViewById(R.id.edtEndereco)
        val edtBairro: EditText = findViewById(R.id.edtBairro)
        val edtCep: EditText = findViewById(R.id.edtCep)
        val edtTelefone: EditText = findViewById(R.id.edtTelefone)
        val btnCadastrar: Button = findViewById(R.id.btnCadastrar)

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, RespostaActivity::class.java)
            intent.putExtra("nome:", edtNome.text.toString())
            intent.putExtra("endereco:", edtEndereco.text.toString())
            intent.putExtra("bairro:", edtBairro.text.toString())
            intent.putExtra("cep:", edtCep.text.toString())
            intent.putExtra("telefone:", edtTelefone.text.toString())
            startActivity(intent)
        }
    }
}

class RespostaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resposta)

        val edtNome: EditText = findViewById(R.id.edtNome)
        val edtEndereco: EditText = findViewById(R.id.edtEndereco)
        val edtBairro: EditText = findViewById(R.id.edtBairro)
        val edtCep: EditText = findViewById(R.id.edtCep)
        val edtTelefone: EditText = findViewById(R.id.edtTelefone)
        val btnConfirmar: Button = findViewById(R.id.btnConfirmar)

        edtNome.setText(intent.getStringExtra("nome:"))
        edtEndereco.setText(intent.getStringExtra("endereco:"))
        edtBairro.setText(intent.getStringExtra("bairro:"))
        edtCep.setText(intent.getStringExtra("cep:"))
        edtTelefone.setText(intent.getStringExtra("telefone:"))

        val db = DBHelper(this, null)
        btnConfirmar.setOnClickListener {
            db.addPessoa(
                edtNome.text.toString(),
                edtEndereco.text.toString(),
                edtBairro.text.toString(),
                edtCep.text.toString(),
                edtTelefone.text.toString()
            )
            finish()
        }
    }
}

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT, " +
                END_COL + " TEXT, " +
                BAR_COL + " TEXT, " +
                CEP_COL + " TEXT, " +
                TEL_COL + " TEXT)")
        db?.execSQL(query) }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db) }

    fun addPessoa(name: String, endereco: String, bairro: String, cep: String, telefone: String) {
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(END_COL, endereco)
        values.put(BAR_COL, bairro)
        values.put(CEP_COL, cep)
        values.put(TEL_COL, telefone)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close() }

    companion object {
        private const val DATABASE_NAME = "appSQLite"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "CadastroPessoa"
        const val ID_COL = "id"
        const val NAME_COL = "name"
        const val END_COL = "endereco"
        const val BAR_COL = "bairro"
        const val CEP_COL = "cep"
        const val TEL_COL = "telefone"
    }
}




