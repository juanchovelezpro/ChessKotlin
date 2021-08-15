package model

abstract class Piece(var position: Coordinate, val team: Team) {

    lateinit var shape: String

    abstract fun possibleMovements(board: Array<Array<Box>>) : List<Box>

    fun move(board: Array<Array<Box>>, destination: Coordinate){
        if(possibleMovements(board).contains(board[destination.x][destination.y])){
            board[position.x][position.y].piece = null
            board[destination.x][destination.y].piece = this
            position = destination
        }else{
            throw Exception("Illegal Movement!")
        }
    }
}