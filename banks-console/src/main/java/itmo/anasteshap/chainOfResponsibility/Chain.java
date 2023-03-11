package itmo.anasteshap.chainOfResponsibility;

import lombok.NonNull;

import java.util.Iterator;
import java.util.ListIterator;

public interface Chain {
    void process(ListIterator<String> enumerator);
    Chain addNext(@NonNull Chain nextChain);
}
