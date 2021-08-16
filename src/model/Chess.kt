package model

class Chess : ObserverActions{

    val board = Array(8) { x -> Array(8) { y -> Box(Coordinate(x, y), null) } }

    val possibleBoxes = mutableListOf<Box>()
    var selectedBox: Box? = null
    var onMovement = false
    var activePiece: Piece? = null
    var whiteTurn = true

    companion object {
        val whitePiecesAlive = ArrayList<Piece?>()
        val blackPiecesAlive = ArrayList<Piece?>()
    }

    init {
        initBoard()
    }

    private fun initBoard() {

        for (i in 0..board.lastIndex) {
            for (j in 0..board[0].lastIndex) {
                putBoxColors(i, j)
                putPiece(i, j)

                val thePiece = board[i][j].piece
                if (thePiece != null) {
                    thePiece.observer = this
                    if (thePiece.team == Team.WHITE) {
                        whitePiecesAlive.add(thePiece)
                    } else {
                        blackPiecesAlive.add(thePiece)
                    }
                }
            }
        }
    }

    private fun putPiece(i: Int, j: Int) {


        board[i][j].piece = when (i) {

            // Putting Black Pieces
            0 -> {
                putPiece(i, j, Team.BLACK)
            }

            1 -> {
                Pawn(Coordinate(i, j), Team.BLACK)
            }

            // Putting White Pieces
            6 -> {
                Pawn(Coordinate(i, j), Team.WHITE)
            }

            7 -> {
                putPiece(i, j, Team.WHITE)
            }

            else -> {
                null
            }
        }
    }

    private fun putPiece(i: Int, j: Int, team: Team): Piece {
        return when (j) {
            1, 6 -> Knight(Coordinate(i, j), team)
            3 -> Queen(Coordinate(i, j), team)
            4 -> King(Coordinate(i, j), team)
            2, 5 -> Bishop(Coordinate(i, j), team)
            else -> Rook(Coordinate(i, j), team)
        }
    }

    private fun putBoxColors(i: Int, j: Int) {
        when {
            i % 2 == 0 -> {
                if (j % 2 == 0) {
                    board[i][j].color = Box.WHITE_COLOR
                    board[i][j].originalColor = Box.WHITE_COLOR
                } else {
                    board[i][j].color = Box.BLACK_COLOR
                    board[i][j].originalColor = Box.BLACK_COLOR
                }
            }
            else -> {
                if (j % 2 == 0) {
                    board[i][j].color = Box.BLACK_COLOR
                    board[i][j].originalColor = Box.BLACK_COLOR
                } else {
                    board[i][j].color = Box.WHITE_COLOR
                    board[i][j].originalColor = Box.WHITE_COLOR
                }
            }
        }
    }

    override fun onKill(murdered: Piece) {
        println("$murdered - ${murdered.team} has been killed on ${murdered.position}")

        if(murdered.team == Team.WHITE){
            whitePiecesAlive.remove(murdered)
            println("White Pieces Remaining: ${whitePiecesAlive.size} = $whitePiecesAlive")
        }else{
            blackPiecesAlive.remove(murdered)
            println("Black Pieces Remaining: ${blackPiecesAlive.size} = $blackPiecesAlive")
        }

    }

    override fun onMovement(from: Coordinate, to: Coordinate, piece: Piece) {
        println("-------------------- MOVEMENT -----------------------------")
        println("$piece ${piece.team} has been moved from $from to $to")
        println("-----------------------------------------------------------")
    }

    override fun onPromotion(pawn: Pawn) {
        TODO("Not yet implemented")
    }

    override fun onCheck() {
        TODO("Not yet implemented")
    }

    override fun onCheckMate() {
        TODO("Not yet implemented")
    }

    override fun onEnPassant() {
        TODO("Not yet implemented")
    }


}