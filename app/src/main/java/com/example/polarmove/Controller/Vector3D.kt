package com.example.firscomposeapp

import kotlin.math.pow
import kotlin.math.sqrt

class Vector3D {
    constructor(){

    }
    constructor(X:Int,Y:Int,Z:Int){
        x=X
        y=Y
        z=Z
    }
    constructor(o:Vector3D){
        x=o.x
        y=o.y
        z=o.z
    }

    val values= arrayOf(0,0,0)

    var x:Int
        get() {return values[0]}
        set(value) {values[0]=value}
    var y:Int
        get() {return values[1]}
        set(value) {values[1]=value}
    var z:Int
        get() {return values[2]}
        set(value) {values[2]=value}

    fun clear(){
        x=0
        y=0
        z=0
    }

    fun length(): Double {
        return sqrt(x.toDouble().pow(2) +y.toDouble().pow(2) +z.toDouble().pow(2))
    }

    fun add(X:Int,Y:Int,Z:Int){
        x+=X
        y+=Y
        z+=Z
    }

    inline operator fun divAssign (divider:Int){
        x/=divider
        y/=divider
        z/=divider
    }

}