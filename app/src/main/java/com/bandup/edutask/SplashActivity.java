/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2023    HORA: 08-09 HRS
:*
:*                Activity que muestra una interfaz de carga de aplicación.
:*
:*  Archivo     : SplashActivity.java
:*  Autor       : Efraín Montalvo Sánchez           Num. Control: 20130810
:*  Fecha       : 30/Oct/2023
:*  Compilador  : Android Studio Giraffe 2022.3.1
:*  Descripcion : Activity que muestra un layout con la interfaz de carga de la aplicación
:*                durante dos segundos, con una imagen del logo de la aplicación antes de
:*                iniciar completamente.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package com.bandup.edutask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.splash_activity );

        //Hacer la transición al MainActivity después de 2 segundos.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            //Crear el intent para llamar a MainActivity
                Intent intent = new Intent( SplashActivity.this, MainActivity.class );
                startActivity( intent );
                finish();
            }
        },300 );
    }
}