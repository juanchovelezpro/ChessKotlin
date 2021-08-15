package model

class Queen(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2655"
        }else{
            "\u265B"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): List<Box> {
        TODO("Not yet implemented")
    }
}