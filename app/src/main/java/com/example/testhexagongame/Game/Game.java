package com.example.testhexagongame.Game;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import com.example.testhexagongame.BoardCheck.HexagonBoardChecker;
import com.example.testhexagongame.Color.ColorGenerator;
import com.example.testhexagongame.Points.PointsCalculator;
import com.example.testhexagongame.hammer.Hammer;
import com.example.testhexagongame.hexagon.center.HexagonCenter;
import com.example.testhexagongame.hexagon.center.HexagonCenterFactory;
import com.example.testhexagongame.piece.Piece;
import com.example.testhexagongame.tiles.tile.BoardFactory2;
import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;
import com.example.testhexagongame.tiles.tile.TileCollectionsGenerator2;
import com.example.testhexagongame.utils.Iterator2;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class Game {
    private final Box2<Triangle, String> triangles;
    private final Observer<Boolean> hammerControl = new Observer<>(false);
    private final Observer<Boolean> trashControl = new Observer<>(false);
    private final ArrayList<HexagonCenter<Triangle>> centers;
    private final Observer<ArrayList<Piece>> pieces = new Observer<>(new ArrayList<>());
    private final ColorGenerator colorGenerator;
    private final Function2<Integer, Integer, Integer> getRandom;
    private final ArrayList<ArrayList<Integer>> options;
    private final Observer<Boolean> onGameOver = new Observer<>(false);
    private final PointsCalculator pointsCalculator;
    private final Observer<Integer> points = new Observer<>(0);

    public Game(ColorGenerator colorGenerator, Function2<Integer, Integer, Integer> getRandom, ArrayList<ArrayList<Integer>> options, PointsCalculator pointsCalculator) {
        this.colorGenerator = colorGenerator;
        this.getRandom = getRandom;
        this.options = options;
        this.pointsCalculator = pointsCalculator;
        this.triangles = new BoardFactory2().create();
        centers = new HexagonCenterFactory(triangles).create();
        System.out.println(centers);
        onCreatePieces();
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
        points.subscribe(callback);
    }

    public void subscribeOnChangePieces(Function1<ArrayList<Piece>, Unit> callback) {
        pieces.subscribe(callback);
    }

    public ArrayList<Piece> getPieces() {
        return pieces.getValue();
    }

    public Iterator2<Iterator2<Box2<Triangle, String>>> getBoardByIterable() {
        return new TileCollectionsGenerator2(triangles).createCollection();
    }

    public void onCreatePieces() {
        ArrayList<Piece> piecesLocal = pieces.getValue();
        while (piecesLocal.size() < 3) {
            piecesLocal.add(new Piece(colorGenerator, getRandom, options));
        }
        pieces.setValue(piecesLocal);
    }

    public void onEnableTrash() {
        if (trashControl.getValue()) trashControl.setValue(false);
        else trashControl.setValue(true);
        hammerControl.setValue(false);
    }

    public void destroyPiece(Piece piece) {
        trashControl.setValue(false);
        ArrayList<Piece> piecesLocal = pieces.getValue();
        List<Piece> newPieces = piecesLocal.stream().filter(e -> e != piece).collect(Collectors.toList());
        pieces.setValue(new ArrayList<>(newPieces));
        onCreatePieces();
        if (!new HexagonBoardChecker<Piece>().check(triangles, pieces.getValue())) onGameOver.setValue(true);
    }

    public void onPutPiece(Piece piece) {
        destroyPiece(piece);
        onCheckCenters();
    }

    public void onEnableHammer() {
        if (hammerControl.getValue()) hammerControl.setValue(false);
        else hammerControl.setValue(true);
        trashControl.setValue(false);
    }

    public void onDestroyTriangle(Box2<Triangle, String> triangle) {
        new Hammer<Triangle, String>(GRAY_BASE).destroy(triangle);
        hammerControl.setValue(false);
    }

    private void onCheckCenters() {
        List<HexagonCenter<Triangle>> centerChecked = centers.stream().filter(HexagonCenter::check).collect(Collectors.toList());
        Integer currentPoints = pointsCalculator.calculate(centerChecked.size());
        points.setValue(currentPoints + points.getValue());
        centerChecked.forEach(HexagonCenter::empty);
    }
}
