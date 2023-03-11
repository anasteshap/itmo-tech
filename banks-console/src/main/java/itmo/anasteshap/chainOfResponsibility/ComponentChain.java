package itmo.anasteshap.chainOfResponsibility;

import lombok.NonNull;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Consumer;

public class ComponentChain extends BaseChain {
    @NonNull private final Consumer<Void> action;

    public ComponentChain(Consumer<Void> action, String str) {
        super(str);
        this.action = action;
    }

    @Override
    public void process(ListIterator<String> enumerator) {
        String cmd = "";
        try {
            cmd = enumerator.next();
        } catch (Exception ignored) {
        }

        if (!isThis(cmd)) {
            if (next != null) {
                enumerator.previous();
                this.next.process(enumerator);
            }
        } else {
            if (enumerator.hasNext()) {
                throw new RuntimeException("Wrong command");
            }
            this.action.accept(null);
        }
    }
}
