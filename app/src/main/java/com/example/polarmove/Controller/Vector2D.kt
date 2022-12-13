package com.example.firscomposeapp

import kotlin.math.pow
import kotlin.math.sqrt

class Vector2D {
    constructor(){

    }
    constructor(X:Double,Y:Double){
        x=X
        y=Y
    }
    constructor(o:Vector3D){
        x=o.x.toDouble()
        y=o.y.toDouble()
    }

    private val values= doubleArrayOf(0.0,0.0)

    var x:Double
        get() {return values[0]}
        set(value) {values[0]=value}
    var y:Double
        get() {return values[1]}
        set(value) {values[1]=value}

    fun clear(){
        x=0.0
        y=0.0
    }

    fun length(): Double {
        return sqrt(x.toDouble().pow(2) +y.toDouble().pow(2))
    }

    fun add(X:Int,Y:Int,Z:Int){
        x+=X
        y+=Y
    }

    fun set(array: IntArray){
        x=array[0].toDouble()
        y=array[1].toDouble()
    }
    fun set(array: DoubleArray) {
        x = array[0]
        y = array[1]
    }
    fun set(vector3D: Vector3D){
        x=vector3D.x.toDouble()
        y=vector3D.y.toDouble()
    }

    fun toIntArray(): IntArray {
        val result = intArrayOf(0,0)
        result[0]=x.toInt()
        result[1]=y.toInt()
        return result
    }

    fun rotate(rotX:Double,rotY:Double,rotZ:Double){
        val rotMatrixX=Matrix3D(Matrix3D.Axis.X,rotX)
        val rotMatrixY=Matrix3D(Matrix3D.Axis.Y,rotY)
        val rotMatrixZ=Matrix3D(Matrix3D.Axis.Z,rotZ)

        this*=rotMatrixX
        this*=rotMatrixY
        this*=rotMatrixZ
    }

    fun dotProduct(other:Vector2D):Double {
        val result=(x*other.x) + (y*other.y)
        return result.toDouble()
    }

    fun normalized(){

    }

    fun rotationFrom(other:Vector3D): Vector3D{
        val result=Vector3D()
        return result
    }

    inline operator fun timesAssign (matrix3D: Matrix3D){
    }

    inline operator fun divAssign (divider:Int){
        x/=divider
        y/=divider
    }
}