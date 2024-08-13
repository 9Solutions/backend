package com.caixadesapato.api.utils.enums;

public enum Condition {
    DISABLE(0), ENABLE(1);

    private int condition;
    Condition(int i) {
        condition = i;
    }

    public int getCondition() {
        return condition;
    }

}
