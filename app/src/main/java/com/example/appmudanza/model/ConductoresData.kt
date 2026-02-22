package com.example.appmudanza.model
import com.example.appmudanza.model.Conductores
import com.example.appmudanza.R

val listaConductores = listOf(
    Conductores(
        id =  "1",
        title = "Conductor 1",
        description = "descripcion por defecto",
        imagesRes = R.drawable.ic_launcher_foreground,
        valoration  = 4.5f
    ),
    Conductores(
        id = "2",
        title = "Conductor 2",
        description = "descripion por defecto",
        imagesRes = R.drawable.ic_launcher_foreground,
        valoration = 3.0f
    )
)