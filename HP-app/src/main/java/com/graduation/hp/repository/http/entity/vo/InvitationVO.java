package com.graduation.hp.repository.http.entity.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.graduation.hp.core.repository.http.bean.Page;

import java.util.Date;

/**
 * t_invitation表对应实体
 *
 * @author shengting_wang
 */
public class InvitationVO extends Page
        implements Parcelable {

    /**
     * 帖子id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 体质主键
     */
    private Long physiqueId;

    /**
     * 点赞数
     */
    private Long likeNum;

    /**
     * 缩略图
     */
    private String pic;

    /**
     * 缩略图2
     */
    private String pic2;

    /**
     * 缩略图3
     */
    private String pic3;

    /**
     * 评论数
     */
    private Long discussNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 内容
     */
    private String context;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像url
     */
    private String headUrl;
    /**
     * 体质字符
     */
    private String physiqueStr;

    public InvitationVO() {
    }

    protected InvitationVO(Parcel in) {
        super(in);
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readLong();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            physiqueId = null;
        } else {
            physiqueId = in.readLong();
        }
        if (in.readByte() == 0) {
            likeNum = null;
        } else {
            likeNum = in.readLong();
        }
        pic = in.readString();
        pic2 = in.readString();
        pic3 = in.readString();
        if (in.readByte() == 0) {
            discussNum = null;
        } else {
            discussNum = in.readLong();
        }
        context = in.readString();
        nickname = in.readString();
        headUrl = in.readString();
        physiqueStr = in.readString();
    }

    public static final Creator<InvitationVO> CREATOR = new Creator<InvitationVO>() {
        @Override
        public InvitationVO createFromParcel(Parcel in) {
            return new InvitationVO(in);
        }

        @Override
        public InvitationVO[] newArray(int size) {
            return new InvitationVO[size];
        }
    };

    @JSONField(deserialize = false, serialize = false)
    public String getImages() {
        StringBuilder sb = new StringBuilder();
        String[] images = new String[3];
        images[0] = pic;
        images[1] = pic2;
        images[2] = pic3;
        for (String image : images) {
            if (!TextUtils.isEmpty(image)) {
                sb.append(image).append(",");
            }
        }
        String str = sb.toString();
        if (!TextUtils.isEmpty(str)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPhysiqueId() {
        return physiqueId;
    }

    public void setPhysiqueId(Long physiqueId) {
        this.physiqueId = physiqueId;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public Long getDiscussNum() {
        return discussNum;
    }

    public void setDiscussNum(Long discussNum) {
        this.discussNum = discussNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPhysiqueStr() {
        return physiqueStr;
    }

    public void setPhysiqueStr(String physiqueStr) {
        this.physiqueStr = physiqueStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        if (userId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(userId);
        }
        parcel.writeString(title);
        if (physiqueId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(physiqueId);
        }
        if (likeNum == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(likeNum);
        }
        parcel.writeString(pic);
        parcel.writeString(pic2);
        parcel.writeString(pic3);
        if (discussNum == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(discussNum);
        }
        parcel.writeString(context);
        parcel.writeString(nickname);
        parcel.writeString(headUrl);
        parcel.writeString(physiqueStr);
    }
}