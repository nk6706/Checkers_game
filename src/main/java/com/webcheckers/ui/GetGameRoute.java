package com.webcheckers.ui;

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

    public GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby, final GameManager gameManager) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();

        vm.put(TITLE_ATTR, "Game");
        vm.put("viewMode", Mode.PLAY);

        Player player = httpSession.attribute("player");
        if(!player.inGame()) {
            response.redirect(WebServer.HOME_URL);
        }

        CheckersGame game = this.gameManager.getGame(player.getGameID());

        CheckerPiece[][] board;
        final Player opponent;
        if (game.isRedPlayer(player)) {
            board = game.getBoard(true);
            opponent = game.getWhitePlayer();
            vm.put("redPlayer", player);
            vm.put("whitePlayer", opponent);
        } else {
            board = game.getBoard(false);
            opponent = game.getRedPlayer();
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", player);
        }

        BoardView boardView = new BoardView( board );

        vm.put("board", boardView);
        vm.put("currentUser", player);
        vm.put("activeColor", CheckerPiece.Color.RED);
        vm.put("gameID", game.getId());

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}