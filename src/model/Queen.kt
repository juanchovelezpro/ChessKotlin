package model

class Queen(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if(team == Team.WHITE){
            "\u2655"
        }else{
            "\u265B"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box> {
        val pMovements = ArrayList<Box>()

        // As the Queen movements are like Rook and Bishop movements, then we can get from those classes

        val rook : Rook
        val bishop : Bishop

        if(team == Team.WHITE) {
            rook = Rook(Coordinate(position.x, position.y), Team.WHITE)
            bishop = Bishop(Coordinate(position.x,position.y), Team.WHITE)
        }else{
            rook = Rook(Coordinate(position.x, position.y), Team.BLACK)
            bishop = Bishop(Coordinate(position.x,position.y), Team.BLACK)
        }

        pMovements.addAll(rook.possibleMovements(board))
        pMovements.addAll(bishop.possibleMovements(board))


        return pMovements
    }


}