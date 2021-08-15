package model

class Bishop(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2657"
        }else{
            "\u265D"
        }


    }

    override fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box> {
        val pMovements = ArrayList<Box>()

        return pMovements
    }




}