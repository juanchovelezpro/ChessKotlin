package model

class Pawn(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2659"
        }else{
            "\u265F"
        }

    }


    override fun possibleMovements(board: Array<Array<Box>>): List<Box> {
        TODO("Not yet implemented")
    }
}