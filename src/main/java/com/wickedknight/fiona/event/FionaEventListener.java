package com.wickedknight.fiona.event;

import java.util.EventListener;

public abstract interface FionaEventListener extends EventListener {
    public abstract void fionaActionPerformed(FionaEvent paramFionaEvent);
}
