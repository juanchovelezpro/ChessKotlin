package model

class Chess : ChessActions {

    val board = Array(8) { x -> Array(8) { y -> Box(Coordinate(x, y), null) } }

    val possibleBoxes = mutableListOf<Box>()
    var selectedBox: Box? = null
    var onMovement = false
    var activePiece: Piece? = null
    var whiteTurn = true
    val whitePiecesAlive = ArrayList<Piece>()
    val blackPiecesAlive = ArrayList<Piece>()
    lateinit var blackKing: King
    lateinit var whiteKing: King

    init {
        initBoard()
    }

    private fun initBoard() {

        for (i in 0..board.lastIndex) {
            for (j in 0..board[0].lastIndex) {
                putBoxColors(i, j)
                putPiece(i, j)

                val piece = board[i][j].piece
                addAlivePiece(piece)

            }
        }

        blackKing = board[0][4].piece as King
        whiteKing = board[7][4].piece as King
    }

    private fun addAlivePiece(piece: Piece?) {
        if (piece != null) {
            if (piece.team == Team.WHITE) {
                whitePiecesAlive.add(piece)
            } else {
                blackPiecesAlive.add(piece)
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
                Pawn(Coordinate(i, j), Team.BLACK, this)
            }

            // Putting White Pieces
            6 -> {
                Pawn(Coordinate(i, j), Team.WHITE, this)
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
            1, 6 -> Knight(Coordinate(i, j), team, this)
            3 -> Queen(Coordinate(i, j), team, this)
            4 -> King(Coordinate(i, j), team, this)
            2, 5 -> Bishop(Coordinate(i, j), team, this)
            else -> Rook(Coordinate(i, j), team, this)
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

    fun boardCopy(): Array<Array<Box>> {

        val boardCopy = Array(8) { x -> Array(8) { y -> Box(Coordinate(x, y), null) } }

        for (i in 0..boardCopy.lastIndex) {
            for (j in 0..boardCopy[0].lastIndex) {
                val boxy = board[i][j]
                boardCopy[i][j] = boxy
            }
        }

        return boardCopy

    }

    // This is for reset properly the active "can be killed en passant" Pawn
    private fun handleActiveEnPassant() {
        if (whiteTurn) {
            whitePiecesAlive.forEach { piece -> if (piece is Pawn) piece.disableCanBeKilledEnPassant() }
        } else {
            blackPiecesAlive.forEach { piece -> if (piece is Pawn) piece.disableCanBeKilledEnPassant() }
        }
    }

    override fun onKill(murdered: Piece) {
        println("------------------------ KILL ---------------------------------------")
        println("$murdered - ${murdered.team} has been killed on ${murdered.position}")

        if (murdered.team == Team.WHITE) {
            whitePiecesAlive.remove(murdered)
            println("White Pieces Remaining: ${whitePiecesAlive.size} = $whitePiecesAlive")
        } else {
            blackPiecesAlive.remove(murdered)
            println("Black Pieces Remaining: ${blackPiecesAlive.size} = $blackPiecesAlive")
        }

        println("----------------------------------------------------------------------")

    }

    override fun onMovement(from: Coordinate, to: Coordinate, piece: Piece) {

        piece.handleMovement(from, to)

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

    override fun onTurnChanged() {
        whiteTurn = !whiteTurn

        handleActiveEnPassant()

        println("$blackKing ${blackKing.position.x} ${blackKing.position.y}")
        println("$whiteKing ${whiteKing.position.x} ${whiteKing.position.y}")

        println(whitePiecesAlive)
        println(blackPiecesAlive)

    }

    override fun toString(): String {
        var theBoard = ""

        for (i in 0..board.lastIndex) {
            for (j in 0..board[0].lastIndex) {
                val piece = board[i][j].piece
                theBoard += if (piece != null) {
                    "$piece "
                } else {
                    "  "
                }
            }
            theBoard += "\n"
        }

        return theBoard
    }
}