package moe.qie2035.market.server;

import fi.iki.elonen.NanoHTTPD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoundRunner implements NanoHTTPD.AsyncRunner {

    private final ExecutorService executorService;
    private final List<NanoHTTPD.ClientHandler> running =
            Collections.synchronizedList(new ArrayList<>());

    public BoundRunner(int maxThreads) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void closeAll() {
        for (NanoHTTPD.ClientHandler clientHandler : new ArrayList<>(this.running)) {
            clientHandler.close();
        }
    }

    @Override
    public void closed(NanoHTTPD.ClientHandler clientHandler) {
        this.running.remove(clientHandler);
    }

    @Override
    public void exec(NanoHTTPD.ClientHandler clientHandler) {
        executorService.submit(clientHandler);
        this.running.add(clientHandler);
    }
}
