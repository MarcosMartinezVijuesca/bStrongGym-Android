package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.domain.Member;
import java.util.List;

public interface MemberContract {

    interface View {
        void onMembersLoaded(List<Member> members);
        void onMemberSaved();
        void onMemberDeleted();
        void onError(String message);
    }

    interface Presenter {
        void loadMembers();
        void loadMembersByName(String name);
        void saveMember(Member member);
        void updateMember(long id, Member member);
        void deleteMember(long id);
    }
}