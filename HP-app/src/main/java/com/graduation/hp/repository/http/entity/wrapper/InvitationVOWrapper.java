package com.graduation.hp.repository.http.entity.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduation.hp.repository.http.entity.pojo.InvitationDiscussPO;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;

import java.util.ArrayList;

public class InvitationVOWrapper implements Parcelable {

    private InvitationVO invitationVO;
    private ArrayList<InvitationDiscussPO> invitationDiscussPOs;
    private boolean isFocusOn;

    public InvitationVOWrapper() {
    }

    public InvitationVOWrapper(InvitationVO invitationVO, ArrayList<InvitationDiscussPO> invitationDiscussPOs, boolean isFocusOn) {
        this.invitationVO = invitationVO;
        this.invitationDiscussPOs = invitationDiscussPOs;
        this.isFocusOn = isFocusOn;
    }

    protected InvitationVOWrapper(Parcel in) {
        invitationVO = in.readParcelable(InvitationVO.class.getClassLoader());
        if (in.readByte() == 0) {
            invitationDiscussPOs = new ArrayList<>();
        } else {
            invitationDiscussPOs = in.readArrayList(InvitationDiscussPO.class.getClassLoader());
        }
        isFocusOn = in.readByte() != 0;
    }

    public static final Creator<InvitationVOWrapper> CREATOR = new Creator<InvitationVOWrapper>() {
        @Override
        public InvitationVOWrapper createFromParcel(Parcel in) {
            return new InvitationVOWrapper(in);
        }

        @Override
        public InvitationVOWrapper[] newArray(int size) {
            return new InvitationVOWrapper[size];
        }
    };

    public InvitationVO getInvitationVO() {
        return invitationVO;
    }

    public void setInvitationVO(InvitationVO invitationVO) {
        this.invitationVO = invitationVO;
    }

    public ArrayList<InvitationDiscussPO> getInvitationDiscussPOs() {
        return invitationDiscussPOs;
    }

    public void setInvitationDiscussPOs(ArrayList<InvitationDiscussPO> invitationDiscussPOs) {
        this.invitationDiscussPOs = invitationDiscussPOs;
    }

    public boolean isFocusOn() {
        return isFocusOn;
    }


    public void setFocusOn(boolean focusOn) {
        isFocusOn = focusOn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(invitationVO, i);
        if (invitationDiscussPOs == null || invitationDiscussPOs.size() == 0) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeList(invitationDiscussPOs);
        }
        parcel.writeByte((byte) (isFocusOn ? 1 : 0));
    }
}
