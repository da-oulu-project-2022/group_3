package com.example.firscomposeapp

import android.util.Log
import io.reactivex.rxjava3.internal.operators.flowable.FlowableDelaySubscriptionOther
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

class Vector3D {
    constructor(){

    }
    constructor(X:Double,Y:Double,Z:Double){
        x=X
        y=Y
        z=Z
    }
    constructor(X:Int,Y:Int,Z:Int){
        x=X.toDouble()
        y=Y.toDouble()
        z=Z.toDouble()
    }
    constructor(o:Vector3D){
        x=o.x
        y=o.y
        z=o.z
    }
    constructor(o:DoubleArray){
        x=o[0]
        y=o[1]
        z=o[2]
    }

    val values= doubleArrayOf(0.0,0.0,0.0)

    var x:Double
        get() {return values[0]}
        set(value) {values[0]=value}
    var y:Double
        get() {return values[1]}
        set(value) {values[1]=value}
    var z:Double
        get() {return values[2]}
        set(value) {values[2]=value}

    fun clear(){
        x=0.0
        y=0.0
        z=0.0
    }

    fun length(): Double {
        return sqrt(x.toDouble().pow(2) +y.toDouble().pow(2) +z.toDouble().pow(2))
    }

    fun add(X:Int,Y:Int,Z:Int){
        x+=X
        y+=Y
        z+=Z
    }

    fun set(array: DoubleArray){
        x=array[0]
        y=array[1]
        z=array[2]
    }
    fun set(array: IntArray){
        x=array[0].toDouble()
        y=array[1].toDouble()
        z=array[2].toDouble()
    }
    fun set(vector3D: Vector3D){
        x=vector3D.x
        y=vector3D.y
        z=vector3D.z
    }

    fun toIntArray(): IntArray {
        var result = intArrayOf(0,0,0)
        result[0] = values[0].toInt()
        result[1] = values[1].toInt()
        result[2] = values[2].toInt()
        return result
    }

    fun rotate(rotation:DoubleArray){
        Log.d("Vector3D", "Before rotation " + toString())

        Log.d("Vector3D", "Rotate by ${rotation[0]} ${rotation[1]} ${rotation[2]}")
        val rotMatrixX=Matrix3D(Matrix3D.Axis.X,rotation[0])
        val rotMatrixY=Matrix3D(Matrix3D.Axis.Y,rotation[1])
        val rotMatrixZ=Matrix3D(Matrix3D.Axis.Z,rotation[2])

        this*=rotMatrixX
        Log.d("Vector3D", "After X rotation " + toString())

        this*=rotMatrixY
        Log.d("Vector3D", "After Y rotation " + toString())

        this*=rotMatrixZ
        Log.d("Vector3D", "After Z rotation " + toString())

    }
    fun rotate(x:Double,y:Double,z:Double) {

        rotate(doubleArrayOf(x,y,z))
    }

        fun dotProduct(other:Vector3D):Double {
        val result=(x*other.x) + (y*other.y) + (z*other.z)
        return result.toDouble()
    }

    private fun rotationFrom2D(me:Vector2D,other:Vector2D):Double{
        var result = 0.0

        if (me.length()==0.0 || other.length()==0.0){
            result=0.0
        }else{
            result = acos((me.dotProduct(other))/(me.length()*other.length()))
        }

        if (me.x-other.x<0||me.y-other.y<0){
            result*=-1.0
        }

        return result
    }
    fun rotationFrom(other:Vector3D):DoubleArray{
        val myValues = values
        val result= doubleArrayOf(0.0,0.0,0.0)

        val yzT=Vector2D(y,z)
        val yzO=Vector2D(other.y,other.z)
        result[0]=rotationFrom2D(yzT,yzO)
        rotate(doubleArrayOf(result[0],0.0,0.0))

        val xzT=Vector2D(x,z)
        val xzO=Vector2D(other.x,other.z)
        result[1] = rotationFrom2D(xzT,xzO)
        rotate(doubleArrayOf(0.0,result[1],0.0))

        val xyT=Vector2D(x,y)
        val xyO=Vector2D(other.x,other.y)
        result[2] = rotationFrom2D(xyT,xyO)

        set(myValues)
        return result
    }

    inline operator fun timesAssign (matrix3D: Matrix3D){
        var dX = x
        var dY = y
        var dZ = z
        x = (dX*matrix3D.values[0][0]+dY*matrix3D.values[0][1]+dZ*matrix3D.values[0][2])
        y = (dX*matrix3D.values[1][0]+dY*matrix3D.values[1][1]+dZ*matrix3D.values[1][2])
        z = (dX*matrix3D.values[2][0]+dY*matrix3D.values[2][1]+dZ*matrix3D.values[2][2])
    }

    inline operator fun divAssign (divider:Int){
        x/=divider
        y/=divider
        z/=divider
    }

    override fun toString():String{
        return "X: $x Y: $y Z: $z"
    }

}