package firewoody.learnspring.database.dbexception;

import firewoody.learnspring.database.jdbc.connection.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(String memberId);
    void update(String memberId, int money);
    void delete(String memberId);
}
