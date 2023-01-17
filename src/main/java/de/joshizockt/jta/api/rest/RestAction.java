package de.joshizockt.jta.api.rest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class RestAction<T> {

    private Function<Void, T> action;
    private Consumer<Exception> error;

    public RestAction(Function<Void, T> action) {
        this.action = action;
    }

    public RestAction<T> error(Consumer<Exception> error) {
        this.error = error;
        return this;
    }

    public T complete() {
        try {
            return action.apply(null);
        } catch (Exception e) {
            if(error != null) {
                error.accept(e);
            } else throw new RuntimeException(e);
            return null;
        }
    }

    public void queue() {
        queue(null);
    }

    public void queue(Consumer<T> then) {
        T t = complete();
        if(then != null) {
            then.accept(t);
        }
    }

}
