package com.wickedknight.util;

public class FulcrumSlide extends HorizontalSlide {
    int fulcrum;
    int balanceMin;
    int balanceMax;

    public FulcrumSlide() {
    }

    public FulcrumSlide(int paramInt1, int paramInt2) {
        super(paramInt1, paramInt2);
    }

    public void setFulcrum(int paramInt) {
        this.fulcrum = paramInt;
        this.balanceMin = (paramInt - 1);
        this.balanceMax = (paramInt + 1);
    }

    @Override
    public void setPos(int paramInt) {
        paramInt = paramInt < this.balanceMax ? paramInt : paramInt > this.balanceMin ? this.fulcrum : paramInt;
        super.setPos(paramInt);
    }

    @Override
    public double getPercentage() {
        int i = this.max - this.fulcrum;
        int j = this.button.getX() - this.fulcrum;
        return j / i;
    }
}
