package itmo.anasteshap.chainOfResponsibility;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Iterator;
import java.util.ListIterator;

public abstract class BaseChain implements Chain {
    private final String str;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    protected Chain next;

    protected BaseChain(@NonNull String str) {
        this.str = str;
    }

    @Override
    public abstract void process(ListIterator<String> enumerator);

    public Chain addNext(@NonNull Chain nextChain) {
        this.next = nextChain;
        return this.next;
    }

    protected boolean isThis(String str) {
        return this.str.equals(str);
    }
}
