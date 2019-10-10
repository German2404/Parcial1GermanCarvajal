package com.example.parcial1germancarvajal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcial1germancarvajal.model.data.CRUDPremio;
import com.example.parcial1germancarvajal.model.data.CRUDUser;
import com.example.parcial1germancarvajal.model.entity.Premio;

import java.util.ArrayList;

public class BibliotecaActivity extends AppCompatActivity {

    private ListView tiendaBiblioteca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        tiendaBiblioteca=findViewById(R.id.lvBiblioteca);
        ArrayList<Premio> premios= CRUDPremio.darPremios();
        ArrayList<String> premiosEnTexto=new ArrayList<>();
        for (Premio p:premios) {
            premiosEnTexto.add(p.getName()+" Precio: "+p.getPrice());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,premiosEnTexto);

        tiendaBiblioteca.setAdapter(adapter);

        tiendaBiblioteca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                int precio=Integer.parseInt(((TextView)view).getText().toString().split(" ")[2]);
                int puntos=CRUDUser.getUser(CRUDUser.masterUser).getPoints();
                if(precio<=puntos){
                    Intent i=new Intent();
                    i.putExtra("puntos",puntos-precio);
                    setResult(RESULT_OK,i);
                    finish();
                }
                else{
                    Toast.makeText(BibliotecaActivity.this,"No tiene suficientes puntos para reclamar este premio",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


}
