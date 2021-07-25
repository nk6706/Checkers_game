package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.CheckerPiece;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
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
    private final GameManager gameManager;
    private final Gson gson;

    public GetGameRoute(final TemplateEngine templateEngine, final GameManager gameManager, Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.gameManager = gameManager;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();

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
                response.redirect(WebServer.HOME_URL);
            }
        }

        final CheckersGame game = this.gameManager.getGame(gameID);

        final CheckerPiece[][] board;
        final Map<String, Object> modeOptions = new HashMap<>(2);
        if (request.uri().equals("/replay/game")) { // Replay mode
            vm.put("viewMode", Mode.REPLAY);

            final int replayPosition = httpSession.attribute("replayPosition");
            board = game.spectatorGetBoard(replayPosition);
            modeOptions.put("hasNext", game.spectatorHasNext(replayPosition));
            modeOptions.put("hasPrevious", game.spectatorHasPrevious(replayPosition));
        } else { // Standard game mode
            vm.put("viewMode", Mode.PLAY);
            board = game.getBoard(player);
            if (game.isGameOver()){
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOverMessage", game.getGameOverMessage());
            }
        }

        vm.put(TITLE_ATTR, "Game");

        vm.put("currentUser", player);
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("board", new BoardView(board));
        vm.put("gameID", game.getId());

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}