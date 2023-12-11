/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2023    HORA: 08-09 HRS
:*
:*             Activity que muestra una interfaz con la información del creador de la app.
:*
:*  Archivo     : AcercaDeActivity.java
:*  Autor       : Efraín Montalvo Sánchez           Num. Control: 20130810
:*  Fecha       : 31/Oct/2023
:*  Compilador  : Android Studio Giraffe 2022.3.1
:*  Descripcion : Activity que muestra un layout con la información del creador de la
:*                aplicación, así como los logos de la institución y la versión de la app.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package com.bandup.edutask;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.acerca_de_activity);
    }
}