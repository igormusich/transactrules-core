package com.transactrules.core.model;

public enum TransactionOperation {
    Add(1),
    Subtract(-1);

    @SuppressWarnings("unused")
    private int value = 0;

    TransactionOperation(int value) {
        this.value = value;
    }

    public int value(){
        return value;
    }

}