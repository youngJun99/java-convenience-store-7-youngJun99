package store.service;

import store.handler.InputHandler;
import store.handler.OutputHandler;

public class StoreRecieptService {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public StoreRecieptService(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }
}
