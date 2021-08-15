package model

class Knight(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2658"
        }else{
            "\u265E"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): List<Box> {
        TODO("Not yet implemented")
    }
}