package model

class AIChess(val team : Team,val chess: Chess) {

    fun getPieces() : ArrayList<Piece>{
        return if(team == Team.WHITE){
            chess.whitePiecesAlive
        }else{
            chess.blackPiecesAlive
        }
    }


}