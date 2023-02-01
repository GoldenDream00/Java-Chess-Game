var stompClient = null;
var nickName = null;
var inviteName = null;
var game, board;
var opponentName = null;
var side = null;
var gameID = null;
//connect button clicked
function connect() {
    $("#connecting").html("Connecting...");
    var socket = new SockJS('/ws');

    nickName = $("#nickname").val().trim();
    stompClient = Stomp.over(socket);
    stompClient.connect({name:nickName}, onConnected, onError);
}
//onConnected
function onConnected() {
    $("#connecting").html("Connected");
    $("#connect").prop("disabled", true);
    $("#start").prop("disabled", false);
    // Tell your username to the server
    stompClient.subscribe('/topic/public', onMessagePublicReceived);
    stompClient.subscribe('/user/queue/connection', onMessagePrivateReceived);
    stompClient.send("/app/chat.addUser", {},
        JSON.stringify({
            type: 'JOIN',
            content:null,
            sender: nickName,
            receiver:null
        })
    )

}
function onMessagePublicReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log(message);
}
function onMessagePrivateReceived(payload) {
    var message = JSON.parse(payload.body);
    if(message.type =="CHECK_USER") {
        if(message.content == "ALIVE") {
            $("#inviteStatus").html("Waiting to Accept Request...");
            stompClient.send("/app/chat.sendRequest", {}, JSON.stringify({
                type:null,
                content:null,
                sender:nickName,
                receiver:inviteName
            }))
        }
        else {
            $("#inviteStatus").html("Your friend is not alive now...");
        }
    }
    if(message.type == "REQUEST_ACTION") {
        if(message.content == "ACCEPT") {
            $("#inviteStatus").html("Accepted");
            $("#settingModal").modal('hide');

        }
        else if (message.content =="CANCEL"){
            $("#inviteStatus").html("Rejected");
        }
        else if ( message.content == "INVITE") {
            toastr.options = {
                "timeout":"5000",
                "closeButton":false,
                "onclick":null,
                "tapToDismiss": false
            }
            toastr.info("" + message.sender + " invites you.<a onclick=InviteAccept('" + message.sender + "') class='btn bg-transparent'>Ok</a><a onclick=InviteCancel('"+ message.sender +"') class='btn bg-transparent'>Cancel</a>");
        }
    }
    if(message.type == "RANDOM_CHOOSE") {
        if(message.content == "false") {
            stompClient.send("/app/chat.randomUser", {}, JSON.stringify({
                type:"CHAT",
                content:null,
                sender:nickName,
                receiver:null,
            }));
        }
        if(message.content == "true") {
            inviteName = message.receiver;
            console.log(inviteName);
            stompClient.send("/app/chat.checkUser", {}, JSON.stringify({
                type:"CHAT",
                content:null,
                sender:nickName,
                receiver:inviteName
            }))
        }
    }
    //Game Start
    if(message.type == "GAME_START") {
            gameID = message.content;
            opponentName = message.receiver;
            side = "w";
            if(message.sender != nickName) {
                opponentName = message.sender;
                side = "b";
                board.flip();
            }
    }
    if ( message.type == "MOVE_PIECE") {
        game.load_pgn(message.content);
        updateBoard(board);
    }
}
function updateBoard(board) {
    board.position(game.fen(), false);
    showSideToMove();
    promoting = false;
    if(game.game_over() === true) {
        var winnerName = (game.turn()==side) ? opponentName : nickName;
        stompClient.send("/app/game.finish", {}, JSON.stringify({
            type:winnerName,
            content:game.fen() + "@@" + game.pgn(),
            sender:gameID,
        }))
        GameFinished(winnerName);
    }
}

function GameFinished(winner) {
    alert(winner);
    reset();
}
function makeMove(game, cfg) {
    // see if the move is legal
    var move = game.move(cfg);
    // illegal move
    if (move === null) return 'snapback';
    stompClient.send("/app/game.move", {}, JSON.stringify({
        type:null,
        content:game.pgn(),
        sender:nickName,
        receiver:opponentName,
    }));
    updateBoard(board);
}
function showSideToMove() {
    var color = game.turn() == side ? nickName: opponentName;
    $("#sideToMove").text(color);
}
function reset() {

    game.reset();
    board.position(game.fen());
    showSideToMove();
}
//Disconnect button clicked
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    $("#connect").prop("disabled", false);
    $("#start").prop("disabled", true);
    $("#connecting").html("Disconnected...");
}
//connection Error
function onError() {
    $("#connecting").html("Could not connect to WebSocket server. Please refresh this page to try again!");
}
function InviteAccept(senderName) {
    if(stompClient != null) {
        stompClient.send("/app/chat.RequestAction", {}, JSON.stringify(({
            type:null,
            content:"ACCEPT",
            sender:senderName,
            receiver:nickName,
        })))
    }
    toastr.clear();
}
function InviteCancel(senderName) {
    if(stompClient != null) {
        stompClient.send("/app/chat.RequestAction", {}, JSON.stringify(({
            type:null,
            content:"CANCEL",
            sender:senderName,
            receiver:nickName,
        })))
    }
    toastr.clear();
}
function openSettingModal() {
    $("#settingModal").modal('show');
}
//invite clicked
function inviteClicked() {
    inviteName = $("#inviteName").val();
    if(inviteName == nickName) {
        $("#inviteStatus").html("Cannot invite yourself.");
        return;
    }
    if (stompClient !== null) {
        stompClient.send("/app/chat.checkUser", {}, JSON.stringify({
            type:"CHAT",
            content:null,
            sender:nickName,
            receiver:inviteName
        }))
    }
}
function randomClicked() {
    if(stompClient !== null) {
        stompClient.send("/app/chat.randomUser", {}, JSON.stringify({
            type:"CHAT",
            content:null,
            sender:nickName,
            receiver:null,
        }))
    }
}
$(function () {
    $( "#connect" ).click(function() { connect(); });
    $("#disconnect").click(function() { disconnect();})
    $("#start").click(function() {openSettingModal(); })
    $("#invite").click(function() { inviteClicked(); })
    $("#random").click(function() { randomClicked();})
    $("#closeSettingBtn").click(function() {});
    $("#start").prop("disabled", true);

    var piece_theme, promote_to, promoting, promotion_dialog;
    //for game
    piece_theme = '/scripts/chessboardjs-0.3.0/img/chesspieces/wikipedia/{piece}.png';
    promotion_dialog = $('#promotion-dialog');

    if($("#board").length) {
        game = new Chess();

        showSideToMove();
        // do not pick up pieces if the game is over
        // only pick up pieces for the side to move
        var onDragStart = function (source, piece, position, orientation) {
            if (game.game_over() === true ||
                (game.turn() === 'w' && piece.search(/^b/) !== -1) ||
                (game.turn() === 'b' && piece.search(/^w/) !== -1) ||
                (side === null || game.turn() !== side.charAt(0))) {
                return false;
            }
        };



        var onDrop = function (source, target) {
            move_cfg = {
                from: source,
                to: target,
                promotion: 'q'
            };
            // check we are not trying to make an illegal pawn move to the 8th or 1st rank,
            // so the promotion dialog doesn't pop up unnecessarily
            // e.g. (p)d7-f8
            var move = game.move(move_cfg);
            // illegal move
            if (move === null) {
                return 'snapback';
            } else {
                game.undo(); //move is ok, now we can go ahead and check for promotion
            }
            // is it a promotion?
            var source_rank = source.substring(2, 1);
            var target_rank = target.substring(2, 1);
            var piece = game.get(source).type;
            if (piece === 'p' &&
                ((source_rank === '7' && target_rank === '8') || (source_rank === '2' && target_rank === '1'))) {
                promoting = true;

                // get piece images
                $('.promotion-piece-q').attr('src', getImgSrc('q'));
                $('.promotion-piece-r').attr('src', getImgSrc('r'));
                $('.promotion-piece-n').attr('src', getImgSrc('n'));
                $('.promotion-piece-b').attr('src', getImgSrc('b'));

                //show the select piece to promote to dialog
                promotion_dialog.dialog({
                    modal: true,
                    height: 46,
                    width: 184,
                    resizable: true,
                    draggable: false,
                    close: onDialogClose,
                    closeOnEscape: false,
                    dialogClass: 'noTitleStuff'
                }).dialog('widget').position({
                    of: $('#board'),
                    my: 'middle middle',
                    at: 'middle middle',
                });
                //the actual move is made after the piece to promote to
                //has been selected, in the stop event of the promotion piece selectable
                return;
            }

            // no promotion, go ahead and move
            makeMove(game, move_cfg);

        }

        var onSnapEnd = function () {
            if (promoting) return; //if promoting we need to select the piece first
            //updateBoard(board);
        };

        function getImgSrc(piece) {
            return piece_theme.replace('{piece}', game.turn() + piece.toLocaleUpperCase());
        }

        var onDialogClose = function () {
            move_cfg.promotion = promote_to;
            makeMove(game, move_cfg);
        }

        //CHESS Board
        var cfg = {
            draggable: true,
            onDragStart: onDragStart,
            onDrop: onDrop,
            onSnapEnd: onSnapEnd,
            pieceTheme: dilena_piece_theme,
            boardTheme:dilena_board_theme,
            position: 'start',
        };
        board = ChessBoard("board", cfg);

        // init promotion piece dialog
        $("#promote-to").selectable({
            stop: function () {
                $(".ui-selected", this).each(function () {
                    var selectable = $('#promote-to li');
                    var index = selectable.index(this);
                    if (index > -1) {
                        var promote_to_html = selectable[index].innerHTML;
                        var span = $('<div>' + promote_to_html + '</div>').find('span');
                        promote_to = span[0].innerHTML;
                    }
                    promotion_dialog.dialog('close');
                    $('.ui-selectee').removeClass('ui-selected');
                    //updateBoard(board);
                });
            }
        });
    }

    jQuery('#board').on('scroll touchmove touchend touchstart contextmenu', function (e) {
        e.preventDefault();
    });


});