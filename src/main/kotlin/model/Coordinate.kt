package model

import java.io.Serializable

class Coordinate(var x: Int, var y: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        val coor = other as Coordinate

        return x == coor.x && y == coor.y
    }

    override fun toString(): String {
        return "X=$x,Y=$y"
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}