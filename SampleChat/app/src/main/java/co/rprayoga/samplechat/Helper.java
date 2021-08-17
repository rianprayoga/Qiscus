package co.rprayoga.samplechat;

import com.qiscus.sdk.chat.core.data.model.QiscusRoomMember;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static List<DummyData> getDummyDataList() {
        List<DummyData> dummyDataList = new ArrayList<>();
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        dummyDataList.add(new DummyData());
        return dummyDataList;
    }

    public static String getOpponent(String user, List<QiscusRoomMember> members){
        String opponent = null;
        for (QiscusRoomMember m: members) {
            if(!m.getEmail().equals(user)){
                opponent = m.getEmail();
            }
        }
        return opponent;
    }
}
