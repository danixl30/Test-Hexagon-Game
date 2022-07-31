package com.example.testhexagongame.Game;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import com.example.testhexagongame.BoardCheck.HexagonBoardChecker;
import com.example.testhexagongame.Points.PointsManager;
import com.example.testhexagongame.hammer.Hammer;
import com.example.testhexagongame.hexagon.center.HexagonCenter;
import com.example.testhexagongame.hexagon.center.HexagonCenterFactory;
import com.example.testhexagongame.piece.Piece;
import com.example.testhexagongame.piece.PieceManager;
import com.example.testhexagongame.tiles.tile.BoardFactory;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;
import com.example.testhexagongame.tiles.tile.TileCollectionsGenerator;
import com.example.testhexagongame.utils.Iterator;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Game {
    private final Box<Triangle, String> triangles;
    private final ArrayList<HexagonCenter<Triangle>> centers;
    private final Observer<Boolean> onGameOver = new Observer<>(false);
    private final PointsManager pointsManager;
    private final PieceManager<Piece> piecePieceManager;

    public Game(PointsManager pointsManager, PieceManager<Piece> piecePieceManager) {
        this.pointsManager = pointsManager;
        this.piecePieceManager = piecePieceManager;
        this.triangles = new BoardFactory().create();
        centers = new HexagonCenterFactory(triangles).create();
    }

    public void subscribeOnGameOver(Function1<Boolean, Unit> callback) {
        onGameOver.subscribe(callback);
    }

    public void subscribeOnPointsChange(Function1<Integer, Unit> callback) {
        pointsManager.subscribeOnPointsChange(callback);
    }

    public void subscribeOnChangePieces(Function1<ArrayList<Piece>, Unit> callback) {
        piecePieceManager.subscribeOnChangePiece(callback);
    }

    public ArrayList<Piece> getPieces() {
        return piecePieceManager.getPieces();
    }

    public Iterator<Iterator<Box<Triangle, String>>> getBoardByIterable() {
        return new TileCollectionsGenerator(triangles).createCollection();
    }

    public void onPutPiece(Piece piece) {
        piecePieceManager.deletePiece(piece);
        onCheckCenters();
        if (!new HexagonBoardChecker<Piece>().check(triangles, piecePieceManager.getPieces())) onGameOver.setValue(true);
    }

    private void onCheckCenters() {
        List<HexagonCenter<Triangle>> centerChecked = centers.stream().filter(HexagonCenter::check).collect(Collectors.toList());
        pointsManager.increasePoints(centerChecked.size());
        centerChecked.forEach(HexagonCenter::empty);
    }
}
