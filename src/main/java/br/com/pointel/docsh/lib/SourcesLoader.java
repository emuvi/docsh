package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SourcesLoader {

    private final File root;
    private final List<Source> sources = Collections.synchronizedList(new ArrayList<>());

    private final AtomicBoolean carryDone = new AtomicBoolean(false);
    private final Deque<File> toLoadDeque = new ConcurrentLinkedDeque<>();
    private final AtomicInteger loadersDone = new AtomicInteger(0);

    public SourcesLoader(File root) {
        this.root = root;
    }

    public List<Source> start() throws Exception {
        new Thread("Carry") {
            @Override
            public void run() {
                try {
                    carry(root);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    carryDone.set(true);
                }
            }
        }.start();
        for (int i = 0; i < LOADERS_COUNT; i++) {
            new Thread("Loader " + i) {
                @Override
                public void run() {
                    try {
                        load();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        loadersDone.incrementAndGet();
                    }
                }
            }.start();
        }
        while (!carryDone.get() || loadersDone.get() < LOADERS_COUNT) {
            WizBase.sleep(10);
        }
        return sources;
    }

    private void carry(File path) {
        if (path.isDirectory()) {
            for (File inside : path.listFiles()) {
                carry(inside);
            }
        } else {
            toLoadDeque.addFirst(path);
        }
    }

    private void load() {
        while (true) {
            var path = toLoadDeque.pollLast();
            if (path == null) {
                if (carryDone.get()) {
                    break;
                } else {
                    WizBase.sleep(10);
                    continue;
                }
            }
            var cached = CACHED.get(path);
            if (cached != null) {
                sources.add(cached);
            } else {
                try {
                    var text = new Reader(path).load();
                    if (text != null && !text.isBlank()) {
                        var source = new Source(path, text);
                        sources.add(source);
                        CACHED.put(path, source);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final static Map<File, Source> CACHED = new ConcurrentHashMap<>();
    private final static Integer LOADERS_COUNT = 4;

}
