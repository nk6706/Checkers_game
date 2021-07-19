package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerPiece;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetSpectatorGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    // View-and-model attributes
    static final String TITLE_ATTR = "title";
    static final String MESSAGE_ATTR = "message";

    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final Gson gson;

    public GetSpectatorGameRoute(TemplateEngine templateEngine, GameManager gameManager, PlayerLobby playerLobby, Gson gson) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.gson = gson;

        LOG.fine("GetSpectatorGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        final int gameID = Integer.parseInt(request.queryParams("gameID"));
        final CheckersGame game = gameManager.getGame(gameID);

        final Player player = httpSession.attribute("player");

        player.setGameID(gameID);

        vm.put(TITLE_ATTR, "Game");
        vm.put("viewMode", GetGameRoute.Mode.SPECTATOR);

        CheckerPiece[][] board;
        board = game.getBoard();

        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());

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
