package com.example.concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

//Para ingresar datos pasamos por el content values y luego ingresa a la base de datos -> insert into
//Para leer usamos la tabla cursor -> select
public class ClienteActivity extends AppCompatActivity {

    EditText jetidentificacion, jetnombre,jetcorreo;
    CheckBox jcbactivo;
    String identificacion,nombre,correo;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Concesionario.db",null,1);
    //Admin es el objeto que está vinculado a la base de datos

    long respuesta;
    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        //Ocultar la barra de título por defecto y se asocian objetos java con obj xml
        getSupportActionBar().hide();
        jetidentificacion = findViewById(R.id.etidentificacion);
        jetnombre = findViewById(R.id.etnombre);
        jetcorreo = findViewById(R.id.etcorreo);
        jcbactivo = findViewById(R.id.cbactivo);
        sw=0;
    }



    public void guardar(View view){
        identificacion = jetidentificacion.getText().toString();
        nombre=jetnombre.getText().toString();
        correo=jetcorreo.getText().toString();
        if(identificacion.isEmpty()||nombre.isEmpty()||correo.isEmpty()){
            Toast.makeText(this, "Ingresar todos los datos", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }else{
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            //lo primero es el nombre como está en la base de datos y lo segundo es la variable
            registro.put("identificacion",identificacion);
            registro.put("nombre",nombre);
            registro.put("correo",correo);

            if(sw==0){
                respuesta=db.insert("TblCliente",null,registro);

            }else{
                respuesta=db.update("TblCliente",registro,"identificacion='"+identificacion+"'",null);
                sw=0;
            }

            if(respuesta==0){
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                limpiar_campos();
            }
            db.close();
        }
    }




    public void Consultar(View view){
        identificacion= jetidentificacion.getText().toString();
        if(!identificacion.isEmpty()){
           SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from TblCliente where identificacion='"+identificacion+"'",null);
            if(fila.moveToNext()){//se mueve al siguiente pq el cursor está en -1
                sw=1;
                jetnombre.setText(fila.getString(1));//los datos los almacena como un array 0 es id, 1 es nombre, 2 correo...
                jetcorreo.setText(fila.getString(2));
                jcbactivo.setChecked(fila.getString(3).equals("Si"));
            }else{
                Toast.makeText(this, "Registro no hallado", Toast.LENGTH_SHORT).show();
            }

            db.close();
        }else{
            Toast.makeText(this, "La identificación es requerida", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
    }


    public void Anular(View view){
        if(sw == 1){ //si es 1 es pq lo buscó y lo encontró
            sw=0;
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("activo","No");
            respuesta = db.update("TblCliente",registro,"identificacion='"+identificacion+"'",null);
            if(respuesta>0){ //si es mayor a 0 es porque lo pudo hacer
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                limpiar_campos();
            }else{
                Toast.makeText(this, "Error, no se pudo anular registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else{
            Toast.makeText(this, "Primero debe consultar", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
    }



    public void Cancelar(View view){
        limpiar_campos();
    }

    private void limpiar_campos(){
        jetidentificacion.setText("");
        jetnombre.setText("");
        jetcorreo.setText("");
        jcbactivo.setChecked(false);
        jetidentificacion.requestFocus();
        sw=0;
    }
}

