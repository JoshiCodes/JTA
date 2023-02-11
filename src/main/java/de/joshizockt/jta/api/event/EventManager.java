package de.joshizockt.jta.api.event;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.jta.ReadyEvent;
import de.joshizockt.jta.api.object.IUpdate;
import de.joshizockt.jta.api.requests.GetUpdatesRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventManager {

    private final JTA jta;
    private final long sleepTimeout;

    private final List<EventListener> listeners;

    private int offset = 0;

    private Thread thread;
    boolean first = true;

    public EventManager(JTA jta, final long sleepTimeout) {
        this.jta = jta;
        this.sleepTimeout = sleepTimeout;
        this.listeners = new ArrayList<>();
    }

    public void startFirst() {
        if(thread != null) {
            thread.interrupt();
        }
        first = true;
        thread = new Thread(this::checkForUpdates);
        thread.start();
    }

    private void checkForUpdates() {
        GetUpdatesRequest request = new GetUpdatesRequest(jta);
        request.offset(offset);
        List<IUpdate<?>> updates = jta.getRequestHandler().execute(request);
        if(first && listeners.size() > 0) {
            fireEvent(new ReadyEvent(jta));
            first = false;
        }
        if(updates == null || updates.isEmpty()) {
            sleep();
            checkForUpdates();
            return;
        }
        for (IUpdate<?> update : updates) {
            int id = update.getId();
            if(id > offset) {
                offset = id;
            }
            fireEvent(update.getUpdate());
        }
        offset++;
        sleep();
        checkForUpdates();
    }

    private void fireEvent(Event event) {
        for(EventListener listener : listeners) {
            Method[] methods = listener.getClass().getDeclaredMethods();
            for(Method m : methods) {
                if(m.isAnnotationPresent(EventHandler.class)) {
                    if(Arrays.asList(m.getParameters()[0].getType().getInterfaces()).contains(Event.class)) {
                        if( m.getParameters()[0].getType() == event.getClass() ) {
                            try {
                                m.setAccessible(true);
                                m.invoke(listener, event);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Registers a new EventListener to listen for updates
     * Can be called multiple times
     * @param listener the listener to register, must be an instance of EventListener
     */
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    private void sleep() {
        if (sleepTimeout <= 0L) return;
        try {
            Thread.sleep(sleepTimeout);
        } catch (InterruptedException ignored) {
        }
    }

}
