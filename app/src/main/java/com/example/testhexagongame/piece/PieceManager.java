package com.example.testhexagongame.piece;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public interface PieceManager<T extends PieceI> {
    public void deletePiece(T piece);
    public void subscribeOnChangePiece(Function1<ArrayList<T>, Unit> callback);
    public ArrayList<T> getPieces();
}
