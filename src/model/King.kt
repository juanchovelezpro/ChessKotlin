package model

class King(position: Coordinate, team: Team) : Piece(position,team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2654"
        }else{
            "\u265A"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): List<Box> {
        TODO("Not yet implemented")
    }
}