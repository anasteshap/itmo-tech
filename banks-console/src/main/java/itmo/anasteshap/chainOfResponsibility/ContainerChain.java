package itmo.anasteshap.chainOfResponsibility;

import lombok.NonNull;
import java.util.ListIterator;

public class ContainerChain extends BaseChain {
    private Chain headSubChain;

    public ContainerChain(String str) {
        super(str);
    }

    public Chain addSubChain(@NonNull Chain subChain) {
        if (this.headSubChain == null) {
            this.headSubChain = subChain;
        } else {
            this.headSubChain.addNext(subChain);
        }

        return subChain;
    }

    @Override
    public void process(ListIterator<String> enumerator) {
        if (!enumerator.hasNext()) {
            throw new RuntimeException("wrong command");
        }

        String cmd = "";
        try {
            cmd = enumerator.next();
        } catch (Exception ignored) {
        }

        if (!isThis(cmd)) {
            if (next != null) {
                enumerator.previous();
                next.process(enumerator);
            }
        } else {
            if (this.headSubChain != null) {
//                enumerator.iterator().remove();
                this.headSubChain.process(enumerator);
            }
        }
    }
}
