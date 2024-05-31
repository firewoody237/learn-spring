package firewoody.learnspring.advanced.app.logtrace;

import firewoody.learnspring.advanced.app.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
