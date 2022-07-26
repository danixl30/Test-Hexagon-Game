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
    private final Observer<Boolean> hammerControl = new Observer<>(false);
    private final Observer<Boolean> trashControl = new Observer<>(false);
    private final ArrayList<HexagonCenter<Triangle>> centers;
    private final Observer<Boolean> onGameOver = new Observer<>(false);
    private final PointsManager pointsManager;
    private final Observer<Integer> hammerCost = new Observer<>(100);
    private final Observer<Integer> trashCost = new Observer<>(50);
    private Function1<String, Unit> onSendMessage;
    private final PieceManager<Piece> piecePieceManager;

    public Game(PointsManager pointsManager, PieceManager<Piece> piecePieceManager) {
        this.pointsManager = pointsManager;
        this.piecePieceManager = piecePieceManager;
        this.triangles = new BoardFactory().create();
        centers = new HexagonCenterFactory(triangles).create();
    }

    public void subscribeOnEnableHammer(Function1<Boolean, Unit> callback) {
        hammerControl.subscribe(callback);
    }

    public void subscribeOnEnableTrash(Function1<Boolean, Unit> callback) {
        trashControl.subscribe(callback);
    }

    public void subscribeOnGameOver(Function1<Boolean, Unit> callback) {
        onGameOver.subscribe(callback);
    }

    public void subscribeOnPointsChange(Function1<Integer, Unit> callback) {
        pointsManager.subscribeOnPointsChange(callback);
    }

    public void subscribeOnHammerCostChange(Function1<Integer, Unit> callback) {
        hammerCost.subscribe(callback);
    }
    public void subscribeOnTrashConstChange(Function1<Integer, Unit> callback) {
        trashCost.subscribe(callback);
    }

    public void subscribeOnChangePieces(Function1<ArrayList<Piece>, Unit> callback) {
        piecePieceManager.subscribeOnChangePiece(callback);
    }

    public void subscribeOnSendMessage(Function1<String, Unit> callback) {
        onSendMessage = callback;
    }

    public ArrayList<Piece> getPieces() {
        return piecePieceManager.getPieces();
    }

    public Iterator<Iterator<Box<Triangle, String>>> getBoardByIterable() {
        return new TileCollectionsGenerator(triangles).createCollection();
    }

    public void onEnableTrash() {
        if (trashControl.getValue()) trashControl.setValue(false);
        else if (pointsManager.getPoints() - trashCost.getValue() < 0)
            onSendMessage.invoke("You can't use the trash... Get more points");
        else trashControl.setValue(true);
        hammerControl.setValue(false);
    }

    public void destroyPiece(Piece piece) {
        if (trashControl.getValue()) {
            trashControl.setValue(false);
            pointsManager.decreasePoints(trashCost.getValue());
            trashCost.setValue(trashCost.getValue() + 50);
        }
        piecePieceManager.deletePiece(piece);
        if (!new HexagonBoardChecker<Piece>().check(triangles, piecePieceManager.getPieces())) onGameOver.setValue(true);
    }

    public void onPutPiece(Piece piece) {
        destroyPiece(piece);
        onCheckCenters();
    }

    public void onEnableHammer() {
        if (hammerControl.getValue()) hammerControl.setValue(false);
        else if (pointsManager.getPoints() - hammerCost.getValue() >= 0)
            hammerControl.setValue(true);
        else onSendMessage.invoke("You can't use the hammer... Get more points");
        trashControl.setValue(false);
    }

    public void onDestroyTriangle(Box<Triangle, String> triangle) {
        Boolean res = new Hammer<Triangle, String>(GRAY_BASE).destroy(triangle);
        if (res && hammerControl.getValue()) {
            hammerControl.setValue(false);
            pointsManager.decreasePoints(hammerCost.getValue());
            hammerCost.setValue(hammerCost.getValue() + 50);
        }
    }

    private void onCheckCenters() {
        List<HexagonCenter<Triangle>> centerChecked = centers.stream().filter(HexagonCenter::check).collect(Collectors.toList());
        pointsManager.increasePoints(centerChecked.size());
        centerChecked.forEach(HexagonCenter::empty);
    }
}
