package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerPiece;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

// @TODO Too much work (from sprint 1 rubric comment)
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    public enum Mode {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    // View-and-model attributes
    static final String TITLE_ATTR = "title";
    static final String MESSAGE_ATTR = "message";

    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final Gson gson;

    public GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby, final GameManager gameManager, Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();

        vm.put(TITLE_ATTR, "Game");
        vm.put("viewMode", Mode.PLAY);

        Player player = httpSession.attribute("player");
        if(player == null) {
            response.redirect(WebServer.HOME_URL);
        }

        int gameID = -1;
        if(player.getGameID() != -1) {
            gameID = player.getGameID();
        } else {
            final String queryParam = request.queryParams("gameID");
            if(queryParam != null) {
                gameID = Integer.parseInt(queryParam);
            } else {
                System.out.println("going home");
                response.redirect(WebServer.HOME_URL);
            }
        }

        CheckersGame game = this.gameManager.getGame(gameID);
        //
        CheckerPiece[][] board;
        if ( game.isPlayersTurn(player) ) {
            board = game.getBoard();
        } else {
            board = game.getFlippedBoard();
        }

        final Player opponent;
        if (game.isRedPlayer(player)) {
            opponent = game.getWhitePlayer();
            vm.put("redPlayer", player);
            vm.put("whitePlayer", opponent);
        } else {
            opponent = game.getRedPlayer();
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", player);
        }

        BoardView boardView = new BoardView( board );
        vm.put("board", boardView);
        vm.put("currentUser", player);
        vm.put("activeColor", game.getActivePlayer().equals(game.getRedPlayer()) ? CheckerPiece.Color.RED : CheckerPiece.Color.WHITE);
        vm.put("gameID", game.getId());

        if (game.isGameOver()){
            final Map<String, Object> modeOptions = new HashMap<>(2);
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", game.getGameOverMessage());
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        }

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}