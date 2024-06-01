package firewoody.learnspring.advanced.app.logproject.logtrace;

import firewoody.learnspring.advanced.app.logproject.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
