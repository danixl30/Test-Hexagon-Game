package com.example.testhexagongame.piece;

import com.example.testhexagongame.Color.ColorGenerator;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class PieceManagerBoard implements PieceManager<Piece> {
    private final Observer<ArrayList<Piece>> pieces = new Observer<>(new ArrayList<>());
    private final ColorGenerator colorGenerator;
    private final Function2<Integer, Integer, Integer> getRandom;
    private final ArrayList<ArrayList<Integer>> options;

    public PieceManagerBoard(ColorGenerator colorGenerator, Function2<Integer, Integer, Integer> getRandom, ArrayList<ArrayList<Integer>> options) {
        this.colorGenerator = colorGenerator;
        this.getRandom = getRandom;
        this.options = options;
        onCreatePieces();
    }

    public void onCreatePieces() {
        ArrayList<Piece> piecesLocal = pieces.getValue();
        while (piecesLocal.size() < 3) {
            piecesLocal.add(new Piece(colorGenerator, getRandom, options));
        }
        pieces.setValue(piecesLocal);
    }

    @Override
    public void deletePiece(Piece piece) {
        ArrayList<Piece> piecesLocal = pieces.getValue();
        List<Piece> newPieces = piecesLocal.stream().filter(e -> e != piece).collect(Collectors.toList());
        pieces.setValue(new ArrayList<>(newPieces));
        onCreatePieces();
    }

    @Override
    public void subscribeOnChangePiece(Function1<ArrayList<Piece>, Unit> callback) {
        pieces.subscribe(callback);
    }

    @Override
    public ArrayList<Piece> getPieces() {
        return pieces.getValue();
    }
}
