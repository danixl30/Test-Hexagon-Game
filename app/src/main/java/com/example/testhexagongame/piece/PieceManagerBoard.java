package com.example.testhexagongame.piece;

import com.example.testhexagongame.Color.ColorGenerator;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class PieceManagerBoard implements PieceManager<HexagonPiece> {
    private final Observer<ArrayList<HexagonPiece>> pieces = new Observer<>(new ArrayList<>());
    private final ColorGenerator<String> colorGenerator;
    private final Function2<Integer, Integer, Integer> getRandom;
    private final ArrayList<ArrayList<Integer>> options;
    private final DataChecker<Triangle, String, String> colorChecker;
    private final DataTransfer<Triangle, String, String> dataTransfer;

    public PieceManagerBoard(ColorGenerator<String> colorGenerator, Function2<Integer, Integer, Integer> getRandom, ArrayList<ArrayList<Integer>> options, DataChecker<Triangle, String, String> colorChecker, DataTransfer<Triangle, String, String> dataTransfer) {
        this.colorGenerator = colorGenerator;
        this.getRandom = getRandom;
        this.options = options;
        this.colorChecker = colorChecker;
        this.dataTransfer = dataTransfer;
        onCreatePieces();
    }

    public void onCreatePieces() {
        ArrayList<HexagonPiece> piecesLocal = pieces.getValue();
        while (piecesLocal.size() < 3) {
            piecesLocal.add(new HexagonPiece(colorGenerator, getRandom, options, colorChecker, dataTransfer));
        }
        pieces.setValue(piecesLocal);
    }

    @Override
    public void deletePiece(HexagonPiece piece) {
        ArrayList<HexagonPiece> piecesLocal = pieces.getValue();
        List<HexagonPiece> newPieces = piecesLocal.stream().filter(e -> e != piece).collect(Collectors.toList());
        pieces.setValue(new ArrayList<>(newPieces));
        onCreatePieces();
    }

    @Override
    public void subscribeOnChangePiece(Function1<ArrayList<HexagonPiece>, Unit> callback) {
        pieces.subscribe(callback);
    }

    @Override
    public ArrayList<HexagonPiece> getPieces() {
        return pieces.getValue();
    }
}
