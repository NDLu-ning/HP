package com.graduation.hp.repository.http.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.graduation.hp.core.repository.http.bean.Page;

import java.util.Date;

/**
 * desc
 *
 * @author wst
 * @date 2019/4/29 12:26
 **/
public class ArticleVO extends Page
        implements Parcelable {

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

    /**
     * 文章类型字符
     */
    private String typedStr;

    /**
     * 文章id
     */
    private Long id;

    /**
     * 作者id
     */
    private Long userId;

    /**
     * 体质id
     */
    private Long physiqueId;

    /**
     * 文章类型
     */
    private Long typedId;

    /**
     * 文章标题
     */
    private String title;

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
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 评论数
     */
    private Integer discussNum;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public ArticleVO() {
    }

    protected ArticleVO(Parcel in) {
        super(in);
        nickname = in.readString();
        headUrl = in.readString();
        physiqueStr = in.readString();
        typedStr = in.readString();
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
        if (in.readByte() == 0) {
            physiqueId = null;
        } else {
            physiqueId = in.readLong();
        }
        if (in.readByte() == 0) {
            typedId = null;
        } else {
            typedId = in.readLong();
        }
        title = in.readString();
        pic = in.readString();
        pic2 = in.readString();
        pic3 = in.readString();
        if (in.readByte() == 0) {
            likeNum = null;
        } else {
            likeNum = in.readInt();
        }
        if (in.readByte() == 0) {
            discussNum = null;
        } else {
            discussNum = in.readInt();
        }
        content = in.readString();
    }

    public static final Creator<ArticleVO> CREATOR = new Creator<ArticleVO>() {
        @Override
        public ArticleVO createFromParcel(Parcel in) {
            return new ArticleVO(in);
        }

        @Override
        public ArticleVO[] newArray(int size) {
            return new ArticleVO[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Long getPhysiqueId() {
        return physiqueId;
    }

    public void setPhysiqueId(Long physiqueId) {
        this.physiqueId = physiqueId;
    }

    public Long getTypedId() {
        return typedId;
    }

    public void setTypedId(Long typedId) {
        this.typedId = typedId;
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

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getDiscussNum() {
        return discussNum;
    }

    public void setDiscussNum(Integer discussNum) {
        this.discussNum = discussNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTypedStr() {
        return typedStr;
    }

    public void setTypedStr(String typedStr) {
        this.typedStr = typedStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(nickname);
        dest.writeString(headUrl);
        dest.writeString(physiqueStr);
        dest.writeString(typedStr);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userId);
        }
        if (physiqueId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(physiqueId);
        }
        if (typedId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(typedId);
        }
        dest.writeString(title);
        dest.writeString(pic);
        dest.writeString(pic2);
        dest.writeString(pic3);
        if (likeNum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(likeNum);
        }
        if (discussNum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(discussNum);
        }
        dest.writeString(content);
    }
}
