package model

class Rook(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2656"
        }else{
            "\u265C"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): List<Box> {
        TODO("Not yet implemented")
    }
}