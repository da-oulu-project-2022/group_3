package com.example.firscomposeapp

import kotlin.math.cos
import kotlin.math.sin

class Matrix3D {

    var values = arrayOf(doubleArrayOf(0.0,0.0,0.0),doubleArrayOf(0.0,0.0,0.0),doubleArrayOf(0.0,0.0,0.0))

    constructor(axis: Axis,angle:Double){
        when(axis){
            Axis.X ->XAxisMatrix(angle)
            Axis.Y ->YAxisMatrix(angle)
            Axis.Z ->ZAxisMatrix(angle)
        }
    }

    fun XAxisMatrix(angle: Double){
        values[0][0]= 1.0
        values[0][1]= 0.0
        values[0][2]= 0.0

        values[1][0]= 0.0
        values[1][1]= cos(angle)
        values[1][2]= -1.0 * sin(angle)

        values[2][0]= 0.0
        values[2][1]= sin(angle)
        values[2][2]= cos(angle)
    }

    fun YAxisMatrix(angle: Double){
        values[0][0]= cos(angle)
        values[0][1]= 0.0
        values[0][2]= sin(angle)

        values[1][0]= 0.0
        values[1][1]= 1.0
        values[1][2]= 0.0

        values[2][0]= -1.0 * sin(angle)
        values[2][1]= 0.0
        values[2][2]= cos(angle)
    }

    fun ZAxisMatrix(angle: Double){
        values[0][0]= cos(angle)
        values[0][1]= -1.0 * sin(angle)
        values[0][2]= 0.0

        values[1][0]= sin(angle)
        values[1][1]= cos(angle)
        values[1][2]= 0.0

        values[2][0]= 0.0
        values[2][1]= 0.0
        values[2][2]= 1.0
    }

    enum class Axis{
        X,Y,Z
    }

}